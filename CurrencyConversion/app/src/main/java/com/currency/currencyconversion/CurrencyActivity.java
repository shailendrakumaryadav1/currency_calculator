package com.currency.currencyconversion;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import modals.Currency;
import services.CurrencyService;
import services.CurrencyServiceImpl;

public class CurrencyActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency);

		LinearLayout ll = (LinearLayout) findViewById(R.id.src_currency_linear_layout);

		ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AsyncTaskRunner runner = new AsyncTaskRunner();
				runner.execute();
			}
		});

	}




		private class AsyncTaskRunner extends AsyncTask<String, String, String> {

			private String resp="ABCD";


			@Override
			protected String doInBackground(String... params) {

				try {
					List<Currency> currencies = CurrencyServiceImpl.getCurrencyService().getAllCurrencies();

					resp = CurrencyServiceImpl.getCurrencyService().getExchangeRate(currencies.get(0), currencies.get(1)).toString();


				}catch (Exception e) {
					System.out.println("GROSS ERROR");
					e.printStackTrace();

				}
				return resp;
			}


			@Override
			protected void onPostExecute(String result) {
				// execution of result of Long time consuming operation

				Toast.makeText(CurrencyActivity.this, resp, Toast.LENGTH_SHORT).show();

			}


			@Override
			protected void onPreExecute() {

			}


			@Override
			protected void onProgressUpdate(String... text) {
				Toast.makeText(CurrencyActivity.this, "Done", Toast.LENGTH_LONG).show();

			}
		}
	}