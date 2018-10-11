package com.example.junemon.travelroutine.feature.tags.inputTags

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.base.BaseActivity
import com.example.junemon.travelroutine.database.model.PersonalTags
import com.example.junemon.travelroutine.helper.KeyboardCloser
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.maltaisn.icondialog.Icon
import com.maltaisn.icondialog.IconDialog
import kotlinx.android.synthetic.main.activity_input_tag.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.design.snackbar


class InputTagActivity : BaseActivity(), InputTagView, IconDialog.Callback, ColorPickerDialogListener {


    private var selectedIcons: Array<Icon>? = null
    private var iconDialog = IconDialog()
    private lateinit var presenter: InputTagPresenter
    private var actualSelectedTag: String? = null
    private var actualSelectedColor: Int? = null
    private var actualSelectedIcon: Int? = null
    private var views: View? = null
    private var tagData: PersonalTags = PersonalTags()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        setContentView(R.layout.activity_input_tag)

        initListener()
        presenter = InputTagPresenter(this)
        presenter.onCreate(this)
        views = getRootView()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_input_tag, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
                true
            }
            R.id.saveAllData -> {
                if (etInputTags.text.isNullOrEmpty()) {
                    etInputTags?.error = "Please fill this first"
                    KeyboardCloser.hideKeyboard(this)
                } else if (!etInputTags.text.isNullOrEmpty()) {
                    actualSelectedTag = etInputTags.text.toString().trim()
                    savingAllData(actualSelectedTag, actualSelectedColor, actualSelectedIcon)
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initListener() {
        ivPickingIcon.setOnClickListener {
            iconDialog.setSelectedIcons(0)
            iconDialog.show(getSupportFragmentManager(), "test")
        }

        ivPickingColors.setOnClickListener {
            ColorPickerDialog.newBuilder()
                    .setColor(Color.BLACK)
                    .setDialogType(ColorPickerDialog.TYPE_CUSTOM).show(this);

        }
    }

    override fun onIconDialogIconsSelected(icons: Array<Icon>?) {
        selectedIcons = icons
        actualSelectedIcon = selectedIcons?.get(0)?.id
        ivPickingIcon.setImageDrawable(selectedIcons?.get(0)?.getDrawable(this))
    }

    override fun onDialogDismissed(dialogId: Int) {
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        ivBackIcon.backgroundColor = color
        ivBackColor.backgroundColor = color
        actualSelectedColor = color
    }

    override fun onSuccesSaveData(views: View) {
        snackbar(views, getString(R.string.saved))
    }

    override fun onSuccesUpdateData(views: View) {
        snackbar(views, getString(R.string.updated))
    }

    private fun savingAllData(tag: String?, colors: Int?, iconized: Int?) {
        if (tag == null && colors == null && iconized == null) {
            snackbar(views!!, R.string.null_alerts)
        } else {
            tagData.newTags = tag
            tagData.newColor = colors
            tagData.newIcons = iconized
            presenter.addNewTag(tagData, views)
            finish()
        }
    }

    private fun resetState() {
        KeyboardCloser.hideKeyboard(this)
        etInputTags.text = Editable.Factory.getInstance().newEditable("")
        ivBackIcon.backgroundColor = Color.WHITE
        ivBackColor.backgroundColor = Color.WHITE
        ivPickingIcon.setImageDrawable(getDrawable(R.drawable.ic_rowing_black_24dp))
    }

    override fun onDestroy() {
        super.onDestroy()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

//    fun getRootView(): View? {
//        val contentViewGroup = findViewById<View>(android.R.id.content) as ViewGroup
//        var rootView: View? = null
//
//        if (contentViewGroup != null)
//            rootView = contentViewGroup.getChildAt(0)
//
//        if (rootView == null)
//            rootView = window.decorView.rootView
//
//        return rootView
//    }
}