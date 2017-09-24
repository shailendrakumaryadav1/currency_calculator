package currency.mastercard.services;

import java.util.List;

import currency.mastercard.modals.Currency;
import currency.mastercard.modals.Exchange;

/**
 * Created by SKY on 9/21/2017.
 */

public interface CurrencyService {

	// To get all currencies.
	List<Currency> getAllCurrencies();

	// To get exchange between two currencies.
	Exchange getExchangeRate(Currency base, Currency target);

	// To get flag url for the currency.
	String getCurrencyFlagUrl(Currency currency);

}
