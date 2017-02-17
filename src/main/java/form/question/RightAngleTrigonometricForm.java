package form.question;

import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import model.Problem;
import model.RightAngleTrigonometric;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightAngleTrigonometricForm extends AbstractProblemForm {

    private RightAngleTrigonometric question;
    private JLabel imageLabel;
    private JLabel questionLabel;

    private static int zoomFactor = 500;

    public RightAngleTrigonometricForm(Problem problem) {
        super(problem);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        question = (RightAngleTrigonometric) problem;

        questionLabel = new JLabel(problem.getQuestion());
        questionLabel.setFont(new Font(questionLabel.getFont().getName(), Font.BOLD, 20));
        questionLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(question.getImage(zoomFactor, true)));

        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(questionLabel);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(imageLabel);
        add(Box.createVerticalGlue());


    }

    public void increaseScaleClicked() {
        zoomFactor += 50;
        imageLabel.setIcon(new ImageIcon(question.getImage(zoomFactor, true)));

    }

    public void decreaseScaleClicked() {
        zoomFactor -= 50;
        imageLabel.setIcon(new ImageIcon(question.getImage(zoomFactor, true)));

    }
}
