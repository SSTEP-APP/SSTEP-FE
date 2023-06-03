package com.example.sstep.start;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.sstep.R;
import com.example.sstep.joinlogin.Login;
import com.example.sstep.mypage.MyPage_Profile;

public class Start4 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.start4_fragment, container, false);
        Button strat4_startBtn=v.findViewById(R.id.strat4_startBtn);
        strat4_startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return v;
    }
}