package com.lgroup.ltms.com.lgroup.ltms.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lgroup.ltms.com.lgroup.ltms.models.Content;
import com.lgroup.ltms.com.lgroup.ltms.models.Learning;
import com.lgroup.ltms.com.lgroup.ltms.models.Period;
import com.lgroup.ltms.com.lgroup.ltms.models.Question;
import com.lgroup.ltms.com.lgroup.ltms.models.Setting;
import com.lgroup.ltms.com.lgroup.ltms.models.Unit;
import com.lgroup.ltms.com.lgroup.ltms.models.Unit_Detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Work on 22/09/2015.
 */
public class ltmsDataSource {

    public static final String LOGTAG = "mylogtag";

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    public ltmsDataSource(Context context){
        dbHelper =new ltmsDBOpenHelper(context);

    }



    private static final String[] Period_AllColumn ={
            ltmsDBOpenHelper.PERIOD_COLUMN_ID,
            ltmsDBOpenHelper.PERIOD_COLUMN_TITLE,
            ltmsDBOpenHelper.PERIOD_COLUMN_DESC,
            ltmsDBOpenHelper.PERIOD_COLUMN_DATESTART,
            ltmsDBOpenHelper.PERIOD_COLUMN_DATEEND,
            ltmsDBOpenHelper.PERIOD_COLUMN_TESTTIME,
            ltmsDBOpenHelper.PERIOD_COLUMN_STATE,
            ltmsDBOpenHelper.PERIOD_COLUMN_LICENCE
    };

    private static final String[] Learning_AllColumn ={
            ltmsDBOpenHelper.LEARNING_COLUMN_ID,
            ltmsDBOpenHelper.LEARNING_COLUMN_UDID,
            ltmsDBOpenHelper.LEARNING_COLUMN_ContentID,
            ltmsDBOpenHelper.LEARNING_COLUMN_CONTENT,
            ltmsDBOpenHelper.LEARNING_COLUMN_DESC,
            ltmsDBOpenHelper.LEARNING_COLUMN_STARTPAGE,
            ltmsDBOpenHelper.LEARNING_COLUMN_PAGENUMBER,
            ltmsDBOpenHelper.LEARNING_COLUMN_STATE
    };

    private static final String[] Unit_Detail_AllColumn ={
            ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_ID,
            ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_UID,
            ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_NAME,
            ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_STARTPAGE,
            ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_ENDPAGE,
            ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_STATE
    };

    private static final String[] Unit_AllColumn ={
            ltmsDBOpenHelper.UNIT_COLUMN_ID,
            ltmsDBOpenHelper.UNIT_COLUMN_PID,
            ltmsDBOpenHelper.UNIT_COLUMN_NAME,
            ltmsDBOpenHelper.UNIT_COLUMN_DESC,
            ltmsDBOpenHelper.UNIT_COLUMN_STATE
    };

    private static final String[] Setting_AllColumn ={
            ltmsDBOpenHelper.SETTING_COLUMN_ID,
            ltmsDBOpenHelper.SETTING_COLUMN_NAME,
            ltmsDBOpenHelper.SETTING_COLUMN_VALUE
    };



    private static final String[] Question_AllColumn ={
            ltmsDBOpenHelper.QUESTION_COLUMN_ID,
            ltmsDBOpenHelper.QUESTION_COLUMN_UID,
            ltmsDBOpenHelper.QUESTION_COLUMN_QUESTION,
            ltmsDBOpenHelper.QUESTION_COLUMN_SEL1,
            ltmsDBOpenHelper.QUESTION_COLUMN_SEL2,
            ltmsDBOpenHelper.QUESTION_COLUMN_SEL3,
            ltmsDBOpenHelper.QUESTION_COLUMN_SEL4,
            ltmsDBOpenHelper.QUESTION_COLUMN_ANSWER,
            ltmsDBOpenHelper.QUESTION_COLUMN_TRUESEL,
            ltmsDBOpenHelper.QUESTION_COLUMN_STATE
    };

