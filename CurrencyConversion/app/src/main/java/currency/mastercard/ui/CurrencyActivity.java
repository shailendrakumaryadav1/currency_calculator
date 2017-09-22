package currency.mastercard.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.currency.currencyconversion.R;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import currency.mastercard.modals.Currency;
import currency.mastercard.modals.Exchange;
import currency.mastercard.services.CurrencyService;
import currency.mastercard.services.CurrencyServiceImpl;

public class CurrencyActivity extends AppCompatActivity {

	@Bind(R.id.base_currency_card)
	CardView baseCurrencyCard;
	@Bind(R.id.text_currency_equivalent_source)
	TextView currencyEquivalentSourceTv;
	@Bind(R.id.src_currency_linear_layout)
	LinearLayout sourceCurrencyLinearLyt;
	@Bind(R.id.src_currency_flag)
	ImageView sourceCurrencyFlag;
	@Bind(R.id.src_currency_code)
	TextView sourceCurrencyCode;
	@Bind(R.id.src_currency_value)
	EditText sourceCurrencyValue;

	@Bind(R.id.target_currency_card)
	CardView targetCurrencyCard;
	@Bind(R.id.text_currency_equivalent_target)
	TextView currencyEquivalentTargetTv;
	@Bind(R.id.target_currency_linear_layout)
	LinearLayout targetCurrencyLinearLyt;
	@Bind(R.id.target_currency_flag)
	ImageView targetCurrencyFlag;
	@Bind(R.id.target_currency_code)
	TextView targetCurrencyCode;
	@Bind(R.id.target_currency_value)
	EditText targetCurrencyValue;

	@Bind(R.id.default_currency_card)
	CardView defaultCurrencyCard;

	private Currency base;
	private Currency target;
	private Exchange exchange;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency);
		ButterKnife.bind(this);

		initValues();

		createView();

	}

	public void initValues()
	{
		base = null;
		target = null;
		exchange = null;
	}

	public void createView()
	{
		displayCard(false);

		AsyncTaskRunner runner = new AsyncTaskRunner();
		runner.execute();
	}

	public void displayCard(boolean isVisible) {
		if (isVisible) {
			baseCurrencyCard.setVisibility(View.VISIBLE);
			targetCurrencyCard.setVisibility(View.VISIBLE);
			defaultCurrencyCard.setVisibility(View.GONE);
		} else {
			baseCurrencyCard.setVisibility(View.GONE);
			targetCurrencyCard.setVisibility(View.GONE);
			defaultCurrencyCard.setVisibility(View.VISIBLE);
		}
	}

	@OnClick(R.id.src_currency_linear_layout)
	public void submit(View view) {
		AsyncTaskRunner runner = new AsyncTaskRunner();
		runner.execute();
	}

	private class AsyncTaskRunner extends AsyncTask<String, String, String> {

		private String resp = "ABCD";

		@Override
		protected String doInBackground(String... params) {
			try {
				CurrencyService currencyService= CurrencyServiceImpl.getCurrencyService();
				if(base == null || target == null)
				{

					List<Currency> currencies = currencyService.getAllCurrencies();
					chooseBaseAndTarget(currencies);
				}
				exchange = currencyService.getExchangeRate(base,target);
			} catch (Exception e) {
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

	public void chooseBaseAndTarget(List<Currency> currencies)
	{
		base = currencies.get((int)(new Date().getTime() % currencies.size()));
		target = currencies.get((int)((new Date().getTime() + 1) % currencies.size()));
	}

}