package com.Monett.todo_ry;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class ViewFragment extends Fragment {

    ViewGroup rootView;

    TextView dateText;
    EditText diaryText;
    Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_view, container, false);
        dateText = rootView.findViewById(R.id.date_text);
        diaryText = rootView.findViewById(R.id.diary_content);
        submitButton = rootView.findViewById(R.id.diary_submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData.getInstance().diaries.get(UserData.getInstance().getEditDiaryPosition()).
                        setContent(diaryText.getText().toString());

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onBackPressed();

                Toast.makeText(mainActivity, dateText.getText().toString() + " 일기가 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

}