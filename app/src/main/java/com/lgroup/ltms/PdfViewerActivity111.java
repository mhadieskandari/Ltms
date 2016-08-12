package com.lgroup.ltms;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class PdfViewerActivity111 extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdf_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


//
//    @Override
//    public int getPreviousPageImageResource() { return R.drawable.left_arrow; }
//    @Override
//    public int getNextPageImageResource() { return R.drawable.right_arrow; }
//    @Override
//    public int getZoomInImageResource() { return R.drawable.zoom_in; }
//    @Override
//    public int getZoomOutImageResource() { return R.drawable.zoom_out; }
//    @Override
//    public int getPdfPasswordLayoutResource() { return R.layout.pdf_file_password; }
//    @Override
//    public int getPdfPageNumberResource() { return R.layout.dialog_pagenumber; }
//    @Override
//    public int getPdfPasswordEditField() { return R.id.etPassword; }
//    @Override
//    public int getPdfPasswordOkButton() { return R.id.btOK; }
//    @Override
//    public int getPdfPasswordExitButton() { return R.id.btExit; }
//    @Override
//    public int getPdfPageNumberEditField() { return R.id.pagenum_edit; }


}
