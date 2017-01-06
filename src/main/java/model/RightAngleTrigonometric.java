package model;

import module.Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Objects;

public class RightAngleTrigonometric extends Problem implements Serializable {
    private final int UNIT_MM = 0;
    private final int UNIT_CM = 1;
    private final int UNIT_M = 2;

    public void setQuestion(String question) {
        this.question = question;
    }

    private final int UNIT_KM = 3;
    private String question;
    private char target;
    private double answer;
    private int unit;
    private int given;
    private int topLeftX;
    private int topLeftY;
    private int topRightX;
    private int topRightY;
    private int botLeftX;
    private int botLeftY;
    private int botRightX;
    private int botRightY;


    private final int TOP_LEFT = 0;
    private final int TOP_RIGHT = 1;
    private final int BOT_LEFT = 2;
    private final int BOT_RIGHT = 3;
    private int IMAGE_HEIGHT = 500;

    public BufferedImage getImage(int zoomFactor) {
        IMAGE_HEIGHT = zoomFactor;
        final int PADDING = 50;
        final int RECTANGLE_SIZE = 10;

        BufferedImage image = new BufferedImage(IMAGE_HEIGHT + PADDING * 2, IMAGE_HEIGHT + PADDING * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

         //Randomising rotation of the triangle
        topLeftX = (int) (PADDING + ((IMAGE_HEIGHT - getScaledSides()[0]) / 2));
        topLeftY = PADDING;

        topRightX = (int) (PADDING + ((IMAGE_HEIGHT - getScaledSides()[0]) / 2) + getScaledSides()[0]);
        topRightY = PADDING;

        botLeftX = (int) (PADDING + ((IMAGE_HEIGHT - getScaledSides()[0]) / 2));
        botLeftY = (int) (PADDING + getScaledSides()[1]);

        botRightX = (int) (PADDING + ((IMAGE_HEIGHT - getScaledSides()[0]) / 2) + getScaledSides()[0]);
        botRightY = (int) (PADDING + getScaledSides()[1]);

        int cornerToSkip = (int) Util.getRandomNumber(0, 3);

        g.setColor(new Color(0, 0, 0));
        switch (cornerToSkip) {
            case TOP_LEFT:
                drawTriangle(g, topRightX, topRightY, botLeftX, botLeftY, botRightX, botRightY);
                g.draw(new Rectangle(botRightX - RECTANGLE_SIZE, botRightY - RECTANGLE_SIZE, RECTANGLE_SIZE, RECTANGLE_SIZE));
                break;
            case TOP_RIGHT:
                drawTriangle(g, topLeftX, topLeftY, botLeftX, botLeftY, botRightX, botRightY);
                g.draw(new Rectangle(topLeftX, botRightY - RECTANGLE_SIZE, RECTANGLE_SIZE, RECTANGLE_SIZE));
                break;
            case BOT_LEFT:
                drawTriangle(g, topLeftX, topLeftY, botRightX, botRightY, topRightX, topRightY);
                g.draw(new Rectangle(botRightX - RECTANGLE_SIZE, PADDING, RECTANGLE_SIZE, RECTANGLE_SIZE));
                break;
            case BOT_RIGHT:
                drawTriangle(g, topLeftX, topLeftY, botLeftX, botLeftY, topRightX, topRightY);
                g.draw(new Rectangle(topLeftX, topLeftY, RECTANGLE_SIZE, RECTANGLE_SIZE));
                break;
        }
        drawLabels(g, cornerToSkip);
        return image;
    }

    private double[] getScaledSides() {
        int longestSide = sides[0] > sides[1] ? 0 : 1;

        double[] scaledSides = new double[3];

        scaledSides[longestSide] = IMAGE_HEIGHT;

        double scalingFactor = IMAGE_HEIGHT / sides[longestSide];

        scaledSides[longestSide == 0 ? 1 : 0] = sides[longestSide == 0 ? 1 : 0] * scalingFactor;

        scaledSides[2] = sides[2] * scalingFactor;
        return scaledSides;
    }

    private void drawTriangle(Graphics2D g, int x1, int y1, int x2, int y2, int x3, int y3) {
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x2, y2, x3, y3);
        g.drawLine(x1, y1, x3, y3);
    }

