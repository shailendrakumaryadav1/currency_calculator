package currency.mastercard.services;

import java.util.List;

import currency.mastercard.modals.Currency;
import currency.mastercard.modals.Exchange;

/**
 * Created by SKY on 9/21/2017.
 */

public interface CurrencyService {


	public List<Currency> getAllCurrencies();

	public Exchange getExchangeRate(Currency base, Currency target);

	public String getCurrencyFlagUrl(Currency currency);

}
