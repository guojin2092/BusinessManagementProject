package baseutil.library.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 手机网络状态(wifi,gprs)
 * 
 * @author Administrator
 * 
 */
public class MyNetworkUtil {

	/**
	 * 网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}


	/**
	 * 判断当前网络是否可用
	 */
	public static boolean isNetAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
	}

	/**
	 * wifi 是否连接
	 * 
	 * @param context
	 * @return
	 */
	public boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 移动网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 网络连接类型
	 * 
	 * @param context
	 * @return
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

	// public static boolean isNetworkAvailable(Context context) {
	// // boolean isConnect(){
	// ConnectivityManager cm = (ConnectivityManager) context
	// .getSystemService(Context.CONNECTIVITY_SERVICE);
	// if (cm != null && cm.get) {
	// return true;
	// }
	// return false;
	// // }
	// // ConnectivityManager connectivity = (ConnectivityManager) context
	// // .getSystemService(Context.CONNECTIVITY_SERVICE);
	// // if (connectivity == null) {
	// // // Log.i("NetWorkState", "Unavailabel");
	// // return false;
	// // } else {
	// // NetworkInfo[] info = connectivity.getAllNetworkInfo();
	// // if (info != null) {
	// // for (int i = 0; i < info.length; i++) {
	// // if (info[i].getState() == NetworkInfo.State.CONNECTED) {
	// // // Log.i("NetWorkState", "Availabel");
	// // return true;
	// // }
	// // }
	// // }
	// // }
	// // return false;
	// }
}
