package com.example.junemon.travelroutine.feature.tags

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalTags
import kotlinx.android.synthetic.main.activity_adding_tag.*
import kotlinx.android.synthetic.main.activity_adding_tag.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.alert


class TagsFragment : Fragment(), TagsView {
    private var ctx: Context? = null
    private var actualView: View? = null
    lateinit var presenter: TagsPresenter
    lateinit var update: EditText
    lateinit var insert: EditText
    var tagData: PersonalTags = PersonalTags()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getLiveDataAllTag(this)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        presenter = TagsPresenter(this)
        presenter.onAttach(ctx)
        initiateFirst(ctx)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views: View = LayoutInflater.from(ctx).inflate(R.layout.activity_adding_tag, container, false)
        presenter.onCreateView(views)
        return views

    }

    override fun initView(view: View) {
        actualView = view
        actualView?.fabNewTags?.setOnClickListener {
            alert(resources.getString(R.string.insert_update_tag)) {
                customView {
                    verticalLayout {
                        insert = editText() {
                            hint = resources.getString(R.string.alert_edittext_hint)
                            background = null
                            maxLines = 1
                            inputType = InputType.TYPE_CLASS_TEXT
                        }
                    }
                }
                yesButton {
                    if (insert.text.isNullOrEmpty()) {
                        snackbar(actualView!!, R.string.null_alerts)
                    } else if (!insert.text.isNullOrEmpty()) {
                        tagData.newTags = insert.text.toString().trim()
                        presenter.addNewTag(tagData, actualView!!)
                    }
                }
                noButton {}
            }.show()
        }
    }

    override fun getData(data: List<PersonalTags>?) {
        rvNewTag.layoutManager = LinearLayoutManager(ctx)
        rvNewTag.adapter = TagsAdapter(ctx, data!!) { it ->
            alert(resources.getString(R.string.alert_update_tag)) {
                customView {
                    verticalLayout {
                        update = editText() {
                            hint = resources.getString(R.string.alert_edittext_hint)
                            background = null
                            maxLines = 1
                            inputType = InputType.TYPE_CLASS_TEXT
                            text = Editable.Factory.getInstance().newEditable(it.newTags)
                            tagData = it
                        }
                    }
                }
                yesButton {
                    if (update.text.isNullOrEmpty()) {
                        snackbar(actualView!!, R.string.null_alerts)
                    } else if (!update.text.isNullOrEmpty()) {
                        tagData.newTags = update.text.toString().trim()
                        presenter.updateTag(tagData, actualView!!)
                    }
                }
                noButton {}
            }.show()

        }
    }


    private fun initiateFirst(ctx: Context?) {
        val prefs = ctx?.getSharedPreferences(TagsPresenter.DEVICE_TOKEN, MODE_PRIVATE)
        val pageNumber = prefs?.getInt(TagsPresenter.KEY, 0)

        if (pageNumber == 0) {
            presenter.initFirstItemTag(ctx)
        }
    }


}