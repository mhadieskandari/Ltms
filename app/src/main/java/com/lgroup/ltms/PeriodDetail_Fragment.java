package com.lgroup.ltms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lgroup.ltms.com.lgroup.ltms.db.ltmsDataSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PeriodDetail_Fragment extends Fragment {


    private static final String URL = "http://ltmsm.lgroup.ir/backend/web/index.php?r=ws%2Fset&accesstoken=lwy4ln1pe7&serial=$QUERY$&grade=$GRADE$";
    ProgressDialog mDialog;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_SECTION_NUMBER = "section_number";
    // TODO: Rename and change types of parameters
    private int mPid;

    ltmsDataSource dataSource ;

    Button btnSendResult;

    TextView txtPeriod,txtStartDate,txtEndDate,txtState,txtTestTime,txtDesc,txtTestResult,txtScore;
    Button btnStartLearning,btnStartTest;


    private OnFragmentInteractionListener mListener;

    public static PeriodDetail_Fragment newInstance(int PeriodId) {
        //mPid=PeriodId;
        PeriodDetail_Fragment fragment = new PeriodDetail_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, PeriodId);
        fragment.setArguments(args);
        return fragment;
    }

    public PeriodDetail_Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        dataSource.open();

        if (getArguments() != null) {
            mPid = getArguments().getInt(ARG_PARAM1);

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.period_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataSource.open();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_period_detail_, container, false);
        txtScore=(TextView)view.findViewById(R.id.txtScore);
        txtPeriod = (TextView) view.findViewById(R.id.txtPeiod);
        txtStartDate = (TextView) view.findViewById(R.id.txtStartDate);
        txtEndDate = (TextView) view.findViewById(R.id.txtEndDate);
        txtDesc = (TextView) view.findViewById(R.id.txtDesc);
        txtState = (TextView) view.findViewById(R.id.txtState);
        txtTestTime = (TextView) view.findViewById(R.id.txtTestTime);
        txtTestResult = (TextView) view.findViewById(R.id.txtTestResult);
        btnSendResult = (Button) view.findViewById(R.id.btnSendResult);
        btnSendResult.setEnabled(false);

        btnStartLearning = (Button) view.findViewById(R.id.btnBeginLearning);
        btnStartLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, Unit_ItemFragment.newInstance(mPid)).addToBackStack("Unit_ItemFragment")
                        .commit();
            }
        });



        txtPeriod.setText(String.valueOf(dataSource.Period_Select(mPid).getTitle()));
        txtStartDate.setText(String.valueOf(dataSource.Period_Select(mPid).getDate_start()));
        txtEndDate.setText(String.valueOf(dataSource.Period_Select(mPid).getDate_end()));
        txtDesc.setText(String.valueOf(dataSource.Period_Select(mPid).getDescryption()));
        txtTestTime.setText(String.valueOf(dataSource.Period_Select(mPid).getTest_time()));
        txtScore.setText(String.valueOf(dataSource.Period_Test_Result(mPid)/5));
        //txtTestResult.setText(String.valueOf(String.valueOf(dataSource.Period_Test_Result(mPid)))+" %");
        if (dataSource.Period_Select(mPid).getState() == 0) {
            txtState.setText("جدید");
        } else if (dataSource.Period_Select(mPid).getState() == 1) {
            txtState.setText("شروع شده");
        } else {
            txtState.setText("به اتمام رسیده");
        }

        if (dataSource.Unit_FindAll(mPid).size() > 0) {
            boolean flag = true;
//            for (int i = 0; i < dataSource.Unit_FindAll(mPid).size(); i++) {
//                if (dataSource.Unit_Test_Result(dataSource.Unit_FindAll(mPid).get(i).getId()) < 60) {
//                    txtTestResult.setText(String.valueOf("ناتمام"));
//                    flag = false;
//                }
//            }
            if (dataSource.Period_Test_Result(mPid) < 60) {
                    txtTestResult.setText(String.valueOf("ناتمام"));
//                    flag = false;
            }
            else {
                txtTestResult.setText(String.valueOf("قبول"));
                btnSendResult.setEnabled(true);
                btnStartLearning.setEnabled(false);
            }

//            if(dataSource.Period_Test_Result(mPid)>=60){
//                txtTestResult.setText(String.valueOf("قبول"));
//                btnSendResult.setEnabled(true);
//                btnStartLearning.setEnabled(false);
//            }
//            else {
//                txtTestResult.setText(String.valueOf("ناتمام"));
//                btnSendResult.setEnabled(false);
//                btnStartLearning.setEnabled(true);
//            }
        }

        txtTestTime.setText(String.valueOf(dataSource.Period_Select(mPid).getTest_time()));

        btnSendResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected void onPreExecute() {
                            mDialog = ProgressDialog.show(getActivity(),
                                   "درحال ارسال کارنامه", "لطفا منتظر بمانید", true, true);
                        }

                        @Override
                        protected String doInBackground(String... params) {
                            //String realUrl = URL.replace("$QUERY$", "Android");
                            String realUrl = URL.replace("$QUERY$", dataSource.Period_Select(mPid).getLicence());
                            realUrl=realUrl.replace("$GRADE$",String.valueOf(dataSource.Period_Test_Result(mPid)/5));


                            return requestContent(realUrl);
                        }

                        @Override
                        protected void onPostExecute(String res) {


                            try {
                                JSONArray jsonArray = new JSONArray(res);


                                JSONObject jObjProf = jsonArray.getJSONObject(0);
                                JSONArray jArrProf = jObjProf.getJSONArray("Message");
                                String s=jArrProf.getString(0);
                                if (s.equals("grade already registered") ){
                                    Toast.makeText(getActivity(), "کارنامه قبلا ارسال شده است", Toast.LENGTH_LONG).show();

                                }
                                else {
                                    Toast.makeText(getActivity(), "کارنامه ارسال شد", Toast.LENGTH_LONG).show();
                                }
//                                setting.setNameVal(ltmsDBOpenHelper.Profile_ID, jArrProf.getJSONObject(0).getString("id"));
//                                dataSource.Setting_Insert(setting);
//                                setting.setNameVal(ltmsDBOpenHelper.Profile_FirstName, jArrProf.getJSONObject(0).getString("firstname"));
//                                dataSource.Setting_Insert(setting);
//                                setting.setNameVal(ltmsDBOpenHelper.Profile_LastName, jArrProf.getJSONObject(0).getString("lastname"));
//                                dataSource.Setting_Insert(setting);
//                                setting.setNameVal(ltmsDBOpenHelper.Profile_NationNumber, jArrProf.getJSONObject(0).getString("national_id"));
//                                dataSource.Setting_Insert(setting);
//                                setting.setNameVal(ltmsDBOpenHelper.Profile_Email, jArrProf.getJSONObject(0).getString("email"));
//                                dataSource.Setting_Insert(setting);
//                                setting.setNameVal(ltmsDBOpenHelper.Profile_MobileNumber, jArrProf.getJSONObject(0).getString("mobile"));
//                                dataSource.Setting_Insert(setting);
//
//                                setting.setNameVal(ltmsDBOpenHelper.Profile_ProfileExist, ltmsDBOpenHelper.Profile_ProfileExist);
//                                dataSource.Setting_Insert(setting);
//
//
//                                JSONObject jObjTerm = jsonArray.getJSONObject(1);
//                                JSONArray jArrTerm = jObjTerm.getJSONArray("Term");
//                                //term in webservice = period in my app
//                                period.setVals(jArrTerm.getJSONObject(0).getLong("id"), jArrTerm.getJSONObject(0).getString("title"), jArrTerm.getJSONObject(0).getString("discription"), jArrTerm.getJSONObject(0).getString("start_date"), jArrTerm.getJSONObject(0).getString("stop_date"), jArrTerm.getJSONObject(0).getInt("hour"), "", 0);
//
//                                dataSource.Period_Insert(period);
//
//
//                                JSONObject jObjChapters = jsonArray.getJSONObject(2);
//                                JSONArray jArrChapters = jObjChapters.getJSONArray("Chapters");
//
//                                for (int i = 0; i < jArrChapters.length(); i++) {
//                                    Unit unit = new Unit(jArrChapters.getJSONObject(i).getInt("id"), jArrChapters.getJSONObject(i).getInt("term_id"), jArrChapters.getJSONObject(i).getString("title"), "", 0);
//                                    dataSource.Unit_Insert(unit);
//                                }
//
//                                JSONObject jObjQuestions = jsonArray.getJSONObject(3);
//                                JSONArray jArrQuestions = jObjQuestions.getJSONArray("Questions");
//
//                                for (int i = 0; i < jArrQuestions.length(); i++) {
//                                    Question question = new Question(jArrQuestions.getJSONObject(i).getLong("id"), jArrQuestions.getJSONObject(i).getLong("chapter_id"), jArrQuestions.getJSONObject(i).getString("text"), jArrQuestions.getJSONObject(i).getString("item1"), jArrQuestions.getJSONObject(i).getString("item2"), jArrQuestions.getJSONObject(i).getString("item3"), jArrQuestions.getJSONObject(i).getString("item4"), "", jArrQuestions.getJSONObject(i).getString("answer"), 0);
//                                    dataSource.Question_Insert(question);
//                                }
//
//                                dataSource.setTransactionSuccessful();
//
//                                dataSource.endTransaction();

                                //Toast.makeText(getActivity(), "دوره بارگذاری شد", Toast.LENGTH_LONG).show();

//                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                                fragmentManager.beginTransaction()
//                                        .replace(R.id.container, PeriodDetail_Fragment.newInstance(period.getId())).addToBackStack("PeriodDetail")
//                                        .commit();


                            } catch (JSONException e) {
                                Log.i("MyTag", e.toString());
                                Toast.makeText(getActivity(), "مشکل در ارتباط با سرور و بارگذاری دوره", Toast.LENGTH_LONG).show();

                            }

                            mDialog.dismiss();
                        }

                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // we target SDK > API 11


                }else {
                    Toast.makeText(getActivity(), "خطا در اتصال به اینترنت", Toast.LENGTH_LONG).show();

                }
            }
        });


        return view;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public String requestContent(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = null;
        InputStream instream = null;

        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {
            Log.i("MyTag",e.toString());
            // manage exceptions
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }

        return sb.toString();
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        dataSource=new ltmsDataSource(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

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



}
