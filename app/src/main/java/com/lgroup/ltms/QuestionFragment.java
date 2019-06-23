package com.lgroup.ltms;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lgroup.ltms.com.lgroup.ltms.db.ltmsDataSource;
import com.lgroup.ltms.com.lgroup.ltms.models.Question;

import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment  implements DialogInterface.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_UID = "UID";
    private static final String ARG_SECTION_NUMBER = "section_number";
    // TODO: Rename and change types of parameters
    private long mUID;
    private Question question;
    List<Question> Qlist;

    TextView tvpass;


    private int CurrentPosition,MinPosition,MaxPosition;

    RadioButton rdbSel1,rdbSel2,rdbSel3,rdbSel4;
    Button btnQuestionNext,btnQuestionPrevious;
    TextView txtAnswer,txtPercentage,txtQuestion ;
    RadioGroup rg ;
    ProgressBar progressBar ;

    Random rand = new Random();



    ltmsDataSource dataSource;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(long UID) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_UID, UID);
        fragment.setArguments(args);
        return fragment;
    }

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUID = getArguments().getLong(ARG_UID);
        }

        dataSource.open();

        //CreateQuestion();

        Qlist= dataSource.Question_FindAll(mUID);

//        if(Qlist.size()==0){
//
//        }











}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_question, container, false);

        tvpass=(TextView)view.findViewById(R.id.tvpass);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBarResult);

        txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        txtAnswer = (TextView) view.findViewById(R.id.txtAnswer);
        txtPercentage = (TextView) view.findViewById(R.id.txtPercentage);

        rdbSel1 = (RadioButton) view.findViewById(R.id.rdbSel1);
        rdbSel2 = (RadioButton) view.findViewById(R.id.rdbSel2);
        rdbSel3 = (RadioButton) view.findViewById(R.id.rdbSel3);
        rdbSel4 = (RadioButton) view.findViewById(R.id.rdbSel4);

        rg = (RadioGroup) view.findViewById(R.id.radioGroupQuestion);


        MinPosition = 0;
        MaxPosition = Qlist.size();
        if (MaxPosition > 0) {
            CurrentPosition = rand.nextInt(MaxPosition);

            txtPercentage.setText(String.valueOf((int) dataSource.Unit_Test_Result(mUID)) + " %");
            progressBar.setProgress((int) dataSource.Unit_Test_Result(mUID));

            if (Qlist.size() > 0) {

                txtQuestion.setText(String.valueOf(Qlist.get(CurrentPosition).getQuestion().toString()));
                rdbSel1.setText(String.valueOf(Qlist.get(CurrentPosition).getSel_1().toString()));
                rdbSel2.setText(String.valueOf(Qlist.get(CurrentPosition).getSel_2().toString()));
                rdbSel3.setText(String.valueOf(Qlist.get(CurrentPosition).getSel_3().toString()));
                rdbSel4.setText(String.valueOf(Qlist.get(CurrentPosition).getSel_4().toString()));
            }
        } else {
            getFragmentManager().popBackStack();


        }

        btnQuestionNext = (Button) view.findViewById(R.id.btnNextQuestion);


        txtAnswer = (TextView) view.findViewById(R.id.txtAnswer);
        txtPercentage = (TextView) view.findViewById(R.id.txtPercentage);


