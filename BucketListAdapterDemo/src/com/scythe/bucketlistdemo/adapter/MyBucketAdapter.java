package com.scythe.bucketlistdemo.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.scythe.bucket.BucketListAdapter;
import com.scythe.bucketlistdemo.R;

public class MyBucketAdapter extends BucketListAdapter<Integer> {

	public MyBucketAdapter(Activity ctx, ArrayList<Integer> elements,
			Integer bucketSize) {
		super(ctx, elements, bucketSize);
	}

	@Override
	protected View getBucketElement(final int position, Integer currentElement) {
		View bucketElement = View.inflate(ctx, R.layout.bucket_element, null);
		
		TextView text = (TextView)bucketElement.findViewById(R.id.text);
		text.setText(String.valueOf(currentElement));
		
		bucketElement.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ctx, "Clicked on: " + position, Toast.LENGTH_SHORT).show();
			}
		});
		
		return bucketElement;
	}

}
