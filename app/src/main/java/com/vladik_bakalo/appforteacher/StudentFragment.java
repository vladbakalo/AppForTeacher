package com.vladik_bakalo.appforteacher;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vladik_bakalo.appforteacher.dbwork.DBWork;
import com.vladik_bakalo.appforteacher.dummy.StudentContent;
import com.vladik_bakalo.appforteacher.dummy.StudentContent.DummyItem;
import com.vladik_bakalo.appforteacher.restwork.Student;

import java.text.ParseException;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class StudentFragment extends Fragment {


    private OnListFragmentInteractionListener mListener;
    private Context appContext;
    private LinearLayoutManager linearLayoutManager;
    private EndlessRecyclerViewScrollListener scrollListener;
    private MyStudentRecyclerViewAdapter myStudentRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private StudentContent studentContent;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StudentFragment newInstance(int columnCount) {
        StudentFragment fragment = new StudentFragment();
        return fragment;
    }
    public void changeData(Cursor cursor)
    {
        studentContent.closeCursor();
        studentContent = new StudentContent(cursor);
        myStudentRecyclerViewAdapter = new MyStudentRecyclerViewAdapter(studentContent.ITEMS, mListener);
        recyclerView.swapAdapter(myStudentRecyclerViewAdapter, false);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            //
            //DBWork
            DBWork dbWork = new DBWork(appContext);
            Cursor cursor = dbWork.getCursorOfAllStudents();
            studentContent = new StudentContent(cursor);
            dbWork.closeAllConnection();

            //Scroll Work
            scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore() {
                    Log.i("----Scroll----", "onLoadMore");
                    try {
                        studentContent.updateArrayByStudents();
                        myStudentRecyclerViewAdapter.notifyDataSetChanged();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            };
            recyclerView.addOnScrollListener(scrollListener);
            myStudentRecyclerViewAdapter = new MyStudentRecyclerViewAdapter(studentContent.ITEMS, mListener);
            recyclerView.setAdapter(myStudentRecyclerViewAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
            appContext = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
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
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);
    }
}
