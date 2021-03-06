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
import util.DatabaseManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The form that shows all the questions
 */
class QuestionViewer {
    private static JFrame frame;
    private static JPanel listsPanel, questionPanel, rightPanel;
    private static JList<Problem> problemList;
    private static Problem[] problems;

    private static AbstractProblemForm currentProblem;
    private static JScrollPane scrollPaneProblems;
    private static JToolBar toolBar;
    private static JButton addToTestButton, deleteButton, increaseScale, decreaseScale;
    private static JSplitPane splitPane;

    private static void initUI() {
        listsPanel = new JPanel();
        listsPanel.setLayout(new GridLayout(1, 2));

        DatabaseManager db = new DatabaseManager();
        problems = db.getProblemsArray();
        db.close();

        problemList = new JList<>(problems);

        scrollPaneProblems = new JScrollPane(problemList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        frame = new JFrame("Question Viewer");
        questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        problemList.setSelectedIndex(0);
        selectProblem();

        toolBar = new JToolBar();

        toolBar.setFloatable(false);
        deleteButton = new JButton("Delete");
        deleteButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.DELETE, 16, Color.BLACK));

        addToTestButton = new JButton("Add to Test");
        addToTestButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ATTACH_FILE, 16, Color.BLACK));

        toolBar.add(addToTestButton);
        toolBar.add(deleteButton);
        increaseScale = new JButton();
        decreaseScale = new JButton();
        increaseScale.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ZOOM_IN, 16, Color.BLACK));
        decreaseScale.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ZOOM_OUT, 16, Color.BLACK));
        toolBar.add(increaseScale);
        toolBar.add(decreaseScale);

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel("Question"), BorderLayout.NORTH);
        rightPanel.add(questionPanel, BorderLayout.CENTER);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneProblems, rightPanel);

        frame.setLayout(new BorderLayout());
        frame.add(toolBar, BorderLayout.PAGE_START);
        frame.add(splitPane, BorderLayout.CENTER);
        frame.setSize(1000, 1000);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        try {
            ArrayList<Image> images = new ArrayList<>();
            images.add(ImageIO.read(Start.class.getResource("/20.png")));
            images.add(ImageIO.read(Start.class.getResource("/40.png")));
            images.add(ImageIO.read(Start.class.getResource("/60.png")));
            frame.setIconImages(images);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            if (!problemList.isSelectionEmpty()) {
                DatabaseManager db = new DatabaseManager();
                int index = problemList.getSelectedIndex();
                db.deleteProblem(problems[index]);
                problems = db.getProblemsArray();
                db.close();
                problemList = new JList<>(problems);
                problemList.setSelectedIndex(index == 0 ? 0 : index - 1);
                problemList.addListSelectionListener(a -> selectProblem());
                selectProblem();
                splitPane.remove(scrollPaneProblems);
                scrollPaneProblems = new JScrollPane(problemList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                splitPane.add(scrollPaneProblems);
                splitPane.revalidate();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Please select a Question",
                        "Error",
                        JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.WARNING, 32, Color.ORANGE));
            }


        });
        addToTestButton.addActionListener(e -> {
            if (!problemList.isSelectionEmpty()) {
                new TestOptionPane(problemList.getSelectedValue());
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Please select a Question",
                        "Error",
                        JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.WARNING, 32, Color.ORANGE));
            }

        });
    }

    /**
     * Initialise and show the form
     */
    static void show() {
        initUI();
        initListeners();
    }

    /**
     * Removes the current problem and replaces it with the selected problem. This is called every time the user clicks
     * the list.
     */
    private static void selectProblem() {
        if (!problemList.getValueIsAdjusting() && !problemList.isSelectionEmpty()) {
            int index = problemList.getSelectedIndex();
            questionPanel.removeAll();
            if (problems[index] instanceof Quadratic) {
                currentProblem = new QuadraticForm((Quadratic) problems[index]);
            } else if (problems[index] instanceof RightAngleTrigonometric) {
                currentProblem = new RightAngleTrigonometricForm(problems[index]);
                try {
                    increaseScale.addActionListener(e -> ((RightAngleTrigonometricForm) currentProblem).increaseScaleClicked());
                    decreaseScale.addActionListener(e -> ((RightAngleTrigonometricForm) currentProblem).decreaseScaleClicked());
                } catch (Exception ignored) {//This exception will never be thrown because currentProblem is always of type RightAngleTrigonometric
                }
            } else if (problems[index] instanceof Text) {
                currentProblem = new TextForm(problems[index]);
            }
            questionPanel.add(currentProblem, BorderLayout.NORTH);
            questionPanel.add(new AnswerForm(problems[index]), BorderLayout.SOUTH);

            questionPanel.revalidate();
            questionPanel.repaint();
        }
    }
}

