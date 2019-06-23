package com.lgroup.ltms;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.graphics.pdf.*;
import android.widget.Toast;

import com.lgroup.ltms.com.lgroup.ltms.db.ltmsDataSource;
import com.lgroup.ltms.com.lgroup.ltms.models.Learning;
import com.lgroup.ltms.com.lgroup.ltms.models.Unit_Detail;


import java.io.File;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class UnitDetail_ItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager mLayoutManager;




    public static final String FRAGMENT_PDF_RENDERER_BASIC = "pdf_renderer_basic";



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_UID = "UID";
    private static final String ARG_SECTION_NUMBER = "section_number";
    // TODO: Rename and change types of parameters
    private long mUID;

    List<Unit_Detail> UnitDetails;

    ltmsDataSource dataSource;


    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static UnitDetail_ItemFragment newInstance(long uid) {
        UnitDetail_ItemFragment fragment = new UnitDetail_ItemFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_UID, uid);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UnitDetail_ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUID = getArguments().getLong(ARG_UID);
        }

        dataSource.open();

        //CreateUnitDetail();

        UnitDetails =dataSource.UnitDetail_FindAll(mUID);


        // TODO: Change Adapter to display your content
//        mAdapter = new ArrayAdapter<Unit_Detail>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, UnitDetails);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unitdetail_fragment_item, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.unitdetail_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter1 = new UnitDetailRecyclerViewAdapter(UnitDetails);

        mRecyclerView.setAdapter(mAdapter1);
        ((UnitDetailRecyclerViewAdapter) mAdapter1).setOnItemClickListener(new UnitDetailRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });

        // Set the adapter
//        mListView = (AbsListView) view.findViewById(android.R.id.list);
//        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);




//        // Set OnItemClickListener so we can be notified on item clicks
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////                fragmentManager.beginTransaction()
////                        .replace(R.id.container, LearningFragment.newInstance(UnitDetails.get(position).getId())).addToBackStack("LearningFragment")
////                        .commit();
//
//                long udid=((Unit_Detail) mListView.getItemAtPosition(position)).getId();
//                Learning learning = dataSource.Learning_FindFromUnitDetail(udid);
//                if (learning != null) {
//                    String path1 = Environment.getExternalStorageDirectory().getPath() + "/Lqroup/" + learning.getContentid() + ".pdf";
//                    File file = new File(path1);
//                    Uri path = Uri.fromFile(file);
//                    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//                    pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    pdfOpenintent.setDataAndType(path, "application/pdf");
//                    try {
//                        startActivity(pdfOpenintent);
//                    } catch (ActivityNotFoundException e) {
//
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "خطا در بارگذاری فایل ضمیمه", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        });




        return view;
    }





    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dataSource =new ltmsDataSource(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(UnitDetails.get(position).getId());
//        }
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

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
        public void onFragmentInteraction(String id);
    }


    public void CreateUnitDetail(){
        Unit_Detail ud = new Unit_Detail();
        ud.setName("مقدمه");
        ud.setUid(mUID);
        ud.setStartpage(1);
        ud.setEndpage(10);
        ud.setState(0);

        dataSource.Unit_Detail_Insert(ud);



    }

}
