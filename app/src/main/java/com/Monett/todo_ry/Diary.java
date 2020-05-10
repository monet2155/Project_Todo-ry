package com.Monett.todo_ry;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Diary {

    String title;
    String content;

    DiaryView diaryView;

    public Diary(Context context, String title){
        this.title = title;

        diaryView = new DiaryView(context, this);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public View getView(){
        return diaryView;
    }
}
class DiaryView extends LinearLayout {

    Context context;

    Diary diary;

    TextView titleView;

    public DiaryView(Context context, Diary diary){
        super(context);
        this.context = context;
        this.diary = diary;

        init(context);
    }

    public DiaryView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
    }

    public DiaryView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void init(Context context){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_diary, this, true);

        titleView = view.findViewById(R.id.diary_title);
        titleView.setText(diary.title);
    }
}


