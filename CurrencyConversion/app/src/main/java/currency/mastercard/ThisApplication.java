package currency.mastercard;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by SKY on 9/22/2017.
 */

public class ThisApplication extends Application {

	static ThisApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;
	}

	public static boolean isConectedToInternet() {
		ConnectivityManager cm =
				(ConnectivityManager) instance.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}

}
