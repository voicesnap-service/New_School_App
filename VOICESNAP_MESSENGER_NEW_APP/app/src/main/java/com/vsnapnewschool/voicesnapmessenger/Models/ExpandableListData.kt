package com.vsnapnewschool.voicesnapmessenger.Models


internal object ExpandableListData {
    val data: HashMap<String, List<String>>
        get() {
            val expandableListDetail =
                HashMap<String, List<String>>()

            val myFavFootballPlayers: MutableList<String> = ArrayList()
            myFavFootballPlayers.add("Bus fee")
            myFavFootballPlayers.add("Tution  Fee")
            myFavFootballPlayers.add("Exam Fee")

            val myFavTennisPlayers: MutableList<String> = ArrayList()
            myFavTennisPlayers.add("Lab Fee ")
            myFavTennisPlayers.add("Sports Fee ")
            myFavTennisPlayers.add("Coaching Fee")

            expandableListDetail["Term Fee1"] = myFavFootballPlayers
            expandableListDetail["Term Fee 2"] = myFavTennisPlayers
            return expandableListDetail
        }
}