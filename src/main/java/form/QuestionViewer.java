package form;

import form.question.AbstractProblemForm;
import form.question.QuadraticForm;
import form.question.RightAngleTrigonometricForm;
import form.question.TextForm;
import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import model.*;
import module.DatabaseManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class QuestionViewer {
    private static Problem problem;
    private static JFrame frame;
    private static JPanel scrollerPanelProblems, listsPanel, questionPanel;
    private static JList<Problem> problemList;
    private static JList<Test> testList;
    private static Problem[] problems;
    private static Test[] tests;

    private static AbstractProblemForm currentProblem;
    private static JButton showTestsButton;
    private static JButton addButton;
    private static JButton deleteButton;
    private static JScrollPane scrollPane;
    private static boolean isPanelExpanded = false;

    private static void initUI() {
        scrollerPanelProblems = new JPanel(new BorderLayout());
        listsPanel = new JPanel(new GridLayout(1,2));

        DatabaseManager db = new DatabaseManager();
        problems = db.getProblemsArray();
        tests = db.getTestArray();
        db.close();

        problemList = new JList<>(problems);
        testList = new JList<>(tests);

        scrollPane = new JScrollPane(problemList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        problemList = new JList<>(problems);
        scrollPane = new JScrollPane(problemList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollerPanelProblems.add(scrollPane, BorderLayout.CENTER);

        showTestsButton = new JButton("Show Tests");
        showTestsButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ARROW_FORWARD, 20, new Color(0, 0, 0)));
        scrollerPanelProblems.add(showTestsButton, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        addButton = new JButton("Add");
        addButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ADD, 20, new Color(0, 223, 37)));
        buttonPanel.add(addButton, BorderLayout.WEST);

        deleteButton = new JButton("Delete");
        deleteButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.DELETE, 20, new Color(255, 0, 19)));
        buttonPanel.add(deleteButton, BorderLayout.EAST);

        scrollerPanelProblems.add(buttonPanel, BorderLayout.NORTH);

        frame = new JFrame("Question Viewer");
        questionPanel = new JPanel(new BorderLayout());
        Border inner = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        questionPanel.setBorder(BorderFactory.createCompoundBorder(inner, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        problemList.setSelectedIndex(0);
        selectProblem();
        listsPanel = new JPanel(new BorderLayout());
        listsPanel.add(scrollerPanelProblems);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listsPanel, questionPanel);
        frame.add(splitPane);
        frame.setSize(1000, 1000);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void initListeners() {
        showTestsButton.addActionListener(e -> {
            if(isPanelExpanded){
                listsPanel.remove(testList);
            }else{
                listsPanel.add(testList);
            }
        });
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowListener() {
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
       problemList.addListSelectionListener(e -> selectProblem());
        deleteButton.addActionListener(e -> {
            DatabaseManager db = new DatabaseManager();
            int index = problemList.getSelectedIndex();
            db.deleteProblem(problems[index]);
            problems = db.getProblemsArray();
            db.close();
            problemList = new JList<>(problems);
            problemList.setSelectedIndex(index);
            problemList.addListSelectionListener(a -> selectProblem());
            scrollerPanelProblems.remove(scrollPane);
            scrollPane = new JScrollPane(problemList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollerPanelProblems.add(scrollPane, BorderLayout.CENTER);
            scrollerPanelProblems.revalidate();
            frame.repaint();

        });
    }

    static void show() {
        initUI();
        initListeners();
    }

    private static void selectProblem() {
        if (!problemList.getValueIsAdjusting()) {
            int index = problemList.getSelectedIndex();
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
