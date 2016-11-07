package form.question;


import model.Problem;

import javax.swing.*;

public abstract class ProblemForm extends JPanel {
    private Problem problem;

    public ProblemForm(Problem problem) {
        this.problem = problem;
    }
}
