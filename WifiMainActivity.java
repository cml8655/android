package com.cml.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cml.wifi.receiver.WiFiDirectBroadcastReceiver;

public class MainActivity extends ActionBarActivity {

	private BroadcastReceiver receiver;
	private IntentFilter mIntentFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

		mIntentFilter = new IntentFilter();

		mIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		mIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		mIntentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		// WifiP2pManager manager = (WifiP2pManager)
		// getSystemService(Context.WIFI_P2P_SERVICE);
		// Channel channel = manager.initialize(this, getMainLooper(), null);
		//
		// manager.discoverPeers(channel, new ActionListener() {
		//
		// @Override
		// public void onSuccess() {
		// Log.e("discoverPeers", "onSuccess");
		// }
		//
		// @Override
		// public void onFailure(int reason) {
		// Log.e("discoverPeers", "onFailure" + reason);
		// }
		// });
		//
		// mIntentFilter = new IntentFilter();
		// mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		// mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		// mIntentFilter
		// .addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		// mIntentFilter
		// .addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
		//
		 receiver = new WiFiDirectBroadcastReceiver(null, null, this);
		
		 registerReceiver(receiver, mIntentFilter);
	}

	/* register the broadcast receiver with the intent values to be matched */
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, mIntentFilter);
	}

	/* unregister the broadcast receiver */
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
