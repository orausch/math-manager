package form;

import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import model.Problem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AnswerForm extends JPanel {
    private JButton submitButton, showButton;
    private JTextField answerField;
    private Problem problem;

    public AnswerForm(Problem problem) {
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
            if (isAnswerCorrect()) {
//                    JOptionPane.showMessageDialog(null, "The answer is wrong", "Answer", JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ADD, 20, new Color(0, 150, 0)));
                System.out.println("right");
            } else {
                System.out.println("wrong");
            }
        });
        showButton.addActionListener(e -> System.out.println(problem.getAnswer()));
        add(answerField);
        add(submitButton);
        add(showButton);
    }

    private boolean isAnswerCorrect() {
        try {
            String input = answerField.getText().replaceAll("\\.0", "");
            if (input.equals("Enter answer")) {
                return false;
            }
            return problem.checkAnswer(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter numbers only");
            return false;
        }
    }

}
