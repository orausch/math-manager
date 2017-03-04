package form;

import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import model.Problem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * The Form used to input and check all answers in the QuestionViewer
 */
class AnswerForm extends JPanel {
    private JButton submitButton, showButton;
    private JTextField answerField;
    private Problem problem;

    /**
     * Initialise a new answer form
     *
     * @param problem the parent problem
     */
    AnswerForm(Problem problem) {
        super(new GridLayout(1, 3));
        this.problem = problem;
        submitButton = new JButton("Submit");
        showButton = new JButton("Show Answer");

        answerField = new JTextField("Enter answer");
        answerField.setForeground(Color.gray);
        answerField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (answerField.getText().equals("Enter answer")) {
                    answerField.setText("");
                    answerField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (answerField.getText().equals("")) {
                    answerField.setForeground(Color.darkGray);
                    answerField.setText("Enter answer");
                }
            }
        });
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());

        submitButton.addActionListener(e -> {
            if (isAnswerCorrect() != null) {
                if (isAnswerCorrect()) {
                    JOptionPane.showMessageDialog(null, "The answer is correct", "Correct", JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.CHECK, 32, new Color(0, 150, 0)));
                } else if (!isAnswerCorrect()) {
                    JOptionPane.showMessageDialog(null, "The answer is wrong", "Incorrect", JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.CLEAR, 32, new Color(255, 0, 19)));
                }
            }
        });
        showButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "The answer is: " + problem.getAnswer(), "Answer", JOptionPane.INFORMATION_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.IMPORT_CONTACTS, 32, new Color(179, 138, 47))));
        add(answerField);
        add(submitButton);
        add(showButton);
    }

    /**
     * Check if the answer is correct or incorrect
     *
     * @return if the text in the answerField matches the problem's answer
     */
    private Boolean isAnswerCorrect() {
        try {
            String input = answerField.getText().replaceAll("\\.0", "");
            if (input.equals("Enter answer")) {
                return null;
            }
            return problem.checkAnswer(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter numbers only", "Error", JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ERROR_OUTLINE, 32, new Color(255, 0, 19)));
            return null;
        }
    }

}