    private static final String[] Content_AllColumn ={
            ltmsDBOpenHelper.QUESTION_COLUMN_ID,
            ltmsDBOpenHelper.QUESTION_COLUMN_UID,
            ltmsDBOpenHelper.QUESTION_COLUMN_QUESTION,
            ltmsDBOpenHelper.QUESTION_COLUMN_SEL1,
            ltmsDBOpenHelper.QUESTION_COLUMN_SEL2,
            ltmsDBOpenHelper.QUESTION_COLUMN_SEL3,
            ltmsDBOpenHelper.QUESTION_COLUMN_SEL4,
            ltmsDBOpenHelper.QUESTION_COLUMN_ANSWER,
            ltmsDBOpenHelper.QUESTION_COLUMN_TRUESEL,
            ltmsDBOpenHelper.QUESTION_COLUMN_STATE
    };

    public void open(){
        Log.i(LOGTAG, "com.lgroup.ltms.database opened");
        database= dbHelper.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "com.lgroup.ltms.database closed");
        dbHelper.close();
    }





    // Unit-----------------------------------------------------------------------------------------






    //Setting---------------------------------------------------------------------------------------

    public Setting Setting_Insert(Setting setting){
        ContentValues values = new ContentValues();
        values.put(ltmsDBOpenHelper.SETTING_COLUMN_NAME,setting.getName());
        values.put(ltmsDBOpenHelper.SETTING_COLUMN_VALUE,setting.getValue());

        long insertid = database.insert(ltmsDBOpenHelper.TABLE_SETTING, null, values);
        setting.setId(insertid);
        return  setting;
    }

    public boolean Profile_Exist(){
        boolean flag = false;
        String sql="select "+ltmsDBOpenHelper.SETTING_COLUMN_ID+" from " + ltmsDBOpenHelper.TABLE_SETTING + " where " + ltmsDBOpenHelper.SETTING_COLUMN_NAME + " = '" + ltmsDBOpenHelper.Profile_ProfileExist+"' ;";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            flag=true;
        }
        else {
            flag=false;
        }

        return flag;
    }



    public int Setting_Update(Setting setting){
        ContentValues values = new ContentValues();
        //values.put(ltmsDBOpenHelper.SETTING_COLUMN_NAME,setting.getName());
        values.put(ltmsDBOpenHelper.SETTING_COLUMN_VALUE, setting.getValue());

        int rows = database.update(ltmsDBOpenHelper.TABLE_SETTING, values, ltmsDBOpenHelper.SETTING_COLUMN_NAME + " = '" + setting.getName() + "'", null);
        //setting.setId(insertid);
        return  rows;
    }

    public String Setting_Select(String settingName){
        String value;

        Cursor cursor = database.rawQuery("select " + ltmsDBOpenHelper.SETTING_COLUMN_VALUE + " from " + ltmsDBOpenHelper.TABLE_SETTING + " where " + ltmsDBOpenHelper.SETTING_COLUMN_NAME + " = '" + settingName + "'", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            value=cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.SETTING_COLUMN_VALUE));
        }
        else {
            value="null";
        }
        //setting.setId(insertid);
        return  value;
    }

    //Period ---------------------------------------------------------------------------------------

    public boolean Peiod_Exist(String Lisence){


        boolean isExist =false;

        String WhereCluase =ltmsDBOpenHelper.PERIOD_COLUMN_LICENCE+" = '"+Lisence+"'";
        Cursor cursor = database.query(ltmsDBOpenHelper.TABLE_PERIOD, Period_AllColumn, WhereCluase, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()==1) {
            isExist=true;
        }

        return isExist;
    }

    public void Period_Clear(){
        Log.i(LOGTAG, "Period Clear");

        database.delete(ltmsDBOpenHelper.TABLE_LEARNING, null, null);
        database.delete(ltmsDBOpenHelper.TABLE_UNIT_DETAIL, null, null);
        database.delete(ltmsDBOpenHelper.TABLE_UNIT, null, null);
        database.delete(ltmsDBOpenHelper.TABLE_QUESTION,null,null);
        database.delete(ltmsDBOpenHelper.TABLE_SETTING,null,null);
        database.delete(ltmsDBOpenHelper.TABLE_PERIOD,null,null);

    }

    public Period Period_Insert(Period period){

        ContentValues values = new ContentValues();
        values.put(ltmsDBOpenHelper.PERIOD_COLUMN_ID,period.getId());
        values.put(ltmsDBOpenHelper.PERIOD_COLUMN_TESTTIME,period.getTest_time());
        values.put(ltmsDBOpenHelper.PERIOD_COLUMN_DESC,period.getDescryption());
        values.put(ltmsDBOpenHelper.PERIOD_COLUMN_TITLE,period.getTitle());
        values.put(ltmsDBOpenHelper.PERIOD_COLUMN_DATESTART,period.getDate_start());
        values.put(ltmsDBOpenHelper.PERIOD_COLUMN_DATEEND,period.getDate_end());
        values.put(ltmsDBOpenHelper.PERIOD_COLUMN_LICENCE, period.getLicence());
        values.put(ltmsDBOpenHelper.PERIOD_COLUMN_STATE, period.getState());
        long insertid = database.insert(ltmsDBOpenHelper.TABLE_PERIOD,null,values);
        //period.setId(insertid);

        return  period;
    }

    public List<Period> Period_FindAll(){
        List<Period> list = new ArrayList<Period>() ;

        Cursor cursor = database.query(ltmsDBOpenHelper.TABLE_PERIOD, Period_AllColumn, null, null, null, null, ltmsDBOpenHelper.PERIOD_COLUMN_ID + " desc");
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            while (!cursor.isAfterLast()) {
                Period period = new Period();
                period.setId(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_ID)));
                period.setTitle(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_TITLE)));
                period.setDescryption(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_DESC)));
                period.setDate_start(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_DATESTART)));
                period.setDate_end(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_DATEEND)));
                period.setTest_time(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_TESTTIME)));
                period.setState(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_STATE)));
                period.setLicence(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_LICENCE)));
                list.add(period);
                cursor.moveToNext();
            }
        }
        return list;
    }


    public Period Period_Select(int id){
        Period period = new Period();

        String WhereCluase =ltmsDBOpenHelper.PERIOD_COLUMN_ID+" = "+String.valueOf(id);
        Cursor cursor = database.query(ltmsDBOpenHelper.TABLE_PERIOD, Period_AllColumn, WhereCluase, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()==1) {
                period.setId(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_ID)));
                period.setTitle(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_TITLE)));
                period.setDescryption(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_DESC)));
                period.setDate_start(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_DATESTART)));
                period.setDate_end(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_DATEEND)));
                period.setTest_time(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_TESTTIME)));
                period.setState(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_STATE)));
                period.setLicence(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.PERIOD_COLUMN_LICENCE)));
        }

        return period;
    }

