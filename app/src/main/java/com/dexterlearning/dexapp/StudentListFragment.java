package com.dexterlearning.dexapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class StudentListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView rvStudentList;
    private LinearLayoutManager layoutManager;
    private StudentRecyclerAdapter rAdapter;
    private boolean isScrolling;
    private int selectedPosition = -1;
    //private View selectedItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvStudentList = (RecyclerView) getActivity().findViewById(R.id.rvStudentList);
        rvStudentList.setHasFixedSize(false);
       /* rvStudentList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = (newState == RecyclerView.SCROLL_STATE_DRAGGING);
            }
        });
        rvStudentList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if(mListener != null && e.getAction() == MotionEvent.ACTION_UP && !isScrolling){

                    View view = rv.findChildViewUnder(e.getX(), e.getY());
                    int position = rv.getChildAdapterPosition(view);
                    //selectPosition(position);

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
*/
        layoutManager = new LinearLayoutManager(getContext());
        rvStudentList.setLayoutManager(layoutManager);

        rAdapter = new StudentRecyclerAdapter();
        rAdapter.setLayoutManager(layoutManager);
        rvStudentList.setAdapter(rAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

   /* public void selectPosition(int position){
        if(position != RecyclerView.NO_POSITION) {
            if(selectedPosition != -1) {
                rvStudentList.getChildAt(selectedPosition).setSelected(false);
            }
            selectedPosition = position;
            rvStudentList.getChildAt(position).setSelected(true);
        }
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }

    public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder>{

        String [] students = new String []{"John Doe", "Mya Komeada",
                "Alexis Voltair", "Caroline Wilhelmina", "Osmosis Jones",
                "Caleb Brown", "Alice Nano", "Sir Victoreeem", "Sakure The Beautifuru",
                "Snoop Dog", "G Lite 10", "Mr.Tough Guy", "Mr. Nice Guy"};


        private LinearLayoutManager layoutManager;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(parent.getContext());
            View view = li.inflate(R.layout.item_student, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 /*   if(selectedPosition > -1) {
                        selectedItem.setSelected(false);
                    }

                    selectedItem = v;
                    selectedItem.setSelected(true);*/
                    selectedPosition = layoutManager.getPosition(v);
                    mListener.onFragmentInteraction(selectedPosition);
                }
            });

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tvProfileName.setText(students[position]);
        }

        @Override
        public int getItemCount() {
            return students.length;
        }

        public void setLayoutManager(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView tvProfileName;
            private ImageView imgvProPic;

            public ViewHolder(View itemView) {
                super(itemView);
                tvProfileName = (TextView)itemView.findViewById(R.id.tvProfileName);
                imgvProPic = (ImageView) itemView.findViewById(R.id.imgvProPic);
            }
        }
    }
}
