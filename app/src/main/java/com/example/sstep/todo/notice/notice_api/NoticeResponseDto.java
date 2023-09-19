package com.example.sstep.todo.notice.notice_api;

import com.example.sstep.document.work_doc_api.PhotoResponseDto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeResponseDto {
    private long id; //공지 고유번호
    private String writerName; //작성자 이름
    private String title; //공지글 제목
    private String writeDate; //공지글 작성 일자
    private String contents; //공지글 내용
    private int hits; //공지 조회수
    private Set<PhotoResponseDto> photo; //사진 정보


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
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

    public Set<PhotoResponseDto> getPhotoResponseDtos() {
        return photo;
    }

    public void setPhotoResponseDtos(Set<PhotoResponseDto> photo) {
        this.photo = photo;
    }
}