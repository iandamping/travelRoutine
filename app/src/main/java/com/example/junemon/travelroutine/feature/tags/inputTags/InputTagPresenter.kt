package com.example.junemon.travelroutine.feature.tags.inputTags

import android.content.Context
import android.view.View
import com.example.junemon.travelroutine.base.BasePresenters
import com.example.junemon.travelroutine.database.model.PersonalTags
import com.example.junemon.travelroutine.repositories.Tags.TagsRepositories

class InputTagPresenter(var mView: InputTagView) : BasePresenters {
    lateinit var ctx: Context

    override fun getContext(): Context? {
        return ctx
    }

    override fun onCreate(context: Context) {
        this.ctx = context
    }

    fun addNewTag(newTag: PersonalTags, view: View?) {
        TagsRepositories.addNewTag(newTag, ctx, view!!)
        view.let { mView.onSuccesSaveData(it) }
    }

    fun updateTag(updateTag: PersonalTags, view: View?) {
        TagsRepositories.updateTag(updateTag, ctx, view!!)
        view.let { mView.onSuccesUpdateData(it) }
    }

    override fun onPause() {
    }
}