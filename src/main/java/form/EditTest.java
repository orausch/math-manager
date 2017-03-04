package form;

import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import model.Problem;
import model.Test;
import util.DatabaseManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collections;
import java.util.LinkedList;

/**
 * The form used to move or delete questions in a test
 */
class EditTest extends JFrame {
    private JList testList;
    private JScrollPane scrollPane;

    /**
     * Initialise the EditTest form
     *
     * @param test the test to edit
     */
    EditTest(Test test) {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        contentPane.setLayout(new BorderLayout());
        DatabaseManager db = new DatabaseManager();

        testList = new JList<>(test.getQuestions().toArray());
        db.close();
        JButton up = new JButton("");
        up.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.KEYBOARD_ARROW_UP, 16, Color.BLACK));
        JButton down = new JButton("");
        down.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.KEYBOARD_ARROW_DOWN, 16, Color.BLACK));

        JButton delete = new JButton("");
        delete.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.DELETE, 16, Color.RED));

        scrollPane = new JScrollPane(testList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(up);
        buttonPanel.add(down);
        buttonPanel.add(delete);
        up.addActionListener(e -> {
            int index = testList.getSelectedIndex();
            if (index != 0) {
                LinkedList<Problem> list = test.getQuestions();
                Collections.swap(list, index, index - 1);
                testList = new JList<>(test.getQuestions().toArray());
                contentPane.removeAll();
                scrollPane = new JScrollPane(testList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                contentPane.add(scrollPane, BorderLayout.CENTER);
                contentPane.add(buttonPanel, BorderLayout.SOUTH);
                contentPane.revalidate();
                contentPane.repaint();
                testList.setSelectedIndex(index-1);
            }
        });
        down.addActionListener(e -> {
            int index = testList.getSelectedIndex();
            if(index != testList.getModel().getSize() -1) {
                LinkedList<Problem> list = test.getQuestions();
                Collections.swap(list, index, index + 1);
                testList = new JList<>(test.getQuestions().toArray());
                contentPane.removeAll();
                scrollPane = new JScrollPane(testList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                contentPane.add(scrollPane, BorderLayout.CENTER);
                contentPane.add(buttonPanel, BorderLayout.SOUTH);
                contentPane.revalidate();
                contentPane.repaint();
                testList.setSelectedIndex(index+1);
            }
        });
        delete.addActionListener(e -> {
            int index = testList.getSelectedIndex();
            LinkedList<Problem> list = test.getQuestions();
            list.remove(index);
            testList = new JList<>(test.getQuestions().toArray());
            contentPane.removeAll();
            scrollPane = new JScrollPane(testList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            contentPane.add(scrollPane, BorderLayout.CENTER);
            contentPane.add(buttonPanel, BorderLayout.SOUTH);
            contentPane.revalidate();
            contentPane.repaint();
            testList.setSelectedIndex(index-1);
        });


        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                //write changes
                DatabaseManager db =  new DatabaseManager();
                db.deleteTest(test);
                db.insertTest(test);
                db.close();
                e.getWindow().dispose();
                TestViewer.show();
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
        setContentPane(contentPane);
        setResizable(true);
        setTitle("Edit Test");
        setMinimumSize(new Dimension(200, 200));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
