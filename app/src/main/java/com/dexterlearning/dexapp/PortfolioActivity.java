/*Base code taken from:
http://www.technetexperts.com/mobile/custom-file-explorer-in-android-application-development
*/

package com.dexterlearning.dexapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PortfolioActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSION_READ_WRITE_EXTERNAL_STORAGE=1;
    private String m_root= Environment.getExternalStorageDirectory().getPath();
    private ArrayList<String> m_item, m_path, m_files, m_filesPath;
    private String m_curDir;
    private PortfolioRecyclerAdapter m_listAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Handler mHandler;
    private boolean selectMode, justSelected;

    //ListView m_RootList;
    RecyclerView m_RootList;
    ImageButton btnNewFile;
    ImageButton btnDeleteFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        selectMode = false;
        m_RootList = (RecyclerView) findViewById(R.id.rvListRoot);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        m_RootList.setHasFixedSize(false);

        btnNewFile = (ImageButton) findViewById(R.id.btnNewFile);
        btnDeleteFile = (ImageButton) findViewById(R.id.btnDeleteFile);


        btnNewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewFolder(1);
            }
        });

        btnDeleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile();
            }
        });

        //Check permission at run time for API >= 23
        boolean grantedRead = ContextCompat.checkSelfPermission(PortfolioActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean grantedWrite = ContextCompat.checkSelfPermission(PortfolioActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (!grantedRead || !grantedWrite){
            ActivityCompat.requestPermissions(PortfolioActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_READ_WRITE_EXTERNAL_STORAGE);


        } else{
            getDirFromRoot(m_root);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Check if both read and write permissions were granted, then call getDirFromRoot
        if(requestCode == REQUEST_PERMISSION_READ_WRITE_EXTERNAL_STORAGE){
            boolean grantedAll = true;
            for(int i = 0; i < grantResults.length; ++i){
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    grantedAll = false;
                }
            }

            if(grantedAll){
                getDirFromRoot(m_root);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(!selectMode)
            super.onBackPressed();
        else{
            selectMode = false;
            deselectAll();
        }
    }

    //get directories and files from selected path
    public void getDirFromRoot(String p_rootPath) {
        m_item = new ArrayList<String>();
        Boolean m_isRoot = true;
        m_path = new ArrayList<String>();
        m_files = new ArrayList<String>();
        m_filesPath = new ArrayList<String>();
        File m_file = new File(p_rootPath);
        File[] m_filesArray = m_file.listFiles();
        if (!p_rootPath.equals(m_root)) {
            m_item.add("../");
            m_path.add(m_file.getParent());
            m_isRoot = false;
        }
        m_curDir = p_rootPath;
        //sorting file list in alphabetical order
        Arrays.sort(m_filesArray);
        for (int i = 0; i < m_filesArray.length; i++) {
            File file = m_filesArray[i];
            if (file.isDirectory()) {
                m_item.add(file.getName());
                m_path.add(file.getPath());
            } else {
                m_files.add(file.getName());
                m_filesPath.add(file.getPath());
            }
        }
        for (String m_AddFile : m_files) {
            m_item.add(m_AddFile);
        }
        for (String m_AddPath : m_filesPath) {
            m_path.add(m_AddPath);
        }

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        m_RootList.setLayoutManager(mLayoutManager);

        m_listAdapter = new PortfolioRecyclerAdapter(this, m_item, m_path, m_isRoot);
        m_RootList.setAdapter(m_listAdapter);
        m_RootList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View view = rv.findChildViewUnder(e.getX(), e.getY());

                //onIntercept is called each time for ACTION_UP, ACTION_DOWN, ACTION_MOVE,
                //so to ensure code gets called once, check the action
                if(view != null) {
                    int position = rv.getChildAdapterPosition(view);

                    switch (e.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (mHandler != null)
                                return false;
                            mHandler = new Handler();
                            mHandler.postDelayed(createRunnable(rv, position), 1000);

                            break;
                        case MotionEvent.ACTION_UP:
                            if (mHandler != null) {
                                mHandler.removeCallbacksAndMessages(null);
                                mHandler = null;
                            }

                            //TODO:Fix select mode bug, detoggles on action up
                            if(selectMode) {
                                if(!justSelected)
                                    selectItem(rv, position);

                                justSelected = false;
                            }
                            else{
                                File m_isFile = new File(m_path.get(position));
                                if (m_isFile.isDirectory()) {
                                    justSelected = false;
                                    Toast.makeText(PortfolioActivity.this, "This is Directory", Toast.LENGTH_SHORT).show();
                                    getDirFromRoot(m_isFile.toString());
                                } else {
                                    Toast.makeText(PortfolioActivity.this, "This is File", Toast.LENGTH_SHORT).show();
                                }
                            }

                            break;
                    }
                }

                return false;
            }

            void selectItem(RecyclerView touchedRv,int position){
                PortfolioRecyclerAdapter.ViewHolder vhItem =
                        (PortfolioRecyclerAdapter.ViewHolder) touchedRv.findViewHolderForAdapterPosition(position);

                //TODO:Fix deselecting bug (Items on second level deselects on selects)
                //TODO:Figure out how to focus selected items
                vhItem.m_cbCheck.setChecked(!vhItem.m_cbCheck.isChecked());
            }

            Runnable createRunnable(final RecyclerView touchedRv, final int position) {
                Runnable mAction = new Runnable() {
                    @Override
                    public void run() {
                        //Activate select mode if held down for some time
                        selectItem(touchedRv, position);
                        selectMode = true;
                        justSelected = true;
                    }
                };
                return mAction;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {


            }
        });
    }

    private void deselectAll(){
        ArrayList<Integer> new_selectItems = (ArrayList<Integer>) m_listAdapter.m_selectedItem.clone();

        for (int pos : new_selectItems){
            PortfolioRecyclerAdapter.ViewHolder vhItem =
                    (PortfolioRecyclerAdapter.ViewHolder)m_RootList.findViewHolderForAdapterPosition(pos);
            vhItem.m_cbCheck.setChecked(false);
        }
    }
    //Method to delete selected files (Not used yet)
    private void deleteFile() {
        for(int m_delItem : m_listAdapter.m_selectedItem)
        {
            File m_delFile =new File(m_path.get(m_delItem));
            Log.d("file",m_path.get(m_delItem));
            boolean m_isDelete=m_delFile.delete();
            Toast.makeText(PortfolioActivity.this, "File(s) Deledted", Toast.LENGTH_SHORT).show();
            getDirFromRoot(m_curDir);
        }

        /*after selected files are deleted, exit select mode
         */
        selectMode = false;
    }

    private void createNewFolder( final int p_opt){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText m_edtinput = new EditText(this);
        // Specify the type of input expected;
        m_edtinput.setInputType(InputType.TYPE_CLASS_TEXT);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_text = m_edtinput.getText().toString();
                if(p_opt == 1) {
                    File m_newPath=new File(m_curDir,m_text);
                    Log.d("cur dir",m_curDir);
                    if(!m_newPath.exists()) {
                        m_newPath.mkdirs();
                    }
                }
                else
                {
                    try {
                        FileOutputStream m_Output = new FileOutputStream((m_curDir+File.separator+m_text), false);
                        m_Output.close();
                        //  <!--<intent-filter>
                        //  <action android:name="android.intent.action.SEARCH" />
                        //  </intent-filter>
                        //  <meta-data android:name="android.app.searchable"
                        //  android:resource="@xml/searchable"/>-->

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                getDirFromRoot(m_curDir);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setView(m_edtinput);
        builder.show();
    }
}


