package services;

import java.util.List;

import modals.Currency;
import modals.Exchange;

/**
 * Created by SKY on 9/21/2017.
 */

public interface CurrencyService {


	public List<Currency> getAllCurrencies();

	public Exchange getExchangeRate(Currency base, Currency target);

}
