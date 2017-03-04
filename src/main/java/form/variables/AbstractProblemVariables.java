package form.variables;

import model.Problem;

import javax.swing.*;

public abstract class AbstractProblemVariables extends JPanel {
    /**
     * Get the problem from the form
     *
     * @param question the text in the question field
     * @param answer   the text in the answer field (if one exists)
     * @return the generated/created problem
     */
    public abstract Problem getProblem(String question, String answer);
    public abstract Problem getProblem();

}
