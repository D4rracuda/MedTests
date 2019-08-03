package vsmu.testing.android.model;

/**
 * Created by Dan on 06.04.2016.
 */
public class Answer {
    private String text;
    private int id;
    private boolean check;

    public Answer(String text, int id) {
        this.text = text;
        this.id = id;
        this.check = false;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
