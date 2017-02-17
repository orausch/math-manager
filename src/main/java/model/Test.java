package model;

import java.util.Date;
import java.util.LinkedList;

public class Test {
    private LinkedList<Problem> questions;
    private String name;
    private int id;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Test(LinkedList<Problem> questions, String name) {
        this.questions = questions;
        this.name = name;
    }

    public Test(String name, String date) {
        this.name = name;
        this.date = date;
        this.questions = new LinkedList<>();
    }

    public LinkedList<Problem> getQuestions() {
        return questions;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString(){
        return name;
    }

    public void setQuestions(LinkedList<Problem> questions) {
        this.questions = questions;
    }
}
