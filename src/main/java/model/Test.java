package model;

import java.util.Date;
import java.util.LinkedList;

public class Test {
    private LinkedList<Problem> questions;
    private String name;
    private int id;

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

    public Test(String name) {
        this.name = name;
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
}
