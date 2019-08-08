package vsmu.testing.android.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vsmu.testing.android.App;
import vsmu.testing.android.R;
import vsmu.testing.android.model.Answer;
import vsmu.testing.android.model.Question;
import vsmu.testing.android.model.Section;

/**
 * Created by Dan on 05.04.2016.
 */
public class advancedAccreditationSectionAccessor {

    private static final String TABLE_SECTION      = "section";
    private static final String TABLE_QUESTION     = "question";
    private static final String TABLE_ANSWER       = "answer";
    //public static final String COLUMN_TITLE       = "title";
    //public static final String COLUMN_ID          = "id";
    private static final String COLUMN_SECTION  = "section";
    private static final String COLUMN_QUESTION = "question";

    private SQLiteDatabase db;
    private String [] subjectNames;
    private String baseDatabaseFolder;

    private String sectionName;

    advancedAccreditationSectionAccessor(Context context) {
        this.sectionName = "advancedAccreditationSection";
        this.subjectNames = context.getResources().getStringArray(R.array.databaseFileNameAccreditation);
        this.baseDatabaseFolder = String.format("/data/data/%s/databases", context.getPackageName());
        String sectionDatabaseFolder = String.format("%s/%s", this.baseDatabaseFolder, this.sectionName);
        File file = new File(sectionDatabaseFolder);
        if(!file.exists())
            file.mkdirs();

        for(String subjectName : subjectNames){
            String sectionNameAndSubjectName = String.format("%s/%s", this.sectionName, subjectName);
            String subjectDatabaseFileWoExtension = String.format("%s/%s", baseDatabaseFolder, sectionNameAndSubjectName);
            String subjectDatabaseFileWithExtension = subjectDatabaseFileWoExtension + ".db";
            try {
                this.db = SQLiteDatabase.openDatabase(subjectDatabaseFileWithExtension, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READONLY);
                this.db.close();
            } catch (SQLiteException e) {
                copyDB(context, sectionNameAndSubjectName, subjectDatabaseFileWithExtension);
            }
        }
    }

    private void copyDB(Context context, String sectionNameAndSubjectName, String subjectDatabaseFileWithExtension) {
        try {
            String assetFileNameWithExtension = sectionNameAndSubjectName + ".sqlite";
            InputStream dbStream = context.getAssets().open(assetFileNameWithExtension);
            OutputStream output = new FileOutputStream(subjectDatabaseFileWithExtension);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = dbStream.read(buffer)) > 0) {
                output.write(buffer, 0, bytesRead);
            }
            output.close();
            dbStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDB(Context context, int position){
        String subjectName = this.subjectNames[position];
        String sectionNameAndSubjectName = String.format("%s/%s", this.sectionName, subjectName);
        String subjectDatabaseFileWoExtension = String.format("%s/%s", this.baseDatabaseFolder, sectionNameAndSubjectName);
        String subjectDatabaseFileWithExtension = subjectDatabaseFileWoExtension + ".db";
        try {
            this.db = SQLiteDatabase.openDatabase(subjectDatabaseFileWithExtension, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Section> getListSection(){
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_SECTION, null);
        ArrayList<Section> list = new ArrayList<>();
        boolean check = true;
        List<String> checked = Arrays.asList(App.get().getCheckedDiscipline());
        if(c.moveToFirst()){
            do{
                if(!checked.get(0).equals("-1")) check = checked.contains("" + c.getInt(1));
                Cursor c2 = db.rawQuery("SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_SECTION + "='" + c.getInt(1) + "'", null);
                list.add(new Section(c.getString(0), c.getInt(1), c2.getCount(), check));
                c2.close();
            }while(c.moveToNext());
        }
        c.close();
        return list;
    }

    public ArrayList<Integer> getSegmented(){
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_QUESTION, null);
        int step = 50;
        ArrayList<Integer> list = new ArrayList<>();
        if(c.getCount() != 0){
            list.add(10);
            if(c.getCount() > step) list.add(step);
            if(c.getCount() > step * 2) list.add(step * 2);
            list.add(c.getCount());
        }
        c.close();
        return list;
    }


    Question getAnswers(int id_question){
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ANSWER + " WHERE " + COLUMN_QUESTION + " ='" + id_question + "'", null);
        ArrayList<Answer> listAnswer = new ArrayList<>();
        int right_id = 0; String text = "";
        if(c.moveToFirst()){
            do{
                if(c.getInt(1) == 1) {
                    text = c.getString(0);
                    right_id = c.getInt(2);
                }
                listAnswer.add(new Answer(c.getString(0), c.getInt(2)));
            }while(c.moveToNext());
        }
        c.close();
        return new Question(id_question, text, right_id, listAnswer);
    }

    public void insertQuestions(String ids, int limit){
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_SECTION + " IN (" + ids + ") ORDER BY RANDOM() LIMIT " +limit, null);
        if(c.moveToFirst()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                StringBuilder value = new StringBuilder();
                int i = 0;
                do {
                    i++;
                    if (i > 400) {
                        value = new StringBuilder(value.toString().replaceAll("[,]$", ";"));
                        DBHelper.getProgress().insertMulti(value.toString());
                        value = new StringBuilder();
                        i = 0;
                    }
                    value.append("('").append(c.getInt(1)).append("','").append(c.getString(0).replaceAll("'", "''")).append("','-1', '-1', 'NULL', 'NULL'),");
                } while (c.moveToNext());
                if (!value.toString().equals("")) {
                    value = new StringBuilder(value.toString().replaceAll("[,]$", ";"));
                    DBHelper.getProgress().insertMulti(value.toString());
                }
            }else{
                do {
                    DBHelper.getProgress().insert(c.getInt(1), c.getString(0));
                } while (c.moveToNext());
            }
        }
        c.close();
    }

}
