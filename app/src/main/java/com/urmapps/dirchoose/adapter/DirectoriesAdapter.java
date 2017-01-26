package com.urmapps.dirchoose.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.urmapps.dirchoose.R;

import java.util.ArrayList;
import java.util.List;

public class DirectoriesAdapter extends RecyclerView.Adapter<DirectoriesAdapter.DirectoryViewHolder> {

    private static final String TAG = "DirectoriesAdapter";

    private DirectoriesActionListener actionListener;

    private List<String> directories = new ArrayList<>();
    private View lastSelected;
    private String selectedDirectory;

    public DirectoriesAdapter(List<String> directories, DirectoriesActionListener listener) {
        this.actionListener = listener;
        setDirectories(directories);
    }

    public void setDirectories(List<String> directories) {
        if (directories != null) {
            this.directories.clear();
            this.directories.addAll(directories);
            notifyDataSetChanged();
        }
    }

    @Override
    public DirectoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_directory, parent, false);
        return new DirectoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DirectoryViewHolder holder, int position) {
        holder.textDirName.setText(directories.get(position));
    }

    @Override
    public int getItemCount() {
        return directories.size();
    }

    class DirectoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout layoutMain;
        ImageView imageViewDirectory;
        TextView textDirName;

        public DirectoryViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            layoutMain = (LinearLayout) view.findViewById(R.id.layout_main);
            imageViewDirectory = (ImageView) view.findViewById(R.id.imageView_direcrory);
            textDirName = (TextView) view.findViewById(R.id.text_directory);
        }

        @Override
        public void onClick(View v) {
            changeColorSelected(v);
            selectedDirectory = textDirName.getText().toString();
            if (actionListener != null)
                actionListener.onDirectorySelected(selectedDirectory);
        }

        private void changeColorSelected(View view) {
            if (lastSelected == null) {
                layoutMain.setBackgroundColor(view.getContext().getResources().getColor(R.color.dialog_selected_directory));
                lastSelected = layoutMain;
            } else {
                lastSelected.setBackgroundColor(view.getContext().getResources().getColor(R.color.dialog_selected_directory_def));
                lastSelected = null;
                changeColorSelected(view);
            }
        }
    }

    public String getSelectedDirectory() {
        return selectedDirectory;
    }

    public void setSelectedDirectory(String selectedDirectory) {
        this.selectedDirectory = selectedDirectory;
    }
}
