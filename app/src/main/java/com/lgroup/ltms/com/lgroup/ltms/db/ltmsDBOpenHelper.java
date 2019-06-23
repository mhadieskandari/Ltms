package com.lgroup.ltms.com.lgroup.ltms.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Work on 21/09/2015.
 */
public class ltmsDBOpenHelper extends SQLiteOpenHelper {


    private   Context context;
    private  static final String LOGTAG="mylogtag";
    private static final String DATABASE_NAME = "ltms.db" ;
    private static final int DATABASE_VERSION = 12 ;

    private static String DB_PATH = "/data/data/com.lgroup.ltms/databases/";
    private static String DB_NAME = "ltms1.db";




    //PERIOD
    public static final String TABLE_PERIOD ="T_Period";
    public static final String PERIOD_COLUMN_ID ="ID";
    public static final String PERIOD_COLUMN_TITLE ="Title";
    public static final String PERIOD_COLUMN_DESC ="Desc";
    public static final String PERIOD_COLUMN_DATESTART ="DateStart";
    public static final String PERIOD_COLUMN_DATEEND ="DateEnd";
    public static final String PERIOD_COLUMN_TESTTIME ="TestTime";
    public static final String PERIOD_COLUMN_LICENCE ="Licence";
    public static final String PERIOD_COLUMN_STATE ="State";

    //SETTING
    public static final String TABLE_SETTING ="T_Setting";
    public static final String SETTING_COLUMN_ID ="ID";
    public static final String SETTING_COLUMN_NAME ="Name";
    public static final String SETTING_COLUMN_VALUE ="Value";

    //UNIT
    public static final String TABLE_UNIT ="T_Unit";
    public static final String UNIT_COLUMN_ID ="ID";
    public static final String UNIT_COLUMN_PID ="PID";
    public static final String UNIT_COLUMN_NAME ="Name";
    public static final String UNIT_COLUMN_DESC ="Desc";
    public static final String UNIT_COLUMN_STATE ="State";

    //UNIT_DETAIL
    public static final String TABLE_UNIT_DETAIL ="T_Unit_Detail";
    public static final String UNIT_DETAIL_COLUMN_ID ="ID";
    public static final String UNIT_DETAIL_COLUMN_UID ="UID";
    public static final String UNIT_DETAIL_COLUMN_NAME ="Name";
    public static final String UNIT_DETAIL_COLUMN_STARTPAGE ="StartPage";
    public static final String UNIT_DETAIL_COLUMN_ENDPAGE ="EndPage";
    public static final String UNIT_DETAIL_COLUMN_STATE ="State";

    //QUESTION
    public static final String TABLE_QUESTION ="T_Question";
    public static final String QUESTION_COLUMN_ID ="ID";
    public static final String QUESTION_COLUMN_UID ="UID";
    public static final String QUESTION_COLUMN_QUESTION ="Question";
    public static final String QUESTION_COLUMN_SEL1 ="Sel1";
    public static final String QUESTION_COLUMN_SEL2 ="Sel2";
    public static final String QUESTION_COLUMN_SEL3 ="Sel3";
    public static final String QUESTION_COLUMN_SEL4 ="Sel4";
    public static final String QUESTION_COLUMN_ANSWER ="Answer";
    public static final String QUESTION_COLUMN_TRUESEL ="TrueSel";
    public static final String QUESTION_COLUMN_STATE ="State";

    //LEARNING
    public static final String TABLE_LEARNING ="T_Learning";
    public static final String LEARNING_COLUMN_ID ="ID";
    public static final String LEARNING_COLUMN_UDID ="UDID";
    public static final String LEARNING_COLUMN_ContentID ="CID";
    public static final String LEARNING_COLUMN_CONTENT ="Content";
    public static final String LEARNING_COLUMN_DESC ="Desc";
    public static final String LEARNING_COLUMN_STARTPAGE ="StartPage";
    public static final String LEARNING_COLUMN_PAGENUMBER ="PageNumber";
    public static final String LEARNING_COLUMN_STATE ="State";


    //Content
    public static final String TABLE_CONTENT ="T_Content";
    public static final String CONTENT_COLUMN_ID ="ID";
    public static final String CONTENT_COLUMN_CONTENT ="Content";

