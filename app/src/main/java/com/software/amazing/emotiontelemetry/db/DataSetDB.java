package com.software.amazing.emotiontelemetry.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by myxroft on 07/10/2017.
 */

public class DataSetDB extends SQLiteOpenHelper{


    public DataSetDB(Context ct){
        super(ct,"emotion_dataset.db",null,1);
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
