package com.supreme.smartclusters;


import com.google.firebase.database.annotations.Nullable;

public class MyListData {

    private String uniCode;
    private String progCode;
    private String courseName;
    private String cutoffOne;
    private String cutoffTwo;

    public MyListData() {
    }

    public MyListData(String uniCode, String courseName, String cutoffOne, String progCode, String cutoffTwo) {
        this.progCode = progCode;
        this.cutoffTwo = cutoffTwo;
        this.uniCode = uniCode;
        this.courseName = courseName;
        this.cutoffOne = cutoffOne;


    }

    public String getUniCode() {
        return uniCode;
    }

    public void setUniCode(String uniCode) {
        this.uniCode = uniCode;
    }

    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }





    public String getCutoffTwo() {
        return cutoffTwo;
    }

    public void setCutoffTwo(String cutoffTwo) {
        this.cutoffTwo = cutoffTwo;
    }

    public String getCutoffOne() {
        return cutoffOne;
    }

    public void setCutoffOne(String cutoffOne) {
        this.cutoffOne = cutoffOne;
    }
}

