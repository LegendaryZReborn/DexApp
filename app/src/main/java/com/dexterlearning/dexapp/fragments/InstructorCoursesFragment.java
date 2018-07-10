package com.dexterlearning.dexapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dexterlearning.dexapp.R;
import com.dexterlearning.dexapp.activities.CourseActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class InstructorCoursesFragment extends Fragment {

    private RecyclerView rvCourseList;
    private LinearLayoutManager layoutManager;
    private ICoursesRecyclerAdapter rAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructor_courses, container, false);
        rvCourseList = (RecyclerView) view.findViewById(R.id.rvCourseList);
        rvCourseList.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(getContext());
        rvCourseList.setLayoutManager(layoutManager);

        rAdapter = new ICoursesRecyclerAdapter();
        rAdapter.setLayoutManager(layoutManager);
        rvCourseList.setAdapter(rAdapter);

        return view;
    }

    public static Fragment newInstance(String title) {
        Fragment f = new InstructorCoursesFragment();
        Bundle args = new Bundle();
        args.putString("msg", title);
        f.setArguments(args);

        return f;
    }

    public class ICoursesRecyclerAdapter extends RecyclerView.Adapter<ICoursesRecyclerAdapter.ViewHolder>{

        String [] courses = new String []{"Summer Camp 2018", "Python", "3D Printing",
        "Test 1", "Test 2"};


        private LinearLayoutManager layoutManager;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(parent.getContext());
            View view = li.inflate(R.layout.item_instructor_course, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CourseActivity.class);
                    TextView tvCourseTitle = (TextView) v.findViewById(R.id.tvCourseTitle);
                    String courseTitle = tvCourseTitle.getText().toString();
                    intent.putExtra("courseTitle", courseTitle);
                    startActivity(intent);
                }
            });

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tvCourseTitle.setText(courses[position]);
            holder.tvCommentText.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Suspendisse at augue et dolor aliquet malesuada at ut neque.");
            holder.imgvCourse.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        @Override
        public int getItemCount() {
            return courses.length;
        }

        public void setLayoutManager(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
           // private TextView tvProfileName;
            private ImageView imgvCourse;
            private TextView tvCourseTitle;
            private TextView tvCommentText;

            public ViewHolder(View itemView) {
                super(itemView);
                tvCourseTitle = (TextView)itemView.findViewById(R.id.tvCourseTitle);
                tvCommentText = (TextView) itemView.findViewById(R.id.tvCommentText);
                imgvCourse = (ImageView) itemView.findViewById(R.id.imgvCourse);

            }
        }
    }

}
