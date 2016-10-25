package model;

/**
 * Created by oliver on 20/10/16.
 */
public class Quadratic extends Problem {
    private double a;
    private double b;
    private double c;
    private String answer;

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    private String question;

    public Quadratic(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public double[] getSolution() {
        double[] solutions = new double[2];
        solutions[0] = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
        solutions[1] = (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);

        for (int i = 0; i < solutions.length; i++) {
            if(solutions[i] == 0){
                solutions[i] = Math.abs(solutions[i]);
            }
        }
        return solutions;
    }

    @Override
    public String toString() {
        String a = String.valueOf(this.a);
        if (this.a == 1) {
            a = "";
        } else if (this.a == -1) {
            a = "-";
        }
        String b = this.a != 0 ? addSign(this.b) : String.valueOf(this.b);

        if (this.b == 1 && this.a != 0) {
            b = "+";
        } else if (this.b == 1) {
            b = "";
        } else if (this.b == -1) {
            b = "-";
        }

        StringBuilder sb = new StringBuilder();
        if (this.a != 0) {
            sb.append(a + "x^2");
        }
        if (this.b != 0) {
            sb.append(b + "x");
        }
        if (this.c != 0) {
            sb.append(addSign(c));
        }
        return sb.toString().replaceAll("\\.0", "");
    }

    private String addSign(double d) {
        if (d >= 0) {
            return "+" + d;
        } else {
            return String.valueOf(d);
        }
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public String getQuestion() {
        return question;
    }
}
