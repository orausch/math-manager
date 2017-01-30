package form;

import model.Problem;
import model.Test;
import module.DatabaseManager;

import javax.swing.*;
import java.awt.*;

class TestOptionPane extends JFrame {
    TestOptionPane(Problem selectedProblem) {
        DatabaseManager db = new DatabaseManager();
        Test[] tests = db.getTestsArray();
        JList<Test> testList = new JList<>(tests);

        db.close();

        JScrollPane testScroller = new JScrollPane(testList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        contentPane.add(testScroller, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            DatabaseManager db1 = new DatabaseManager();
            db1.insertIntoTest(selectedProblem, testList.getSelectedValue());
            db1.close();
            dispose();
        });
        cancelButton.addActionListener(e -> dispose());

        setResizable(true);
        setTitle("Add to Test");
        setMinimumSize(new Dimension(200, 200));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
