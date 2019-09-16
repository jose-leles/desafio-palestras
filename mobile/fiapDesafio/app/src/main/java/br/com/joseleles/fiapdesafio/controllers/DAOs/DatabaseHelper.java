package br.com.joseleles.fiapdesafio.controllers.DAOs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by allanromanato on 5/27/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String table_name = "usuario";
    private static int version_bd = 1;
    private Context context;

    public DatabaseHelper(Context context){
        super(context,table_name,null,version_bd);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
