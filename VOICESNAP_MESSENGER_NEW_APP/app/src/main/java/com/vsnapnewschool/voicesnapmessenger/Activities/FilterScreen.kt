package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import com.vsnapnewschool.voicesnapmessenger.R

class FilterScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_screen)

        parentActionbar()
        setTitle(getString(R.string.title_Assignment))
        enableSearch(false)

    }
}
