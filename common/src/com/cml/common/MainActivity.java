package com.cml.common;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class MainActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().addFooterView(
				LayoutInflater.from(getApplication()).inflate(
						R.layout.activity_main, null));
		addPreferencesFromResource(R.xml.setting);

	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		Log.i("MySearchActivity", "onQueryTextSubmit：");
		// search在3之后才添加的，所以要先判断
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Log.i("MySearchActivity",
					"onQueryTextSubmit：sdk version gt required!");
			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			SearchView searchView = (SearchView) menu.findItem(R.id.search)
					.getActionView();
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
			searchView.setIconifiedByDefault(false);
			searchView.setSubmitButtonEnabled(true);
			searchView.setOnQueryTextListener(new OnQueryTextListener() {

				@Override
				public boolean onQueryTextSubmit(String query) {
					Log.i("MySearchActivity", "onQueryTextSubmit：" + query);
					return true;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					Log.i("MySearchActivity", "onQueryTextChange：" + newText);
					return true;
				}
			});
		} else {
			// 使用自定义界面进行搜索
		}

		return true;
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		Toast.makeText(MainActivity.this, "onPreferenceTreeClick点击了", 1).show();
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {

	}
}
