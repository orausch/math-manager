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
        return input.equals(answer);
    }

    @Override
    public String toString() {
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Text text = (Text) o;

        if (question != null ? !question.equals(text.question) : text.question != null) return false;
        return answer != null ? answer.equals(text.answer) : text.answer == null;
    }

}
