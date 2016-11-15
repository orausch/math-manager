package model;

public class Text extends Problem {
    private String question, answer;

    public Text(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public boolean checkAnswer(String input) {
        return input.equals(input.trim().replaceAll("\\.0", ""));
    }

    @Override
    public String toString() {
        return question;
    }
}
