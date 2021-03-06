package com.vsnapnewschool.voicesnapmessenger.UtilCommon;


import android.content.Context;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;


public  class ScrollTextView extends TextView {
    // scrolling feature
    private Scroller mSlr;

    // milliseconds for a round of scrolling
    public int mRndDuration = 2000;

    // the X offset when paused
    private int mXPaused = 0;

    // whether it's being paused
    private boolean mPaused = true;
    /*
     * constructor
     */
    public ScrollTextView(Context context) {
        this(context, null);
        // customize the TextView
        //setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);
    }
    /*
     * constructor
     */
    public ScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
        // customize the TextView
       // setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);
    }
    /*
     * constructor
     */
    public ScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // customize the TextView
       // setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);
    }
    /**
     * begin to scroll the text from the original position
     */
    public void startScroll() {
        // begin from the very right side
        mXPaused = -1 * getHeight();
        // assume it's paused
        mPaused = true;
        resumeScroll();
    }
    /**
     * resume the scroll from the pausing point
     */
    public void resumeScroll() {
        if (!mPaused)
            return;
        // Do not know why it would not scroll sometimes
        // if setHorizontallyScrolling is called in constructor.
        setVerticalScrollBarEnabled(true);

        // use LinearInterpolator for steady scrolling
        mSlr = new Scroller(this.getContext(), new LinearInterpolator());
        setScroller(mSlr);

        int scrollingLen = calculateScrollingLen();
        int distance = scrollingLen - (getHeight() + mXPaused);
        int duration = (new Double(mRndDuration * distance * 1.00000
                / scrollingLen)).intValue();

       mRndDuration=10000;

        setVisibility(VISIBLE);
        mSlr.startScroll(0, mXPaused, 0, distance, duration);
        mPaused = false;
    }
    /**
     * calculate the scrolling length of the text in pixel
     *
     * @return the scrolling length in pixels
     */
    private int calculateScrollingLen() {
        TextPaint tp = getPaint();
        Rect rect = new Rect();
        String strTxt = getText().toString();
        tp.getTextBounds(strTxt, 0, strTxt.length(), rect);
        int scrollingLen = rect.height() + getHeight();
        rect = null;
        return scrollingLen;
    }

    /**
     * pause scrolling the text
     */
    public void pauseScroll() {
        if (null == mSlr)
            return;

        if (mPaused)
            return;

        mPaused = true;
        // abortAnimation sets the current X to be the final X,
        // and sets isFinished to be true
        // so current position shall be saved
        mXPaused = mSlr.getCurrX();

        mSlr.abortAnimation();
    }
    @Override
    /*
     * override the computeScroll to restart scrolling when finished so as that
     * the text is scrolled forever
     */
    public void computeScroll() {
        super.computeScroll();

        if (null == mSlr)
            return;

        if (mSlr.isFinished() && (!mPaused)) {
            this.startScroll();
        }
    }

    public int getRndDuration() {
        return mRndDuration;
    }

    public void setRndDuration(int duration) {
        this.mRndDuration = duration;
    }

    public boolean isPaused() {
        return mPaused;
    }
}