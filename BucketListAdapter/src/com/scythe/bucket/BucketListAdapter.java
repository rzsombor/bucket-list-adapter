package com.scythe.bucket;

import java.util.List;

import com.capigami.outofmilk.OutOfMilk;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * A bucket adapter presenting rows of buckets.
 * 
 * @author Scythe
 * 
 * @param <T>
 */
public abstract class BucketListAdapter<T> extends ArrayAdapter<T> {

    private static final String TAG = "BucketListAdapter";
    private final boolean DEBUG = false;
    protected Activity ctx;
    protected Integer bucketSize;

    /**
     * Basic constructor, takes an Activity context and the list of elements.
     * Assumes a 1 column view by default.
     * 
     * @param ctx
     *            The Activity context.
     * @param elements
     *            The list of elements to present.
     */
    public BucketListAdapter(Activity ctx, List<T> elements) {
        this(ctx, elements, 1);
    }

    /**
     * Extended constructor, takes an Activity context, the list of elements and
     * the exact number of columns.
     * 
     * @param ctx
     *            The Activity context.
     * @param elements
     *            The list of elements to present.
     * @param bucketSize
     *            The exact number of columns.
     * 
     */
    public BucketListAdapter(Activity ctx, List<T> elements, Integer bucketSize) {
        super(ctx, 0, elements);

        //this.elements = elements;
        this.ctx = ctx;
        this.bucketSize = bucketSize;
    }

    /**
     * Calculates the required number of columns based on the actual screen
     * width (in DIP) and the given minimum element width (in DIP).
     * 
     * @param minBucketElementWidthDip
     *            The minimum width in DIP of an element.
     */
    public void enableAutoMeasure(float minBucketElementWidthDip) {
        float screenWidth = getScreenWidthInDip();

        if (minBucketElementWidthDip >= screenWidth) {
            bucketSize = 1;
        } else {
            bucketSize = (int) (screenWidth / minBucketElementWidthDip);
        }
    }
    
    public int getBucketSize() {
        return bucketSize;
    }

    @Override
    public int getCount() {
        return (super.getCount() + bucketSize - 1) / bucketSize;
        //return (elements.size() + bucketSize - 1) / bucketSize;
    }

    @Override
    public T getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int bucketPosition, View convertView, ViewGroup parent)
    {
        LinearLayout bucket;
        if (convertView != null) {
            bucket = (LinearLayout)convertView;
            
            if (DEBUG) {
                Log.i(TAG, "Reusing bucket view");
            }
        } else {
            bucket = new LinearLayout(ctx);
            bucket.setLayoutParams(new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
            bucket.setOrientation(LinearLayout.HORIZONTAL);
            
            if (DEBUG) {
                Log.i(TAG, "Instantiating new bucket view");
            }
        }
        
        int j = 0;
        for (int i = (bucketPosition * bucketSize); i < ((bucketPosition * bucketSize) + bucketSize); i++) {
            FrameLayout bucketElementFrame;
            if (j < bucket.getChildCount()) {
                bucketElementFrame = (FrameLayout)bucket.getChildAt(j);
                
                if (DEBUG) {
                    Log.i(TAG, "Reusing bucketElementFrame view");
                }
                
                if (i < super.getCount()) {
                    View currentConvertView = bucketElementFrame.getChildAt(0);
                    View current = getBucketElement(i, getItem(i), currentConvertView);
                    
                    if (DEBUG) {
                        Log.i(TAG, "Reusing element view");
                    }
                    if (currentConvertView != null) {
                        currentConvertView.setVisibility(View.VISIBLE);
                    }
                } else {
                    View currentConvertView = bucketElementFrame.getChildAt(0);
                    if (currentConvertView != null) {
                        currentConvertView.setVisibility(View.GONE);
                    }
                }
            } else {
                bucketElementFrame = new FrameLayout(ctx);
                bucketElementFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.FILL_PARENT, 1));
                
                if (i < super.getCount()) {
                    View current = getBucketElement(i, getItem(i), null);
                    bucketElementFrame.addView(current);
                }
                
                bucket.addView(bucketElementFrame);
            }
            j++;
        }

        return bucket;
    }

    /**
     * Extending classes should return a bucket-element with this method. Each
     * row in the list contains bucketSize total elements.
     * 
     * @param position
     *            The absolute, global position of the current item.
     * @param currentElement
     *            The current element for which the View should be constructed
     * @param convertView
     *            The old view to reuse, if possible.
     * @return The View that should be presented in the corresponding bucket.
     */
    protected abstract View getBucketElement(final int position,
            T currentElement, View convertView);

    protected float getScreenWidthInDip() {
        WindowManager wm = ctx.getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth_in_pixel = dm.widthPixels;
        float screenWidth_in_dip = screenWidth_in_pixel / dm.density;

        return screenWidth_in_dip;
    }
}
