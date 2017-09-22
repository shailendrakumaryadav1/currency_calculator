package currency.mastercard.api;

import java.util.List;

import currency.mastercard.modals.Currency;
import currency.mastercard.modals.Exchange;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by SKY on 9/21/2017.
 */

public interface APIMethods {

	@GET("/currency/mastercard/api/currency")
	List<Currency> getCurrencies();

	@GET("/currency/mastercard/api/exchange")
	Exchange getExchangeResponse(@Query("baseCode") String baseCode,
			@Query("targetCode") String targetCode);

}
