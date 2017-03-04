package util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Miscellaneous Utility functions
 */
public class Utility {
    /**
     * Converts all '2' to '²'. Used for quadratic equations
     *
     * @param str the input string
     * @return the input string with superscripted '2's.
     */
    public static String superscript(String str) {
        str = str.replaceAll("\\^2", "²");
        return str;
    }

    /**
     * Get a random number within a range
     *
     * @param lowest  lower bound on the range
     * @param highest upper bound on the range
     * @return a random number between the lower and upper bound
     */
    public static double getRandomNumber(int lowest, int highest) {
        return ThreadLocalRandom.current().nextInt(lowest, highest + 1);

    }

    /**
     * Round a double
     *
     * @param value         the input double
     * @param decimalPlaces the amount of decimal places to round to
     * @return the input double rounded to the specified decimal places
     */
    public static double round(double value, int decimalPlaces) {
        double number = Math.round(value * 100);
        number = number / 100;
        return number;
    }

    /**
     * Converts radians to degrees
     *
     * @param degrees the input degrees
     * @return the input converted to radians
     */
    public static double toDegrees(double degrees) {
        return degrees * 180 / Math.PI;
    }

    /**
     * Converts degrees to radians
     *
     * @param radians the input radians
     * @return the input converted to degrees
     */
    public static double[] toDegrees(double[] radians) {
        double[] degrees = new double[radians.length];
        for (int i = 0; i < radians.length; i++) {
            degrees[i] = toDegrees(radians[i]);
        }
        return degrees;
    }

}
