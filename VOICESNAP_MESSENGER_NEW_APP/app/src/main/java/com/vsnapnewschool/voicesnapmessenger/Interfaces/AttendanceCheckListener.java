package com.vsnapnewschool.voicesnapmessenger.Interfaces;


import com.vsnapnewschool.voicesnapmessenger.Models.AttendanceClass;

public interface AttendanceCheckListener {
    public void check(AttendanceClass menu);

    public void uncheck(AttendanceClass menu);
}
