package form.question;

import model.Problem;

import javax.swing.*;
import java.awt.*;

public class TextForm extends AbstractProblemForm {
    private JLabel questionLabel;

    public TextForm(Problem problem) {
        super(problem);
        questionLabel = new JLabel(problem.getQuestion());
        questionLabel.setFont(new Font(questionLabel.getFont().getName(), Font.BOLD, 20));
        add(questionLabel);
    }

}
