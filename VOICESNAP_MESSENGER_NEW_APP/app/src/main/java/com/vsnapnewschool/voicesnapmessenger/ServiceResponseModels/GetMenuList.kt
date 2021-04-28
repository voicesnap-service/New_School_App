package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GetMenuList(
    val `data`: List<MenuListData>,
    val message: String,
    val status: Int
)

data class MenuListData(
    val color_code: String,
    val id: String,
    val module: String,
    val name: String
)