package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.TotalSubmissionsAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentMemberSubmissions

interface totalsubmissionsListener {

    fun onAssingmentSubmissionsClick(holder: TotalSubmissionsAdapter.MyViewHolder, item: GetAssingmentMemberSubmissions.MemberSubmmisionsData)

}