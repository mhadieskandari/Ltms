package com.lgroup.ltms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lgroup.ltms.com.lgroup.ltms.db.ltmsDBOpenHelper;
import com.lgroup.ltms.com.lgroup.ltms.db.ltmsDataSource;
import com.lgroup.ltms.com.lgroup.ltms.models.Content;
import com.lgroup.ltms.com.lgroup.ltms.models.Learning;
import com.lgroup.ltms.com.lgroup.ltms.models.Period;
import com.lgroup.ltms.com.lgroup.ltms.models.Question;
import com.lgroup.ltms.com.lgroup.ltms.models.Setting;
import com.lgroup.ltms.com.lgroup.ltms.models.Unit;
import com.lgroup.ltms.com.lgroup.ltms.models.Unit_Detail;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class CheckPeriodLicenseFragment extends Fragment {



    //private static final String URL = "http://ltmsm.lgroup.ir/backend/web/index.php?r=ws%2Fget&accesstoken=lwy4ln1pe7&serial=$QUERY$";
    //private static final String URL = "http://ltmsm.lgroup.ir/backend/web/index.php?r=ws%2Fget&accesstoken=lwy4ln1pe7&serial=$QUERY$";
    private static final String URL = "http://ltmsm.lgroup.ir/backend/web/index.php?r=ws%2Fget&accesstoken=lwy4ln1pe7&serial=$QUERY$";
    //private static final String URL = "http://192.168.1.103:81/ltmsmanager/backend/web/index.php?r=ws%2Fget&accesstoken=lwy4ln1pe7&serial=$QUERY$";


    EditText etLisenceCode;

    ProgressDialog mDialog;
    ltmsDataSource db;
    String Lisence;



    private static final String ARG_SECTION_NUMBER = "section_number";
    //private static final String ARG_PeriodId = "Pid";

    private int mSectionNumber;
//    private long mPid;

    Button btnCehckLicense;

    private OnFragmentInteractionListener mListener;

    public static CheckPeriodLicenseFragment newInstance(int sectionNumber) {
        CheckPeriodLicenseFragment fragment = new CheckPeriodLicenseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        //args.putLong(ARG_PeriodId, Pid);
        fragment.setArguments(args);
        return fragment;
    }

    public CheckPeriodLicenseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            //mPid = getArguments().getLong(ARG_PeriodId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_check_period_license, container, false);

        db=new ltmsDataSource(getActivity());
        db.open();

        etLisenceCode = (EditText) view.findViewById(R.id.etLisenceCode);

        btnCehckLicense = (Button) view.findViewById(R.id.btnCheckLicense);

        btnCehckLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lisence=etLisenceCode.getText().toString();

                if(Lisence.equals("")){
                    Toast.makeText(getActivity(),"لطفا لایسنس را وارد کنید",Toast.LENGTH_LONG).show();
                }
                else if(db.Peiod_Exist(Lisence)){
                    Toast.makeText(getActivity(),"این دوره قبلا ثبت شده است",Toast.LENGTH_LONG).show();
                }else {


                    if (isNetworkAvailable()) {
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected void onPreExecute() {
                                mDialog = ProgressDialog.show(getActivity(),
                                        "درحال بارگذاری", "لطفا منتظر بمانید", true, true);
                            }

                            @Override
                            protected String doInBackground(String... params) {
                                //String realUrl = URL.replace("$QUERY$", "Android");
                                String realUrl = URL.replace("$QUERY$", Lisence);
                                return requestContent(realUrl);
                            }

                            @Override
                            protected void onPostExecute(String res) {

                                Setting setting = new Setting();
                                Period period = new Period();
                                ArrayList<Unit> units = new ArrayList<Unit>();
                                ArrayList<Question> questions = new ArrayList<Question>();
                                ArrayList<Unit_Detail> unit_details = new ArrayList<Unit_Detail>();
                                ArrayList<Content> Contents = new ArrayList<Content>();
                                ArrayList<Learning> learnings = new ArrayList<Learning>();
//                          String profile =res.substring(res.indexOf("<profile"),res.indexOf("/>")+2);
//                          res=res.replace(profile,"");
                                //res="[{\"Profile\":[{\"id\":5,\"firstname\":\"میثم\",\"lastname\":\"ساغرچیها\",\"national_id\":\"4323513968\",\"email\":\"maysam_sa@yahoo.com\",\"mobile\":\"09107501062\",\"fullname\":\"میثم ساغرچیها\"}]},{\"Term\":[{\"id\":1,\"title\":\"نماز\",\"hour\":16,\"price\":\"25000\",\"start_date\":1442115000,\"stop_date\":1442773800,\"discription\":\"<p>sdasdasd<\\/p>\\n\",\"organization_id\":1}]}]";
//                            res="{\"Profile\":[{\"id\":5,\"firstname\":\"میثم\",\"lastname\":\"ساغرچیها\",\"national_id\":\"4323513968\",\"email\":\"maysam_sa@yahoo.com\",\"mobile\":\"09107501062\",\"fullname\":\"میثم ساغرچیها\"},{\"id\":5,\"firstname\":\"محمد\",\"lastname\":\"اسکندری\",\"national_id\":\"4323513961\",\"email\":\"maysam_sa@yahoo.com\",\"mobile\":\"09107501062\",\"fullname\":\"میثم ساغرچیها\"}]}";
                                //res =res.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>","");
//                            res=res.replace("<response>","");
//                            res=res.replace("</response>","");
                                try {
                                    JSONArray jsonArray = new JSONArray(res);

                                    db.beginTransaction();

                                    JSONObject jObjProf = jsonArray.getJSONObject(0);
                                    JSONArray jArrProf = jObjProf.getJSONArray("Profile");
                                    setting.setNameVal(ltmsDBOpenHelper.Profile_ID, jArrProf.getJSONObject(0).getString("id"));
                                    db.Setting_Insert(setting);
                                    setting.setNameVal(ltmsDBOpenHelper.Profile_FirstName, jArrProf.getJSONObject(0).getString("firstname"));
                                    db.Setting_Insert(setting);
                                    setting.setNameVal(ltmsDBOpenHelper.Profile_LastName, jArrProf.getJSONObject(0).getString("lastname"));
                                    db.Setting_Insert(setting);
                                    setting.setNameVal(ltmsDBOpenHelper.Profile_NationNumber, jArrProf.getJSONObject(0).getString("national_id"));
                                    db.Setting_Insert(setting);
                                    setting.setNameVal(ltmsDBOpenHelper.Profile_Email, jArrProf.getJSONObject(0).getString("email"));
                                    db.Setting_Insert(setting);
                                    setting.setNameVal(ltmsDBOpenHelper.Profile_MobileNumber, jArrProf.getJSONObject(0).getString("mobile"));
                                    db.Setting_Insert(setting);

                                    setting.setNameVal(ltmsDBOpenHelper.Profile_ProfileExist, ltmsDBOpenHelper.Profile_ProfileExist);
                                    db.Setting_Insert(setting);


                                    JSONObject jObjTerm = jsonArray.getJSONObject(1);
                                    JSONArray jArrTerm = jObjTerm.getJSONArray("Term");
                                    //term in webservice = period in my app
                                    period.setVals(jArrTerm.getJSONObject(0).getInt("id"), jArrTerm.getJSONObject(0).getString("title"), jArrTerm.getJSONObject(0).getString("discription"), jArrTerm.getJSONObject(0).getString("start_date"), jArrTerm.getJSONObject(0).getString("stop_date"), jArrTerm.getJSONObject(0).getInt("hour"), Lisence,0);

                                    db.Period_Insert(period);


                                    JSONObject jObjChapters = jsonArray.getJSONObject(2);
                                    JSONArray jArrChapters = jObjChapters.getJSONArray("Chapters");

                                    for (int i=0;i<jArrChapters.length();i++){
                                        Unit unit= new Unit(jArrChapters.getJSONObject(i).getInt("id"),jArrChapters.getJSONObject(i).getInt("term_id"),jArrChapters.getJSONObject(i).getString("title"),"",0);
                                        db.Unit_Insert(unit);
                                    }

                                    JSONObject jObjQuestions = jsonArray.getJSONObject(3);
                                    JSONArray jArrQuestions = jObjQuestions.getJSONArray("Questions");

                                    for (int i=0;i<jArrQuestions.length();i++){
                                        Question question=new Question(jArrQuestions.getJSONObject(i).getLong("id"),jArrQuestions.getJSONObject(i).getLong("chapter_id"),jArrQuestions.getJSONObject(i).getString("text"),jArrQuestions.getJSONObject(i).getString("item1"),jArrQuestions.getJSONObject(i).getString("item2"),jArrQuestions.getJSONObject(i).getString("item3"),jArrQuestions.getJSONObject(i).getString("item4"),"",jArrQuestions.getJSONObject(i).getString("answer"),0);
                                        db.Question_Insert(question);
                                    }

                                    JSONObject jObjUnitDetails = jsonArray.getJSONObject(4);
                                    JSONArray jArrUnitDetails = jObjUnitDetails.getJSONArray("ChapterDetail");

                                    for (int i=0;i<jArrUnitDetails.length();i++){
                                        Unit_Detail unit_detail=new Unit_Detail(jArrUnitDetails.getJSONObject(i).getLong("id"),jArrUnitDetails.getJSONObject(i).getString("title"),jArrUnitDetails.getJSONObject(i).getLong("chapter_id"));
                                        db.Unit_Detail_Insert(unit_detail);
                                        unit_details.add(unit_detail);
                                    }

                                    JSONObject jObjLearnings = jsonArray.getJSONObject(5);
                                    JSONArray jArrLearnings = jObjLearnings.getJSONArray("ChapterDetailPage");

                                    for (int i=0;i<jArrLearnings.length();i++){
                                        Learning learning=new Learning(jArrLearnings.getJSONObject(i).getLong("id"),jArrLearnings.getJSONObject(i).getLong("chapter_detail_id"),jArrLearnings.getJSONObject(i).getLong("content_id"),jArrLearnings.getJSONObject(i).getInt("page"));
                                        db.Learning_Insert(learning);
                                        learnings.add(learning);
                                    }

                                    JSONObject jObjContents = jsonArray.getJSONObject(6);
                                    JSONArray jArrContents = jObjContents.getJSONArray("ChapterDetailContent");

                                    for (int i=0;i<jArrContents.length();i++){
                                        Content content=new Content(jArrContents.getJSONObject(i).getLong("id"),jArrContents.getJSONObject(i).getString("content"));
                                        db.Content_Insert(content);
                                        Contents.add(content);
                                    }

                                    for (int i=0;i<Contents.size();i++){

                                        Contents.get(i);
                                        FileOutputStream fos = null;
                                        try {
                                            fos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Lqroup/"+Contents.get(i).getId()+".pdf");
                                            try {
                                                fos.write(Base64.decode(Contents.get(i).getContent(), Base64.DEFAULT));
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            fos.close();
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }


                                    db.setTransactionSuccessful();

                                    db.endTransaction();

                                    Toast.makeText(getActivity(),"دوره بارگذاری شد",Toast.LENGTH_LONG).show();

                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.container, PeriodDetail_Fragment.newInstance(period.getId())).addToBackStack("PeriodDetail")
                                            .commit();




                                    //JSONObject json = new JSONObject(res);

                                    //JSONArray pr = json.getJSONArray("Profile");


//

//                                JSONObject p0=pr.getJSONObject(0);
//
//                                int id=p0.getInt("id");
//                                String fn=p0.getString("firstname");

//                                JSONObject p1=pr.getJSONObject(1);
//
//                                int id1=p1.getInt("id");
//                                String fn1=p1.getString("firstname");

//                                JSONArray tr=json.getJSONArray("Term");
//                                JSONObject tr0=tr.getJSONObject(0);
//
//                                int tid=tr0.getInt("id");
//                                String ttitle=tr0.getString("title");


                                    //  JSONArray jArray = new JSONArray(res);
//                                JSONArray jarr = json.getJSONArray("Profile");
//
//
//                                int id=jobj.getInt("id");
//                                String fn=jobj.getString("firstname");


                                    //JSONObject jProfileTerms = json.getJSONObject("Term ");


                                    //JSONObject dataObject = json.getJSONObject("data");
                                    //JSONArray items = dataObject.getJSONArray("item");
//                                for (int i = 0; i < jArray.length(); i++) {
//                                    JSONObject termObject = jArray.getJSONObject(i);
//                                    Period period = new Period();
//                                    period.setId(termObject.getInt("id"));
//                                    period.setTitle(termObject.getString("title"));
//                                    period.setDescryption(termObject.getString("discription"));
//                                    period.setDate_start(termObject.getInt("start_date"));
//                                    period.setDate_end(termObject.getInt("stop_date"));
//                                    period.setState(0);
//                                    period.setTest_time(termObject.getInt("hour"));
////                                termObject.getInt("id"),termObject.getString("title")
////                                ,termObject.getString("hour"),termObject.getString("price")
////                                ,termObject.getString("start_date"),termObject.getString("stop_date")
////                                ,termObject.getString("discription"), termObject.getString("organization_id")
//
//
//                                }

                                } catch (JSONException e) {
                                    Log.i("MyTag", e.toString());
                                    Toast.makeText(getActivity(),"مشکل در ارتباط با سرور و بارگذاری دوره",Toast.LENGTH_LONG).show();

                                    // manage exceptions
                                }

//                            mPeriodAdapter = new ArrayAdapter<Period>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1,periods);
//                            mPeriodLv.setAdapter(mPeriodAdapter);
                                // dismiss progress dialog
                                mDialog.dismiss();
                            }

                        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // we target SDK > API 11


                    }else {
                        Toast.makeText(getActivity(), "خطا در اتصال به اینترنت", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });


        return view;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
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




    //xml

    public Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }


    public String getXmlFromUrl(String url) {
        String xml = null;

        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return XML
        return xml;
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

}
