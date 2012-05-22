package com.scythe.bucket;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
public abstract class BucketListAdapter<T> extends BaseAdapter {

	protected List<T> elements;
	protected Activity ctx;
	protected Integer bucketSize;

	public BucketListAdapter(Activity ctx, ArrayList<T> elements) {
		this(ctx, elements, 1);
	}

	public BucketListAdapter(Activity ctx, ArrayList<T> elements,
			Integer bucketSize) {
		this.elements = elements;
		this.ctx = ctx;
		this.bucketSize = bucketSize;
	}
	
	public void enableAutoMeasure(float minBucketElementWidthDip){
		float screenWidth = getScreenWidthInDip();
		
		if(minBucketElementWidthDip >= screenWidth){
			bucketSize = 1;
		} else {
			bucketSize = (int)(screenWidth / minBucketElementWidthDip);
		}		
	}

	@Override
	public int getCount() {
		return (elements.size() + bucketSize - 1) / bucketSize;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int bucketPosition, View convertView, ViewGroup parent) {
		
		ViewGroup bucket = (ViewGroup) View.inflate(ctx, R.layout.bucket, null);

		for (int i = (bucketPosition * bucketSize); i < ((bucketPosition * bucketSize) + bucketSize); i++) {
			FrameLayout bucketElementFrame = new FrameLayout(ctx);
			bucketElementFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
					LinearLayout.LayoutParams.WRAP_CONTENT, 1));

			if (i < elements.size()) {
				View current = getBucketElement(i, elements.get(i));

				bucketElementFrame.addView(current);
			}

			bucket.addView(bucketElementFrame);
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
	 * @return The View that should be presented in the corresponding bucket.
	 */
	protected abstract View getBucketElement(int position, T currentElement);

	protected float getScreenWidthInDip() {
		WindowManager wm = ctx.getWindowManager();
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int screenWidth_in_pixel = dm.widthPixels;
		float screenWidth_in_dip = screenWidth_in_pixel / dm.density;

		return screenWidth_in_dip;
	}
}
