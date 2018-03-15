package com.software.amazing.emotiontelemetry.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.software.amazing.emotiontelemetry.core.Globals;
import com.software.amazing.emotiontelemetry.objects.SessionObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by myxroft on 10/02/2018.
 */

public class SessionManager extends DataSetDB {

    private SQLiteDatabase sq;
    private Cursor c;
    private ContentValues cv;

    public static final int READ = 1;
    public static final int WRITE = 2;

    public SessionManager(Context ct) {
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

    public SessionObject getNewSession(String user_id, String gender){

        this.cv.clear();

        Calendar cal = Calendar.getInstance();

        Integer mo = cal.get(Calendar.MONTH) + 1;
        Integer dd = cal.get(Calendar.DATE);
        Integer yr = cal.get(Calendar.YEAR);

        String date = mo.toString() + "/" + dd.toString() + "/" + yr.toString();

        String timeStamp = cal.getTime().toString();

        this.cv.put(Session.USER_ID, user_id);
        this.cv.put(Session.GENDER, gender);
        this.cv.put(Session.SESSION_DATE, date);
        this.cv.put(Session.TIMESTAMP,timeStamp);

        long sess_stat = this.sq.insert(Session.TABLE_NAME,Session.ID,this.cv);

        if(sess_stat > 0){

            SessionObject sessionObject = new SessionObject();

            sessionObject.GENDER = gender;
            sessionObject.DATE = date;
            sessionObject.TIMESTAMP = timeStamp;
            sessionObject.USER_ID = user_id;
            sessionObject.ID = Long.valueOf(sess_stat).toString();


            return sessionObject;
        }
        else{
            return null;
        }
    }

    /**
     * Fetches all recorded session for a specific user from the database.
     *
     * returns null if none found
     * @return
     */
    public List<SessionObject> getAllSessionsByUser(String user_id){

        String query = "SELECT * FROM " + Session.TABLE_NAME + " WHERE " + Session.USER_ID + "='" + user_id + "'";

        this.c  = this.sq.rawQuery(query,null);

        if(this.c.getCount() > 0){

            List<SessionObject> sessionObjects = new ArrayList<>();

            while(c.moveToNext()){
                //Handle data here

                SessionObject sObj = new SessionObject();

                sObj.ID = c.getString(c.getColumnIndex(Session.ID));
                sObj.USER_ID = c.getString(c.getColumnIndex(Session.USER_ID));
                sObj.TIMESTAMP = c.getString(c.getColumnIndex(Session.TIMESTAMP));
                sObj.DATE = c.getString(c.getColumnIndex(Session.SESSION_DATE));
                sObj.GENDER = c.getString(c.getColumnIndex(Session.GENDER));


                sessionObjects.add(sObj);
            }

            return sessionObjects;
        }
        else{
            return null;
        }
    }

    public List<SessionObject> getAllSessionsRecorded(){

        String query = "SELECT * FROM " + Session.TABLE_NAME;

        this.c  = this.sq.rawQuery(query,null);

        if(this.c.getCount() > 0){

            List<SessionObject> sessionObjects = new ArrayList<>();

            while(c.moveToNext()){
                //Handle data here

                SessionObject sObj = new SessionObject();

                sObj.ID = c.getString(c.getColumnIndex(Session.ID));
                sObj.USER_ID = c.getString(c.getColumnIndex(Session.USER_ID));
                sObj.TIMESTAMP = c.getString(c.getColumnIndex(Session.TIMESTAMP));
                sObj.DATE = c.getString(c.getColumnIndex(Session.SESSION_DATE));
                sObj.GENDER = c.getString(c.getColumnIndex(Session.GENDER));


                sessionObjects.add(sObj);
            }

            return sessionObjects;
        }
        else{
            return null;
        }

    }

    public List<SessionObject> getAllSessionRecordedFemale(){

        String query = "SELECT * FROM " + Session.TABLE_NAME + " WHERE " + Session.GENDER + "='" + Globals.CONST_FEMALE + "'";

        this.c  = this.sq.rawQuery(query,null);

        if(this.c.getCount() > 0){

            List<SessionObject> sessionObjects = new ArrayList<>();

            while(c.moveToNext()){
                //Handle data here

                SessionObject sObj = new SessionObject();

                sObj.ID = c.getString(c.getColumnIndex(Session.ID));
                sObj.USER_ID = c.getString(c.getColumnIndex(Session.USER_ID));
                sObj.TIMESTAMP = c.getString(c.getColumnIndex(Session.TIMESTAMP));
                sObj.DATE = c.getString(c.getColumnIndex(Session.SESSION_DATE));
                sObj.GENDER = c.getString(c.getColumnIndex(Session.GENDER));


                sessionObjects.add(sObj);
            }

            return sessionObjects;
        }
        else{
            return null;
        }
    }

    public List<SessionObject> getAllSessionRecordedMale(){

        String query = "SELECT * FROM " + Session.TABLE_NAME + " WHERE " + Session.GENDER + "='" + Globals.CONST_MALE + "'";

        this.c  = this.sq.rawQuery(query,null);

        if(this.c.getCount() > 0){

            List<SessionObject> sessionObjects = new ArrayList<>();

            while(c.moveToNext()){
                //Handle data here

                SessionObject sObj = new SessionObject();

                sObj.ID = c.getString(c.getColumnIndex(Session.ID));
                sObj.USER_ID = c.getString(c.getColumnIndex(Session.USER_ID));
                sObj.TIMESTAMP = c.getString(c.getColumnIndex(Session.TIMESTAMP));
                sObj.DATE = c.getString(c.getColumnIndex(Session.SESSION_DATE));
                sObj.GENDER = c.getString(c.getColumnIndex(Session.GENDER));


                sessionObjects.add(sObj);
            }

            return sessionObjects;
        }
        else{
            return null;
        }
    }




}
