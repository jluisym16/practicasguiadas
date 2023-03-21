package com.example.practicaguiada16;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BibliotecaSQLiteHelper extends SQLiteOpenHelper {
    Context context;

    public BibliotecaSQLiteHelper(Context context, int version) {
        super(context, "biblioteca.db", null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        InputStream stream = context.getResources().openRawResource(R.raw.biblioteca);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String sql;
            while ((sql = br.readLine()) != null)
                db.execSQL(sql);
        } catch (IOException e) {
            Log.e("/raw/biblioteca.sql", e.getLocalizedMessage());
        } catch (SQLException e) {
            Log.e("SQL", e.getLocalizedMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

