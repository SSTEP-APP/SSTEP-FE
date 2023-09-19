package com.example.sstep.todo.notice.notice_api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import okhttp3.MultipartBody;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NoticeRequestDto {
    private long id; //공지 고유번호
    private String title; //공지글 제목
    private String writeDate; //공지글 작성 일자
    private String contents; //공지글 내용
    private int hits; //공지 조회수
    private MultipartBody.Part[] multipartFile; //사진 정보

    public NoticeRequestDto(String title, String writeDate, String contents, int hits, MultipartBody.Part[] multipartFile) {
        this.title = title;
        this.writeDate = writeDate;
        this.contents = contents;
        this.hits = hits;
        this.multipartFile = multipartFile;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public MultipartBody.Part[] getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartBody.Part[] multipartFile) {
        this.multipartFile = multipartFile;
    }
}
