package com.example.sstep.user.staff;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateYear_NavigationClickListener implements View.OnClickListener {
    private TextView dateTextView;
    private Button leftButtonId;
    private Button rightButtonId;

    public DateYear_NavigationClickListener(TextView dateTextView, Button leftButtonId, Button rightButtonId) {
        this.dateTextView = dateTextView;
        this.leftButtonId = leftButtonId;
        this.rightButtonId = rightButtonId;
    }

    @Override
    public void onClick(View view) {
        String currentDate = dateTextView.getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년", Locale.getDefault());

        try {
            Date date = sdf.parse(currentDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (view == leftButtonId) {
                // 왼쪽 버튼 클릭 시 이전 년도로 이동
                calendar.add(Calendar.YEAR, -1);
            } else if (view == rightButtonId) {
                // 오른쪽 버튼 클릭 시 다음 년도로 이동
                calendar.add(Calendar.YEAR, 1);
            }

            Date newDate = calendar.getTime();
            String newDateStr = sdf.format(newDate);

            dateTextView.setText(newDateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}