package currency.mastercard.services;

import android.net.Uri;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;

import currency.mastercard.api.APIClient;
import currency.mastercard.modals.Currency;
import currency.mastercard.modals.Exchange;

/**
 * Created by SKY on 9/21/2017.
 */

public class CurrencyServiceImpl implements CurrencyService {

	private static CurrencyService currencyService = null;

	private CurrencyServiceImpl() {
	}

	public static CurrencyService getCurrencyService() {
		if (currencyService == null)
			currencyService = new CurrencyServiceImpl();
		return currencyService;
	}

	public List<Currency> getAllCurrencies() {
		return APIClient.getInstance().getCurrencies();
	}

	public Exchange getExchangeRate(Currency base, Currency target) {
		return APIClient.getInstance().getExchange(base, target);
	}

	public String getCurrencyFlagUrl(Currency currency) {
		return APIClient.BASE_URL + currency.getFlagPath();
	}

}