    //Profile
    public static final String Profile_ID="ID";
    public static final String Profile_FirstName="FirstName";
    public static final String Profile_LastName="LastName";
    public static final String Profile_PersonalNumber="PersonalNumber";
    public static final String Profile_NationNumber="NationNumber";
    public static final String Profile_Mantagheh="Mantagheh";
    public static final String Profile_School="School";
    public static final String Profile_ShSh="ShSh";
    public static final String Profile_MobileNumber="PhoneNumber";
    public static final String Profile_HomeNumber="HomeNumber";
    public static final String Profile_Email="Email";
    public static final String Profile_ProfileExist="ProfileExist";


    public static final String Profile_OrganizationID="OrganID";
    public static final String Profile_OrganizationTitle="OrganTitle";



//    //Organization
//    public static final String TABLE_ORGANIZATION ="T_Organization";
//    public static final String ORGANIZATION_COLUMN_ID ="ID";
//    public static final String ORGANIZATION_COLUMN_TITLE ="Title";


//    private static final String TABLE_ORGANIZATION_CREATE =
//            "CREATE TABLE "+TABLE_ORGANIZATION+" ("+ORGANIZATION_COLUMN_ID+" integer PRIMARY KEY AUTOINCREMENT" +
//                    ","+ORGANIZATION_COLUMN_TITLE+" text)";



    private static final String TABLE_PERIOD_CREATE =
                    "CREATE TABLE "+TABLE_PERIOD+" ("+PERIOD_COLUMN_ID+" integer PRIMARY KEY" +
                    ","+PERIOD_COLUMN_TITLE+" text,"+PERIOD_COLUMN_DESC+" text,"+PERIOD_COLUMN_DATESTART+" text,"+PERIOD_COLUMN_DATEEND+" text" +
                    ","+PERIOD_COLUMN_TESTTIME+" integer,"+PERIOD_COLUMN_LICENCE+" text,"+PERIOD_COLUMN_STATE+" smallint)";

    private static final String TABLE_UNIT_CREATE ="CREATE TABLE "+TABLE_UNIT+" ("+
            UNIT_COLUMN_ID+" integer PRIMARY KEY ," +
            UNIT_COLUMN_PID+" integer," +
            UNIT_COLUMN_NAME+" text," +
            UNIT_COLUMN_DESC +" text," +
            UNIT_COLUMN_STATE+" smallint," +
            "FOREIGN KEY("+UNIT_COLUMN_PID+") REFERENCES "+TABLE_PERIOD+" ( "+PERIOD_COLUMN_ID+" ) ON DELETE CASCADE ON UPDATE CASCADE" +
            ");";

    private static final String TABLE_UNIT_DETAIL_CREATE ="CREATE TABLE "+TABLE_UNIT_DETAIL+" (" +
            UNIT_DETAIL_COLUMN_ID+" integer PRIMARY KEY ," +
            UNIT_DETAIL_COLUMN_UID+" integer," +
            UNIT_DETAIL_COLUMN_NAME+" text," +
            UNIT_DETAIL_COLUMN_STARTPAGE+" integer," +
            UNIT_DETAIL_COLUMN_ENDPAGE+" integer," +
            UNIT_DETAIL_COLUMN_STATE+" smallint," +
            "FOREIGN KEY("+UNIT_DETAIL_COLUMN_UID+") REFERENCES "+TABLE_UNIT+" ( "+UNIT_COLUMN_ID+" ) ON DELETE CASCADE ON UPDATE CASCADE" +
            ");";

    private static final String TABLE_QUESTION_CREATE ="CREATE TABLE "+TABLE_QUESTION+" (" +
            QUESTION_COLUMN_ID+" integer PRIMARY KEY ," +
            QUESTION_COLUMN_UID+" integer," +
            QUESTION_COLUMN_QUESTION+" text," +
            QUESTION_COLUMN_SEL1+" text," +
            QUESTION_COLUMN_SEL2+" text," +
            QUESTION_COLUMN_SEL3+" text," +
            QUESTION_COLUMN_SEL4+" text," +
            QUESTION_COLUMN_ANSWER+" text," +
            QUESTION_COLUMN_TRUESEL+" text," +
            QUESTION_COLUMN_STATE+" smallint DEFAULT 0," +
            "FOREIGN KEY("+QUESTION_COLUMN_UID+") REFERENCES "+TABLE_UNIT+" ( "+UNIT_COLUMN_ID+" ) ON DELETE CASCADE ON UPDATE CASCADE" +
            ");";

