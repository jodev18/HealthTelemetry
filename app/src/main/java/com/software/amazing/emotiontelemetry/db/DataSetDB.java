package com.software.amazing.emotiontelemetry.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by myxroft on 07/10/2017.
 *
 * DATABASE INITIALIZATION ONLY. DO NOT PUT CRUD ACTIONS HERE.
 */

public class DataSetDB extends SQLiteOpenHelper{


    public DataSetDB(Context ct){
        super(ct,"emotion_dataset.db",null,1);
    }

    /**
     * Schema for respondents
     */
    protected class Respondents{

        public static final String TABLE_NAME = "tbl_respondent";

        public static final String ID = "resp_id";

        public static final String NAME = "resp_name";

        public static final String AGE = "resp_age";

        public static final String GENDER = "resp_gender";

        public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " TEXT,"
                + AGE + " TEXT,"
                + GENDER + " TEXT);";

    }

    protected class PulseRate{

    }

    protected class SkinTemp{

    }

    protected class EnvironmentTemp{

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
