package form.variables;

import factory.QuadraticProblemFactory;
import model.Problem;
import model.Quadratic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Hashtable;

public class QuadraticGenerateVariables extends AbstractProblemVariables {

    private JSlider difficultySelector;
    private JCheckBox isCustomDifficulty;
    private JSpinner aSpinner, bSpinner, xSpinner;
    private JLabel aLabel, xLabel, bLabel;

    public QuadraticGenerateVariables() {
        setLayout(new GridLayout(2, 1));
        difficultySelector = new JSlider(JSlider.HORIZONTAL, 0, 3, 1);
        difficultySelector.setMinorTickSpacing(1);
        difficultySelector.setPaintTicks(true);
        difficultySelector.setPreferredSize(new Dimension(350, 50));

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("Easy"));
        labelTable.put(1, new JLabel("Medium"));
        labelTable.put(2, new JLabel("Hard"));
        labelTable.put(3, new JLabel("Calculator"));
        difficultySelector.setLabelTable(labelTable);
        difficultySelector.setPaintLabels(true);
        add(difficultySelector);

        isCustomDifficulty = new JCheckBox();

        aSpinner = new JSpinner(new SpinnerNumberModel(VAL[1][0], 1, 50, 1));
        bSpinner = new JSpinner(new SpinnerNumberModel(VAL[1][1], 1, 50, 1));
        xSpinner = new JSpinner(new SpinnerNumberModel(VAL[1][2], 1, 50, 1));

        JPanel spinnersLabels = new JPanel(new FlowLayout());
        spinnersLabels.add(new JLabel("Use Custom Ranges:"));
        spinnersLabels.add(isCustomDifficulty);

        JPanel spinners = new JPanel(new FlowLayout());

        aLabel = new JLabel("A: ");
        bLabel = new JLabel("B: ");
        xLabel = new JLabel("X: ");
        setVariablesEnabled(false);

        spinners.add(aLabel);
        spinners.add(aSpinner);
        spinners.add(bLabel);
        spinners.add(bSpinner);
        spinners.add(xLabel);
        spinners.add(xSpinner);

        JPanel spinnersTotal = new JPanel(new BorderLayout());
        spinnersTotal.add(spinners, BorderLayout.EAST);
        spinnersTotal.add(spinnersLabels, BorderLayout.WEST);
        add(spinnersTotal);
        isCustomDifficulty.setSelected(false);
        isCustomDifficulty.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                setVariablesEnabled(false);

            }
            if (e.getStateChange() == ItemEvent.SELECTED) {
                setVariablesEnabled(true);
            }
        });
        difficultySelector.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int difficulty = source.getValue();
                aSpinner.setValue(VAL[difficulty][0]);
                bSpinner.setValue(VAL[difficulty][1]);
                xSpinner.setValue(VAL[difficulty][2]);
            }
        });

        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10), BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Difficulty")));

    }

    private void setVariablesEnabled(boolean enabled) {
        aSpinner.setEnabled(enabled);
        bSpinner.setEnabled(enabled);
        xSpinner.setEnabled(enabled);
        aLabel.setEnabled(enabled);
        bLabel.setEnabled(enabled);
        xLabel.setEnabled(enabled);
        difficultySelector.setEnabled(!enabled);
    }

    private final int[][] VAL = {
            {1, 3, 5},
            {1, 5, 15},
            {2, 10, 10},
            {3, 20, 20}
    };

    @Override
    public Problem getProblem(String question, String answer) {
        Quadratic problem;
        if (isCustomDifficulty.isSelected()) {
            problem = QuadraticProblemFactory.generateSolveQuadraticQuestion((int) aSpinner.getValue(), (int) bSpinner.getValue(), (int) xSpinner.getValue());
        } else {
            int difficulty = difficultySelector.getValue();
            problem = QuadraticProblemFactory.generateSolveQuadraticQuestion(VAL[difficulty][0], VAL[difficulty][1], VAL[difficulty][2]);
        }
        problem.setQuestion(question + "Solve for x: ");
        return problem;
    }

    @Override
    public Problem getProblem() {
        if (isCustomDifficulty.isSelected()) {
            return QuadraticProblemFactory.generateSolveQuadraticQuestion((int) aSpinner.getValue(), (int) bSpinner.getValue(), (int) xSpinner.getValue());
        } else {
            int difficulty = difficultySelector.getValue();
            return QuadraticProblemFactory.generateSolveQuadraticQuestion(VAL[difficulty][0], VAL[difficulty][1], VAL[difficulty][2]);
        }
    }

}
