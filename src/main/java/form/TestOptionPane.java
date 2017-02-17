package form;

import com.sun.org.apache.xpath.internal.SourceTree;
import model.Problem;
import model.Test;
import module.DatabaseManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class TestOptionPane extends JFrame {
    TestOptionPane(Problem currentProblem) {

        DatabaseManager db = new DatabaseManager();
        Test[] tests = db.getTestsArray();
        JList<Test> testList = new JList<>(tests);

        db.close();

        JScrollPane testScroller = new JScrollPane(testList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.add(testScroller, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            DatabaseManager db1 = new DatabaseManager();
            if(!testList.isSelectionEmpty())
                db1.insertIntoTest(currentProblem, testList.getSelectedValue());
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
