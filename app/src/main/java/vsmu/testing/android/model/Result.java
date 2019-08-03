package vsmu.testing.android.model;

/**
 * Created by Dan on 07.04.2016.
 */
public class Result {
    private String text;
    private int id_answer;
    private int id_right;
    private String answer;
    private String true_answer;

    public Result(String text, int id_answer, int id_right, String answer, String true_answer) {
        this.text = text;
        this.id_answer = id_answer;
        this.id_right = id_right;
        this.answer = answer;
        this.true_answer = true_answer;
    }

    public String getText() {
        return text;
    }

    public boolean isRight() {
        return id_right == id_answer;
    }

    public String getTrue_answer() {
        return true_answer;
    }

    public String getAnswer() {
        return answer;
    }
}
