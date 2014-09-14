package com.cml.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MySearchActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		Intent intent = getIntent();
		if (null != intent) {
			Log.i("MySearchActivity", "获取Action：" + intent.getAction());
			Bundle extra = intent.getExtras();
			if (null != extra) {
				Log.i("MySearchActivity", "获取key：" + extra.keySet());
			}
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}
}
