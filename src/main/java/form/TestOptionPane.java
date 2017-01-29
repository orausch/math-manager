package form;

import model.Problem;
import model.Test;
import module.DatabaseManager;
import org.h2.engine.Database;

import javax.swing.*;
import java.awt.*;

public class TestOptionPane extends JFrame{
    public TestOptionPane(Problem selectedProblem) {
        DatabaseManager db = new DatabaseManager();
        JList testList = new JList(db.getTestArray());

        db.close();

        JScrollPane testScroller = new JScrollPane();
        testScroller.add(testList);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        setContentPane(contentPane);

        contentPane.add(testScroller);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton add = new JButton("Add");
        JButton cancel = new JButton("Cancel");
        buttonPanel.add(add);
        buttonPanel.add(cancel);
        contentPane.add(buttonPanel);
        setResizable(true);
        setTitle("Add to Test");
        setMinimumSize(new Dimension(200,200));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }
}
