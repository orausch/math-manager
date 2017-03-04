package factory;

import model.Quadratic;
import util.Utility;

public class QuadraticProblemFactory {
    public static Quadratic generateSolveQuadraticQuestion(int rangeA, int rangeB, int rangeX) {
        Quadratic question = generateQuadratic(rangeA, rangeB, rangeX);
        question.setId(1);
        question.setAnswer((String.valueOf(question.getSolution()[0]) + "," + String.valueOf(question.getSolution()[1])).replaceAll("\\.0", ""));
        return question;
    }

    private static Quadratic generateQuadratic(int rangeA, int rangeB, int rangeX) {
        //ax^2 + bx + c
        double b = Utility.getRandomNumber(-rangeB, rangeB);
        double x1 = Utility.getRandomNumber(-rangeX, rangeX);
        double a = Utility.getRandomNumber(-rangeA, rangeA);
        while (a == 0) {
            a = Utility.getRandomNumber(-rangeA, rangeA);
        }
        double c = -x1 * (a * x1 + b);
        return new Quadratic(a, b, c);
    }


}
