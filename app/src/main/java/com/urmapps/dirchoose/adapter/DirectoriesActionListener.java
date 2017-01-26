package com.urmapps.dirchoose.adapter;

/**
 * Created by urma9_000 on 05.08.2016.
 */
public interface DirectoriesActionListener {
    void onDirectoryOpen(String directory);
    void onDirectorySelected(String directory);
    void onDirectoryCreate(String newDirectoryName);
}
