package form.question;

import model.Quadratic;
import module.Util;

import javax.swing.*;
import java.awt.*;

public class QuadraticForm extends AbstractProblemForm {

    private JLabel questionLabel;

    public QuadraticForm(Quadratic quadratic) {
        super(quadratic);
        setLayout(new GridLayout(0, 1));
        questionLabel = new JLabel(Util.superscript(quadratic.getQuestion()));
        questionLabel.setFont(new Font(questionLabel.getFont().getName(), Font.BOLD, 20));
        add(questionLabel);
    }

}
