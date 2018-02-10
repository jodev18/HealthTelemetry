package com.software.amazing.emotiontelemetry.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.software.amazing.emotiontelemetry.objects.LoginObject;

/**
 * Created by myxroft on 10/02/2018.
 */

public class LoginManager extends DataSetDB {

    private SQLiteDatabase sq;
    private Cursor c;
    private ContentValues cv;

    public static final int READ = 1;
    public static final int WRITE = 2;

    public LoginManager(Context ct, int status) {
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

    public long insertUser(LoginObject login){

        //To prevent any mashups...
        this.cv.clear();

        if(login != null){

            this.cv.put(Login.USERNAME,login.USERNAME);
            this.cv.put(Login.PASSWORD,login.PASSWORD);
            this.cv.put(Login.RESPONDENT_ID, login.RESPONDENT_ID);

            long inStat = this.sq.insert(Login.TABLE_NAME,Login.ID,this.cv);

            return inStat;

        }
        else{
            return 0; //
        }
    }
}