//    public boolean Period_ReadyToTest(long PID){
//
//        for(int i=0;i<Unit_FindAll(PID).size();i++){
//            for (int j=0;j<UnitDetail_FindAll(Unit_FindAll(PID).get(i).getId()).size();j++){
//                for(int k=0;k<Learning_FindAll(UnitDetail_FindAll(Unit_FindAll(PID).get(i).getId()).get(j).getId()).size();k++){
//                    if(Learning_FindAll(UnitDetail_FindAll(Unit_FindAll(PID).get(i).getId()).get(j).getId()).get(k).getState()==0) {
//                        return false;
//                    }
//                }
//
//            }
//        }
//        return true;
//    }

//    public float Period_Test_Result(int PID){
//        float res=0;
//
//        float TrueTest_Number =Question_FindAll(PID,2).size()+Question_FindAll(PID,1).size()/3;
//        float FalseTest_Number =Question_FindAll(PID,0).size();
//        float Total =TrueTest_Number+FalseTest_Number;
//
//        res =TrueTest_Number/Total *100;
//        return res;
//    }
    public float Period_Test_Result(int PID){
        float res=0;
        //boolean flag=false;


        for (int i = 0; i < Unit_FindAll(PID).size(); i++) {
            if (Unit_Test_Result(Unit_FindAll(PID).get(i).getId()) >= 60) {
                res+=Unit_Test_Result(Unit_FindAll(PID).get(i).getId());

            }
            else{
                res=0;
                break;
            }
        }

//        float TrueTest_Number =Question_FindAll(PID,2).size()+Question_FindAll(PID,1).size()/3;
//        float FalseTest_Number =Question_FindAll(PID,0).size();
//        float Total =TrueTest_Number+FalseTest_Number;
//
//        res =TrueTest_Number/Total *100;
//        res/=5;
        res/=Unit_FindAll(PID).size();
        return res;
    }
