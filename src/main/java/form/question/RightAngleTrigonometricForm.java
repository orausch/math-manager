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
    private JButton increaseScale;
    private JButton decreaseScale;
    private int zoomFactor = 500;
    public RightAngleTrigonometricForm(Problem problem) {
        super(problem);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        question = (RightAngleTrigonometric) problem;
        increaseScale = new JButton();
        decreaseScale = new JButton();
        increaseScale.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ZOOM_IN, 20, Color.BLACK));
        decreaseScale.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ZOOM_OUT, 20, Color.BLACK));


        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

        buttonPanel.add(increaseScale);
        buttonPanel.add(decreaseScale);
        buttonPanel.setSize(new Dimension(150, 52));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        questionLabel = new JLabel(problem.getQuestion());
        questionLabel.setFont(new Font(questionLabel.getFont().getName(), Font.BOLD, 20));
        questionLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(question.getImage(zoomFactor)));

        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(questionLabel);
        add(buttonPanel);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(imageLabel);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(Box.createVerticalGlue());

        increaseScale.addActionListener(e -> increaseScaleClicked());
        decreaseScale.addActionListener(e -> decreaseScaleClicked());

    }

    private void increaseScaleClicked() {
        zoomFactor +=50;
        imageLabel.setIcon(new ImageIcon(question.getImage(zoomFactor)));

    }

    private void decreaseScaleClicked() {
        zoomFactor -=50;
        imageLabel.setIcon(new ImageIcon(question.getImage(zoomFactor)));
    }
}
