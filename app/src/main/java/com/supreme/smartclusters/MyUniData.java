package com.supreme.smartclusters;


public class MyUniData {

    private String uniCode;
    private String courseName;


    public MyUniData() {
    }

    public MyUniData(String uniCode, String courseName) {
        this.uniCode = uniCode;
        this.courseName = courseName;



    }

    public String getUniCode() {
        return uniCode;
    }

    public void setUniCode(String uniCode) {
        this.uniCode = uniCode;
    }



    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

}

