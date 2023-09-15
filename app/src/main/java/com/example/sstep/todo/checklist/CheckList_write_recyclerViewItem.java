package com.example.sstep.todo.checklist;

public class CheckList_write_recyclerViewItem{
    private String CheckList_write_Img;
    private String CheckList_write_cancelImg;
    private String checklist_write_staffName;

    // 클릭 이벤트 처리를 위한 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener onItemClickListener;


    public String getChecklist_write_staffName() {
        return checklist_write_staffName;
    }

    public void setChecklist_write_staffName(String checklist_write_staffName) {
        this.checklist_write_staffName = checklist_write_staffName;
    }

    // 클릭 이벤트 핸들러 설정 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // 클릭 이벤트를 호출하는 메서드
    public void performItemClick(int position) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(position);
        }
    }
}