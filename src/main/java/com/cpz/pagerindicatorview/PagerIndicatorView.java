package com.cpz.pagerindicatorview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cpzzzz on 5/29/2016.
 */
public class PagerIndicatorView extends View implements ViewPager.OnPageChangeListener
{
    //指示器数量
    private int mCount = 3;
    //未选中颜色
    private int mUnSetColor = Color.GRAY;
    //选中颜色
    private int mSetColor = Color.RED;
    //指示器半径
    private int mIndicatorRadius = 10;
    //指示器宽度
    private int mIndicatorWith = 2 * mIndicatorRadius;
    //指示器间隔
    private int mIndicatorMarg = 10;
    //指示器选中位置
    private int mSetPosition = 0;
    private float mSetOffset = 0;

    private ViewPager.OnPageChangeListener mOnPagerChangeListener;


    public PagerIndicatorView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PagerIndicatorView(Context context)
    {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        //这里方法套路都是一样，不管三七 二十一，上来就先把mode 和 size 获取出来。

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //View 真正需要显示的大小
        int width = 0, height = 0;

        //这里是去测量字体大小
        //字体宽度加图片宽度取最大宽度，这里因为字体和图片是上下排列
        int contentWidth = mCount * mIndicatorWith + mIndicatorMarg * (mCount - 1);
        // 我们渴望得到的宽度
        int desiredWidth = getPaddingLeft() + getPaddingRight() + contentWidth;
        //重点来了，判断模式，这个模式哪里来的呢，就是在编写xml的时候，设置的layout_width
        switch (widthMode)
        {
            //如果是AT_MOST，不能超过父View的宽度
            case MeasureSpec.AT_MOST:
                width = Math.min(widthSize, desiredWidth);
                break;
            //如果是精确的，好说，是多少，就给多少；
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            //这种情况，纯属在这里打酱油的，可以不考虑
            case MeasureSpec.UNSPECIFIED://我是路过的
                width = desiredWidth;
                break;
        }

        int contentHeight = mIndicatorWith;
        int desiredHeight = getPaddingTop() + getPaddingBottom() + contentHeight;
        switch (heightMode)
        {
            case MeasureSpec.AT_MOST:
                height = Math.min(heightSize, desiredHeight);
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                height = contentHeight;
                break;
        }
        //最后不要忘记了，调用父类的测量方法
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mUnSetColor);

//        int left = getMeasuredWidth() / 2 - mDefWith * mCount / 2 + getPaddingLeft();

        for (int i = 0; i < mCount; i++)
        {
            paint.setAntiAlias(true);
            paint.setColor(mUnSetColor);
            canvas.drawCircle(i * mIndicatorWith + mIndicatorRadius + i * mIndicatorMarg,
                    mIndicatorRadius, mIndicatorRadius, paint);
            paint.setColor(mSetColor);
            float left = mIndicatorRadius + (mSetOffset + mSetPosition) * (mIndicatorWith + mIndicatorMarg);
            canvas.drawCircle(left, mIndicatorRadius, mIndicatorRadius, paint);
        }
    }


    /**
     * 绑定ViewPager
     *
     * @param viewPager 与指示器绑定的ViewPager
     */
    public void setViewPager(ViewPager viewPager)
    {
        if (viewPager == null)
            return;
        PagerAdapter adapter = viewPager.getAdapter();
        mCount = adapter.getCount();
        viewPager.setOnPageChangeListener(this);
        invalidate();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
//        System.out.println("debug" + "position=" + position + "+,positionOffset=" + positionOffset + ",positionOffsetPixels=" + positionOffsetPixels);
        mSetOffset = positionOffset;
        mSetPosition = position;
        invalidate();
        if (mOnPagerChangeListener != null)
            mOnPagerChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position)
    {
        mSetPosition = position;
        invalidate();
        if (mOnPagerChangeListener != null)
            mOnPagerChangeListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
        if (mOnPagerChangeListener != null)
            mOnPagerChangeListener.onPageScrollStateChanged(state);
    }

    /**
     * 给ViewPager设置onPageChangeListener
     *
     * @param onPageChangeListener
     */
    public void setOnPagerChangeListener(ViewPager.OnPageChangeListener onPageChangeListener)
    {
        this.mOnPagerChangeListener = onPageChangeListener;
    }

    /**
     * 设置未选中指示器颜色
     *
     * @param color
     */
    public void setUnSetColor(int color)
    {
        this.mUnSetColor = color;
        invalidate();
    }

    /**
     * 设置选中指示器颜色
     *
     * @param color
     */
    public void setSetColor(int color)
    {
        this.mSetColor = color;
        invalidate();
    }

    /**
     * 设置指示器间隔
     *
     * @param indicatorMarg
     */
    public void setIndicatorMarg(int indicatorMarg)
    {
        this.mIndicatorMarg = indicatorMarg;
        invalidate();
    }

    /**
     * 设置指示器半径
     *
     * @param indicatorradius
     */
    public void setIndicatorRadius(int indicatorradius)
    {
        this.mIndicatorRadius = indicatorradius;
        mIndicatorWith=2*mIndicatorRadius;
        invalidate();
    }
}
