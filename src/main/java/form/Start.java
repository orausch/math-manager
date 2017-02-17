package form;

import jdk.nashorn.internal.scripts.JO;
import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import model.Test;
import module.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start {
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel titleLabel, versionLabel;
    private static JButton generateButton, browseProblemsButton, browseTestsButton, newTestsButton;

    private static void initUI() {

        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());

        frame = new JFrame("Math Manager");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        titleLabel = new JLabel("Math Manager");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.PLAIN, 36));
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(titleLabel, constraints);

        versionLabel = new JLabel("Version 0.0.1");
        versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        versionLabel.setForeground(new Color(161, 161, 161));
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 10, 10, 20);
        panel.add(versionLabel, constraints);

        Dimension buttonSize = new Dimension(230, 30);
        generateButton = new JButton("New Question");
        generateButton.setHorizontalAlignment(SwingConstants.LEFT);
        generateButton.setPreferredSize(buttonSize);
        generateButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ADD, 20, new Color(0, 150, 0)));
        constraints.gridy = 2;
        constraints.insets = new Insets(1, 5, 5, 1);
        panel.add(generateButton, constraints);

        browseProblemsButton = new JButton("Browse Questions");
        browseProblemsButton.setHorizontalAlignment(SwingConstants.LEFT);
        browseProblemsButton.setPreferredSize(buttonSize);
        browseProblemsButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.FOLDER_OPEN, 20, new Color(179, 138, 47)));

        constraints.gridy = 3;

        panel.add(browseProblemsButton, constraints);

        newTestsButton = new JButton("New Test");
        newTestsButton.setHorizontalAlignment(SwingConstants.LEFT);
        newTestsButton.setPreferredSize(buttonSize);
        newTestsButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.INSERT_DRIVE_FILE, 20, new Color(0, 169, 255)));

        constraints.gridy = 4;
        panel.add(newTestsButton, constraints);

        browseTestsButton = new JButton("Browse Tests");
        browseTestsButton.setHorizontalAlignment(SwingConstants.LEFT);
        browseTestsButton.setPreferredSize(buttonSize);
        browseTestsButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.INSERT_DRIVE_FILE, 20, new Color(0, 169, 255)));

        constraints.gridy = 5;
        panel.add(browseTestsButton, constraints);
        frame.add(panel);

        frame.setSize(300, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void initListeners() {
        browseProblemsButton.addActionListener(e -> {
            frame.dispose();
            QuestionViewer.show();

        });
        generateButton.addActionListener(e -> {
            frame.dispose();
            new CreateQuestion(true);
        });
        browseTestsButton.addActionListener(e -> {
            frame.dispose();
            TestViewer.show();
        });
        newTestsButton.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField dateField = new JTextField();
            Object[] message = {
                    "Name:", nameField,
                    "Date (Optional):", dateField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "New Test", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon());
            if (option == JOptionPane.OK_OPTION && !nameField.getText().equals("")) {
                DatabaseManager db = new DatabaseManager();
                db.insertTest(new Test(nameField.getText(), dateField.getText()));
                db.close();
            }else{
                if(option != JOptionPane.CLOSED_OPTION) {
                    JOptionPane.showMessageDialog(null,
                            "Name cannot be empty",
                            "Error",
                            JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.WARNING, 32, Color.ORANGE));
                }
            }
        });
    }

    public static void show() {
        initUI();
        initListeners();
    }
}
