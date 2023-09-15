package com.example.sstep.document.work_doc_api;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoResponseDto implements Parcelable{
    private long id;
    private String fileName;
    private String contentType;
    private byte[] data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(fileName);
        dest.writeString(contentType);
        dest.writeByteArray(data);

    }

    public static final Parcelable.Creator<PhotoResponseDto> CREATOR = new Parcelable.Creator<PhotoResponseDto>() {
        public PhotoResponseDto createFromParcel(Parcel in) {
            return new PhotoResponseDto(in);
        }

        public PhotoResponseDto[] newArray(int size) {
            return new PhotoResponseDto[size];
        }
    };

    private PhotoResponseDto(Parcel in) {
        id = in.readLong();
        fileName = in.readString();
        contentType = in.readString();
        data = in.createByteArray();
    }


}