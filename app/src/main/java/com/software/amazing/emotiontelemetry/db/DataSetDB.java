package com.software.amazing.emotiontelemetry.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.software.amazing.emotiontelemetry.Emotions;

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

    protected class Emotion{

        public static final String TABLE_NAME = "tbl_emotion";

        public static final String ID = "_id";

        public static final String EMOTION_NAME = "emotion_name";

        public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EMOTION_NAME + " TEXT);";
    }

    protected class Session{

        public static final String TABLE_NAME = "tbl_session";

        public static final String ID = "_id";

        public static final String USER_ID = "sess_user_id";

        public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USER_ID + " INTEGER);";

    }

    protected class PulseRate{

        public static final String TABLE_NAME = "tbl_pulse";

        public static final String ID = "_id";

        public static final String PULSE_RATE = "pulse";

        public static final String RESPONDENT_ID = "resp_id";

        public static final String SESSION_ID = "sess_id";

        public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PULSE_RATE + " INTEGER,"
                + SESSION_ID + " INTEGER,"
                + RESPONDENT_ID + " INTEGER);";

    }

    protected class Temperatures{

        public static final String TABLE_NAME = "tbl_temps";

        public static final String ID = "_id";

        public static final String SKIN_TEMP = "skin_temp";

        public static final String ENVIRONMENT_TEMP = "env_temp";

        public static final String RESPONDENT_ID = "resp_id";

        public static final String SESSION_ID = "sess_id";

        public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SKIN_TEMP + " INTEGER,"
                + ENVIRONMENT_TEMP + " INTEGER,"
                + SESSION_ID +  " INTEGER,"
                + RESPONDENT_ID + " INTEGER);";
    }

    protected class Login{

        public static final String TABLE_NAME = "tbl_login";

        public static final String ID = "_id";

        public static final String USERNAME = "login_username";
        public static final String PASSWORD = "login_password";
        public static final String RESPONDENT_ID = "resp_id";

        public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USERNAME + " TEXT,"
                + RESPONDENT_ID + " INTEGER,"
                + PASSWORD + " TEXT);";
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(Respondents.TABLE_CREATE);
        sqLiteDatabase.execSQL(PulseRate.TABLE_CREATE);
        sqLiteDatabase.execSQL(Emotion.TABLE_CREATE);
        sqLiteDatabase.execSQL(Session.TABLE_CREATE);
        sqLiteDatabase.execSQL(Temperatures.TABLE_CREATE);
        sqLiteDatabase.execSQL(Login.TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
