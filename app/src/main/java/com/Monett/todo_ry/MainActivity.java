package com.Monett.todo_ry;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    private static final int NUM_PAGES = 2;

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MainActivity.PagerAdapter(getSupportFragmentManager());

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mPager.setAdapter(pagerAdapter);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    public void onFragmentChanged(int index){
        pagerAdapter.viewFragment.dateText.setText(UserData.getInstance().editDiary.title);
        pagerAdapter.viewFragment.diaryText.setText(UserData.getInstance().editDiary.content);
        mPager.setCurrentItem(index);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0){
            super.onBackPressed();
        } else{
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        ListFragment listFragment;
        ViewFragment viewFragment;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            listFragment = new ListFragment();
            viewFragment = new ViewFragment();
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return listFragment;
            } else if (position == 1){
                return viewFragment;
            }
            return new ListFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
