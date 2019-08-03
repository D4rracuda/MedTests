package vsmu.testing.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dan on 05.04.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TestingBase.db";
    private static final int    DB_VERSION    = 1;

    private final dbTesting testing;
    private final dbProgress progress;

    private static DBHelper instance;

    public static DBHelper get() {
        return instance;
    }

    public static dbTesting getData(){
        return get().testing;
    }

    public static dbProgress getProgress() {
        return get().progress;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        instance = this;
        progress = new dbProgress(this);
        testing = new dbTesting(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(dbProgress.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dbProgress.DROP_TABLE);
        onCreate(db);
    }
}
