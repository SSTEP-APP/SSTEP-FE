package com.example.sstep.document.contract;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;

public class SaveWorkDoc extends AsyncTask<Bitmap, Void, Boolean> {
    private File file;
    private Callback callback;

    public SaveWorkDoc(File file, Callback callback) {
        this.file = file;
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Bitmap... bitmaps) {
        if (bitmaps.length == 0 || bitmaps[0] == null) {
            return false;
        }

        Bitmap bitmap = bitmaps[0];
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (callback != null) {
            callback.onSaveComplete(success);
        }
    }

    public interface Callback {
        void onSaveComplete(boolean success);
    }
}