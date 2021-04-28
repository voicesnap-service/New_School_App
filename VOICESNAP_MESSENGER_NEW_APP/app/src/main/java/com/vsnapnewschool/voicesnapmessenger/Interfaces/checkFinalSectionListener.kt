package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Models.SelecetedPreviewSection


interface checkFinalSectionListener {
    fun addFinalSection(sections: SelecetedPreviewSection?)
    fun removeFinalSection(sections: SelecetedPreviewSection?)
}