package br.com.joseleles.fiapdesafio.controllers.DAOs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by allanromanato on 5/27/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static String TABLE_NAME = "usuario";
    public static String FIELD_EMAIL = "email";
    private static int VERSION_BD = 1;

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME,null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABLE_NAME +"("
                +"id integer primary key autoincrement,"
                +"email text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
