package factory;

import model.Quadratic;
import module.Util;

public class QuadraticProblemFactory {
    public static Quadratic generateSolveQuadraticQuestion(int range) {
        Quadratic question = generateQuadratic(range);
        question.setQuestion("Solve for x: " + question);
        question.setAnswer((String.valueOf(question.getSolution()[0]) + "," + String.valueOf(question.getSolution()[1])).replaceAll("\\.0", ""));
        return question;
    }

    public static Quadratic generateFactoriseQuadraticQuestion(int range) {
        Quadratic question = generateQuadratic(range);
        question.setAnswer(question.toFactorisedForm());
        question.setQuestion("Factorise: " + question);
        return question;
    }

    private static Quadratic generateQuadratic(int range) {
        //ax^2 + bx + c
        double b = Util.getRandomNumber(-range, range);
        double x1 = Util.getRandomNumber(-range, range);
        double a = Util.getRandomNumber(-2, 2);
        while (a == 0) {
            a = Util.getRandomNumber(-2, 2);
        }
        double c = -x1 * (a * x1 + b);
        return new Quadratic(a, b, c);
    }


}
