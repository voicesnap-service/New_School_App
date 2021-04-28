package com.vsnapnewschool.voicesnapmessenger.Interfaces
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentHomeGridAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.SchoolHomeGridAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MenuListData

interface schoolHomeMenuClicklistener {
    fun onClick(holder: SchoolHomeGridAdapter.QuestionViewHolder, item: MenuListData)
}