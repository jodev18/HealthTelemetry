package com.software.amazing.emotiontelemetry.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by myxroft on 10/02/2018.
 */

public class PulseRateManager extends DataSetDB {

    private SQLiteDatabase sq;
    private Cursor c;
    private ContentValues cv;

    public static final int READ = 1;
    public static final int WRITE = 2;

    public PulseRateManager(Context ct, int status) {
        super(ct);

        switch (status){
            case READ:
                this.sq = getReadableDatabase();
                break;
            case WRITE:
                this.sq = getWritableDatabase();
                break;
            default:
                this.sq = getReadableDatabase();
        }

        this.cv = new ContentValues();
    }
}
