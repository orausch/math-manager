package model;

/**
 * The model class for a problem. All types of problems MUST inherit from this class in order to function with the data-
 * base and the rest of the program.
 */
public abstract class Problem {

    private int id;

    /**
     * Get the ID of a problem
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of a problem
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the answer of a problem
     *
     * @return the answer in text form
     */
    public abstract String getAnswer();

    /**
     * Get the question of a problem
     * @return the question in text form
     */
    public abstract String getQuestion();

    /**
     * Check the answer of a problem. This should contain all logic for the QuestionViewer answer checking feature as
     * well as data trimming, etc.
     * @param input the user input answer
     * @return if the answer is correct or not
     */
    public abstract boolean checkAnswer(String input);
}
