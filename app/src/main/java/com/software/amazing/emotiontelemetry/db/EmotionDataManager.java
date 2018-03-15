package com.software.amazing.emotiontelemetry.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.software.amazing.emotiontelemetry.objects.EmotionDataObject;

/**
 * Created by myxroft on 16/03/2018.
 */

public class EmotionDataManager extends DataSetDB {

    private SQLiteDatabase sq;
    private Cursor c;
    private ContentValues cv;

    public EmotionDataManager(Context ct) {
        super(ct);

        this.sq = getWritableDatabase();
        this.cv = new ContentValues();
    }

    public void cleanUp(){

        if(this.cv != null){
            this.cv.clear();
        }

        if(this.sq != null){
            if(this.sq.isOpen()){
                this.sq.close();
            }
        }
    }

//    public long insertEmotionData(EmotionDataObject emotionDataObject){
//
//    }
}
