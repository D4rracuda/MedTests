package vsmu.testing.android.model;

/**
 * Created by Dan on 05.04.2016.
 */
public class Section {
    private String name;
    private int id;
    private int countQuestions;
    private boolean check;

    public Section(String name, int id, int count, boolean check) {
        this.name = name;
        this.id = id;
        this.countQuestions = count;
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getCountQuestions() {
        return countQuestions;
    }
}
