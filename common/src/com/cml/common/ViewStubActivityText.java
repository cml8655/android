package com.cml.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewStub;

public class ViewStubActivityText extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_stub_main);

		int radom = (int) (Math.random() * 10);
		ViewStub stub = null;

		if (radom < 5) {
			stub = (ViewStub) findViewById(R.id.v1);
		} else if (radom < 6) {
			stub = (ViewStub) findViewById(R.id.v2);
		} else {
			stub = (ViewStub) findViewById(R.id.v1);
			((ViewStub) findViewById(R.id.v2)).inflate();
		}

		stub.inflate();
	}
}
