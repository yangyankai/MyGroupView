/*
 * Copyright (c) 2015-2015 by Shanghai shootbox Information Technology Co., Ltd.
 * Create: 2015/9/21 4:55:49
 * Project: T
 * File: MyScrollView.java
 * Class: MyScrollView
 * Module: app
 * Author: yangyankai
 * Version: 1.0
 */

package com.ykai.t;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by yangyankai on 2015/9/21.
 */
public class MyScrollView extends ViewGroup {

	private int      mScreenHeight;
	private Scroller mScroller;
	private int      mLastY;
	private int      mStart;
	private int      mEnd;


	public MyScrollView(Context context)
	{
		super(context);
		initView(context);
	}

	private void initView(Context context)
	{
		WindowManager  windowManager  = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(displayMetrics);
		mScreenHeight = displayMetrics.heightPixels;
		mScroller = new Scroller(context);

	}


	public MyScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView(context);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		int                childCounnt        = getChildCount();
		MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();
		marginLayoutParams.height = mScreenHeight * childCounnt;
		setLayoutParams(marginLayoutParams);
		for (int i = 0; i < childCounnt; i++)
		{
			View child = getChildAt(i);
			if (child.getVisibility() != View.GONE)
			{
				child.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
			}

		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int count = getChildCount();
		for (int i = 0; i < count; i++)
		{
			View childView = getChildAt(i);
			measureChild(childView, widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int y = (int) event.getY();
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				mLastY = y;
				Log.e("down","actionDown : "+y);
				break;
			case MotionEvent.ACTION_MOVE:
				if (!mScroller.isFinished())
				{
					mScroller.abortAnimation();
				}
				int dy = mLastY - y;
				scrollBy(0, dy);
				mLastY = y;
				Log.e("move","         getScrollY :"+getScrollY()+"    y:"+y+"        mLastY:" +
						""+mLastY+"      getHeight:"+getHeight()+"   mScreenHeight:"+mScreenHeight);
				break;

		}

		postInvalidate();
		return true;

	}

	@Override
	public void computeScroll()
	{
		super.computeScroll();
		if (mScroller.computeScrollOffset())
		{
			scrollTo(0, mScroller.getCurrY());
			postInvalidate();
		}
	}
}
