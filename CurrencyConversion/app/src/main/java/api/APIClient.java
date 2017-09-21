package api;

import java.util.List;

import modals.Currency;
import modals.Exchange;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by SKY on 9/21/2017.
 */

public class APIClient implements RequestInterceptor {

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

	private APIClient.APIMethods getApi() {
		if (api == null) {
			RestAdapter restAdapter = new RestAdapter.Builder().setRequestInterceptor(this)
					.setEndpoint(APIClient.BASE_URL).setLogLevel(RestAdapter.LogLevel.FULL).build();

			api = restAdapter.create(APIClient.APIMethods.class);
		}
		return api;
	}

	@Override
	public void intercept(RequestFacade request) {
		//request.addQueryParam(APIClient.KEY_PARAM, APIClient.API_KEY);
	}

	public List<Currency> getCurrencies() {
		List<Currency> result = getApi().getCurrencies();
		return result;
	}

	public Exchange getExchange(Currency base, Currency target) {
		String baseCode = base.getCode();
		String targetCode = target.getCode();

		Exchange result = getApi().getExchangeResponse(baseCode, targetCode);
		return result;
	}

	private interface APIMethods {

		@GET("/api/currency")
		List<Currency> getCurrencies();

		@GET("/api/exchange")
		Exchange getExchangeResponse(@Query("baseCode") String baseCode,
				@Query("targetCode") String targetCode);

	}

}