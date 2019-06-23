package com.lgroup.ltms;


import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
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
import com.lgroup.ltms.com.lgroup.ltms.models.Unit;

import java.util.List;

public class UnitRecyclerViewAdapter extends RecyclerView
        .Adapter<UnitRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "UnitRecyclerViewAdapter";
    private List<Unit> mDataset;
    private static MyClickListener myClickListener;
    private Object context;
    private ltmsDataSource dataSource;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView tvUnitName;
        Button btnTest, btnLearn;

        public DataObjectHolder(View itemView) {
            super(itemView);
            tvUnitName = (TextView) itemView.findViewById(R.id.tvUnitName);



            btnLearn =(Button)itemView.findViewById(R.id.btnLearn);
            btnTest =(Button)itemView.findViewById(R.id.btnTest);
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

    public UnitRecyclerViewAdapter(List<Unit> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unit_card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        context=parent.getContext();


        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {


        dataSource=new ltmsDataSource(((FragmentActivity)context).getApplication());
        dataSource.open();

        holder.tvUnitName.setText("عنوان: " + String.valueOf(mDataset.get(position).getName())+"     تعداد بخش ها:  "+String.valueOf(dataSource.UnitDetail_FindAll(mDataset.get(position).getId()).size()));

        if (dataSource.Unit_ReadyToTestNew(mDataset.get(position).getPid(),mDataset.get(position).getId())) {

            //((CardView)holder.itemView.findViewById(R.id.card_view_Unit)).setCardBackgroundColor(Color.parseColor("#C5E1A5"));
            ((CardView)holder.itemView.findViewById(R.id.card_view_Unit)).setCardBackgroundColor(Color.parseColor("#00ff00"));

        }
        else {
           // ((CardView)holder.itemView.findViewById(R.id.card_view_Unit)).setCardBackgroundColor(Color.parseColor("#90A4AE"));
            ((CardView)holder.itemView.findViewById(R.id.card_view_Unit)).setCardBackgroundColor(Color.parseColor("#b7b7b7"));

        }

        holder.btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataSource.Unit_ReadyToTestNew(mDataset.get(position).getPid(),mDataset.get(position).getId())) {

                    FragmentManager fragmentManager =( (FragmentActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, UnitDetail_ItemFragment.newInstance(mDataset.get(position).getId())).addToBackStack("UnitDetail")
                            .commit();

                }
                else {
                    Toast.makeText((FragmentActivity) context, "ابتدا باید فصل های قبل را به پایان برسانید", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dataSource.Unit_ReadyToTestNew(mDataset.get(position).getPid(),mDataset.get(position).getId())) {

                    if (dataSource.Unit_Test_Result(mDataset.get(position).getPid()) >= 60) {

                        Toast.makeText((FragmentActivity)context, "پایان آزمون با موفقیت", Toast.LENGTH_SHORT).show();
                    }
                    FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, QuestionFragment.newInstance(mDataset.get(position).getId())).addToBackStack("Question")
                            .commit();
                } else {
                    Toast.makeText((FragmentActivity)context, "ابتدا باید فصل های قبل را به پایان برسانید", Toast.LENGTH_SHORT).show();
                }


//                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, Unit_ItemFragment.newInstance(mDataset.get(position).getId())).addToBackStack("Unit_ItemFragment")
//                        .commit();


//                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
//                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, AddPayFragment.newInstance(3, mDataset.get(position).getPayID())).addToBackStack("Pay")
//                        .commit();
            }
        });

        // dataSource.close();
    }



    public void addItem(Unit dataObj, int index) {
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