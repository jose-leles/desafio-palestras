package br.com.joseleles.fiapdesafio.controllers.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

public class UsuarioDAO {
    private SQLiteDatabase db;
    private DatabaseHelper banco;

    public UsuarioDAO(@NonNull Context context){
        banco = new DatabaseHelper(context);
    }

    public void insertEmail(String email){
        ContentValues valores;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(DatabaseHelper.FIELD_EMAIL, email);

        db.insert(DatabaseHelper.TABLE_NAME, null, valores);
        db.close();
    }

    public void deleteOthersEmail(){
        db = banco.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, null, null);
        db.close();
    }

    public String getEmail(){
        String email=null;
        String[] campos =  {banco.FIELD_EMAIL};
        db = banco.getReadableDatabase();
        Cursor cursor = db.query(banco.TABLE_NAME, campos, null, null, null, null, null, null);

        if(cursor!=null){
            if(cursor.moveToFirst()){
                int indexEmail = cursor.getColumnIndex(banco.FIELD_EMAIL);
                email = cursor.getString(indexEmail);
            }
        }
        db.close();
        return email==null? "" : email;
    }
}
