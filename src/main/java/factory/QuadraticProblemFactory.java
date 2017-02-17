package factory;

import model.Quadratic;
import module.Util;

public class QuadraticProblemFactory {
    public static Quadratic generateSolveQuadraticQuestion(int rangeA, int rangeB, int rangeX) {
        Quadratic question = generateQuadratic(rangeA, rangeB, rangeX);
        question.setQuestion("Solve for x: ");
        question.setAnswer((String.valueOf(question.getSolution()[0]) + "," + String.valueOf(question.getSolution()[1])).replaceAll("\\.0", ""));
        return question;
    }

    private static Quadratic generateQuadratic(int rangeA, int rangeB, int rangeX) {
        //ax^2 + bx + c
        double b = Util.getRandomNumber(-rangeB, rangeB);
        double x1 = Util.getRandomNumber(-rangeX, rangeX);
        double a = Util.getRandomNumber(-rangeA, rangeA);
        while (a == 0) {
            a = Util.getRandomNumber(-rangeA, rangeA);
        }
        double c = -x1 * (a * x1 + b);
        return new Quadratic(a, b, c);
    }


}
