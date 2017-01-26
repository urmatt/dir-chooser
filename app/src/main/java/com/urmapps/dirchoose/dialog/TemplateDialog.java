package com.urmapps.dirchoose.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

/**
 * Created by urma9_000 on 04.08.2016.
 */
public abstract class TemplateDialog extends Dialog implements View.OnClickListener{

    private Context context;

    public TemplateDialog(Context context){
        super(context);
        this.context = context;
    }

    public void message(String message){
        message(message, Toast.LENGTH_SHORT);
    }

    public void message(String message, int length){
        Toast.makeText(context, message, length).show();
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
