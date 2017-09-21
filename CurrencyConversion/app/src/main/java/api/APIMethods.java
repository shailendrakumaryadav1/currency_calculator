package api;

import java.util.List;

import modals.Currency;
import modals.Exchange;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by SKY on 9/21/2017.
 */

public interface APIMethods {

	@GET("/api/currency")
	List<Currency> getCurrencies();

	@GET("/api/exchange")
	Exchange getExchangeResponse(@Query("baseCode") String baseCode,
			@Query("targetCode") String targetCode);

}