//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }

        btnQuestionNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selChoosed = "-1";

                if (rdbSel1.isChecked()) {
                    selChoosed = "1";
                } else if (rdbSel2.isChecked()) {
                    selChoosed = "2";
                } else if (rdbSel3.isChecked()) {
                    selChoosed = "3";
                } else if (rdbSel4.isChecked()) {
                    selChoosed = "4";
                } else {
                    selChoosed = "-1";
                }
                String sel = Qlist.get(CurrentPosition).getTruesel();


                if (sel.equals(selChoosed)) {
                    if (Qlist.get(CurrentPosition).getState() == 0) {
                        dataSource.Question_Update_State(Qlist.get(CurrentPosition).getId(), 1);
                    } else if (Qlist.get(CurrentPosition).getState() == 1) {
                        dataSource.Question_Update_State(Qlist.get(CurrentPosition).getId(), 2);
                    }
                    Toast.makeText(getActivity(), "صحیح", Toast.LENGTH_SHORT).show();

//                    if (selChoosed.equals("1")) {
//                        rdbSel1.setBackgroundColor(Color.GREEN);
//                    } else if (selChoosed.equals("2")) {
//                        rdbSel2.setBackgroundColor(Color.GREEN);
//                    } else if (selChoosed.equals("3")) {
//                        rdbSel3.setBackgroundColor(Color.GREEN);
//                    } else if (selChoosed.equals("4")) {
//                        rdbSel4.setBackgroundColor(Color.GREEN);
//                    }

                } else {
                    if (Qlist.get(CurrentPosition).getState() == 1) {
                        dataSource.Question_Update_State(Qlist.get(CurrentPosition).getId(), 0);
                    }

                    Toast.makeText(getActivity(), "غلط", Toast.LENGTH_SHORT).show();


//                    if (selChoosed.equals("1")) {
//                        rdbSel1.setBackgroundColor(Color.RED);
//                    } else if (selChoosed.equals("2")) {
//                        rdbSel2.setBackgroundColor(Color.RED);
//                    } else if (selChoosed.equals("3")) {
//                        rdbSel3.setBackgroundColor(Color.RED);
//                    } else if (selChoosed.equals("4")) {
//                        rdbSel4.setBackgroundColor(Color.RED);
//                    }

                }


                //txtPercentage.setText(String.valueOf(dataSource.Period_Test_Result_1(mUID))+" %");\

                txtPercentage.setText(String.valueOf((int) dataSource.Unit_Test_Result(mUID)) + " %");
                progressBar.setProgress((int) dataSource.Unit_Test_Result(mUID));

                if (dataSource.Unit_Test_Result(mUID) >= 60) {
                    txtPercentage.setBackgroundColor(Color.GREEN);
                    dataSource.Unit_SetReadyToTest(mUID);
                    tvpass.setVisibility(View.VISIBLE);

                } else {
                    txtPercentage.setBackgroundColor(Color.TRANSPARENT);
                }


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                //CurrentPosition++;
                Qlist = dataSource.Question_FindAll(mUID);

                MaxPosition = Qlist.size();
                if (MaxPosition > 0) {
                    CurrentPosition = rand.nextInt(MaxPosition);


                    if (Qlist.size() > 0) {

                        rg.clearCheck();

//                    rdbSel1.setBackgroundColor(Color.TRANSPARENT);
//                    rdbSel2.setBackgroundColor(Color.TRANSPARENT);
//                    rdbSel3.setBackgroundColor(Color.TRANSPARENT);
//                    rdbSel4.setBackgroundColor(Color.TRANSPARENT);

                        txtQuestion.setText(Qlist.get(CurrentPosition).getQuestion().toString());
                        rdbSel1.setText(Qlist.get(CurrentPosition).getSel_1());
                        rdbSel2.setText(Qlist.get(CurrentPosition).getSel_2());
                        rdbSel3.setText(Qlist.get(CurrentPosition).getSel_3());
                        rdbSel4.setText(Qlist.get(CurrentPosition).getSel_4());

                    } else {
                        Toast.makeText(getActivity(), "پایان آزمون", Toast.LENGTH_LONG).show();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        //getActivity().onBackPressed();

                    }
                } else {
                    Toast.makeText(getActivity(), "پایان آزمون", Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    getFragmentManager().popBackStack();
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
        dataSource = new ltmsDataSource(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {



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

    public void CreateQuestion(){
        Question q = new Question();
        q.setUid(mUID);
        q.setQuestion("آیا این طور است که");
        q.setSel_1("گزینه 1");
        q.setSel_2("گزینه 2");
        q.setSel_3("گزینه 3");
        q.setSel_4("گزینه 4");
        q.setAnswer("جواب");
        q.setTruesel("1");
        q.setState(0);

        dataSource.Question_Insert(q);


    }

}

