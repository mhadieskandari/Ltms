package com.lgroup.ltms;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
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
import com.lgroup.ltms.com.lgroup.ltms.models.Learning;
import com.lgroup.ltms.com.lgroup.ltms.models.Unit;
import com.lgroup.ltms.com.lgroup.ltms.models.Unit_Detail;

import java.io.File;
import java.util.List;

public class UnitDetailRecyclerViewAdapter extends RecyclerView
        .Adapter<UnitDetailRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "UnitDetailRecyclerViewAdapter";
    private List<Unit_Detail> mDataset;
    private static MyClickListener myClickListener;
    private Object context;
    private ltmsDataSource dataSource;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView tvUnitName,tvUnitDes,tvUnitState;
        Button btnLearn;

        public DataObjectHolder(View itemView) {
            super(itemView);
            tvUnitName = (TextView) itemView.findViewById(R.id.tvUnitName);


            btnLearn =(Button)itemView.findViewById(R.id.btnLearn);

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

    public UnitDetailRecyclerViewAdapter(List<Unit_Detail> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unitdetail_card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        context=parent.getContext();

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.tvUnitName.setText("عنوان: "+String.valueOf(mDataset.get(position).getName()));

        dataSource=new ltmsDataSource(((FragmentActivity)context).getApplication());
        dataSource.open();


        holder.btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                long udid= mDataset.get(position).getId();
                Learning learning = dataSource.Learning_FindFromUnitDetail(udid);
                if (learning != null) {
                    String path1 = Environment.getExternalStorageDirectory().getPath() + "/Lqroup/" + learning.getContentid() + ".pdf";
                    File file = new File(path1);
                    Uri path = Uri.fromFile(file);
                    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                    pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pdfOpenintent.setDataAndType(path, "application/pdf");
                    try {
                        ( (FragmentActivity)context).startActivity(pdfOpenintent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText((FragmentActivity)context, "لطفا یک برنامه برای باز کردن فایل PDF نصب کنید", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText((FragmentActivity)context, "خطا در بارگذاری فایل ضمیمه", Toast.LENGTH_SHORT).show();

                }








            }
        });


        //dataSource.close();
    }



    public void addItem(Unit_Detail dataObj, int index) {
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