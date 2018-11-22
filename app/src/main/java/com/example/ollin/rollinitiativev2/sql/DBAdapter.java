package com.example.ollin.rollinitiativev2.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.ollin.rollinitiativev2.model.Spacecraft;

import java.util.ArrayList;

public class DBAdapter {
    Context context;
    SQLiteDatabase database;
    DBHelper helper;

    //INICIALIZAR LA DB Y PASARLA AL CONTEXTO

    public DBAdapter(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    //GUARDAR DATOS EN LA BD
    public boolean saveSpacecraft(Spacecraft spacecraft){
        try{
            database = helper.getWritableDatabase();
            ContentValues contentValues =new ContentValues();
            contentValues.put(Constants.NAME, spacecraft.getName());
            contentValues.put(Constants.CATEGORY, spacecraft.getCategory());

            long result = database.insert(Constants.TB_NAME,Constants.ROW_ID,contentValues);
            if(result > 0){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            helper.close();
        }
        return false;
    }

    //OBTENER RESULTADOS DE SQL Y POPULAR LA ARRAYLIST
    public ArrayList<Spacecraft> retrieveSpacecrafts(String category){
        ArrayList<Spacecraft> spacecrafts=new ArrayList<>();

        try {
            database = helper.getWritableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM "+ Constants.TB_NAME
                    + " WHERE " + Constants.CATEGORY + " = '"+ category + "'",null);

            Spacecraft spacecraft;
            spacecrafts.clear();

            while(cursor.moveToNext()){
                String s_name = cursor.getString(1);
                String s_category = cursor.getString(2);

                spacecraft = new Spacecraft();
                spacecraft.setName(s_name);
                spacecraft.setCategory((s_category));
                spacecrafts.add(spacecraft);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            helper.close();
        }

        return spacecrafts;
    }
    public ArrayList<Spacecraft> retrieveAll(String category){
        ArrayList<Spacecraft> spacecrafts=new ArrayList<>();

        try {
            database = helper.getWritableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM "+ Constants.TB_NAME,null);

            Spacecraft spacecraft;
            spacecrafts.clear();

            while(cursor.moveToNext()){
                String s_name = cursor.getString(1);
                String s_category = cursor.getString(2);

                spacecraft = new Spacecraft();
                spacecraft.setName(s_name);
                spacecraft.setCategory((s_category));
                spacecrafts.add(spacecraft);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            helper.close();
        }

        return spacecrafts;
    }
}
