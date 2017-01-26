package com.urmapps.dirchoose.dialog;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.urmapps.dirchoose.R;
import com.urmapps.dirchoose.adapter.DirectoriesActionListener;
import com.urmapps.dirchoose.adapter.DirectoriesAdapter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectDirectoryDialog extends TemplateDialog implements DirectoriesActionListener {

    private static final String TAG = "SelectDirectoryDialog";

    private RecyclerView recyclerView;
    private DirectoriesAdapter directoriesAdapter;
    private Button buttonOpen, buttonSelect;
    private ImageView imageViewBack;
    private TextView textPath;
    private String rootPath = Environment.getExternalStorageDirectory().getPath();
    private String selectedPath = rootPath;

    private List<String> directories = new ArrayList<>();

    public SelectDirectoryDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_select_directory);
        buttonOpen = (Button) findViewById(R.id.button_open);
        buttonOpen.setOnClickListener(this);
        buttonSelect = (Button) findViewById(R.id.button_select);
        buttonSelect.setOnClickListener(this);

        imageViewBack = (ImageView) findViewById(R.id.imageView_back);
        imageViewBack.setOnClickListener(this);

        textPath = (TextView) findViewById(R.id.text_path);
        textPath.setText(rootPath);

        directories = getDirectories(rootPath);

        directoriesAdapter = new DirectoriesAdapter(directories, this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_directories);
        recyclerView.setAdapter(directoriesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onClick(View v) {
        String selectedDir = directoriesAdapter.getSelectedDirectory();
        switch (v.getId()) {
            case R.id.button_open:
                if (selectedDir != null && !selectedDir.isEmpty()) {
                    selectedPath = buildNavigationPath();
                    openDirectory(selectedPath);
                }
                break;
            case R.id.button_select:
                if (selectedDir.equals(".."))
                    Log.i(TAG, " Navigation Path " + buildNavigationPath());
                dismiss();
                break;
            case R.id.imageView_back:
                if (!selectedPath.equals(rootPath)) {
                    directoriesAdapter.setSelectedDirectory("..");
                    selectedPath = buildNavigationPath();
                    openDirectory(selectedPath);
                    textPath.setText(selectedPath);
                }
                break;
        }
    }

    private void openDirectory(String path) {
        if (path != null && !path.isEmpty()) {
            directoriesAdapter.setDirectories(getDirectories(path));
            directoriesAdapter.setSelectedDirectory(null);
        }
    }

    private String buildNavigationPath() {
        return directoriesAdapter.getSelectedDirectory().equals("..")
                ?
                selectedPath.substring(0, selectedPath.lastIndexOf("/"))
                :
                selectedPath + "/" + directoriesAdapter.getSelectedDirectory();
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
        textPath.setText(buildNavigationPath());
    }

    @Override
    public void onDirectoryCreate(String newDirectoryName) {

    }
}
