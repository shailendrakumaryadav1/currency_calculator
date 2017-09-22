package currency.mastercard.ui;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.currency.currencyconversion.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import currency.mastercard.ThisApplication;
import currency.mastercard.modals.Currency;
import currency.mastercard.modals.Exchange;
import currency.mastercard.modals.State;
import currency.mastercard.services.CurrencyService;
import currency.mastercard.services.CurrencyServiceImpl;

public class CurrencyActivity extends AppCompatActivity {

	private static final int UNIT = 1;
	private static final double DEFAULT_VALUE = 1000.0;

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
	@Bind(R.id.default_currency_card_image)
	ImageView defaultCurrencyCardImage;
	@Bind(R.id.default_currency_card_progress)
	ProgressBar defaultCurrencyCardProgress;
	@Bind(R.id.default_currency_card_text)
	TextView defaultCurrencyCardText;

	private Currency source;
	private Currency target;
	private Exchange exchange;
	private Exchange reverseExchange;
	private State state;
	private Double sourceValue;
	private Double targetValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency);
		ButterKnife.bind(this);

		initValues();

		createView();

	}

	public void initValues() {
		source = null;
		target = null;
		exchange = null;
		reverseExchange = null;
		state = null;
		sourceValue = null;
		targetValue = null;
	}

	public void createView() {
		fillLoadingView();

		AsyncTaskRunner runner = new AsyncTaskRunner();
		runner.execute();
	}

	public void displayCurrencyCard(boolean isVisible) {
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

	@OnClick(R.id.default_currency_card)
	public void refresh() {
		createView();
	}

	@OnClick(R.id.src_currency_linear_layout)
	public void clickSourceCurrencyChange() {

	}

	@OnClick(R.id.target_currency_linear_layout)
	public void clickTargetCurrencyChange() {

	}

	private class AsyncTaskRunner extends AsyncTask<String, String, String> {

		private String resp = "";

		@Override
		protected String doInBackground(String... params) {
			try {
				CurrencyService currencyService = CurrencyServiceImpl.getCurrencyService();
				if (source == null || target == null) {

					List<Currency> currencies = currencyService.getAllCurrencies();
					chooseBaseAndTargetCurrency(currencies);
				}
				exchange = currencyService.getExchangeRate(source, target);
				reverseExchange = currencyService.getExchangeRate(target, source);
				if (sourceValue == null || targetValue == null) {
					chooseBaseAndTargetCurrencyValue();
				}
				state = State.SUCCESS;
				resp = "SUCCESS";
			} catch (Exception e) {

				if (!ThisApplication.isConectedToInternet()) {
					state = State.NO_INTERNET;
					resp = "NO INTERNET";
				} else {
					state = State.ERROR;
					resp = "ERROR - SERVER";
				}
				System.out.println("GROSS ERROR");
				e.printStackTrace();
			}
			return resp;
		}

		@Override
		protected void onPostExecute(String result) {
			// execution of result of Long time consuming operation

			switch (state) {
				case SUCCESS:
					fillCurrencyView();
					break;
				case ERROR:
					fillErrorView();
					break;
				case NO_INTERNET:
					fillNoInternetView();
					break;
			}

			Toast.makeText(CurrencyActivity.this, result, Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(String... text) {
			Toast.makeText(CurrencyActivity.this, "Done", Toast.LENGTH_LONG).show();
		}
	}

	public void chooseBaseAndTargetCurrency(List<Currency> currencies) {
		source = currencies.get((int) (new Date().getTime() % currencies.size()));
		target = currencies.get((int) ((new Date().getTime() + 1) % currencies.size()));

	}

	public void chooseBaseAndTargetCurrencyValue() {
		sourceValue = DEFAULT_VALUE;
		targetValue = exchange.getValue(sourceValue);

	}

	public void fillNoInternetView() {
		defaultCurrencyCardProgress.setVisibility(View.GONE);
		defaultCurrencyCardImage.setImageResource(R.mipmap.ic_no_internet);
		defaultCurrencyCardText.setText(R.string.text_no_connection);
		defaultCurrencyCardImage.setVisibility(View.VISIBLE);
		displayCurrencyCard(false);
	}

	public void fillErrorView() {
		defaultCurrencyCardProgress.setVisibility(View.GONE);
		defaultCurrencyCardImage.setImageResource(R.mipmap.ic_error);
		defaultCurrencyCardText.setText(R.string.text_something_went_wrong);
		defaultCurrencyCardImage.setVisibility(View.VISIBLE);
		displayCurrencyCard(false);
	}

	public void fillCurrencyView() {
		fillSourceCard();
		fillTargetCard();
		displayCurrencyCard(true);

	}

	public void fillLoadingView() {
		defaultCurrencyCardProgress.setVisibility(View.VISIBLE);
		defaultCurrencyCardText.setText(R.string.text_loading);
		defaultCurrencyCardImage.setVisibility(View.GONE);
		displayCurrencyCard(false);
	}

	public void fillSourceCard() {
		sourceCurrencyCode.setText(source.getCode());
		fillFlag(source, sourceCurrencyFlag);

		currencyEquivalentSourceTv.setText(
				String.format(getString(R.string.text_currency_equivalent), UNIT, source.getCode(),
						exchange.getValue(UNIT), target.getCode()));

		sourceCurrencyValue
				.setText(String.format(getString(R.string.text_currency_format), sourceValue));
		
	}

	public void fillTargetCard() {
		targetCurrencyCode.setText(target.getCode());
		fillFlag(target, targetCurrencyFlag);

		currencyEquivalentTargetTv.setText(
				String.format(getString(R.string.text_currency_equivalent), UNIT, target.getCode(),
						reverseExchange.getValue(UNIT), source.getCode()));

		targetCurrencyValue
				.setText(String.format(getString(R.string.text_currency_format), targetValue));

	}

	public void fillFlag(Currency currency, ImageView imageView) {

		Transformation transformation =
				new RoundedTransformationBuilder().borderColor(Color.BLACK).borderWidthDp(1)
						.cornerRadiusDp(R.integer.flag_radius).oval(false).build();

		Picasso.with(this)
				.load(CurrencyServiceImpl.getCurrencyService().getCurrencyFlagUrl(currency))
				.error(R.mipmap.ic_error).fit().transform(transformation).into(imageView);

	}

}