    private void drawLabels(Graphics2D g, int cornerToSkip) {
        //SIDES
        String[] sidesLabels = new String[3];
        for (int i = 0; i < sides.length; i++) {
            if (isGivenSide[i]) {
                sidesLabels[i] = String.valueOf(Util.round(sides[i], 1)) + " " + getUnit();
            } else if (isTarget[i]) {
                sidesLabels[i] = String.valueOf(target);
            } else {
                sidesLabels[i] = "";
            }
        }
        String[] anglesLabels = new String[2];
        for (int i = 0; i < anglesLabels.length; i++) {
            if (isGivenAngle[i]) {
                anglesLabels[i] = String.valueOf(Util.round(angles[i], 1)) + "°";
            } else if (isTarget[i + 3]) {
                anglesLabels[i] = String.valueOf(target);
            } else {
                anglesLabels[i] = "";
            }
        }
        switch (cornerToSkip) {
            case TOP_LEFT:
                drawAngleLabel(g, BOT_LEFT, anglesLabels[0]);
                drawAngleLabel(g, TOP_RIGHT, anglesLabels[1]);
                drawSideLabel(g, Side.SOUTH, sidesLabels[0]);
                drawSideLabel(g, Side.EAST, sidesLabels[1]);
                break;
            case TOP_RIGHT:
                drawAngleLabel(g, BOT_RIGHT, anglesLabels[0]);
                drawAngleLabel(g, TOP_LEFT, anglesLabels[1]);
                drawSideLabel(g, Side.SOUTH, sidesLabels[0]);
                drawSideLabel(g, Side.WEST, sidesLabels[1]);
                break;
            case BOT_LEFT:
                drawAngleLabel(g, BOT_RIGHT, anglesLabels[1]);
                drawAngleLabel(g, TOP_LEFT, anglesLabels[0]);
                drawSideLabel(g, Side.NORTH, sidesLabels[0]);
                drawSideLabel(g, Side.EAST, sidesLabels[1]);
                break;
            case BOT_RIGHT:
                drawAngleLabel(g, BOT_LEFT, anglesLabels[1]);
                drawAngleLabel(g, TOP_RIGHT, anglesLabels[0]);
                drawSideLabel(g, Side.NORTH, sidesLabels[0]);
                drawSideLabel(g, Side.WEST, sidesLabels[1]);
                break;
        }
        drawSideLabel(g, Side.CENTRE, sidesLabels[2]);
    }

    private void drawAngleLabel(Graphics g, int corner, String label) {
        final int padding = 5;
        FontMetrics metrics = g.getFontMetrics();
        int fontHeight = metrics.getHeight() - 6;
        int fontWidth = metrics.stringWidth(label);
        int[] topLeft = {topLeftX - fontWidth - padding, topLeftY - padding};
        int[] topRight = {topRightX + padding, topLeftY - padding};
        int[] botLeft = {botLeftX - fontWidth - padding, botLeftY + fontHeight + padding};
        int[] botRight = {botRightX + padding, botRightY + padding + fontHeight};

        switch (corner) {
            case TOP_LEFT:
                g.drawString(label, topLeft[0], topLeft[1]);
                break;
            case TOP_RIGHT:
                g.drawString(label, topRight[0], topRight[1]);
                break;
            case BOT_LEFT:
                g.drawString(label, botLeft[0], botLeft[1]);
                break;
            case BOT_RIGHT:
                g.drawString(label, botRight[0], botRight[1]);
                break;
        }
    }

