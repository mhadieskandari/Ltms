package com.lgroup.ltms;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.lgroup.ltms.com.lgroup.ltms.db.ltmsDataSource;
import com.lgroup.ltms.com.lgroup.ltms.models.Learning;
import com.lgroup.ltms.com.lgroup.ltms.models.Period;
import com.lgroup.ltms.com.lgroup.ltms.models.Question;
import com.lgroup.ltms.com.lgroup.ltms.models.Unit;
import com.lgroup.ltms.com.lgroup.ltms.models.Unit_Detail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public static final String LOGTAG="mylogtag";

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private ContactUs_Fragment ContactFragment ;
    private AboutUs_Fragment AboutFragment ;

    private ltmsDataSource dataSource;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dataSource = new ltmsDataSource(this);

        dataSource.open();

        File myDirectory = new File(Environment.getExternalStorageDirectory(), "Lqroup");

        if(!myDirectory.exists()) {
            myDirectory.mkdirs();
        }

//        //copy pdf file to storage
//
//        File myDirectory = new File(Environment.getExternalStorageDirectory(), "Lqroup");
//
//        if(!myDirectory.exists()) {
//            myDirectory.mkdirs();
//            InputStream inputStream;
//            File file;
//            String path1;
//
//            inputStream = getResources().openRawResource(R.raw.icdl1);
//            path1= Environment.getExternalStorageDirectory().getPath() + "/Lqroup/icdl1.pdf";
//            file = new File(path1);
//            copyInputStreamToFile(inputStream, file);
//
//            inputStream = getResources().openRawResource(R.raw.icdl2);
//            path1= Environment.getExternalStorageDirectory().getPath() + "/Lqroup/icdl2.pdf";
//            file = new File(path1);
//            copyInputStreamToFile(inputStream, file);
//
//            inputStream = getResources().openRawResource(R.raw.icdl3);
//            path1= Environment.getExternalStorageDirectory().getPath() + "/Lqroup/icdl3.pdf";
//            file = new File(path1);
//            copyInputStreamToFile(inputStream, file);
//
//            inputStream = getResources().openRawResource(R.raw.icdl4);
//            path1= Environment.getExternalStorageDirectory().getPath() + "/Lqroup/icdl4.pdf";
//            file = new File(path1);
//            copyInputStreamToFile(inputStream, file);
//
//
//            //dolat electronic
//
//            inputStream = getResources().openRawResource(R.raw.dol1);
//            path1= Environment.getExternalStorageDirectory().getPath() + "/Lqroup/dolat1.pdf";
//            file = new File(path1);
//            copyInputStreamToFile(inputStream, file);
//
//            inputStream = getResources().openRawResource(R.raw.dol2);
//            path1= Environment.getExternalStorageDirectory().getPath() + "/Lqroup/dolat2.pdf";
//            file = new File(path1);
//            copyInputStreamToFile(inputStream, file);
//
//            inputStream = getResources().openRawResource(R.raw.dol3);
//            path1= Environment.getExternalStorageDirectory().getPath() + "/Lqroup/dolat3.pdf";
//            file = new File(path1);
//            copyInputStreamToFile(inputStream, file);
//
//            inputStream = getResources().openRawResource(R.raw.dol4);
//            path1= Environment.getExternalStorageDirectory().getPath() + "/Lqroup/dolat4.pdf";
//            file = new File(path1);
//            copyInputStreamToFile(inputStream, file);


//        }



//        Uri path = Uri.fromFile(file);
//        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        pdfOpenintent.setDataAndType(path, "application/pdf");
//        try {
//            startActivity(pdfOpenintent);
//        }
//        catch (ActivityNotFoundException e) {
//
//        }








        //CreateSampleMaharat();

        //dataSource.Period_Clear();
