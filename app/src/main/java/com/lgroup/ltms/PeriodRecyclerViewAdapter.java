package com.lgroup.ltms;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lgroup.ltms.com.lgroup.ltms.db.ltmsDataSource;
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
import java.util.List;

public class PeriodRecyclerViewAdapter extends RecyclerView
        .Adapter<PeriodRecyclerViewAdapter
        .DataObjectHolder> {

    private static final String URL = "http://ltmsm.lgroup.ir/backend/web/index.php?r=ws%2Fset&accesstoken=lwy4ln1pe7&serial=$QUERY$&grade=$GRADE$";
    ProgressDialog mDialog;

    private static String LOG_TAG = "PeriodRecyclerViewAdapter";
    private List<Period> mDataset;
    private static MyClickListener myClickListener;
    private Object context;
    private ltmsDataSource dataSource;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView tvPeriodTitle, tvPeriodUnitsCount,tvPeriodStartDate,tvPeriodEndDate,tvPeriodTestTime,tvPeriodLicense,tvPeriodState;
        Button btnSend, btnStart;

        public DataObjectHolder(View itemView) {
            super(itemView);
//            tvPeriodID = (TextView) itemView.findViewById(R.id.tvUnitName);
            tvPeriodTitle = (TextView) itemView.findViewById(R.id.tvPeriodTitle);
            tvPeriodUnitsCount = (TextView) itemView.findViewById(R.id.tvPeriodUnitsCount);
            tvPeriodStartDate= (TextView) itemView.findViewById(R.id.tvPeriodStartDate);
            tvPeriodEndDate=(TextView)itemView.findViewById(R.id.tvPeriodEndDate);
            tvPeriodTestTime = (TextView) itemView.findViewById(R.id.tvPeriodTestTime);
            tvPeriodLicense= (TextView) itemView.findViewById(R.id.tvPeriodLicense);
            tvPeriodState= (TextView) itemView.findViewById(R.id.tvPeriodState);

            btnSend =(Button)itemView.findViewById(R.id.btnSend);
            btnStart =(Button)itemView.findViewById(R.id.btnStart);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public PeriodRecyclerViewAdapter(List<Period> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.period_card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        context=parent.getContext();
        dataSource=new ltmsDataSource(((FragmentActivity) context).getApplication());
        dataSource.open();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
//        holder.tvPeriodID.setText("ID: "+String.valueOf(mDataset.get(position).getId()));
        holder.tvPeriodTitle.setText("عنوان: "+mDataset.get(position).getTitle());
        holder.tvPeriodUnitsCount.setText("تعداد فصل ها: "+ String.valueOf(dataSource.Unit_FindAll(mDataset.get(position).getId()).size()));
        holder.tvPeriodStartDate.setText("تاریخ شروع: "+mDataset.get(position).getDate_start());
        holder.tvPeriodEndDate.setText("تاریخ پایان: "+mDataset.get(position).getDate_end());
        holder.tvPeriodTestTime.setText("زمان آزمون: "+String.valueOf(mDataset.get(position).getTest_time()));
        holder.tvPeriodLicense.setText("شماره سریال: "+mDataset.get(position).getLicence());
        holder.tvPeriodState.setText("وضعیت: "+mDataset.get(position).getState(""));

        if (dataSource.Unit_FindAll(mDataset.get(position).getId()).size() > 0) {
            boolean flag = true;
//            for (int i = 0; i < dataSource.Unit_FindAll(mPid).size(); i++) {
//                if (dataSource.Unit_Test_Result(dataSource.Unit_FindAll(mPid).get(i).getId()) < 60) {
//                    txtTestResult.setText(String.valueOf("ناتمام"));
//                    flag = false;
//                }
//            }
            if (dataSource.Period_Test_Result(mDataset.get(position).getId()) < 60) {
                holder.btnSend.setEnabled(false);
                holder.btnStart.setEnabled(true);
            }
            else {
;
                holder.btnSend.setEnabled(true);
                holder.btnStart.setEnabled(false);
            }

        }

        holder.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected void onPreExecute() {
                            mDialog = ProgressDialog.show(((FragmentActivity) context).getApplication(),
                                    "درحال ارسال کارنامه", "لطفا منتظر بمانید", true, true);
                        }

                        @Override
                        protected String doInBackground(String... params) {
                            //String realUrl = URL.replace("$QUERY$", "Android");
                            String realUrl = URL.replace("$QUERY$", mDataset.get(position).getLicence());
                            realUrl = realUrl.replace("$GRADE$", String.valueOf(dataSource.Period_Test_Result(mDataset.get(position).getId()) / 5));


                            return requestContent(realUrl);
                        }

                        @Override
                        protected void onPostExecute(String res) {


                            try {
                                JSONArray jsonArray = new JSONArray(res);


                                JSONObject jObjProf = jsonArray.getJSONObject(0);
                                JSONArray jArrProf = jObjProf.getJSONArray("Message");
                                String s = jArrProf.getString(0);
                                if (s.equals("grade already registered")) {
                                    Toast.makeText(((FragmentActivity) context).getApplication(), "کارنامه قبلا ارسال شده است", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(((FragmentActivity) context).getApplication(), "کارنامه ارسال شد", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(((FragmentActivity) context).getApplication(), "مشکل در ارتباط با سرور و بارگذاری دوره", Toast.LENGTH_LONG).show();

                            }

                            mDialog.dismiss();
                        }

                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // we target SDK > API 11


                } else {
                    Toast.makeText(((FragmentActivity) context).getApplication(), "خطا در اتصال به اینترنت", Toast.LENGTH_LONG).show();

                }
            }
        });



        holder.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, Unit_ItemFragment.newInstance(mDataset.get(position).getId())).addToBackStack("Unit_ItemFragment")
                        .commit();


//                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
//                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, AddPayFragment.newInstance(3, mDataset.get(position).getPayID())).addToBackStack("Pay")
//                        .commit();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ((Activity)context).getSystemService(Context.CONNECTIVITY_SERVICE);
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





    public void addItem(Period dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}