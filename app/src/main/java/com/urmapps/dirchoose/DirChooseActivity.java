package com.urmapps.dirchoose;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.urmapps.dirchoose.adapter.DirectoriesActionListener;
import com.urmapps.dirchoose.adapter.DirectoriesAdapter;
import com.urmapps.dirchoose.dialog.SelectDirectoryDialog;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirChooseActivity extends TemplateActivity implements DirectoriesActionListener {

    private RecyclerView mRecyclerView;
    private SelectDirectoryDialog mSelectDirectoryDialog;
    private DirectoriesAdapter mDirectoriesAdapter;
    private TextView mTextPath;
    private String mRootPath = Environment.getExternalStorageDirectory().getPath();
    private String mSelectedPath = mRootPath;

    private List<String> mDirectories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dirchoose);


        if (isStoragePermissionGranted()) {
//            showDirChooseDialog();
        } else {
            message(getString(R.string.message_permission_needs));
            finish();
            return;
        }

        mDirectories = getDirectories(mRootPath);
        mDirectoriesAdapter = new DirectoriesAdapter(mDirectories, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_dirchoose);
        mRecyclerView.setAdapter(mDirectoriesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void showDirChooseDialog() {
        mSelectDirectoryDialog = new SelectDirectoryDialog(DirChooseActivity.this);
        mSelectDirectoryDialog.setOwnerActivity(this);
        mSelectDirectoryDialog.show();
    }

    private List<String> getDirectories(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return Arrays.asList(file.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return new File(dir, filename).isDirectory();
                }
            }));
        }
        return null;
    }

    @Override
    public void onDirectoryOpen(String directory) {

    }

    @Override
    public void onDirectorySelected(String directory) {

    }

    @Override
    public void onDirectoryCreate(String newDirectoryName) {

    }
}
