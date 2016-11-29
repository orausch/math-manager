package form.variables;

import model.Problem;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class TrigonometricGenerateVariables extends AbstractProblemVariables {
    private JCheckBox generateValue;
    private JRadioButton findSide, findAngle;
    private String[] questionTypesLabelsAngle = {"2 Sides", "2 Angles"};
    private String[] questionTypesLabelsSide = {"2 Sides", "Side and Angle"};
    private JComboBox comboBox;

    public TrigonometricGenerateVariables() {
        setLayout(new GridLayout(3, 2));
        findAngle = new JRadioButton("Angle");
        findSide = new JRadioButton("Side");
        ButtonGroup group = new ButtonGroup();
        group.add(findAngle);
        group.add(findSide);
        findAngle.addActionListener(e -> comboBox.setModel(new DefaultComboBoxModel(questionTypesLabelsAngle)));
        findSide.addActionListener(e -> comboBox.setModel(new DefaultComboBoxModel(questionTypesLabelsSide)));
        JPanel panel = new JPanel();
        panel.add(findAngle);
        panel.add(findSide);
        add(new JLabel("Find: "));
        add(panel);
        add(new JLabel("Given: "));
        comboBox = new JComboBox(questionTypesLabelsAngle);
        comboBox.setModel(new DefaultComboBoxModel(questionTypesLabelsAngle));
        add(comboBox);
        add(new JLabel("Randomise"));
        generateValue = new JCheckBox();
        generateValue.addActionListener(e -> enableButtons(!((JCheckBox) e.getSource()).isSelected()));
        add(generateValue);

        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10), BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Difficulty")));
    }

    private void enableButtons(boolean enabled) {
        findAngle.setEnabled(enabled);
        findSide.setEnabled(enabled);
        comboBox.setEnabled(enabled);
    }


    @Override
    public Problem getProblem(String question, String answer) {
        return null;
    }

    @Override
    public Problem getProblem() {
        return null;
    }

}
