package currency.mastercard;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import currency.mastercard.modals.Currency;

/**
 * Created by SKY on 9/22/2017.
 */

public class ThisApplication extends Application {

	static ThisApplication instance;
	static List<Currency> currencyList;

	@Override
	public void onCreate() {
		super.onCreate();

		Picasso.Builder builder = new Picasso.Builder(this);
		builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
		Picasso built = builder.build();
		Picasso.setSingletonInstance(built);

		instance = this;
	}

	public static boolean isConectedToInternet() {
		ConnectivityManager cm =
				(ConnectivityManager) instance.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}

	public static void setCurrencyList(List<Currency> currencyList)
	{
		ThisApplication.currencyList = currencyList;
	}

	public static List<Currency> getCurrencyList()
	{
		return currencyList;
	}

}
