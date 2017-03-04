package model;

import java.util.LinkedList;

public class Test {
    private LinkedList<Problem> questions;
    private String name;
    private int id;
    private String date;

    /**
     * Get the date of the Test
     *
     * @return the date in text form
     */
    public String getDate() {
        return date;
    }

    /**
     * Get the ID of the Test
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the Test
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Initialise a new Test
     *
     * @param questions list of all questions in the test, in the order they appear in
     * @param name      name of the test
     * @param date      date of the test
     */
    public Test(LinkedList<Problem> questions, String name, String date) {
        this.questions = questions;
        this.name = name;
        this.date = date;
    }

    /**
     * Initialise a new Test
     *
     * @param name name of the test
     * @param date date of the test
     */
    public Test(String name, String date) {
        this.name = name;
        this.date = date;
        this.questions = new LinkedList<>();
    }

    /**
     * Get a list of the questions in the test
     * @return the questions in the test
     */
    public LinkedList<Problem> getQuestions() {
        return questions;
    }

    /**
     * Get the name of the test
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
