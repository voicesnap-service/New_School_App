package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentHomeGridAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MenuListData

interface parentHomeMenuClickListener {
    fun onClick(holder: ParentHomeGridAdapter.QuestionViewHolder, item: MenuListData)

}