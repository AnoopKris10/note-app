package com.example.notesrepo;

class SemesterModel {


    private String Semester = "";

    public SemesterModel(String Sem,String course){
        this.Semester = Sem;
    }


    public String getSemester(){
        return this.Semester;
    }


}