//
//    public float Period_Test_Result_1(int PID){
//        float res=0;
//
//        float TrueTest_Number =Question_FindAll(PID,1).size()+Question_FindAll(PID,2).size();
//        float FalseTest_Number =Question_FindAll(PID,0).size();
//        float Total =TrueTest_Number+FalseTest_Number;
//
//        res =TrueTest_Number/Total * 100;
//        return res;
//    }




    //Learning--------------------------------------------------------------------------------------

    public List<Learning> Learning_FindAll(long UDID){
        List<Learning> list = new ArrayList<Learning>() ;

        String WhereCluase =ltmsDBOpenHelper.LEARNING_COLUMN_UDID+" = "+String.valueOf(UDID);

        Cursor cursor = database.query(ltmsDBOpenHelper.TABLE_LEARNING, Learning_AllColumn, WhereCluase, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            while (!cursor.isAfterLast()) {
                Learning learning = new Learning();
                learning.setId(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_ID)));
                learning.setUdid(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_UDID)));
                learning.setContentid(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_ContentID)));
                learning.setContent(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_CONTENT)));
                learning.setDescryption(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_DESC)));
                learning.setStartpage(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_STARTPAGE)));
                learning.setPagenumber(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_PAGENUMBER)));
                learning.setState(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_STATE)));

                list.add(learning);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public Learning Learning_Insert(Learning learning){
        ContentValues values = new ContentValues();
        values.put(ltmsDBOpenHelper.LEARNING_COLUMN_ID,learning.getContent());
        values.put(ltmsDBOpenHelper.LEARNING_COLUMN_ContentID,learning.getContentid());
        values.put(ltmsDBOpenHelper.LEARNING_COLUMN_CONTENT,learning.getContent());
        values.put(ltmsDBOpenHelper.LEARNING_COLUMN_DESC,learning.getDescryption());
        values.put(ltmsDBOpenHelper.LEARNING_COLUMN_UDID,learning.getUdid());
        values.put(ltmsDBOpenHelper.LEARNING_COLUMN_STARTPAGE,learning.getStartpage());
        values.put(ltmsDBOpenHelper.LEARNING_COLUMN_PAGENUMBER,learning.getPagenumber());
        values.put(ltmsDBOpenHelper.LEARNING_COLUMN_STATE,learning.getState());
        long insertid = database.insert(ltmsDBOpenHelper.TABLE_LEARNING, null, values);
        learning.setId(insertid);
        return  learning;
    }

    public Learning Learning_FindFromUnitDetail(long UDID){
        Learning learning;

        String WhereCluase =ltmsDBOpenHelper.LEARNING_COLUMN_UDID+" = "+String.valueOf(UDID);

        Cursor cursor = database.query(ltmsDBOpenHelper.TABLE_LEARNING, Learning_AllColumn, WhereCluase, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {

                learning = new Learning();
                learning.setId(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_ID)));
                learning.setUdid(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_UDID)));
                learning.setContentid(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_ContentID)));
                learning.setContent(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_CONTENT)));
                learning.setDescryption(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_DESC)));
                learning.setStartpage(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_STARTPAGE)));
                learning.setPagenumber(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_PAGENUMBER)));
                learning.setState(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.LEARNING_COLUMN_STATE)));




        }
        else {
            learning=null;
        }


        return learning;
    }

    public void Learning_Read(long id){
        ContentValues values = new ContentValues();
        values.put(ltmsDBOpenHelper.LEARNING_COLUMN_STATE, 1);
        String WhereCluase =ltmsDBOpenHelper.LEARNING_COLUMN_ID+" = "+String.valueOf(id);

        database.update(ltmsDBOpenHelper.TABLE_LEARNING, values, WhereCluase, null);

    }

    //Content
    public Content Content_Select(long ContentID){
        Content content = new Content() ;

        String WhereCluase =ltmsDBOpenHelper.CONTENT_COLUMN_ID+" = "+String.valueOf(ContentID);

        Cursor cursor = database.query(ltmsDBOpenHelper.TABLE_CONTENT, Content_AllColumn, WhereCluase, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()==1) {
            content.setId(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.CONTENT_COLUMN_ID)));
            content.setContent(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.CONTENT_COLUMN_CONTENT)));
            return content;




        }
        else {
            return  null;
        }

    }

    public void Content_Insert(Content content){
        ContentValues values = new ContentValues();
        values.put(ltmsDBOpenHelper.CONTENT_COLUMN_ID,content.getId());
        values.put(ltmsDBOpenHelper.CONTENT_COLUMN_CONTENT,content.getContent());
        database.insert(ltmsDBOpenHelper.TABLE_CONTENT, null, values);

    }

    public List<Content> Content_FindAll(long UDID){
        List<Content> list = new ArrayList<Content>() ;

        String WhereCluase =ltmsDBOpenHelper.CONTENT_COLUMN_ID+" = "+String.valueOf(UDID);

        Cursor cursor = database.query(ltmsDBOpenHelper.TABLE_CONTENT, Content_AllColumn, WhereCluase, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            while (!cursor.isAfterLast()) {
                Content content = new Content();
                content.setId(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.CONTENT_COLUMN_ID)));
                content.setId(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.CONTENT_COLUMN_CONTENT)));
                list.add(content);
                cursor.moveToNext();
            }
        }
        return list;
    }


    //Unit------------------------------------------------------------------------------------------



    public Unit Unit_Insert(Unit unit){
        ContentValues values = new ContentValues();

        values.put(ltmsDBOpenHelper.UNIT_COLUMN_ID,unit.getId());
        values.put(ltmsDBOpenHelper.UNIT_COLUMN_PID,unit.getPid());
        values.put(ltmsDBOpenHelper.UNIT_COLUMN_NAME,unit.getName());
        values.put(ltmsDBOpenHelper.UNIT_COLUMN_DESC,unit.getDescryption());
        values.put(ltmsDBOpenHelper.UNIT_COLUMN_STATE, unit.getState());
        long insertid = database.insert(ltmsDBOpenHelper.TABLE_UNIT,null,values);
        //unit.setId(insertid);

        return  unit;
    }





    public boolean Unit_ReadyToTest(long UID){

//        for(int i=0;i<Unit_FindAll(UID).size();i++){
            for (int j=0;j<UnitDetail_FindAll(UID).size();j++){
                for(int k=0;k<Learning_FindAll(UnitDetail_FindAll(UID).get(j).getId()).size();k++){
                    if(Learning_FindAll(UnitDetail_FindAll(UID).get(j).getId()).get(k).getState()==0) {
                        return false;
                    }
                }

            }
//        }
        return true;
    }

    public boolean Unit_ReadyToTestNew(int PID,long UID){

        List<Unit> list = new ArrayList<Unit>() ;

        String WhereCluase =ltmsDBOpenHelper.UNIT_COLUMN_PID+" = "+String.valueOf(PID);
        int state=0;
        boolean flag=false;
        Cursor cursor = database.query(ltmsDBOpenHelper.TABLE_UNIT, Unit_AllColumn, WhereCluase, null, null, null, null);
        cursor.moveToFirst();

        if(cursor.getCount()>0) {
            while (!cursor.isAfterLast()) {
                Unit unit = new Unit();
                unit.setId(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_ID)));
                unit.setState(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_STATE)));
                list.add(unit);
                cursor.moveToNext();
            }
        }


        for(int i=0;i<list.size();i++){
            if(list.get(i).getId()==UID && i==0){
                flag=true;
                break;
            }
            else {
                if(list.get(i).getId()==UID && list.get(i-1).getState()==1 ){
                    flag=true;
                    break;
                }
                else {
                    flag=false;

                }
            }
        }



        return flag;
    }

    public void Unit_SetReadyToTest(long UID){
        String WhereCluase =ltmsDBOpenHelper.UNIT_COLUMN_ID+" = "+String.valueOf(UID);
        ContentValues values = new ContentValues();
        values.put(ltmsDBOpenHelper.UNIT_COLUMN_STATE,1);
        database.update(ltmsDBOpenHelper.TABLE_UNIT,values,WhereCluase,null);
    }

    public List<Unit> Unit_FindAll(int PID){
        List<Unit> list = new ArrayList<Unit>() ;

        String WhereCluase =ltmsDBOpenHelper.UNIT_COLUMN_PID+" = "+String.valueOf(PID);

        Cursor cursor = database.query(ltmsDBOpenHelper.TABLE_UNIT, Unit_AllColumn, WhereCluase, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            while (!cursor.isAfterLast()) {
                Unit unit = new Unit();
                unit.setId(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_COLUMN_ID)));
                unit.setPid(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_COLUMN_PID)));
                unit.setName(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_COLUMN_NAME)));
                unit.setDescryption(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_COLUMN_DESC)));
                unit.setState(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_COLUMN_STATE)));

                list.add(unit);
                cursor.moveToNext();
            }
        }
        return list;
    }

