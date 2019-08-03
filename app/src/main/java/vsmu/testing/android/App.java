package vsmu.testing.android;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import vsmu.testing.android.database.DBHelper;

/**
 * Created by Dan on 05.04.2016.
 */
public class App extends Application {

    private static final String EXAM_KEY       = "exam";
    private static final String SAVEKEY        = "save";
    private static final String NUMBER         = "number";
    private static final String MAX            = "max";
    private static final String DISCIPLINE_KEY = "discipline";
    private static final String NOTSHOW_KEY    = "notshow";
    private static final String MONTH_KEY      = "month";

    private SharedPreferences preferences;
    private static App instance;

    public static App get() {
        return instance;
    }

    public int select_id;
    public DBHelper db;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = new DBHelper(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void setExam(boolean b) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(EXAM_KEY, b);
        editor.commit();
    }
    public boolean isExam() {
        return preferences.getBoolean(EXAM_KEY, false);
    }

    public void setSave(boolean b) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SAVEKEY, b);
        editor.commit();
    }
    public boolean isSave() {
        return preferences.getBoolean(SAVEKEY, false);
    }

    public void setDisciplinePosition(int p){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(DISCIPLINE_KEY, p);
        editor.commit();
    }
    public int getDisciplinePosition(){
        return preferences.getInt(DISCIPLINE_KEY, 0);
    }

    public void setCheckedDiscipline(ArrayList<Integer> arr){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DISCIPLINE_KEY + getDisciplinePosition(), TextUtils.join(",", arr));
        editor.commit();
    }
    public String[] getCheckedDiscipline(){
        return preferences.getString(DISCIPLINE_KEY + getDisciplinePosition(), "-1").split(",");
    }

    public void setNumber(int p){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(NUMBER, p);
        editor.commit();
    }
    public int getNumber(){
        return preferences.getInt(NUMBER, 0);
    }

    public void setMax(int p){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(MAX, p);
        editor.commit();
    }
    public int getMax(){
        return preferences.getInt(MAX, -1);
    }

    public void setShowDialog(boolean b) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(NOTSHOW_KEY, b);
        editor.commit();
    }
    public boolean isShowDialog() {
        return preferences.getBoolean(NOTSHOW_KEY, true);
    }

    public void setMonthLater() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(MONTH_KEY, System.currentTimeMillis());
        editor.commit();
    }
    public boolean isMonthLaterDialog() {
        long days0 = TimeUnit.MILLISECONDS.toDays(preferences.getLong(MONTH_KEY, System.currentTimeMillis()));
        long days1 = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis());
        return (days1 - days0) >= 30;
    }
}
