package com.example.sstep;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;

public class CalendarDialog extends DatePickerDialog {
    private Context mContext;

    public CalendarDialog(Context context, OnDateSetListener listener) {
        super(context, R.style.MyDatePickerDialogTheme, listener, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        mContext = context;

    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {
        super.onDateChanged(view, year, month, dayOfMonth);

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, dayOfMonth);

//        Calendar currentDate = Calendar.getInstance();
//        if (selectedDate.before(currentDate)) {
//            // 선택한 날짜가 오늘 날짜보다 이전인 경우
//            Toast.makeText(mContext, "오늘 날짜 이후로 선택하세요.", Toast.LENGTH_SHORT).show();
//            // 오늘 날짜로 초기화
//            view.updateDate(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
//        }
    }
}