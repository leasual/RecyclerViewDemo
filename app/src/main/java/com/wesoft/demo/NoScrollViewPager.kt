package com.wesoft.demo

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent



/**
 * Created by james.li on 2017/8/16.
 * 不可以滑动，但是可以setCurrentItem的ViewPager。
 */
class NoScrollViewPager : ViewPager {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return false
    }

    //去除页面切换时的滑动翻页效果
    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {

        super.setCurrentItem(item, false)
    }
}