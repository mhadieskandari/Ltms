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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lgroup.ltms.com.lgroup.ltms.models.Period;

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
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddPeriodFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddPeriodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPeriodFragment extends Fragment {

    //private static final String URL = "http://ltmsm.lgroup.ir/backend/web/index.php?r=wsterm&accesstoken=hadi";
    private static final String URL = "http://ltmsm.lgroup.ir/backend/web/index.php?r=ws%2Fget&accesstoken=lwy4ln1pe7";
    private static final String ARG_SECTION_NUMBER = "section_number";

    // TODO: Rename and change types of parameters
    private int SectionNumber;

    private OnFragmentInteractionListener mListener;





    ArrayList<Period> periods ;

    ProgressDialog mDialog;
    ListAdapter mPeriodAdapter;
    ListView mPeriodLv;

    public static AddPeriodFragment newInstance(int sectionSumber) {
        AddPeriodFragment fragment = new AddPeriodFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionSumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AddPeriodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            SectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_period, container, false);

        if(isNetworkAvailable()){
            Log.i("MyTag", "Internet connected");
        }
        else {
            Log.i("MyTag","Internet is not connected");
        }


        mPeriodLv =(ListView)view.findViewById(android.R.id.list);
        mPeriodLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                if(isNetworkAvailable()){
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.container, CheckPeriodLicenseFragment.newInstance(getArguments().getInt(ARG_SECTION_NUMBER),periods.get(position).getId())).addToBackStack(null)
//                            .commit();
//                }
//                else {
//                    Toast.makeText(getActivity(),getString(R.string.failedToConnectInternet),Toast.LENGTH_LONG).show();
//                }

            }
        });

        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {
                mDialog = ProgressDialog.show(getActivity(),
                        "درحال بارگذاری", "لطفا منتظر بمانید", true, true);
            }

            @Override
            protected String doInBackground(String... params) {
                String realUrl = URL.replace("$QUERY$", "Android");
                return requestContent(realUrl);
            }

            @Override
            protected void onPostExecute(String res) {
                periods = new ArrayList<Period>();

                try {
//                    JSONObject json = new JSONObject(res);
                    JSONArray items = new JSONArray(res);
//                    JSONObject dataObject = json.getJSONObject("data");
//                    JSONArray items = dataObject.getJSONArray("item");

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject termObject = items.getJSONObject(i);
                        Period period = new Period();
                        period.setId(termObject.getInt("id"));
                        period.setTitle(termObject.getString("title"));
                        period.setDescryption(termObject.getString("discription"));
                        period.setDate_start(termObject.getString("start_date"));
                        period.setDate_end(termObject.getString("stop_date"));
                        period.setState(0);
                        period.setTest_time(termObject.getInt("hour"));
//                                termObject.getInt("id"),termObject.getString("title")
//                                ,termObject.getString("hour"),termObject.getString("price")
//                                ,termObject.getString("start_date"),termObject.getString("stop_date")
//                                ,termObject.getString("discription"), termObject.getString("organization_id")

                        periods.add(period);
                    }

                } catch (JSONException e) {
                    Log.i("MyTag",e.toString());
                    // manage exceptions
                }

                mPeriodAdapter = new ArrayAdapter<Period>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1,periods);
                mPeriodLv.setAdapter(mPeriodAdapter);
                // dismiss progress dialog
                mDialog.dismiss();
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // we target SDK > API 11



        return view;
    }


    public String requestContent(String url) {
        HttpClient httpclient = new  DefaultHttpClient();
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
