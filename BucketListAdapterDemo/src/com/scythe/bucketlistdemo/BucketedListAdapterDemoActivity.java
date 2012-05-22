package com.scythe.bucketlistdemo;

import java.util.ArrayList;

import com.scythe.bucketlistdemo.adapter.MyBucketAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class BucketedListAdapterDemoActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView bucketList = (ListView)findViewById(R.id.BucketList);
        
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i=1; i < 147; i++){
        	list.add(i);
        }
        
        MyBucketAdapter adap = new MyBucketAdapter(this, list, 3);
        adap.enableAutoMeasure(100);
        
        bucketList.setAdapter(adap);
    }
	
}