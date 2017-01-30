package form;


import form.question.QuadraticForm;
import form.question.RightAngleTrigonometricForm;
import form.question.TextForm;
import model.*;
import module.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class TestViewer {
    private static Test currentTest;
    private static JFrame frame;
    private static JToolBar toolBar;
    private static JPanel testDisplay;
    private static JScrollPane rightPanel, leftPanel;
    private static JSplitPane splitPane;
    private static JList<Test> testList;


    static void show() {
        initUI();
        initListeners();
    }

    private static void initUI() {
        frame = new JFrame("Question Viewer");
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        DatabaseManager db = new DatabaseManager();
        testList = new JList<>(db.getTestsArray());
        db.close();

        testDisplay = new JPanel();
        testDisplay.setLayout(new BoxLayout(testDisplay, BoxLayout.Y_AXIS));

        rightPanel = new JScrollPane(testList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanel = new JScrollPane(testDisplay, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, rightPanel, leftPanel);

        frame.setLayout(new BorderLayout());
        frame.add(toolBar, BorderLayout.PAGE_START);
        frame.add(splitPane, BorderLayout.CENTER);
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

        testList.addListSelectionListener(e -> selectTest());
    }

    private static void selectTest() {
        try {
            if (!testList.getValueIsAdjusting()) {
                leftPanel.remove(testDisplay);
                testDisplay.removeAll();
                currentTest = testList.getSelectedValue();

                for (Problem p : currentTest.getQuestions()) {
                    if (p instanceof Quadratic) {
                        testDisplay.add(new QuadraticForm((Quadratic) p));
                    } else if (p instanceof RightAngleTrigonometric) {
                        testDisplay.add(new RightAngleTrigonometricForm(p));
                    } else if (p instanceof Text) {
                        testDisplay.add(new TextForm(p));
                    }
                }

                testDisplay.revalidate();
                testDisplay.repaint();

            }
        } catch (ClassCastException e) {
            //This should never be thrown
            e.printStackTrace();
        }
    }
}
