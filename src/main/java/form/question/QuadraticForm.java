package form.question;

import model.Quadratic;
import util.Utility;

import javax.swing.*;
import java.awt.*;

public class QuadraticForm extends AbstractProblemForm {

    private JLabel questionLabel;

    public QuadraticForm(Quadratic quadratic) {
        super(quadratic);
        questionLabel = new JLabel(Utility.superscript(quadratic.getQuestion()));
        questionLabel.setFont(new Font(questionLabel.getFont().getName(), Font.BOLD, 20));
        add(questionLabel);
    }

}
