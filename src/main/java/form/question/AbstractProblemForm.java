package form.question;


import model.Problem;

import javax.swing.*;

/**
 * This is the Form that is used to display the problem in the QuestionViewer
 */
public abstract class AbstractProblemForm extends JPanel {

    private Problem problem;

    /**
     * Initialise the form
     *
     * @param problem The problem that is being displayed
     */

    AbstractProblemForm(Problem problem) {
        this.problem = problem;
    }

    /**
     * Get the problem that belongs to this form
     *
     * @return the problem
     */
    public Problem getProblem() {
        return problem;
    }
}
