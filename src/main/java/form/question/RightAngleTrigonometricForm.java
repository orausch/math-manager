package form.question;

import model.Problem;
import model.RightAngleTrigonometric;

import javax.swing.*;
import java.awt.*;

public class RightAngleTrigonometricForm extends AbstractProblemForm {

    private RightAngleTrigonometric question;
    private JLabel imageLabel;
    private JLabel questionLabel;

    public RightAngleTrigonometricForm(Problem problem) {
        super(problem);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        question = (RightAngleTrigonometric) problem;
        imageLabel = new JLabel();
        questionLabel = new JLabel(problem.getQuestion());
        questionLabel.setFont(new Font(questionLabel.getFont().getName(), Font.BOLD, 20));
        imageLabel.setIcon(new ImageIcon(question.getImage()));
        add(questionLabel);
        add(imageLabel);
    }

}
