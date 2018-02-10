package com.software.amazing.emotiontelemetry.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.software.amazing.emotiontelemetry.objects.Respondent;

/**
 * Created by myxroft on 10/02/2018.
 */

public class RespondentManager extends DataSetDB {

    private SQLiteDatabase sq;
    private Cursor c;
    private ContentValues cv;

    public static final int READ = 1;
    public static final int WRITE = 2;

    private int currstat = 0;

    public RespondentManager(Context ct, int status) {
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

    public long insertResponent(Respondent respondent){

        if(currstat > 0){
            if(currstat == WRITE){

                this.cv.clear();

                this.cv.put(Respondents.NAME,respondent.NAME);
                this.cv.put(Respondents.GENDER, respondent.GENDER);
                this.cv.put(Respondents.AGE, respondent.AGE);

                long insertStat = this.sq.insert(Respondents.TABLE_NAME,Respondents.ID,this.cv);

                return insertStat;
            }
            else{
                throw new IllegalArgumentException("Set to write before inserting!");
            }
        }
        else{
            throw new IllegalArgumentException("Set to write before inserting!");
        }
    }


}