    private static final String TABLE_LEARNING_CREATE ="CREATE TABLE "+TABLE_LEARNING+" (" +
            LEARNING_COLUMN_ID+" integer PRIMARY KEY ," +
            LEARNING_COLUMN_UDID+" integer," +
            LEARNING_COLUMN_ContentID+" integer," +
            LEARNING_COLUMN_STARTPAGE+" text," +
            LEARNING_COLUMN_PAGENUMBER+" text," +
            LEARNING_COLUMN_CONTENT+" text," +
            LEARNING_COLUMN_DESC+" text," +
            LEARNING_COLUMN_STATE+" smallint," +
            "FOREIGN KEY("+LEARNING_COLUMN_UDID+") REFERENCES "+TABLE_UNIT_DETAIL+" ( "+UNIT_DETAIL_COLUMN_ID+" ) ON DELETE CASCADE ON UPDATE CASCADE ," +
            "FOREIGN KEY("+LEARNING_COLUMN_ContentID+") REFERENCES "+TABLE_CONTENT+" ( "+CONTENT_COLUMN_CONTENT+" ) ON DELETE CASCADE ON UPDATE CASCADE" +
            ");";

    private static final String TABLE_CONTENT_CREATE ="CREATE TABLE "+TABLE_CONTENT+" (" +
            CONTENT_COLUMN_ID+" integer PRIMARY KEY ," +
            CONTENT_COLUMN_CONTENT+" text" +
            ");";

    private static final String TABLE_SETTING_CREATE ="CREATE TABLE "+TABLE_SETTING+" (" +
            SETTING_COLUMN_ID+" integer PRIMARY KEY ," +
            SETTING_COLUMN_NAME+" text," +
            SETTING_COLUMN_VALUE+" text" +
            ");";


    public ltmsDBOpenHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);

        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_PERIOD_CREATE);
        db.execSQL(TABLE_UNIT_CREATE);
        db.execSQL(TABLE_UNIT_DETAIL_CREATE);
        db.execSQL(TABLE_QUESTION_CREATE);
        db.execSQL(TABLE_LEARNING_CREATE);
        db.execSQL(TABLE_SETTING_CREATE);
        db.execSQL(TABLE_CONTENT_CREATE);
//        db.execSQL(TABLE_ORGANIZATION_CREATE);



        Log.i(LOGTAG, "tables has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.beginTransaction();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEARNING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNIT_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_UNIT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PERIOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENT);
       // db.setTransactionSuccessful();
        //db.endTransaction();
        Log.i(LOGTAG, "tables has been droped");
        onCreate(db);
        Log.i(LOGTAG, "tables has been created");
    }


//    private boolean checkDataBase(){
//
//        SQLiteDatabase checkDB = null;
//
//        try{
//            String myPath = DB_PATH + DATABASE_NAME;
//            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//
//        }catch(SQLiteException e){
//
//            //com.lgroup.ltms.database does't exist yet.
//
//        }
//
//        if(checkDB != null){
//
//            checkDB.close();
//
//        }
//
//        return checkDB != null ? true : false;
//    }
//
//
//    private void copyDataBase() throws IOException{
//
//        //Open your local db as the input stream
//        InputStream myInput = context.getAssets().open(DB_NAME);
//
//        // Path to the just created empty db
//        String outFileName = DB_PATH + DB_NAME;
//
//        //Open the empty db as the output stream
//        OutputStream myOutput = new FileOutputStream(outFileName);
//
//        //transfer bytes from the inputfile to the outputfile
//        byte[] buffer = new byte[1024];
//        int length;
//        while ((length = myInput.read(buffer))>0){
//            myOutput.write(buffer, 0, length);
//        }
//
//        //Close the streams
//        myOutput.flush();
//        myOutput.close();
//        myInput.close();
//
//    }
//
//    public void createDataBase() throws IOException{
//
//        boolean dbExist = checkDataBase();
//
//        if(dbExist){
//            //do nothing - com.lgroup.ltms.database already exist
//        }else{
//
//            //By calling this method and empty com.lgroup.ltms.database will be created into the default system path
//            //of your application so we are gonna be able to overwrite that com.lgroup.ltms.database with our com.lgroup.ltms.database.
//            this.getReadableDatabase();
//
//            try {
//
//                copyDataBase();
//
//            } catch (IOException e) {
//
//                throw new Error("Error copying com.lgroup.ltms.database");
//
//            }
//        }
//
//    }

}
