package form;

import form.variables.QuadraticVariables;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ItemEvent;

class GenerateQuestion extends JFrame {
    private JComboBox<String> comboBox;
    private final String[] questionTypes = {"Text", "Quadratic", "Trigonometric"};
    private JPanel selectorPanel, settingPanel;

    GenerateQuestion() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        comboBox = new JComboBox<>(questionTypes);
        comboBox.addActionListener(e -> boxChanged());
        selectorPanel = new JPanel(new BorderLayout());

        selectorPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        selectorPanel.add(new JLabel("Question type: "), BorderLayout.WEST);
        selectorPanel.add(comboBox, BorderLayout.EAST);
        add(selectorPanel, BorderLayout.NORTH);

        JPanel textpanel = new TextPanel();
        add(textpanel, BorderLayout.CENTER);
        settingPanel = new JPanel();
        setResizable(true);
        setTitle("New Question");
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class TextPanel extends JPanel {
        private JLabel questionLabel, answerLabel, generateLabel;
        private JTextField questionField, answerField;
        private JCheckBox generateValues;

        private TextPanel() {
            questionLabel = new JLabel("Question: ");
            answerLabel = new JLabel("Answer: ");
            questionField = new JTextField();
            answerField = new JTextField();
            generateLabel = new JLabel("Generate Values: ");
            generateValues = new JCheckBox();
            generateValues.setSelected(false);

            setLayout(new GridLayout(3, 1));
            add(questionLabel);
            add(questionField);
            add(answerLabel);
            add(answerField);
            add(generateLabel);
            add(generateValues);
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10), BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Text")));
            generateValues.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    setVariablesEnabled(false);

                }
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    setVariablesEnabled(true);
                }
            });
        }

        private void setVariablesEnabled(boolean enabled) {
            questionField.setEnabled(enabled);
            answerField.setEnabled(enabled);
            answerLabel.setEnabled(enabled);
            questionLabel.setEnabled(enabled);

        }
    }

    private void boxChanged() {
        remove(settingPanel);
        switch ((String) comboBox.getSelectedItem()) {
            case "Quadratic":
                settingPanel = new QuadraticVariables();
                add(settingPanel, BorderLayout.SOUTH);
                break;
            case "Trigonometric":
                break;
            case "Text":
                break;
        }
        settingPanel.revalidate();
        settingPanel.repaint();
        pack();
    }
}
