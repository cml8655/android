package com.cml.wifi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Parcelable;
import android.util.Log;

import com.cml.wifi.MainActivity;

/**
 * A BroadcastReceiver that notifies of important Wi-Fi p2p events.
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

	private WifiP2pManager mManager;
	private Channel mChannel;
	private MainActivity mActivity;
	private PeerListListener peerListener = new PeerListListener() {

		@Override
		public void onPeersAvailable(WifiP2pDeviceList peers) {
			Log.e("peerListener", "获取到连接点：" + peers.getDeviceList().size());
		}
	};

	public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel,
			MainActivity activity) {
		super();
		this.mManager = manager;
		this.mChannel = channel;
		this.mActivity = activity;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {

			int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);

			if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
				Log.e("=====", "打开wifi");
			} else if (wifiState == WifiManager.WIFI_STATE_DISABLING) {
				Log.e("=====", "正在关闭wifi");
			} else if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
				Log.e("=====", "关闭wifi");
				// 4s后打开wifi
				try {
					Thread.sleep(4000);

					WifiManager manager = (WifiManager) context
							.getSystemService(Context.WIFI_SERVICE);
					manager.setWifiEnabled(true);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
		// 网络状态改变
		if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {

			Parcelable info = intent
					.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			if (null != info) {
				NetworkInfo networkInfo = (NetworkInfo) info;
				State state = networkInfo.getState();
				boolean isConnected = state == State.CONNECTED;// 当然，这边可以更精确的确定状态
				Log.e("H3c", "isConnected" + isConnected);
				if (isConnected) {
					Log.e("aa", "有数据啦");
				} else {
					Log.e("sdf", "断开了：");
				}
			}

		}

		if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {

			int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
			if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
				// Wifi P2P is enabled
				Log.e("接收到广播", " Wifi P2P is enabled");
			} else {
				Log.e("接收到广播", "wi-Fi P2P is not enabled");
				// Wi-Fi P2P is not enabled
			}

			// Check to see if Wi-Fi is enabled and notify appropriate activity

		} else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

			// 获取到点对点wifi
			if (null != mManager) {
				mManager.requestPeers(mChannel, peerListener);
			}

			// Call WifiP2pManager.requestPeers() to get a list of current peers
			Log.e("接收到广播", "WIFI_P2P_PEERS_CHANGED_ACTION:" + action);

		} else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION
				.equals(action)) {

			Log.e("接收到广播", "WIFI_P2P_CONNECTION_CHANGED_ACTION:" + action);
			// Respond to new connection or disconnections

		} else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
				.equals(action)) {

			Log.e("接收到广播", "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION:" + action);
			// Respond to this device's wifi state changing

		}
	}
}
