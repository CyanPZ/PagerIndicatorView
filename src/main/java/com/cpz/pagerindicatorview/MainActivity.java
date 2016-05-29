package com.cpz.pagerindicatorview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity
{
    private ViewPager viewPager;
    private int[] mImageIds = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private List<ImageView> ivList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PagerIndicatorView piv = (PagerIndicatorView) findViewById(R.id.piv);

        piv.setIndicatorMarg(30);
        piv.setSetColor(Color.BLUE);
        piv.setUnSetColor(Color.BLACK);
        piv.setIndicatorRadius(20);


//        piv.setCount(5);
        viewPager = (ViewPager) findViewById(R.id.vp);
        MyPagerAdapter mAdapter = new MyPagerAdapter();
        ivList = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++)
        {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mImageIds[i]);
            ivList.add(imageView);
        }
        viewPager.setAdapter(mAdapter);
        piv.setViewPager(viewPager);
        piv.setOnPagerChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                System.out.println("debug from main");
            }

            @Override
            public void onPageSelected(int position)
            {
                System.out.println("debug from main");

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                System.out.println("debug from main");

            }
        });

    }

    class MyPagerAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            container.addView(ivList.get(position));
            // return super.instantiateItem(container, position);
            return ivList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    }
}
