package Generators;

import model.Problem;
import model.Quadratic;

public class QuadraticProblemGenerator {
    public static Quadratic generateSolveQuadraticQuestion(int range) {
        Quadratic question = generateQuadratic(range);
        question.setQuestion("Solve for x: " + question);
        question.setAnswer((String.valueOf(question.getSolution()[0]) + "," + String.valueOf(question.getSolution()[1])).replaceAll("\\.0", ""));
        return question;
    }

    public static Quadratic generateFactoriseQuadraticQuestion(int range) {
        Quadratic question = generateQuadratic(range);
        question.setAnswer(("(x" +
                (question.getSolution()[0] > 0 ? "+" + question.getSolution()[0] : question.getSolution()[0]) +
                ")(x" +
                (question.getSolution()[1] > 0 ? "+" + question.getSolution()[1] : question.getSolution()[1]) +
                ")"
        ).replaceAll("\\.0", ""));

        question.setQuestion("Factorise: " + question);
        return question;
    }

    private static Quadratic generateQuadratic(int range) {
        double b = RandomNumber.getRandomNumber(-range, range);
        double x1 = RandomNumber.getRandomNumber(-range, range);
        double a = RandomNumber.getRandomNumber(-2, 2);
        while (a == 0) {
            a = RandomNumber.getRandomNumber(-2, 2);
        }
        double c = -x1 * (a * x1 + b);
        return new Quadratic(a, b, c);
    }


}
