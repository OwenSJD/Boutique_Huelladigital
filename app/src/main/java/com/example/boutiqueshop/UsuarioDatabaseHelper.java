package com.example.boutiqueshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class UsuarioDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "usuarios";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_CONTRASEÑA = "contraseña";

    public UsuarioDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No es necesario crear una nueva tabla, ya que se supone que la tabla 'usuarios' ya existe.
        // Si la tabla no existe, se lanzará una excepción y se deberá manejar adecuadamente.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No es necesario realizar ninguna actualización de la tabla existente.
        // Si se requiere una actualización en la estructura de la tabla, se deberá manejar adecuadamente.
    }

    public boolean agregarUsuario(String email, String contraseña) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_CONTRASEÑA, contraseña);

        long resultado = db.insert(TABLE_NAME, null, values);
        return resultado != -1;
    }

    public boolean actualizarContraseña(String email, String nuevaContraseña) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTRASEÑA, nuevaContraseña);

        int filasActualizadas = db.update(TABLE_NAME, values, COLUMN_EMAIL + "=?", new String[]{email});
        return filasActualizadas > 0;
    }

    public boolean existeUsuario(String email, String contraseña) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_CONTRASEÑA + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email, contraseña});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public boolean verificarCredenciales(String email, String contraseña) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_CONTRASEÑA + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email, contraseña});
        boolean credencialesCorrectas = cursor.getCount() > 0;
        cursor.close();
        return credencialesCorrectas;
    }
}