    private void drawSideLabel(Graphics g, int side, String label) {
        if (!Objects.equals(label, "")) {
            Color gray = new Color(238, 238, 238);
            FontMetrics metrics = g.getFontMetrics();
            final int padding = 2;
            int fontHeight = metrics.getHeight() - 6 + 2 * padding;
            int fontWidth = metrics.stringWidth(label) + 2 * padding;
            int[] top = {getMidPoint(topLeftX, topRightX) - (fontWidth / 2), topRightY + (fontHeight / 2)};
            int[] bot = {getMidPoint(topLeftX, topRightX) - (fontWidth / 2), botRightY + (fontHeight / 2)};
            int[] left = {topLeftX - (fontWidth / 2), getMidPoint(topLeftY, botLeftY) + (fontHeight / 2)};
            int[] right = {topRightX - (fontWidth / 2), getMidPoint(topRightY, botRightY) + (fontHeight / 2)};
            int[] centre = {getMidPoint(topLeftX, botRightX) - (fontWidth / 2), getMidPoint(topLeftY, botRightY) + (fontHeight / 2)};

            switch (side) {
                case Side.NORTH:
                    g.setColor(gray);
                    g.fillRect(top[0] - padding, top[1] - fontHeight + padding, fontWidth, fontHeight);
                    g.setColor(Color.black);
                    g.drawString(label, top[0], top[1]);
                    break;
                case Side.SOUTH:
                    g.setColor(gray);
                    g.fillRect(bot[0] - padding, bot[1] - fontHeight + padding, fontWidth, fontHeight);
                    g.setColor(Color.black);
                    g.drawString(label, bot[0], bot[1]);
                    break;
                case Side.EAST:
                    g.setColor(gray);
                    g.fillRect(right[0] - padding, right[1] - fontHeight + padding, fontWidth, fontHeight);
                    g.setColor(Color.black);
                    g.drawString(label, right[0], right[1]);
                    break;
                case Side.WEST:
                    g.setColor(gray);
                    g.fillRect(left[0] - padding, left[1] - fontHeight + padding, fontWidth, fontHeight);
                    g.setColor(Color.black);
                    g.drawString(label, left[0], left[1]);
                    break;
                case Side.CENTRE:
                    g.setColor(gray);
                    g.fillRect(centre[0] - padding, centre[1] - fontHeight + padding, fontWidth, fontHeight);
                    g.setColor(Color.black);
                    g.drawString(label, centre[0], centre[1]);
                    break;
            }
        }

    }

    interface Side {
        int NORTH = 0;
        int EAST = 1;
        int SOUTH = 2;
        int WEST = 3;
        int CENTRE = 4;
    }


    private int getMidPoint(int val1, int val2) {
        return (val1 + val2) / 2;
    }

    @Override
    public String getAnswer() {
        return String.valueOf(answer);
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public boolean checkAnswer(String input) {
        double correct = Double.parseDouble(input);
        final double RANGE = 0.1;
        return (correct >= (answer - RANGE)) && (correct <= (answer + RANGE));
    }

    private double[] sides, angles;
    private final double a3 = 90;
    private boolean[] isGivenAngle, isGivenSide, isTarget;


    public RightAngleTrigonometric(String question, char target, double answer, int unit, double[] sides, double[] angles, boolean[] isGivenSide, boolean[] isGivenAngle, int given, boolean[] isTarget) {
        this.question = question;
        this.target = target;
        this.answer = answer;
        this.unit = unit;
        this.sides = sides;
        this.angles = angles;
        this.isGivenAngle = isGivenAngle;
        this.isGivenSide = isGivenSide;
        this.given = given;
        this.isTarget = isTarget;
    }

    @Override
    public String toString() {
        switch (given) {
            case 0:
                return "Find angle given 2 sides";
            case 1:
                return "Find side given 2 sides";
            case 2:
                return "Find angle given 2 angles";
            case 3:
                return "Find side given side and angle";
        }
        return "Trigonometry";
    }

    private String getUnit() {
        switch (unit) {
            case UNIT_MM:
                return "mm";
            case UNIT_CM:
                return "cm";
            case UNIT_M:
                return "m";
            case UNIT_KM:
                return "km";
        }
        return " ";
    }

    public static Object parseDatabaseForm(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    public String toDatabaseForm() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
