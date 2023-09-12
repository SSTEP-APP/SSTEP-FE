package com.example.sstep.todo.notice;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sstep.photo_api.PhotoApiService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoticePhotoTask extends AsyncTask<Void, Void, List<Long>> {
    private static final String TAG = "NoticePhotoTask";
    private List<Bitmap> bitmapList;
    private OnPhotoUploadListener listener;

    public NoticePhotoTask(List<Bitmap> bitmapList, OnPhotoUploadListener listener) {
        this.bitmapList = bitmapList;
        this.listener = listener;
    }

    @Override
    protected List<Long> doInBackground(Void... params) {
        List<Long> uploadedPhotoIds = new ArrayList<>();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PhotoApiService apiService = retrofit.create(PhotoApiService.class);

        for (int i = 0; i < bitmapList.size(); i++) {
            Bitmap photoBitmap = bitmapList.get(i);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", i + "_hdoc.jpeg", requestFile);

            Call<Long> call = apiService.savePhoto(body);

            try {
                Response<Long> response = call.execute();

                if (response.isSuccessful()) {
                    Long photoId = response.body();
                    uploadedPhotoIds.add(photoId);
                } else {
                    Log.e(TAG, "사진 업로드 실패: " + i);
                    uploadedPhotoIds.add(-1L);
                }
            } catch (IOException e) {
                e.printStackTrace();
                uploadedPhotoIds.add(-1L);
            }
        }

        return uploadedPhotoIds;
    }

    @Override
    protected void onPostExecute(List<Long> results) {
        if (listener != null) {
            listener.onUploadComplete(results);
        }
    }

    public interface OnPhotoUploadListener {
        void onUploadComplete(List<Long> photoIds);
    }
}
