package vsmu.testing.android.model;

import java.util.ArrayList;

/**
 * Created by Dan on 06.04.2016.
 */
public class Question {
    private int id;
    private String text;
    private int right_id;
    private ArrayList<Answer> answers;

    public Question(int id, String text, int right_id, ArrayList<Answer> answers) {
        this.id = id;
        this.text = text;
        this.right_id = right_id;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getRight_id() {
        return right_id;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

}
