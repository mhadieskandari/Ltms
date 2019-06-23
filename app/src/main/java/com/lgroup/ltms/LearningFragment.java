package com.lgroup.ltms;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lgroup.ltms.com.lgroup.ltms.db.ltmsDataSource;
import com.lgroup.ltms.com.lgroup.ltms.models.Learning;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LearningFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LearningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearningFragment extends Fragment {
    private static final String STATE_CURRENT_PAGE_INDEX = "current_page_index";
















    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_UDID = "UDID";
    private static final String ARG_SECTION_NUMBER = "section_number";




//    List<Learning> Learnings;

    ltmsDataSource dataSource;

    Button btnNext,btnPrevious;
//    TextView txtLearning;
//    ScrollView svLearning;

    // TODO: Rename and change types of parameters
    private long mUDID;

    private int CurrentPosition=0;
    private int MinPosition,MaxPosition;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LearningFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LearningFragment newInstance(long UDID) {
        LearningFragment fragment = new LearningFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_UDID, UDID);
        fragment.setArguments(args);
        return fragment;
    }

    public LearningFragment() {
        // Required empty public constructor
    }

//    public void CreateLearning(){
//        Learning l = new Learning();
//        l.setUdid(mUDID);
//        l.setContent("درباره این درس اینگونه است که باید طوری شود که فکر نکنند طوری شده");
//        l.setPagenumber(5);
//        l.setDescryption("توضیحات");
//        l.setStartpage(1);
//        l.setState(0);
//
//        dataSource.Learning_Insert(l);
//
//    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUDID = getArguments().getLong(ARG_UDID);
        }

//        dataSource.open();



        //CreateLearning();



//        Learnings=dataSource.Learning_FindAll(mUDID);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_learning, container, false);


//        txtLearning=(TextView)view.findViewById(R.id.txtLearning);
//        svLearning=(ScrollView)view.findViewById(R.id.svLearning);
        //svLearning.setLayoutParams();

//        if (Learnings.size()>0) {
//            MinPosition = 0;
//            MaxPosition = Learnings.size() - 1;
//            txtLearning.setText(Learnings.get(CurrentPosition).getContent());
//            dataSource.Learning_Read(Learnings.get(CurrentPosition).getId());
//        }
//        else {
//            txtLearning.setText("اطلاعاتی برای نمایش وجود ندارد");
//            MinPosition = -1;
//            MaxPosition = -1;
//        }

        btnNext=(Button)view.findViewById(R.id.btnNext);
        btnPrevious=(Button)view.findViewById(R.id.btnPervius);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
//                if (CurrentPosition<MaxPosition && MinPosition!=-1 && MaxPosition!=-1){
//                    CurrentPosition++;
//                    txtLearning.setText(Learnings.get(CurrentPosition).getContent());
//                    dataSource.Learning_Read(Learnings.get(CurrentPosition).getId());
//                    svLearning.pageScroll(View.FOCUS_UP);
//
//                }
//                else {
//                    Toast.makeText(getActivity(),"صفحه آخر",Toast.LENGTH_SHORT).show();
//                }


            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
//                if (CurrentPosition>MinPosition && MinPosition!=-1 && MaxPosition!=-1){
//                    CurrentPosition--;
//                    txtLearning.setText(Learnings.get(CurrentPosition).getContent());
//                    svLearning.pageScroll(View.FOCUS_UP);
//                }
//                else {
//                    Toast.makeText(getActivity(),"صفحه اول",Toast.LENGTH_SHORT).show();
//                }


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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dataSource =new ltmsDataSource(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));

//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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

}
