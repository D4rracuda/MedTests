package vsmu.testing.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import vsmu.testing.android.App;
import vsmu.testing.android.model.Question;
import vsmu.testing.android.model.Result;

/**
 * Created by Dan on 07.04.2016.
 */
public class dbProgress  {

    private static final String TABLE_PROGRESS        = "progress";
    private static final String COLUMN_TEXT           = "text";
    private static final String COLUMN_ID             = "id";
    private static final String COLUMN_ID_ANSWER      = "id_answer";
    private static final String COLUMN_TRUE_ID_ANSWER = "true_id_answer";
    private static final String COLUMN_TEXT_ANSWER    = "answer";
    private static final String COLUMN_TEXT_TRUE      = "true_answer";

    private SQLiteOpenHelper dbHelper;

    static final String CREATE_TABLE  = "CREATE TABLE " + TABLE_PROGRESS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_TEXT + " TEXT, " + COLUMN_ID_ANSWER + " INTEGER, "
            + COLUMN_TRUE_ID_ANSWER + " INTEGER, " + COLUMN_TEXT_ANSWER + " TEXT, " + COLUMN_TEXT_TRUE + " TEXT)";
    static final String DROP_TABLE    = "DROP TABLE IF EXISTS " + TABLE_PROGRESS;

    dbProgress(SQLiteOpenHelper databaseHelper) {
        this.dbHelper = databaseHelper;
    }

    void insert(int id, String text) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_TEXT, text);
        contentValues.put(COLUMN_ID_ANSWER, "-1");
        db.insertWithOnConflict(TABLE_PROGRESS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    void insertMulti(String value) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO "+ TABLE_PROGRESS +" VALUES " + value);
        db.close();
    }

    public void update(int id, int answer_id, String textAns) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_ANSWER, answer_id);
        contentValues.put(COLUMN_TEXT_ANSWER, textAns);
        db.update(TABLE_PROGRESS, contentValues, COLUMN_ID + " = '" + id + "'", null);
        db.close();
    }

    private void updateTrue(int id, int true_id, String textTrue) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TRUE_ID_ANSWER, true_id);
        contentValues.put(COLUMN_TEXT_TRUE, textTrue);
        db.update(TABLE_PROGRESS, contentValues, COLUMN_ID + " = '" + id +"'", null);
        db.close();
    }

    public void delete_table() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_PROGRESS, null, null);
    }

    public Question getQuestion(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PROGRESS + " WHERE " + COLUMN_ID_ANSWER + " = '" + -1 + "'", null);
        c.moveToFirst();
        db.close();
        if(c.getCount() > 0) {
            Question ans = DBHelper.getData().getAnswers(c.getInt(0));
            updateTrue(c.getInt(0), ans.getRight_id(), ans.getText());
            Question q = new Question(c.getInt(0), c.getString(1), ans.getRight_id(), ans.getAnswers());
            c.close();
            return q;
        }else {
            c.close();
            return null;
        }
    }

    public ArrayList<Result> getResult(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PROGRESS, null);
        ArrayList<Result> list = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                list.add(new Result(c.getString(1), c.getInt(2), c.getInt(3), c.getString(4), c.getString(5)));
            }while(c.moveToNext());
        }
        c.close();
        return list;
    }

    public int getCountTrue(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PROGRESS + " WHERE " + COLUMN_ID_ANSWER + " == " + COLUMN_TRUE_ID_ANSWER, null);
        int count = c.getCount();
        c.close();
        return count;
    }
}
