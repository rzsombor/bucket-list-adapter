bucket-list-adapter
===================

Android BucketListAdapter

This is an implementation of a bucket list adapter, similar to what you see in the 
Google Play application. It features a grid-like layout of list elements, with 
the advantage that you can still use list headers and footers - something that's not 
possible with the standard GridView.

To use the adapter, you need to override the "protected View getBucketElement(int position, T currentElement)"
method. In this method, you should return the View of a bucket-element of the specified position. For convenience,
you also get the current element for that position as a second parameter.

Two constructors exist for the adapter. The first, "BucketListAdapter(Activity ctx, List<T> elements)" takes the Activity
as and the elements be presented, and assumes that the elements should be presented in 1 column. 
The other, "BucketListAdapter(Activity ctx, List<T> elements, Integer bucketSize)" takes the exact column number 
as a third parameter.

The "void enableAutoMeasure(float minBucketElementWidthDip)" method may be used to automatically calculate the
required column number based on the minimum element width given as a parameter.

Here's some example code:

	List<String> myListContent = new ArrayList<String>();
        myListContent.add("First");
        myListContent.add("Second");
        myListContent.add("Thrid");
        
        BucketListAdapter<String> myAdapter = new BucketListAdapter<String>(this, myListContent, 2) {
			@Override
			protected View getBucketElement(final int position, String currentElement) {
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
		};
        
        bucketList.setAdapter(myAdapter);


An demo application is also provided with the project, check it out.