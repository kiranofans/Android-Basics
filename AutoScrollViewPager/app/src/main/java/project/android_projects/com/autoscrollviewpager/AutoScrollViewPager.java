package project.android_projects.com.autoscrollviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class AutoScrollViewPager extends ViewPager {

    public static final int DEFAULT_INTERVAL = 1500;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    private static final String TAG = "AutoScrollViewPager";
    public static final int SLIDE_BORDER_MODE_NONE = 0;
    public static final int SLIDE_BORDER_MODE_CYCLE = 1;
    public static final int SLIDE_BORDER_MODE_TO_PARENT = 2;

    private long interval = DEFAULT_INTERVAL;
    private int direction = RIGHT;
    private boolean isCycle = true;
    private boolean stopScrollWhenTouch = true;
    private int slideBorderMode = SLIDE_BORDER_MODE_NONE;
    private boolean isBorderAnimation = true;
    private double autoScrollFactor = 1.0;
    private double swipeScrollFactor = 1.0;

    private Handler handler;
    @Nullable
    private DurationScroller scroller;

    public static final int SCROLL_WHAT = 0;

    public AutoScrollViewPager(Context paramContext) {
        super(paramContext);
        init();
    }

    public AutoScrollViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    private void init() {
        handler = new MyHandler(this);
        setViewPagerScroller();
    }

    public void startAutoScroll() {
        if (scroller != null) {
            sendScrollMessage((long) (interval + scroller.getDuration() / autoScrollFactor * swipeScrollFactor));
        }
    }

    public void stopAutoScroll() {
        handler.removeMessages(SCROLL_WHAT);
    }

    public void setSwipeScrollDurationFactor(double scrollFactor) {
        swipeScrollFactor = scrollFactor;
    }

    //Set the factor by which the duraction of sliding animation will change while auto scrolling
    public void setAutoScrollDurationFactor(double scrollFactor) {
        autoScrollFactor = scrollFactor;
    }

    private void sendScrollMessage(long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    //Set viewPager scroller to change animation duraion when sliding
    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorFiled = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorFiled.setAccessible(true);

            scroller = new DurationScroller(getContext(), (Interpolator) interpolatorFiled.get(null));
            scrollerField.set(this, scroller);

        } catch (IllegalAccessException e) {
            Log.d("IllegalAccessException", e.getMessage());
        } catch (NoSuchFieldException e) {
            Log.d("NoSuchFiledException", e.getMessage());
        }
    }

    public void scrollOnce() {
        //Scroll only once
        PagerAdapter adp = getAdapter();
        int currentitem = getCurrentItem();
        int totalCount = adp != null ? adp.getCount() : -100;
        if (adp == null || totalCount <= 1) {
            return;
        }

        int nextItem = (direction == LEFT) ? --currentitem : ++currentitem;
        if (nextItem < 0) {
            if (isCycle) {
                setCurrentItem(totalCount - 1, isBorderAnimation);
            }
        } else if (nextItem == totalCount) {
            if (isCycle) {
                setCurrentItem(0, isBorderAnimation);
            }
        } else {
            setCurrentItem(nextItem, true);
        }
    }

    /**
     * Get auto scroll tiem in milliseconds, default is {@link #DEFAULT_INTERVAL}
     *
     * @return the interval
     */
    public long getInterval() {
        return interval;
    }

    /**
     * get auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL}.
     *
     * @return the interval.
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    //Get auto scroll direction, default is RIGHT
    public int getDirection() {
        return (direction == LEFT) ? LEFT : RIGHT;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    //determine if automatic cycle when auto scroll reaching
    // the last or first item, default value is true
    public boolean isCycleScroll() {
        return isCycle;
    }

    public void setCycleScroll(boolean isCycle) {
        this.isCycle = isCycle;
    }

    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        this.stopScrollWhenTouch = stopScrollWhenTouch;
    }

    //Get whether stop auto scroll when touching, default value is true
    public boolean isStopScrollWhenTouch() {
        return stopScrollWhenTouch;
    }

    //get how to process when sliding at the last or first item
    public int getSlideBorderMode() {
        return slideBorderMode;
    }

    public void setSlideBorderMode(int slideBorderMode) {
        this.slideBorderMode = slideBorderMode;
    }

    //determine if ainmating when auto scroll at the last or first item, default is true
    public boolean isBorderAnimationEnabled() {
        return isBorderAnimation;
    }

    public void setBorderAnimation(boolean isBorderAnimation) {
        this.isBorderAnimation = isBorderAnimation;
    }

    private static class MyHandler extends android.os.Handler {
        private final WeakReference<AutoScrollViewPager> autoScrollViewPagerRef;

        private MyHandler(AutoScrollViewPager autoScroll) {
            this.autoScrollViewPagerRef =
                    new WeakReference<AutoScrollViewPager>(autoScroll);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == SCROLL_WHAT) {
                AutoScrollViewPager autoViewPager = this.autoScrollViewPagerRef.get();

                if (autoViewPager != null && autoViewPager.scroller != null) {
                    autoViewPager.scroller.setScrollDurationFactor(autoViewPager.autoScrollFactor);
                    autoViewPager.scrollOnce();
                    autoViewPager.scroller.setScrollDurationFactor(autoViewPager.swipeScrollFactor);
                    autoViewPager.sendScrollMessage(autoViewPager
                            .interval + autoViewPager.scroller.getDuration());
                }
            }
        }
    }
}
