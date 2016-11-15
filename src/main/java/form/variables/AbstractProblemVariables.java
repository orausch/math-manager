package form.variables;

import model.Problem;

import javax.swing.*;

public abstract class AbstractProblemVariables extends JPanel {

    public abstract Problem getProblem(String question, String answer);
    public abstract Problem getProblem();

}
