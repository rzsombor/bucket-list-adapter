package com.scythe.bucketlistdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.scythe.bucketlistdemo.adapter.MyBucketAdapter;

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
        
        MyBucketAdapter adap = new MyBucketAdapter(this, list, 100);
        adap.enableAutoMeasure(100);
        
        View header = View.inflate(this, R.layout.header, null);
        View footer = View.inflate(this, R.layout.footer, null);
        
        bucketList.addHeaderView(header);
        bucketList.addFooterView(footer);
        
        bucketList.setAdapter(adap);
    }
	
}