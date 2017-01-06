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
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class QuestionViewer {
    private static Problem problem;
    private static JFrame frame;
    private static JPanel listsPanel, questionPanel, rightPanel, leftPanel;
    private static JList<Problem> problemList;
    private static JList<Test> testList;
    private static Problem[] problems;
    private static Test[] tests;

    private static AbstractProblemForm currentProblem;
    private static JButton addButton;
    private static JButton deleteButton;
    private static JScrollPane scrollPaneProblems, scrollPaneTests;
    private static JToolBar toolBar;
    private static JSplitPane splitPaneLists;
    private static JButton increaseScale;
    private static JButton decreaseScale;

    private static void initUI() {
        listsPanel = new JPanel();
        listsPanel.setLayout(new GridLayout(1, 2));

        DatabaseManager db = new DatabaseManager();
        problems = db.getProblemsArray();
        tests = db.getTestArray();
        db.close();

        problemList = new JList<>(problems);
        testList = new JList<>(tests);

        scrollPaneProblems = new JScrollPane(problemList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneTests = new JScrollPane(testList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);




        frame = new JFrame("Question Viewer");
        questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        problemList.setSelectedIndex(0);
        selectProblem();
        splitPaneLists = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneProblems, scrollPaneTests);

        toolBar = new JToolBar();

        toolBar.setFloatable(false);
        addButton = new JButton("Add");
        addButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ADD, 32, new Color(0, 223, 37)));
        deleteButton = new JButton("Delete");
        deleteButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.DELETE, 32, new Color(255, 0, 19)));
        toolBar.add(addButton);
        toolBar.add(deleteButton);
        increaseScale = new JButton();
        decreaseScale = new JButton();
        increaseScale.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ZOOM_IN, 32, Color.BLACK));
        decreaseScale.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ZOOM_OUT, 32, Color.BLACK));
        toolBar.add(increaseScale);
        toolBar.add(decreaseScale);


        leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(splitPaneLists, BorderLayout.CENTER);
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel("Question"), BorderLayout.NORTH);
        rightPanel.add(questionPanel, BorderLayout.CENTER);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);

        frame.setLayout(new BorderLayout());
        frame.add(toolBar, BorderLayout.PAGE_START);
        frame.add(splitPane,BorderLayout.CENTER);
        frame.setSize(1000, 1000);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void initListeners() {
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
            splitPaneLists.remove(scrollPaneProblems);
            scrollPaneProblems = new JScrollPane(problemList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            splitPaneLists.add(scrollPaneProblems);
            splitPaneLists.revalidate();
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

                increaseScale.addActionListener(e -> ((RightAngleTrigonometricForm) currentProblem).increaseScaleClicked());
                decreaseScale.addActionListener(e -> ((RightAngleTrigonometricForm) currentProblem).decreaseScaleClicked());
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
