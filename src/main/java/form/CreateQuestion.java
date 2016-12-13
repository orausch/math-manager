package form;

import form.variables.AbstractProblemVariables;
import form.variables.QuadraticCreateVariables;
import form.variables.QuadraticGenerateVariables;
import form.variables.TrigonometricGenerateVariables;
import model.Problem;
import model.Text;
import module.DatabaseManager;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class CreateQuestion extends JFrame {
    private final String[] questionGenerateTypes = {"Quadratic", "Trigonometric"};
    private final String[] questionCreateTypes = {"Text", "Quadratic", "Trigonometric"};

    private JPanel generateTabPanel, createTabPanel;
    private JPanel settingPanel;
    private JTabbedPane pane;
    private JButton submitButton;
    private TextPanel textpanelGenerate, textpanelCreate;
    private JPanel comboPanelGenerate, comboPanelCreate;

    CreateQuestion() {
        setLayout(new BorderLayout());
        pane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        initGenerateUI();
        initCreateUI();
        add(pane, BorderLayout.NORTH);
        submitButton = new JButton("Add Question");
        submitButton.addActionListener(e -> {
            if (pane.getSelectedIndex() == 0) {
                Problem problem;
                if (textpanelGenerate.getGenerateValues()) {
                    problem = ((AbstractProblemVariables) settingPanel).getProblem();
                } else {
                    problem = ((AbstractProblemVariables) settingPanel).getProblem(textpanelGenerate.getQuestion(), textpanelGenerate.getQuestion());
                }
                DatabaseManager db = new DatabaseManager();
                db.insertProblem(problem);
                db.close();
            } else if (pane.getSelectedIndex() == 1) {
                DatabaseManager db = new DatabaseManager();
                switch ((String) ((JComboBox<String>) comboPanelCreate.getComponent(1)).getSelectedItem()) {
                    case "Text":
                        db.insertProblem(new Text(textpanelCreate.getQuestion(), textpanelCreate.getAnswer()));
                        break;
                    case "Quadratic":
                        if (textpanelCreate.getGenerateValues()) {
                            db.insertProblem(((AbstractProblemVariables) settingPanel).getProblem());
                        } else {
                            db.insertProblem(((AbstractProblemVariables) settingPanel).getProblem(textpanelCreate.getQuestion(), textpanelCreate.getQuestion()));
                        }
                        break;
                    case "Trigonometric":
                        if (textpanelCreate.getGenerateValues()) {
                            db.insertProblem(((AbstractProblemVariables) settingPanel).getProblem());
                        } else {
                            db.insertProblem(((AbstractProblemVariables) settingPanel).getProblem(textpanelCreate.getQuestion(), textpanelCreate.getQuestion()));
                        }
                        break;
                }
                db.close();
            }
        });
        setMinimumSize(new Dimension(261, 247));

        add(submitButton, BorderLayout.SOUTH);
        setResizable(true);
        setTitle("New Question");
        pack();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                Start.show();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }

        });
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initGenerateUI() {
        generateTabPanel = new JPanel(new BorderLayout());
        comboPanelGenerate = getSelectorPanel(true);
        generateTabPanel.add(comboPanelGenerate, BorderLayout.NORTH);

        textpanelGenerate = new TextPanel(true, false);
        generateTabPanel.add(textpanelGenerate, BorderLayout.CENTER);
        settingPanel = new QuadraticGenerateVariables();
        generateTabPanel.add(settingPanel, BorderLayout.SOUTH);
        pane.addTab("Generate Question", generateTabPanel);
    }

    private void initCreateUI() {
        createTabPanel = new JPanel(new BorderLayout());
        comboPanelCreate = getSelectorPanel(false);

        createTabPanel.add(comboPanelCreate, BorderLayout.NORTH);
        textpanelCreate = new TextPanel(false, true);
        createTabPanel.add(textpanelCreate, BorderLayout.CENTER);
        pane.addTab("Create Question", createTabPanel);

    }

    private void close() {

    }

    private class TextPanel extends JPanel {
        private JLabel questionLabel, answerLabel, generateLabel;
        private JTextField questionField, answerField;
        private JCheckBox generateValues;

        private String getQuestion() {
            return questionField.getText();
        }

        private String getAnswer() {
            return answerField.getText();
        }

        private boolean getGenerateValues() {
            return generateValues.isSelected();
        }

        private TextPanel(boolean showGenerateCheckBox, boolean showAnswerField) {
            int rows = 3;
            if (!showAnswerField)
                rows--;
            if (!showGenerateCheckBox)
                rows--;
            setLayout(new GridLayout(rows, 2));
            questionLabel = new JLabel("Question: ");
            answerLabel = new JLabel("Answer: ");
            questionField = new JTextField();
            answerField = new JTextField();
            generateValues = new JCheckBox();

            if (showGenerateCheckBox) {
                generateLabel = new JLabel("Generate Values: ");
                generateValues.setSelected(false);
            }
            add(questionLabel);
            add(questionField);
            if (showAnswerField) {
                add(answerLabel);
                add(answerField);
            }
            if (showGenerateCheckBox) {
                add(generateLabel);
                add(generateValues);
                generateValues.addItemListener(e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        setVariablesEnabled(false);

                    }
                    if (e.getStateChange() == ItemEvent.DESELECTED) {
                        setVariablesEnabled(true);
                    }
                });
            }

            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10), BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Text")));

        }

        private void setVariablesEnabled(boolean enabled) {
            questionField.setEnabled(enabled);
            answerField.setEnabled(enabled);
            answerLabel.setEnabled(enabled);
            questionLabel.setEnabled(enabled);

        }
    }

    private void generateBoxChanged(ActionEvent e) {
        generateTabPanel.remove(settingPanel);
        switch ((String) ((JComboBox) e.getSource()).getSelectedItem()) {
            case "Quadratic":
                settingPanel = new QuadraticGenerateVariables();
                generateTabPanel.add(settingPanel, BorderLayout.SOUTH);

                break;
            case "Trigonometric":
                settingPanel = new TrigonometricGenerateVariables();
                generateTabPanel.add(settingPanel, BorderLayout.SOUTH);
                break;
        }
        settingPanel.revalidate();
        settingPanel.repaint();
        pack();
    }

    private void createBoxChanged(ActionEvent e) {
        createTabPanel.remove(settingPanel);
        createTabPanel.remove(textpanelCreate);
        switch ((String) ((JComboBox) e.getSource()).getSelectedItem()) {
            case "Quadratic":
                settingPanel = new QuadraticCreateVariables();
                createTabPanel.add(settingPanel, BorderLayout.SOUTH);
                textpanelCreate = new TextPanel(true, false);
                createTabPanel.add(textpanelCreate, BorderLayout.CENTER);
                break;
            case "Trigonometric":
                break;
            case "Text":
                createTabPanel.remove(textpanelCreate);
                textpanelCreate = new TextPanel(false, true);
                createTabPanel.add(textpanelCreate, BorderLayout.CENTER);
                break;
        }
        textpanelCreate.revalidate();
        textpanelCreate.repaint();
        settingPanel.revalidate();
        settingPanel.repaint();
        pack();
    }

    private JPanel getSelectorPanel(boolean isGenerate) {
        JPanel selectorPanel = new JPanel(new BorderLayout());
        JComboBox<String> comboBox = new JComboBox<>(isGenerate ? questionGenerateTypes : questionCreateTypes);
        if (isGenerate)
            comboBox.addActionListener(e -> generateBoxChanged(e));
        else
            comboBox.addActionListener(e -> createBoxChanged(e));

        selectorPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        selectorPanel.add(new JLabel("Question type: "), BorderLayout.WEST);
        selectorPanel.add(comboBox, BorderLayout.EAST);
        selectorPanel.setPreferredSize(new Dimension(256, 45));
        return selectorPanel;
    }
}
