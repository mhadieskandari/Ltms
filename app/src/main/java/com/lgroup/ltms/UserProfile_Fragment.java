package com.lgroup.ltms;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.lgroup.ltms.com.lgroup.ltms.db.ltmsDBOpenHelper;
import com.lgroup.ltms.com.lgroup.ltms.db.ltmsDataSource;
import com.lgroup.ltms.com.lgroup.ltms.models.Setting;

/**
 * Created by Work on 24/09/2015.
 */
public class UserProfile_Fragment extends Fragment {

    EditText txtFirstName,txtLastName,txtMobileNumber,txtHomeNumber,txtSchool,txtMantagheh,txtNationNumber,txtPersonalNumber,txtEmail,txtShSh;

    private static final String ARG_SECTION_NUMBER = "section_number";


    ltmsDataSource dataSource ;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static UserProfile_Fragment newInstance(int sectionNumber) {
        UserProfile_Fragment fragment = new UserProfile_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    public UserProfile_Fragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        dataSource.open();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_userprofile, container, false);

        txtEmail=(EditText)view.findViewById(R.id.txtEmail);
        txtFirstName=(EditText)view.findViewById(R.id.txtFirstName);
        txtLastName=(EditText)view.findViewById(R.id.txtLastName);
        txtMobileNumber=(EditText)view.findViewById(R.id.txtMobileNumber);
//        txtMantagheh=(EditText)view.findViewById(R.id.txtMantagheh);
//        txtHomeNumber=(EditText)view.findViewById(R.id.txtHomeNumber);
//        txtShSh=(EditText)view.findViewById(R.id.txtShSh);
//        txtSchool=(EditText)view.findViewById(R.id.txtSchool);
        txtNationNumber=(EditText)view.findViewById(R.id.txtNationNumber);
//        txtPersonalNumber=(EditText)view.findViewById(R.id.txtPersonalNumber);


        txtEmail.setEnabled(false);
        txtFirstName.setEnabled(false);
        txtLastName.setEnabled(false);
        txtMobileNumber.setEnabled(false);
        txtNationNumber.setEnabled(false);
        txtEmail.setEnabled(false);

        if(dataSource.Profile_Exist()){
            txtFirstName.setText(dataSource.Setting_Select(ltmsDBOpenHelper.Profile_FirstName));
            txtLastName.setText(dataSource.Setting_Select(ltmsDBOpenHelper.Profile_LastName));
            txtMobileNumber.setText(dataSource.Setting_Select(ltmsDBOpenHelper.Profile_MobileNumber));
           // txtHomeNumber.setText(dataSource.Setting_Select(ltmsDBOpenHelper.Profile_HomeNumber));
            //txtPersonalNumber.setText(dataSource.Setting_Select(ltmsDBOpenHelper.Profile_PersonalNumber));
            txtNationNumber.setText(dataSource.Setting_Select(ltmsDBOpenHelper.Profile_NationNumber));
           // txtSchool.setText(dataSource.Setting_Select(ltmsDBOpenHelper.Profile_School));
           // txtShSh.setText(dataSource.Setting_Select(ltmsDBOpenHelper.Profile_ShSh));
            txtEmail.setText(dataSource.Setting_Select(ltmsDBOpenHelper.Profile_Email));
            //txtMantagheh.setText(dataSource.Setting_Select(ltmsDBOpenHelper.Profile_Mantagheh));
        }


        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // TODO Add your menu entries here
//        inflater.inflate(R.menu.profile_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//
//
//
//
//
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_Save) {
//            ProfileSave();
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        dataSource=new ltmsDataSource(activity);
    }

    private void ProfileSave() {

        if (txtFirstName.getText().equals("")) {
            txtFirstName.requestFocus();
        } else if (txtLastName.getText().equals("")) {
            txtLastName.requestFocus();
        } else if (txtNationNumber.getText().equals("")) {
            txtNationNumber.requestFocus();
        } else if (txtEmail.getText().equals("")) {
            txtEmail.requestFocus();

        } else if (txtMobileNumber.getText().equals("")) {
            txtMobileNumber.requestFocus();
        } else {


            if (dataSource.Profile_Exist()) {

                Setting setting = new Setting();

                setting.setName(ltmsDBOpenHelper.Profile_FirstName);
                setting.setValue(txtFirstName.getText().toString());
                dataSource.Setting_Update(setting);

                setting.setName(ltmsDBOpenHelper.Profile_LastName);
                setting.setValue(txtLastName.getText().toString());
                dataSource.Setting_Update(setting);

                setting.setName(ltmsDBOpenHelper.Profile_NationNumber);
                setting.setValue(txtNationNumber.getText().toString());
                dataSource.Setting_Update(setting);



                setting.setName(ltmsDBOpenHelper.Profile_Email);
                setting.setValue(txtEmail.getText().toString());
                dataSource.Setting_Update(setting);

                setting.setName(ltmsDBOpenHelper.Profile_MobileNumber);
                setting.setValue(txtMobileNumber.getText().toString());
                dataSource.Setting_Update(setting);




                Toast toast= Toast.makeText(getActivity(), getResources().getString(R.string.Save), Toast.LENGTH_SHORT);
                toast.show();
            }
            else {

                Setting setting = new Setting();

                setting.setName(ltmsDBOpenHelper.Profile_FirstName);
                setting.setValue(txtFirstName.getText().toString());
                dataSource.Setting_Insert(setting);

                setting.setName(ltmsDBOpenHelper.Profile_LastName);
                setting.setValue(txtLastName.getText().toString());
                dataSource.Setting_Insert(setting);

                setting.setName(ltmsDBOpenHelper.Profile_NationNumber);
                setting.setValue(txtNationNumber.getText().toString());
                dataSource.Setting_Insert(setting);

//                setting.setName(ltmsDBOpenHelper.Profile_PersonalNumber);
//                setting.setValue(txtPersonalNumber.getText().toString());
//                dataSource.Setting_Insert(setting);

//                setting.setName(ltmsDBOpenHelper.Profile_Email);
//                setting.setValue(txtEmail.getText().toString());
//                dataSource.Setting_Insert(setting);

//                setting.setName(ltmsDBOpenHelper.Profile_ShSh);
//                setting.setValue(txtShSh.getText().toString());
//                dataSource.Setting_Insert(setting);

                setting.setName(ltmsDBOpenHelper.Profile_MobileNumber);
                setting.setValue(txtMobileNumber.getText().toString());
                dataSource.Setting_Insert(setting);

//                setting.setName(ltmsDBOpenHelper.Profile_HomeNumber);
//                setting.setValue(txtHomeNumber.getText().toString());
//                dataSource.Setting_Insert(setting);

//                setting.setName(ltmsDBOpenHelper.Profile_Mantagheh);
//                setting.setValue(txtMantagheh.getText().toString());
//                dataSource.Setting_Insert(setting);

//                setting.setName(ltmsDBOpenHelper.Profile_School);
//                setting.setValue(txtSchool.getText().toString());
//                dataSource.Setting_Insert(setting);

                setting.setName(ltmsDBOpenHelper.Profile_ProfileExist);
                setting.setValue(ltmsDBOpenHelper.Profile_ProfileExist);
                dataSource.Setting_Insert(setting);

                Toast toast= Toast.makeText(getActivity(), getResources().getString(R.string.Save), Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }




}
