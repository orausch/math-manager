package form.question;


import model.Problem;

import javax.swing.*;

public abstract class AbstractProblemForm extends JPanel {
    private Problem problem;

    AbstractProblemForm(Problem problem) {
        this.problem = problem;
    }
}
