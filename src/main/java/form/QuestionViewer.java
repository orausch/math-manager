package form;

import form.question.AbstractProblemForm;
import form.question.QuadraticForm;
import form.question.RightAngleTrigonometricForm;
import form.question.TextForm;
import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import model.Problem;
import model.Quadratic;
import model.RightAngleTrigonometric;
import model.Text;
import module.DatabaseManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class QuestionViewer {
    private static Problem problem;
    private static JFrame frame;
    private static JPanel scrollerPanel, questionPanel;
    private static JList<Problem> list;
    private static Problem[] problems;
    private static AbstractProblemForm currentProblem;
    private static JButton backButton;
    private static JButton addButton;
    private static JButton deleteButton;
    private static JScrollPane scrollPane;

    private static void initUI() {
        scrollerPanel = new JPanel(new BorderLayout());
        DatabaseManager db = new DatabaseManager();
        problems = db.getProblemsArray();
        db.close();

        list = new JList<>(problems);
        scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollerPanel.add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ARROW_BACK, 20, new Color(0, 0, 0)));
        scrollerPanel.add(backButton, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        addButton = new JButton("Add");
        addButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ADD, 20, new Color(0, 223, 37)));
        buttonPanel.add(addButton, BorderLayout.WEST);

        deleteButton = new JButton("Delete");
        deleteButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.DELETE, 20, new Color(255, 0, 19)));
        buttonPanel.add(deleteButton, BorderLayout.EAST);

        scrollerPanel.add(buttonPanel, BorderLayout.NORTH);

        frame = new JFrame("Question Viewer");
        questionPanel = new JPanel(new BorderLayout());
        Border inner = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        questionPanel.setBorder(BorderFactory.createCompoundBorder(inner, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        list.setSelectedIndex(0);
        selectProblem();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollerPanel, questionPanel);
        frame.add(splitPane);
        frame.setSize(1000, 1000);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void initListeners() {
        backButton.addActionListener(e -> {
            Start.show();
            frame.dispose();
        });
       list.addListSelectionListener(e -> selectProblem());
        deleteButton.addActionListener(e -> {
            DatabaseManager db = new DatabaseManager();
            int index = list.getSelectedIndex();
            db.deleteProblem(problems[index]);
            problems = db.getProblemsArray();
            db.close();
            list = new JList<>(problems);
            list.setSelectedIndex(index);
            list.addListSelectionListener(a -> selectProblem());
            scrollerPanel.remove(scrollPane);
            scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollerPanel.add(scrollPane, BorderLayout.CENTER);
            scrollerPanel.revalidate();
            frame.repaint();

        });
    }

    static void show() {
        initUI();
        initListeners();
    }

    private static void selectProblem() {
        if (!list.getValueIsAdjusting()) {
            int index = list.getSelectedIndex();
            if (problems[index] instanceof Quadratic) {
                questionPanel.removeAll();
                currentProblem = new QuadraticForm((Quadratic) problems[index]);
            } else if (problems[index] instanceof RightAngleTrigonometric) {
                questionPanel.removeAll();
                currentProblem = new RightAngleTrigonometricForm(problems[index]);
            } else if (problems[index] instanceof Text) {
                questionPanel.removeAll();
                currentProblem = new TextForm(problems[index]);
            }
            questionPanel.add(currentProblem, BorderLayout.NORTH);
            questionPanel.add(new AnswerForm(problems[index]), BorderLayout.SOUTH);

            questionPanel.revalidate();
            questionPanel.repaint();
        }
    }
}
