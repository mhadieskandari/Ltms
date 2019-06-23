package com.lgroup.ltms;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.lgroup.ltms.com.lgroup.ltms.db.ltmsDataSource;
import com.lgroup.ltms.com.lgroup.ltms.models.Period;

import java.sql.SQLException;
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
public class PeriodItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static final String LOGTAG="mylogtag";

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    ltmsDataSource dataSource ;

    List<Period> periods;
    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private Period mAdapter;



    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager mLayoutManager;



    Button btnAddPeriod;

    // TODO: Rename and change types of parameters
    public static PeriodItemFragment newInstance(int sectionNumber) {
        PeriodItemFragment fragment = new PeriodItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PeriodItemFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSource.open();
//        createPeriod();

        periods = dataSource.Period_FindAll();

        // TODO: Change Adapter to display your content
//        mAdapter = new ArrayAdapter<Period>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1,periods );


//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast toast=Toast.makeText(getActivity(),String.valueOf(periods.get(position).getId()),Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
    }

    public void createPeriod(){

        Period period = new Period();
        period.setTitle("دوره دوم مطهری");
        period.setDescryption(" توضیحات 2");
        period.setDate_start("13940701");
        period.setDate_end("13940725");
        period.setTest_time(20);
        period.setLicence("mksda42dfdamfsdsd");
        period.setState(0);

        period=dataSource.Period_Insert(period);
        Log.i(LOGTAG, "Period has been created by id :" + period.getId());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.period_fragment_item, container, false);
       // dataSource.open();
        // Set the adapter
//        mListView = (AbsListView) view.findViewById(android.R.id.list);
//        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.period_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter1 = new PeriodRecyclerViewAdapter(periods);

        mRecyclerView.setAdapter(mAdapter1);
        ((PeriodRecyclerViewAdapter) mAdapter1).setOnItemClickListener(new PeriodRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
               // Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });


        btnAddPeriod=(Button)view.findViewById(R.id.btnAddPeriod);

        btnAddPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, AddPeriodFragment.newInstance(getArguments().getInt(ARG_SECTION_NUMBER))).addToBackStack("addP")
//                        .commit();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, CheckPeriodLicenseFragment.newInstance(getArguments().getInt(ARG_SECTION_NUMBER))).addToBackStack("chkP")
                        .commit();


            }
        });



        // Set OnItemClickListener so we can be notified on item clicks
       // mListView.setOnItemClickListener(this);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Toast toast=Toast.makeText(getActivity(),String.valueOf(periods.get(position).getId()),Toast.LENGTH_SHORT);
////                toast.show();
//
//                //long adsfas= periods.get(position).getId();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, PeriodDetail_Fragment.newInstance(periods.get(position).getId())).addToBackStack("PeriodDetail")
//                        .commit();
//
//
//            }
//        });

        return view;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (null != mListener) {
//            mListener.onFragmentInteraction(String.valueOf(periods.get(position).getId()));
//            Log.i(LOGTAG,"the selected period is "+periods.get(position).getId());
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