//      createSample();


        ContactFragment = new ContactUs_Fragment();
        AboutFragment = new AboutUs_Fragment();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        FragmentManager fragmentManager = getSupportFragmentManager();
        UserProfile_Fragment userProfile_fragment = new UserProfile_Fragment();
        AboutUs_Fragment aboutUs_fragment = new AboutUs_Fragment();
        ContactUs_Fragment contactUs_fragment =new ContactUs_Fragment();
        PeriodItemFragment  periodItemFragment = new PeriodItemFragment();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if(position==1){

            //FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, userProfile_fragment.newInstance(position + 1)).addToBackStack("UserProfile")
                    .commit();

        }
        else if(position==2){

            //FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, aboutUs_fragment.newInstance(position + 1)).addToBackStack("AboutUs")
                    .commit();
        }
        else if(position==3){

            //FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, contactUs_fragment.newInstance(position + 1)).addToBackStack("ContactUs")
                    .commit();
        }
        else {
            //FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, periodItemFragment.newInstance(position + 1)).addToBackStack("PeriodItem")
                    .commit();
        }


    }

    public void onSectionAttached(int number) {
        switch (number) {

            case 1:
                mTitle = getString(R.string.title_section_Period);
                break;
            case 2:
                mTitle = getString(R.string.title_section_Profile);
                break;
            case 3:
                mTitle = getString(R.string.title_section_AboutUs);
                break;
            case 4:
                mTitle = getString(R.string.title_section_ContactUs);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.

//            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        dataSource.close();
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm =getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i(LOGTAG, "popping backstack");
                fm.popBackStack();


        } else {
            Log.i(LOGTAG, "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

//    public void createSample(){
//
//        Period period = new Period();
//        period.setTitle("دوره دوم مطهری");
//        period.setDescryption(" توضیحات 2");
//        period.setDate_start("13940701");
//        period.setDate_end("13940725");
//        period.setTest_time(20);
//        period.setLicence("mksda42dfdamfsdsd");
//        period.setState(0);
//
//        period=dataSource.Period_Insert(period);
//        Log.i(LOGTAG, "Period has been created by id :" + period.getId());
//
//
//        Unit unit =new Unit();
//        unit.setPid(period.getId());
//        unit.setName("فصل اول");
//        unit.setDescryption("درباره فصل اول");
//        unit.setState(0);
//        unit=dataSource.Unit_Insert(unit);
//
//        Unit_Detail ud = new Unit_Detail();
//        ud.setName("مقدمه");
//        ud.setUid(unit.getId());
//        ud.setStartpage(1);
//        ud.setEndpage(10);
//        ud.setState(0);
//        ud=dataSource.Unit_Detail_Insert(ud);
//
//        Learning l = new Learning();
//        l.setUdid(ud.getId());
//        l.setContent("درباره این درس اینگونه است که باید طوری شود که فکر نکنند طوری شده");
//        l.setPagenumber(5);
//        l.setDescryption("توضیحات");
//        l.setStartpage(1);
//        l.setState(0);
//        dataSource.Learning_Insert(l);
//
//        l = new Learning();
//        l.setUdid(ud.getId());
//        l.setContent("درباره این درس اینگونه است که باید طوری شود که فکر نکنند طوری شده");
//        l.setPagenumber(5);
//        l.setDescryption("توضیحات");
//        l.setStartpage(1);
//        l.setState(0);
//        dataSource.Learning_Insert(l);
//
//        l = new Learning();
//        l.setUdid(ud.getId());
//        l.setContent("درباره این درس اینگونه است که باید طوری شود که فکر نکنند طوری شده");
//        l.setPagenumber(5);
//        l.setDescryption("توضیحات");
//        l.setStartpage(1);
//        l.setState(0);
//        dataSource.Learning_Insert(l);
//
//
//
//
//
//        ud = new Unit_Detail();
//        ud.setName("زندگی نامه");
//        ud.setUid(unit.getId());
//        ud.setStartpage(5);
//        ud.setEndpage(10);
//        ud.setState(0);
//        ud=dataSource.Unit_Detail_Insert(ud);
//
//        l = new Learning();
//        l.setUdid(ud.getId());
//        l.setContent("درباره این درس اینگونه است که باید طوری شود که فکر نکنند طوری شده");
//        l.setPagenumber(5);
//        l.setDescryption("توضیحات");
//        l.setStartpage(1);
//        l.setState(0);
//        dataSource.Learning_Insert(l);
//
//        l = new Learning();
//        l.setUdid(ud.getId());
//        l.setContent("درباره این درس اینگونه است که باید طوری شود که فکر نکنند طوری شده");
//        l.setPagenumber(5);
//        l.setDescryption("توضیحات");
//        l.setStartpage(1);
//        l.setState(0);
//        dataSource.Learning_Insert(l);
//
//        l = new Learning();
//        l.setUdid(ud.getId());
//        l.setContent("درباره این درس اینگونه است که باید طوری شود که فکر نکنند طوری شده");
//        l.setPagenumber(5);
//        l.setDescryption("توضیحات");
//        l.setStartpage(1);
//        l.setState(0);
//        dataSource.Learning_Insert(l);
//
//        Question q = new Question();
//        q.setUid(unit.getId());
//        q.setQuestion("آیا این طور است که");
//        q.setSel_1("گزینه 1");
//        q.setSel_2("گزینه 2");
//        q.setSel_3("گزینه 3");
//        q.setSel_4("گزینه 4");
//        q.setAnswer("جواب");
//        q.setTruesel("1");
//        q.setState(0);
//        dataSource.Question_Insert(q);
//
//        q = new Question();
//        q.setUid(unit.getId());
//        q.setQuestion("آیا این طور است که");
//        q.setSel_1("گزینه 1");
//        q.setSel_2("گزینه 2");
//        q.setSel_3("گزینه 3");
//        q.setSel_4("گزینه 4");
//        q.setAnswer("جواب");
//        q.setTruesel("1");
//        q.setState(0);
//        dataSource.Question_Insert(q);
//
//        q = new Question();
//        q.setUid(unit.getId());
//        q.setQuestion("آیا این طور است که");
//        q.setSel_1("گزینه 1");
//        q.setSel_2("گزینه 2");
//        q.setSel_3("گزینه 3");
//        q.setSel_4("گزینه 4");
//        q.setAnswer("جواب");
//        q.setTruesel("1");
//        q.setState(0);
//        dataSource.Question_Insert(q);
//
//        q = new Question();
//        q.setUid(unit.getId());
//        q.setQuestion("آیا این طور است که");
//        q.setSel_1("گزینه 1");
//        q.setSel_2("گزینه 2");
//        q.setSel_3("گزینه 3");
//        q.setSel_4("گزینه 4");
//        q.setAnswer("جواب");
//        q.setTruesel("1");
//        q.setState(0);
//        dataSource.Question_Insert(q);
//
//        q = new Question();
//        q.setUid(unit.getId());
//        q.setQuestion("آیا این طور است که");
//        q.setSel_1("گزینه 1");
//        q.setSel_2("گزینه 2");
//        q.setSel_3("گزینه 3");
//        q.setSel_4("گزینه 4");
//        q.setAnswer("جواب");
//        q.setTruesel("1");
//        q.setState(0);
//        dataSource.Question_Insert(q);
//
//        q = new Question();
//        q.setUid(unit.getId());
//        q.setQuestion("آیا این طور است که");
//        q.setSel_1("گزینه 1");
//        q.setSel_2("گزینه 2");
//        q.setSel_3("گزینه 3");
//        q.setSel_4("گزینه 4");
//        q.setAnswer("جواب");
//        q.setTruesel("1");
//        q.setState(0);
//        dataSource.Question_Insert(q);
//
//
//
//
//
//
//    }

    private void copyInputStreamToFile( InputStream in, File file ) {

        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