//    public float Unit_Test_Result(long UID){
//        float res=0;
//
//        float Total =  Question_FindAll(UID).size();
//
//
//            float TrueTest_Number =Question_FindAll(UID,2).size()+Question_FindAll(UID,1).size()/3;
//            float FalseTest_Number =Question_FindAll(UID,0).size();
//
//
//            res =(TrueTest_Number)/Total * 100;
//
//
//
//        return res;
//    }

    public float Unit_Test_Result(long UID){
        float res=0;

        float True1 =Question_FindAll(UID,1).size();
        float True2 =Question_FindAll(UID,2).size();

        float FalseTest_Number =Question_FindAll(UID,1).size()+Question_FindAll(UID,0).size();
        float Total =Question_FindAll_1(UID).size();


            res = (float) (((True2+(True1/3))/Total )* 100.0);
        return res;
    }

    //Unit Detail-----------------------------------------------------------------------------------

    public Unit_Detail Unit_Detail_Insert(Unit_Detail unitDetail){
        ContentValues values = new ContentValues();
        values.put(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_UID,unitDetail.getUid());
        values.put(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_NAME,unitDetail.getName());
        values.put(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_STARTPAGE,unitDetail.getStartpage());
        values.put(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_ENDPAGE,unitDetail.getEndpage());
        values.put(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_STATE,unitDetail.getState());
        long insertid = database.insert(ltmsDBOpenHelper.TABLE_UNIT_DETAIL,null,values);
        unitDetail.setId(insertid);
        return  unitDetail;
    }

    public List<Unit_Detail> UnitDetail_FindAll(long UID){
        List<Unit_Detail> list = new ArrayList<Unit_Detail>() ;

        String WhereCluase =ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_UID+" = "+String.valueOf(UID);


        Cursor cursor = database.query(ltmsDBOpenHelper.TABLE_UNIT_DETAIL, Unit_Detail_AllColumn, WhereCluase, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            while (!cursor.isAfterLast()) {
                Unit_Detail unit_detail = new Unit_Detail();
                unit_detail.setId(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_ID)));
                unit_detail.setUid(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_UID)));
                unit_detail.setName(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_NAME)));
                unit_detail.setStartpage(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_STARTPAGE)));
                unit_detail.setEndpage(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_ENDPAGE)));
                unit_detail.setState(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.UNIT_DETAIL_COLUMN_STATE)));

                list.add(unit_detail);
                cursor.moveToNext();
            }
        }
        return list;
    }


    // Question-------------------------------------------------------------------------------------


    public Question Question_Insert(Question question){
        ContentValues values = new ContentValues();

        values.put(ltmsDBOpenHelper.QUESTION_COLUMN_ID,question.getId());
        values.put(ltmsDBOpenHelper.QUESTION_COLUMN_UID,question.getUid());
        values.put(ltmsDBOpenHelper.QUESTION_COLUMN_QUESTION,question.getQuestion());
        values.put(ltmsDBOpenHelper.QUESTION_COLUMN_SEL1,question.getSel_1());
        values.put(ltmsDBOpenHelper.QUESTION_COLUMN_SEL2,question.getSel_2());
        values.put(ltmsDBOpenHelper.QUESTION_COLUMN_SEL3,question.getSel_3());
        values.put(ltmsDBOpenHelper.QUESTION_COLUMN_SEL4,question.getSel_4());
        values.put(ltmsDBOpenHelper.QUESTION_COLUMN_ANSWER,question.getAnswer());
        values.put(ltmsDBOpenHelper.QUESTION_COLUMN_TRUESEL,question.getTruesel());
        values.put(ltmsDBOpenHelper.QUESTION_COLUMN_STATE,question.getState());

        long insertid = database.insert(ltmsDBOpenHelper.TABLE_QUESTION, null, values);
        //question.setId(insertid);
        return  question;
    }

    public void Question_Update_State(long QID,int State){
        ContentValues values = new ContentValues();
        values.put(ltmsDBOpenHelper.QUESTION_COLUMN_STATE, State);
        String WhereCluase =ltmsDBOpenHelper.QUESTION_COLUMN_ID+" = "+String.valueOf(QID);

         database.update(ltmsDBOpenHelper.TABLE_QUESTION, values, WhereCluase, null);
    }

    public List<Question> Question_FindAll(long UID){
        List<Question> list = new ArrayList<Question>() ;
        Cursor cursor;
        String WhereCluase =ltmsDBOpenHelper.QUESTION_COLUMN_UID+" = "+String.valueOf(UID)+" and "+ltmsDBOpenHelper.QUESTION_COLUMN_STATE+" = 0 ";

        cursor = database.query(ltmsDBOpenHelper.TABLE_QUESTION, Question_AllColumn, WhereCluase, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {


            while (!cursor.isAfterLast()) {
                Question question = new Question();
                question.setId(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_ID)));
                question.setUid(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_UID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_QUESTION)));
                question.setSel_1(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL1)));
                question.setSel_2(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL2)));
                question.setSel_3(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL3)));
                question.setSel_4(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL4)));
                question.setAnswer(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_ANSWER)));
                question.setTruesel(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_TRUESEL)));
                question.setState(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_STATE)));


                list.add(question);
                cursor.moveToNext();
            }

        }
        else {
            WhereCluase =ltmsDBOpenHelper.QUESTION_COLUMN_UID+" = "+String.valueOf(UID)+" and "+ltmsDBOpenHelper.QUESTION_COLUMN_STATE+" = 1 ";
            cursor = database.query(ltmsDBOpenHelper.TABLE_QUESTION, Question_AllColumn, WhereCluase, null, null, null, null);
            cursor.moveToFirst();
            if(cursor.getCount()>0) {


                while (!cursor.isAfterLast()) {
                    Question question = new Question();
                    question.setId(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_ID)));
                    question.setUid(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_UID)));
                    question.setQuestion(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_QUESTION)));
                    question.setSel_1(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL1)));
                    question.setSel_2(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL2)));
                    question.setSel_3(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL3)));
                    question.setSel_4(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL4)));
                    question.setAnswer(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_ANSWER)));
                    question.setTruesel(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_TRUESEL)));
                    question.setState(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_STATE)));


                    list.add(question);
                    cursor.moveToNext();
                }

            }





        }
        return list;
    }

    public List<Question> Question_FindAll_1(long UID){
        List<Question> list = new ArrayList<Question>() ;
        Cursor cursor;
        String WhereCluase =ltmsDBOpenHelper.QUESTION_COLUMN_UID+" = "+String.valueOf(UID);

        cursor = database.query(ltmsDBOpenHelper.TABLE_QUESTION, Question_AllColumn, WhereCluase, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {


            while (!cursor.isAfterLast()) {
                Question question = new Question();
                question.setId(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_ID)));
                question.setUid(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_UID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_QUESTION)));
                question.setSel_1(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL1)));
                question.setSel_2(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL2)));
                question.setSel_3(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL3)));
                question.setSel_4(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL4)));
                question.setAnswer(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_ANSWER)));
                question.setTruesel(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_TRUESEL)));
                question.setState(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_STATE)));


                list.add(question);
                cursor.moveToNext();
            }

        }
        return list;
    }

    public List<Question> Question_FindAll(long UID ,int State){
        List<Question> list = new ArrayList<Question>() ;
        Cursor cursor;
        String WhereCluase =ltmsDBOpenHelper.QUESTION_COLUMN_UID+" = "+String.valueOf(UID)+" and "+ltmsDBOpenHelper.QUESTION_COLUMN_STATE+" =  "+State;

        cursor = database.query(ltmsDBOpenHelper.TABLE_QUESTION, Question_AllColumn, WhereCluase, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {


            while (!cursor.isAfterLast()) {
                Question question = new Question();
                question.setId(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_ID)));
                question.setUid(cursor.getLong(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_UID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_QUESTION)));
                question.setSel_1(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL1)));
                question.setSel_2(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL2)));
                question.setSel_3(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL3)));
                question.setSel_4(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_SEL4)));
                question.setAnswer(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_ANSWER)));
                question.setTruesel(cursor.getString(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_TRUESEL)));
                question.setState(cursor.getInt(cursor.getColumnIndex(ltmsDBOpenHelper.QUESTION_COLUMN_STATE)));


                list.add(question);
                cursor.moveToNext();
            }

        }
        return list;
    }


    public  void beginTransaction(){
        database.beginTransaction();
    }

    public  void endTransaction(){
        database.endTransaction();
    }
    public  void setTransactionSuccessful(){
        database.setTransactionSuccessful();
    }


    public void execSql(String sql){

        database.execSQL(sql);


    }








}
