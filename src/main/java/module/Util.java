package module;

import java.util.concurrent.ThreadLocalRandom;

public class Util {
    public static String superscript(String str) {
        str = str.replaceAll("\\^2", "²");
        return str;
    }

    public static double getRandomNumber(int lowest, int highest) {
        return ThreadLocalRandom.current().nextInt(lowest, highest + 1);

    }

    public static double round(double value, int decimalPlaces) {
        double number = Math.round(value * 100);
        number = number / 100;
        return number;
    }

    public static double toDegrees(double radians) {
        return radians * 180 / Math.PI;
    }

    public static double[] toDegrees(double[] radians) {
        double[] degrees = new double[radians.length];
        for (int i = 0; i < radians.length; i++) {
            degrees[i] = toDegrees(radians[i]);
        }
        return degrees;
    }

}
