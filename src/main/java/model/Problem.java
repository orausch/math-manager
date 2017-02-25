package model;

public abstract class Problem {
    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String getAnswer();
    public abstract String getQuestion();
    public abstract boolean checkAnswer(String input);
}
