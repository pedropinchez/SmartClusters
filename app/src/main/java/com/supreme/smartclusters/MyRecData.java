package com.supreme.smartclusters;


public class MyRecData {

    private String unicode;
    private String progcode;
    private String course;
    private String cutofftwo;
    private  String mycluster;


    public MyRecData() {
    }

    public MyRecData(String unicode, String course, String cutofftwo, String progcode, String mycluster) {
        this.progcode = progcode;
        this.mycluster=mycluster;
        this.unicode = unicode;
        this.course = course;
        this.cutofftwo = cutofftwo;


    }


    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public String getProgcode() {
        return progcode;
    }

    public void setProgcode(String progcode) {
        this.progcode = progcode;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCutofftwo() {
        return cutofftwo;
    }

    public void setCutofftwo(String cutofftwo) {
        this.cutofftwo = cutofftwo;
    }

    public String getMycluster() {
        return mycluster;
    }

    public void setMycluster(String mycluster) {
        this.mycluster = mycluster;
    }
}

