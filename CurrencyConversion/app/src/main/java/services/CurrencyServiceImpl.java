package services;

import java.util.List;

import modals.Currency;
import modals.Exchange;

/**
 * Created by SKY on 9/21/2017.
 */

public class CurrencyServiceImpl implements CurrencyService {

	private static CurrencyService currencyService = null;

	private CurrencyServiceImpl()
	{
	}

	public static CurrencyService getCurrencyService()
	{
		if(currencyService != null)
			return currencyService;
		currencyService = new CurrencyServiceImpl();
		return currencyService;
	}

	public List<Currency> getAllCurrencies() {
		return null;
	}

	public Exchange getExchangeRate(Currency base, Currency target) {
		return null;
	}

}
