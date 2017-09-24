package com.currency.currencyconversion;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import currency.mastercard.modals.Currency;
import currency.mastercard.modals.Exchange;
import currency.mastercard.services.CurrencyService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by SKY on 9/24/2017.
 */

public class CurrencyServiceTest {

	private static CurrencyService mockService;

	private static Currency USD;
	private static Currency AUD;
	private static Currency SGD;
	private static Currency fakeCurrency;

	private static Exchange USD_AUD;
	private static Exchange AUD_SGD;

	@BeforeClass
	public static void setUp() {

		mockService = mock(CurrencyService.class);

		USD = new Currency("USD", "America", "American Dollar", "/flags/us.png", 1);
		AUD = new Currency("AUD", "Australia", "Australian Dollar", "/flags/au.png", 0.75);
		SGD = new Currency("SGD", "Singapore", "Singapore Dollar", "/flags/sg.png", 0.80);
		fakeCurrency = new Currency("FAKE", "FAKE_COUNTRY", "FAKE_NAME", "FAKE_FLAG.png", -1);

		USD_AUD = new Exchange(USD.getCode(), AUD.getCode(), 1.4);
		AUD_SGD = new Exchange(AUD.getCode(), SGD.getCode(), 1.2);

		when(mockService.getAllCurrencies()).thenReturn(Arrays.asList(USD, AUD, SGD));
		when(mockService.getExchangeRate(USD, AUD)).thenReturn(USD_AUD);
		when(mockService.getExchangeRate(AUD, SGD)).thenReturn(AUD_SGD);
		when(mockService.getExchangeRate(USD, fakeCurrency)).thenThrow(Exception.class);
	}

	@Test
	public void testGetAllCurrency() throws Exception {
		List<Currency> currencies = mockService.getAllCurrencies();

		assertEquals(3, currencies.size());
		assertEquals("America", currencies.get(0).getCountry());
		assertEquals(AUD, currencies.get(1));
		assertEquals(SGD.getFullName(), currencies.get(2).getFullName());
	}

	@Test
	public void testGetExchange() throws Exception {
		Exchange exchange_AUD_SGD = mockService.getExchangeRate(AUD, SGD);
		Exchange exchange_USD_AUD = mockService.getExchangeRate(USD, AUD);

		assertEquals("AUD", exchange_AUD_SGD.getBaseCode());
		assertEquals("SGD", exchange_AUD_SGD.getTargetCode());
		assertEquals(1.2, exchange_AUD_SGD.getRate(), 0);
		assertEquals(AUD_SGD, exchange_AUD_SGD);

		assertEquals("USD", exchange_USD_AUD.getBaseCode());
		assertEquals("AUD", exchange_USD_AUD.getTargetCode());
		assertEquals(1.4, exchange_USD_AUD.getRate(), 0);
		assertEquals(USD_AUD, exchange_USD_AUD);
	}

	@Test(expected = Exception.class)
	public void testGetExchangeInvalidCodeFails() {
		//use the fake currency
		mockService.getExchangeRate(USD, fakeCurrency);
	}

}
