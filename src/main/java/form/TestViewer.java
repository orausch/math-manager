package form;


import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import model.*;
import module.DatabaseManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

class TestViewer {
    private static Test currentTest;
    private static JFrame frame;
    private static JToolBar toolBar;
    private static JPanel testDisplay;
    private static JScrollPane leftPanel, rightPanel;
    private static JSplitPane splitPane;
    private static JList<Test> testList;
    private static JButton deleteButton, exportButton, editButton;


    static void show() {
        initUI();
        initListeners();
    }

    private static void initUI() {
        frame = new JFrame("Test Viewer");
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        DatabaseManager db = new DatabaseManager();
        testList = new JList<>(db.getTestsArray());
        db.close();

        deleteButton = new JButton("Delete");
        editButton = new JButton("Edit");
        exportButton = new JButton("Export");
        deleteButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.DELETE, 16, Color.BLACK));
        editButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.EDIT, 16, Color.BLACK));
        exportButton.setIcon(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.FILE_DOWNLOAD, 16, Color.BLACK));
        testDisplay = new JPanel();
        testDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel = new JScrollPane(testList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel = new JScrollPane(testDisplay, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(100);
        toolBar.add(deleteButton);
        toolBar.add(editButton);
        toolBar.add(exportButton);
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
        editButton.addActionListener(e -> {
            if (!testList.isSelectionEmpty()) {
                frame.dispose();
                new EditTest(testList.getSelectedValue());
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Please select a Test",
                        "Error",
                        JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.WARNING, 32, Color.ORANGE));
            }
        });
        exportButton.addActionListener(e -> {
            if (!testList.isSelectionEmpty()) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
                fileChooser.setApproveButtonText("Export");
                fileChooser.setDialogTitle("Export as PDF");
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    selectedFile = new File(selectedFile.getAbsolutePath().concat(".pdf"));

                    BufferedImage bufferedImage = getImage(testList.getSelectedValue());
                    final float PAGE_WIDTH = 700;
                    final float PAGE_HEIGHT = 700 * 1.414f;
                    assert bufferedImage != null;
                    int requiredPages = (int) Math.ceil(bufferedImage.getHeight() / PAGE_HEIGHT);
                    //Splitting the image into pages
                    BufferedImage[] pages = new BufferedImage[requiredPages];
                    for (int i = 0; i < requiredPages; i++) {
                        if ((i * PAGE_HEIGHT) + PAGE_HEIGHT > bufferedImage.getHeight()) {
                            pages[i] = bufferedImage.getSubimage(0, (int) (i * PAGE_HEIGHT), bufferedImage.getWidth(), (int) (bufferedImage.getHeight() - i * PAGE_HEIGHT));
                        } else {
                            pages[i] = bufferedImage.getSubimage(0, (int) (i * PAGE_HEIGHT), bufferedImage.getWidth(), (int) PAGE_HEIGHT);
                        }
                    }
                    Document document = new Document(new Rectangle(PAGE_WIDTH, PAGE_HEIGHT));
                    try {
                        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(selectedFile.getAbsolutePath()));
                        document.open();
                        PdfContentByte pdfCB = new PdfContentByte(writer);
                        for (BufferedImage p : pages) {
                            if (p != null) {
                                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(Toolkit.getDefaultToolkit().createImage(p.getSource()), null);
                                document.add(image);
                                document.newPage();
                            }
                        }
                        document.close();
                        writer.close();

                    } catch (DocumentException | IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Please select a Test",
                        "Error",
                        JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.WARNING, 32, Color.ORANGE));
            }

        });
        deleteButton.addActionListener(e -> {
            if (!testList.isSelectionEmpty()) {
                DatabaseManager db = new DatabaseManager();
                int index = testList.getSelectedIndex();
                db.deleteTest(testList.getSelectedValue());

                testList = new JList<>(db.getTestsArray());
                db.close();
                testList.setSelectedIndex(index);
                testList.addListSelectionListener(a -> selectTest());
                splitPane.remove(leftPanel);
                leftPanel = new JScrollPane(testList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                splitPane.add(leftPanel);

                splitPane.revalidate();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Please select a Test",
                        "Error",
                        JOptionPane.WARNING_MESSAGE, IconFontSwing.buildIcon(GoogleMaterialDesignIcons.WARNING, 32, Color.ORANGE));
            }

        });
        testList.addListSelectionListener(e -> selectTest());
    }

    private static void selectTest() {
        if (!testList.getValueIsAdjusting()) {
            rightPanel.remove(testDisplay);
            testDisplay.removeAll();
            currentTest = testList.getSelectedValue();
            JLabel imageLabel = new JLabel("");
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            if (getImage(currentTest) != null) {
                imageLabel.setIcon(new ImageIcon(getImage(currentTest)));
            }
            testDisplay.add(imageLabel);
            testDisplay.revalidate();
            testDisplay.repaint();
        }
    }

    private static BufferedImage getImage(Test test) {

        if (test.getQuestions() != null) {
            if (!test.getQuestions().isEmpty()) {
                int totalHeight = 150;
                final int FONT_HEIGHT = 50;
                for (Problem p : test.getQuestions()) {
                    if (p instanceof RightAngleTrigonometric)
                        totalHeight += 520;
                    totalHeight += 2 * FONT_HEIGHT;
                }

                BufferedImage image = new BufferedImage(600, totalHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = image.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setBackground(Color.WHITE);
                g.setColor(Color.black);
                final Font TITLE = new Font(new JLabel().getFont().getName(), Font.BOLD, 20);
                final Font QUESTION = new Font(new JLabel().getFont().getName(), Font.ITALIC, 18);

                final Font ANSWER = new Font(new JLabel().getFont().getName(), Font.PLAIN, 15);

                g.setFont(TITLE);
                int currentY = 20;
                drawCenteredString(g, test.getName(), new Rectangle(0, 0, 600, 50), TITLE);
                currentY += 50;
                if (test.getDate() != null) {
                    drawCenteredString(g, " Name:_______________  " + "Date: " + test.getDate(), new Rectangle(0, -10, 600, 120), ANSWER);
                } else {
                    drawCenteredString(g, " Name:_______________", new Rectangle(0, -10, 600, 120), ANSWER);
                }
                currentY += FONT_HEIGHT;
                for (Problem p : test.getQuestions()) {
                    g.setFont(QUESTION);
                    g.drawString(p.getQuestion(), 10, currentY);
                    currentY += FONT_HEIGHT;

                    if (p instanceof RightAngleTrigonometric) {
                        RightAngleTrigonometric problem = (RightAngleTrigonometric) p;
                        g.drawImage(problem.getImage(500, false), 10, currentY, null);
                        currentY += problem.getImage(500, false).getHeight() + 20;
                    }
                    g.setFont(ANSWER);
                    g.drawString("Answer:____________", 450, currentY);
                    currentY += FONT_HEIGHT;
                }
                return image;
            } else return null;
        } else return null;
    }

    private static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (int) ((rect.getWidth() - metrics.stringWidth(text)) / 2);
        int y = (int) (((rect.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());
        g.setFont(font);
        g.drawString(text, x, y);
    }
}
