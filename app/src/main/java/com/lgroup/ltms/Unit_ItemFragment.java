package com.lgroup.ltms;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import com.lgroup.ltms.com.lgroup.ltms.db.ltmsDataSource;
import com.lgroup.ltms.com.lgroup.ltms.models.Unit;

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
public class Unit_ItemFragment extends Fragment implements AbsListView.OnItemClickListener {



    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager mLayoutManager;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PID = "PID";
    private static final String ARG_SECTION_NUMBER = "section_number";
    ltmsDataSource dataSource;

    List<Unit> units;

    // TODO: Rename and change types of parameters
    private int mPid;

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
    public static Unit_ItemFragment newInstance(int PID) {
        Unit_ItemFragment fragment = new Unit_ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PID, PID);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public Unit_ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource.open();


        if (getArguments() != null) {
            mPid = getArguments().getInt(ARG_PID);
        }

        //CreateUnit();

        dataSource.open();
         units = dataSource.Unit_FindAll(mPid);

//        // TODO: Change Adapter to display your content
//        mAdapter = new ArrayAdapter<Unit>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, units);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unit_fragment_item, container, false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.unit_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter1 = new UnitRecyclerViewAdapter(units);

        mRecyclerView.setAdapter(mAdapter1);

        //mRecyclerView.getChildAt(0).setBackgroundColor(Color.parseColor("#00ff00"));


        ((UnitRecyclerViewAdapter) mAdapter1).setOnItemClickListener(new UnitRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });





        // Set the adapter
//        mListView = (AbsListView) view.findViewById(android.R.id.list);
//        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
//
//        // Set OnItemClickListener so we can be notified on item clicks
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                if (dataSource.Unit_ReadyToTestNew(mPid, units.get(position).getId())) {
//
//                    if (dataSource.Unit_Test_Result(units.get(position).getId()) >= 60) {
//
//                        Toast.makeText(getActivity(), "پایان آزمون با موفقیت", Toast.LENGTH_SHORT).show();
//                    }
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.container, QuestionFragment.newInstance(units.get(position).getId())).addToBackStack("QuestionFragment")
//                            .commit();
//                } else {
//                    Toast.makeText(getActivity(), "ابتدا باید فصل های قبل را به پایان برسانید", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });

//        if(dataSource.Unit_ReadyToTest(units.get(position).getId())){
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.container, QuestionFragment.newInstance(units.get(position).getId())).addToBackStack("QuestionFragment")
//                    .commit();
//
//        }


//        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                if (dataSource.Unit_ReadyToTestNew(mPid, units.get(position).getId())) {
//
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.container, UnitDetail_ItemFragment.newInstance(units.get(position).getId())).addToBackStack("UnitDetail_ItemFragment")
//                            .commit();

//                    File file;
//                    String path1;
//                    if (mPid == 1) {
//                        if (position == 0) {
//
//                            path1 = Environment.getExternalStorageDirectory().getPath() + "/Lqroup/icdl1.pdf";
//                            file = new File(path1);
//                            Uri path = Uri.fromFile(file);
//                            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//                            pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            pdfOpenintent.setDataAndType(path, "application/pdf");
//                            try {
//                                startActivity(pdfOpenintent);
//                            } catch (ActivityNotFoundException e) {
//
//                            }
//                        } else if (position == 1) {
//
//                            path1 = Environment.getExternalStorageDirectory().getPath() + "/Lqroup/icdl2.pdf";
//                            file = new File(path1);
//                            Uri path = Uri.fromFile(file);
//                            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//                            pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            pdfOpenintent.setDataAndType(path, "application/pdf");
//                            try {
//                                startActivity(pdfOpenintent);
//                            } catch (ActivityNotFoundException e) {
//
//                            }
//                        } else if (position == 2) {
//
//                            path1 = Environment.getExternalStorageDirectory().getPath() + "/Lqroup/icdl3.pdf";
//                            file = new File(path1);
//                            Uri path = Uri.fromFile(file);
//                            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//                            pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            pdfOpenintent.setDataAndType(path, "application/pdf");
//                            try {
//                                startActivity(pdfOpenintent);
//                            } catch (ActivityNotFoundException e) {
//
//                            }
//                        } else if (position == 3) {
//
//                            path1 = Environment.getExternalStorageDirectory().getPath() + "/Lqroup/icdl4.pdf";
//                            file = new File(path1);
//                            Uri path = Uri.fromFile(file);
//                            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//                            pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            pdfOpenintent.setDataAndType(path, "application/pdf");
//                            try {
//                                startActivity(pdfOpenintent);
//                            } catch (ActivityNotFoundException e) {
//
//                            }
//                        }
//                    }
//
//                    //dolat electronic
//                    if(mPid == 2) {
//                        if (position == 0) {
//
//                            path1 = Environment.getExternalStorageDirectory().getPath() + "/Lqroup/dolat1.pdf";
//                            file = new File(path1);
//                            Uri path = Uri.fromFile(file);
//                            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//                            pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            pdfOpenintent.setDataAndType(path, "application/pdf");
//                            try {
//                                startActivity(pdfOpenintent);
//                            } catch (ActivityNotFoundException e) {
//
//                            }
//                        } else if (position == 1) {
//
//                            path1 = Environment.getExternalStorageDirectory().getPath() + "/Lqroup/dolat2.pdf";
//                            file = new File(path1);
//                            Uri path = Uri.fromFile(file);
//                            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//                            pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            pdfOpenintent.setDataAndType(path, "application/pdf");
//                            try {
//                                startActivity(pdfOpenintent);
//                            } catch (ActivityNotFoundException e) {
//
//                            }
//                        } else if (position == 2) {
//
//                            path1 = Environment.getExternalStorageDirectory().getPath() + "/Lqroup/dolat3.pdf";
//                            file = new File(path1);
//                            Uri path = Uri.fromFile(file);
//                            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//                            pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            pdfOpenintent.setDataAndType(path, "application/pdf");
//                            try {
//                                startActivity(pdfOpenintent);
//                            } catch (ActivityNotFoundException e) {
//
//                            }
//                        } else if (position == 3) {
//
//                            path1 = Environment.getExternalStorageDirectory().getPath() + "/Lqroup/dolat4.pdf";
//                            file = new File(path1);
//                            Uri path = Uri.fromFile(file);
//                            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//                            pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            pdfOpenintent.setDataAndType(path, "application/pdf");
//                            try {
//                                startActivity(pdfOpenintent);
//                            } catch (ActivityNotFoundException e) {
//
//                            }
//                        }
//                    }


//                } else {
//                    Toast.makeText(getActivity(), "ابتدا باید فصل های قبل را به پایان برسانید", Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }
//        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dataSource = new ltmsDataSource(activity);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
//        }
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





    private void CreateUnit(){
        Unit unit =new Unit();
        unit.setPid(mPid);
        unit.setName("فصل دوم");
        unit.setDescryption("درباره فصل دوم");
        unit.setState(0);
        dataSource.Unit_Insert(unit);


    }

}
