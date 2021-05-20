package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.SectionWiseStrengthAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetSectionWiseStrength

interface sectionStrengthListener {

    fun onSectionStrength(holder: SectionWiseStrengthAdapter.MyViewHolder, item: GetSectionWiseStrength.SectionStrengthData)

}