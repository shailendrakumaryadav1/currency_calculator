package currency.mastercard.api;

import java.util.List;

import currency.mastercard.modals.Currency;
import currency.mastercard.modals.Exchange;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by SKY on 9/21/2017.
 */

public class APIClient implements RequestInterceptor {

	// Server URL - Update here.
	public static final String BASE_URL = "http://192.168.1.66:8080";
	private static APIMethods api;
	private static APIClient instance;

	private APIClient() {

	}

	public static APIClient getInstance() {
		if (instance == null) {
			instance = new APIClient();
		}
		return instance;
	}

	private APIMethods getApi() {
		if (api == null) {
			RestAdapter restAdapter = new RestAdapter.Builder().setRequestInterceptor(this)
					.setEndpoint(APIClient.BASE_URL).setLogLevel(RestAdapter.LogLevel.FULL).build();

			api = restAdapter.create(APIMethods.class);
		}
		return api;
	}

	@Override
	public void intercept(RequestFacade request) {
		//Query parameters can be added here, like API keys, if exist
		//request.addQueryParam(APIClient.KEY_PARAM, APIClient.API_KEY);
	}

	// To get all currencies.
	public List<Currency> getCurrencies() {
		return getApi().getCurrencies();
	}

	// To get exchange between two currencies.
	public Exchange getExchange(Currency base, Currency target) {
		String baseCode = base.getCode();
		String targetCode = target.getCode();

		return getApi().getExchangeResponse(baseCode, targetCode);
	}

}