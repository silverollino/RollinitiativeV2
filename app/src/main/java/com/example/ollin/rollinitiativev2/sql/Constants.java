package com.example.ollin.rollinitiativev2.sql;

public class Constants {

    //COLUMNAS
    static final String ROW_ID="id";
    static final String NAME="name";
    static final String CATEGORY="category";

    //DB PROPERTIES
    static final String DB_NAME="lv_DB"; //NOMBRE DE LA BASE DE DATOS
    static final String TB_NAME="lv_TB"; //NOMBRE DE LA TABLA
    static final int DB_VERSION=1;

    //CREACION DE TABLA
    static final String CREATE_TB="CREATE TABLE " + TB_NAME
            + "(" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME + " TEXT NOT NULL,"
            + CATEGORY + " TEXT NOT NULL);";

    //BORRAR TABLA
    static final String DROP_TB="DROP TABLE IF EXISTS "+ TB_NAME;

}
