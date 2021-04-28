package com.vsnapnewschool.voicesnapmessenger.Interfaces;


import com.vsnapnewschool.voicesnapmessenger.Models.CheckListClass;

public interface RecipientListener {
    public void check(CheckListClass menu);

    public void uncheck(CheckListClass menu);
}
