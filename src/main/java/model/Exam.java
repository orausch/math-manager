package model;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by oliver on 20/10/16.
 */
public class Exam {
    private LinkedList<Problem> questions;
    private String name;
    private Date creationDate;

    public Exam(LinkedList<Problem> questions, String name, Date creationDate) {
        this.questions = questions;
        this.name = name;
        this.creationDate = creationDate;
    }

    public Exam(String name, Date creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }
}
