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

//        switch (status){
//            case READ:
//                this.sq = getReadableDatabase();
//                break;
//            case WRITE:
//                this.sq = getWritableDatabase();
//                break;
//            default:
//                this.sq = getReadableDatabase();
//        }

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

    public long insertResponent(Respondent respondent){

        this.cv.clear();

        this.cv.put(Respondents.NAME,respondent.NAME);
        this.cv.put(Respondents.GENDER, respondent.GENDER);
        this.cv.put(Respondents.AGE, respondent.AGE);

        long insertStat = this.sq.insert(Respondents.TABLE_NAME,Respondents.ID,this.cv);

        return insertStat;
    }


    public Respondent getRespondentInformation(String ID){

        String q = "SELECT * FROM " + Respondents.TABLE_NAME + " WHERE "
                + Respondents.ID + "='" + ID + "'";

        this.c = this.sq.rawQuery(q,null);

        if(this.c.getCount() > 0){

            Respondent respondent = new Respondent();

            while(this.c.moveToNext()){
                respondent.GENDER = this.c.getString(c.getColumnIndex(Respondents.GENDER));
                respondent.NAME = this.c.getString(c.getColumnIndex(Respondents.NAME));
                respondent.AGE = this.c.getString(c.getColumnIndex(Respondents.AGE));
                respondent.ID = this.c.getString(c.getColumnIndex(Respondents.GENDER));
            }

            return respondent;
        }
        else{
            return null;
        }
    }


}
