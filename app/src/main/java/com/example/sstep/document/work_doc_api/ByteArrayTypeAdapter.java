package com.example.sstep.document.work_doc_api;

import android.util.Base64;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ByteArrayTypeAdapter extends TypeAdapter<byte[]> {
    @Override
    public void write(JsonWriter out, byte[] value) throws IOException {
        // 필요하다면 직렬화 로직을 작성합니다.
        // (클라이언트에서 서버로 데이터를 보낼 때 필요한 경우)
        // 이 경우 여기에 작성해야 합니다.
    }

    @Override
    public byte[] read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        String base64Data = in.nextString();
        return Base64.decode(base64Data, Base64.DEFAULT);
    }
}