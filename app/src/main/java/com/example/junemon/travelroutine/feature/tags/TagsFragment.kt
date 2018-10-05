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
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.alert


class TagsFragment : Fragment(), TagsView {
    private var ctx: Context? = null
    private var actualView: View? = null
    lateinit var presenter: TagsPresenter
    lateinit var update: EditText
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
    }

    override fun getData(data: List<PersonalTags>?) {
        rvNewTag.layoutManager = LinearLayoutManager(ctx)
        rvNewTag.adapter = TagsAdapter(ctx, data!!) {
            alert("Update Your Tag ") {
                customView {
                    verticalLayout {
                        update = editText() {
                            hint = "New Tag"
                            background = null
                            inputType = InputType.TYPE_CLASS_TEXT
                            text = Editable.Factory.getInstance().newEditable(it.newTags)
                            tagData = it
                        }
                    }
                }
                yesButton {
                    tagData.newTags = update.text.toString().trim()
                    presenter.updateTag(tagData)
                    snackbar(actualView!!, "${tagData.newTags}")
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