package form.variables;

import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import model.Problem;
import model.Quadratic;
import util.Utility;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class QuadraticCreateVariables extends AbstractProblemVariables {

    private JSpinner aSpinner, bSpinner, cSpinner;
    private JTextField x1, x2;

    public QuadraticCreateVariables() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel spinners = new JPanel(new FlowLayout());
        aSpinner = new JSpinner(new SpinnerNumberModel(1, -100, 100, 1));
        bSpinner = new JSpinner(new SpinnerNumberModel(1, -100, 100, 1));
        cSpinner = new JSpinner(new SpinnerNumberModel(1, -100, 100, 1));

        spinners.add(aSpinner);
        spinners.add(new JLabel(Utility.superscript("x^2 + ")));
        spinners.add(bSpinner);
        spinners.add(new JLabel(Utility.superscript("x + ")));
        spinners.add(cSpinner);
        spinners.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10), BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Coefficients")));
        add(spinners);

        JPanel solutions = new JPanel(new GridLayout(3, 2));
        x1 = new JTextField();
        x1.setEditable(false);
        x2 = new JTextField();
        x2.setEditable(false);
        solutions.add(new JLabel("X1: "));
        solutions.add(x1);
        solutions.add(new JLabel("X2: "));
        solutions.add(x2);
        solutions.add(new JLabel());
        JButton getSolutionsButton = new JButton("Get solutions");
        getSolutionsButton.addActionListener(e -> {
            Quadratic quadratic = new Quadratic((int) aSpinner.getValue(), (int) bSpinner.getValue(), (int) cSpinner.getValue());
            double solution1 = quadratic.getSolution()[0];
            double solution2 = quadratic.getSolution()[1];
            if (!Double.isNaN(solution1) && !Double.isNaN(solution2)) {
                x1.setText(String.valueOf(solution1));
                x2.setText(String.valueOf(solution2));
            } else {
                JOptionPane.showMessageDialog(null,
                        "No real roots",
                        "Error",
                        JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.WARNING, 32, Color.ORANGE));
            }
        });
        solutions.add(getSolutionsButton);
        solutions.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10), BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Solutions")));
        add(solutions);

    }

    @Override
    public Problem getProblem(String question, String answer) {
        Quadratic quadratic = new Quadratic((int) aSpinner.getValue(), (int) bSpinner.getValue(), (int) cSpinner.getValue());
        double solution1 = quadratic.getSolution()[0];
        double solution2 = quadratic.getSolution()[1];
        if (!Double.isNaN(solution1) && !Double.isNaN(solution2)) {
            return new Quadratic((int) aSpinner.getValue(), (int) bSpinner.getValue(), (int) cSpinner.getValue(), question);
        } else {
            JOptionPane.showMessageDialog(null,
                    "No real roots",
                    "Error",
                    JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.WARNING, 32, Color.ORANGE));
            return null;
        }
    }

    @Override
    public Problem getProblem() {
        Quadratic quadratic = new Quadratic((int) aSpinner.getValue(), (int) bSpinner.getValue(), (int) cSpinner.getValue());
        double solution1 = quadratic.getSolution()[0];
        double solution2 = quadratic.getSolution()[1];
        if (!Double.isNaN(solution1) && !Double.isNaN(solution2)) {
            return new Quadratic((int) aSpinner.getValue(), (int) bSpinner.getValue(), (int) cSpinner.getValue());
        } else {
            JOptionPane.showMessageDialog(null,
                    "No real roots",
                    "Error",
                    JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.WARNING, 32, Color.ORANGE));
            return null;
        }
    }
}
