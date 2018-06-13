/*Base code taken from:
http://www.technetexperts.com/mobile/custom-file-explorer-in-android-application-development
*/

package com.dexterlearning.dexapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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
    ArrayList<String> m_item, m_path, m_files, m_filesPath;
    String m_curDir;
    ListAdapter m_listAdapter;

    ListView m_RootList;
    ImageButton btnNewFile;
    ImageButton btnDeleteFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portfolio_activity);

        m_RootList = (ListView) findViewById(R.id.rl_lvListRoot);
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

    //get directories and files from selected path
    public void getDirFromRoot(String p_rootPath){
        m_item = new ArrayList<String>();
        Boolean m_isRoot=true;
        m_path = new ArrayList<String>();
        m_files=new ArrayList<String>();
        m_filesPath=new ArrayList<String>();
        File m_file = new File(p_rootPath);
        File[] m_filesArray = m_file.listFiles();
        if(!p_rootPath.equals(m_root))
        {
            m_item.add("../");
            m_path.add(m_file.getParent());
            m_isRoot=false;
        }
        m_curDir=p_rootPath;
        //sorting file list in alphabetical order
        Arrays.sort(m_filesArray);
        for(int i=0; i < m_filesArray.length; i++)
        {
            File file = m_filesArray[i];
            if(file.isDirectory())
            {
                m_item.add(file.getName());
                m_path.add(file.getPath());
            }
            else
            {
                m_files.add(file.getName());
                m_filesPath.add(file.getPath());
            }
        }
        for(String m_AddFile:m_files)
        {
            m_item.add(m_AddFile);
        }
        for(String m_AddPath:m_filesPath)
        {
            m_path.add(m_AddPath);
        }
        m_listAdapter =new ListAdapter(this,m_item,m_path,m_isRoot);
        m_RootList.setAdapter(m_listAdapter);
        m_RootList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                File m_isFile=new File(m_path.get(position));
                if(m_isFile.isDirectory())
                {
                    getDirFromRoot(m_isFile.toString());
                }
                else
                {
                    Toast.makeText(PortfolioActivity.this, "This is File", Toast.LENGTH_SHORT).show();
                }
            }
        });
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


