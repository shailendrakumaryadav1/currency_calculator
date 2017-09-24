package currency.mastercard.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class CurrencyActivity extends AppCompatActivity implements View.OnFocusChangeListener {

	private static final int UNIT = 1;
	private static final double DEFAULT_VALUE = 1000.0;
	private static final double DEFAULT_VALUE_HINT = 1000.0;
	private static final int SOURCE_CURRENCY_CHANGE_REQUEST_CODE = 1001;
	private static final int TARGET_CURRENCY_CHANGE_REQUEST_CODE = 1002;
	private static final String TAG = CurrencyActivity.class.getName();

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
	private TextWatcher sourceCurrencyValueWatcher;
	private TextWatcher targetCurrencyValueWatcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency);
		ButterKnife.bind(this);
		init();
		createView();
	}

	public void init() {
		source = null;
		target = null;
		exchange = null;
		reverseExchange = null;
		state = null;
		sourceValue = null;
		targetValue = null;
	}

	public void createView() {
		initActionBar();
		initFocusChangeListeners();
		fillLoadingView();
		AsyncTaskRunner runner = new AsyncTaskRunner();
		runner.execute();
	}

	@OnClick(R.id.src_currency_linear_layout)
	public void changeSourceCurrency(View view) {
		Intent intent = new Intent(this, CurrencySelectionActivity.class);
		startActivityForResult(intent, SOURCE_CURRENCY_CHANGE_REQUEST_CODE);
	}

	@OnClick(R.id.target_currency_linear_layout)
	public void changeTargetCurrency(View view) {
		Intent intent = new Intent(this, CurrencySelectionActivity.class);
		startActivityForResult(intent, TARGET_CURRENCY_CHANGE_REQUEST_CODE);
	}

	@OnClick(R.id.default_currency_card)
	public void refresh() {
		createView();
	}

	public void initActionBar() {
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			getSupportActionBar().setCustomView(R.layout.action_bar_currency_activity);
		}
	}

	public void displayCurrencyCards(boolean isVisible) {
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case SOURCE_CURRENCY_CHANGE_REQUEST_CODE:
					source = (Currency) data.getExtras()
							.getSerializable(CurrencySelectionActivity.SELECTED_CURRENCY_KEY);
					break;
				case TARGET_CURRENCY_CHANGE_REQUEST_CODE:
					target = (Currency) data.getExtras()
							.getSerializable(CurrencySelectionActivity.SELECTED_CURRENCY_KEY);
					break;
			}
			createView();
		}
	}

	private class AsyncTaskRunner extends AsyncTask<String, String, String> {

		private String resp = "";

		@Override
		protected String doInBackground(String... params) {
			try {
				CurrencyService currencyService = CurrencyServiceImpl.getCurrencyService();
				List<Currency> currencies = currencyService.getAllCurrencies();
				ThisApplication.setCurrencyList(currencies);
				if (source == null || target == null) {
					chooseBaseAndTargetCurrency(currencies);
				}
				exchange = currencyService.getExchangeRate(source, target);
				reverseExchange = currencyService.getExchangeRate(target, source);
				if (sourceValue == null || targetValue == null) {
					chooseBaseAndTargetCurrencyValue();
				}
				state = State.SUCCESS;
			} catch (Exception e) {

				if (!ThisApplication.isConectedToInternet()) {
					state = State.NO_INTERNET;
				} else {
					state = State.ERROR;
				}
				e.printStackTrace();
			}
			resp = state.toString();
			return resp;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d(TAG, result);
			switch (state) {
				case SUCCESS:
					fillCurrencyView();
					break;
				case ERROR:
					// in case any currency is removed from server.
					source = null;
					target = null;
					fillErrorView();
					break;
				case NO_INTERNET:
					fillNoInternetView();
					break;
			}
		}

	}

	public void chooseBaseAndTargetCurrency(List<Currency> currencies) {
		// choose any two currencies to show to user for first time
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
		displayCurrencyCards(false);
	}

	public void fillErrorView() {
		defaultCurrencyCardProgress.setVisibility(View.GONE);
		defaultCurrencyCardImage.setImageResource(R.mipmap.ic_error);
		defaultCurrencyCardText.setText(R.string.text_something_went_wrong);
		defaultCurrencyCardImage.setVisibility(View.VISIBLE);
		displayCurrencyCards(false);
	}

	public void fillCurrencyView() {
		fillSourceCard();
		fillTargetCard();
		applyCurrencyValueChangeListener();
		displayCurrencyCards(true);
	}

	public void fillLoadingView() {
		defaultCurrencyCardProgress.setVisibility(View.VISIBLE);
		defaultCurrencyCardText.setText(R.string.text_loading);
		defaultCurrencyCardImage.setVisibility(View.GONE);
		displayCurrencyCards(false);
	}

	public void fillSourceCard() {
		sourceCurrencyCode.setText(source.getCode());
		fillFlag(source, sourceCurrencyFlag);
		currencyEquivalentSourceTv.setText(
				String.format(getString(R.string.text_currency_equivalent), UNIT, source.getCode(),
						exchange.getValue(UNIT), target.getCode()));
		sourceCurrencyValue
				.setText(String.format(getString(R.string.text_currency_format), sourceValue));
		sourceCurrencyValue.setHint(
				String.format(getString(R.string.text_currency_format), DEFAULT_VALUE_HINT));
	}

	public void fillTargetCard() {
		targetCurrencyCode.setText(target.getCode());
		fillFlag(target, targetCurrencyFlag);
		currencyEquivalentTargetTv.setText(
				String.format(getString(R.string.text_currency_equivalent), UNIT, target.getCode(),
						reverseExchange.getValue(UNIT), source.getCode()));
		targetValue = exchange.getValue(sourceValue);
		targetCurrencyValue
				.setText(String.format(getString(R.string.text_currency_format), targetValue));
		targetCurrencyValue.setHint(String.format(getString(R.string.text_currency_format),
				exchange.getValue(DEFAULT_VALUE_HINT)));
	}

	public void fillFlag(Currency currency, ImageView imageView) {
		Transformation transformation =
				new RoundedTransformationBuilder().borderColor(Color.BLACK).borderWidthDp(1)
						.cornerRadiusDp(R.integer.flag_radius).oval(false).build();
		Picasso.with(this)
				.load(CurrencyServiceImpl.getCurrencyService().getCurrencyFlagUrl(currency))
				.error(R.mipmap.ic_error).fit().transform(transformation).into(imageView);
	}

	public void applyCurrencyValueChangeListener() {
		sourceCurrencyValueWatcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				if (sourceCurrencyValue.getText().toString().length() == 0) {
					sourceValue = null;
					targetValue = null;
					targetCurrencyValue.setText("");
				} else {
					sourceValue = Double.parseDouble(sourceCurrencyValue.getText().toString());
					targetValue = exchange.getValue(sourceValue);
					targetCurrencyValue.setText(
							String.format(getString(R.string.text_currency_format), targetValue));
				}

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		};

		targetCurrencyValueWatcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (targetCurrencyValue.getText().toString().length() == 0) {
					targetValue = null;
					sourceValue = null;
					sourceCurrencyValue.setText("");
				} else {
					targetValue = Double.parseDouble(targetCurrencyValue.getText().toString());
					sourceValue = reverseExchange.getValue(targetValue);
					sourceCurrencyValue.setText(
							String.format(getString(R.string.text_currency_format), sourceValue));
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		};
	}

	public void initFocusChangeListeners() {
		sourceCurrencyValue.setOnFocusChangeListener(this);
		targetCurrencyValue.setOnFocusChangeListener(this);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (v.equals(findViewById(R.id.src_currency_value))) {
			if (hasFocus) {
				sourceCurrencyValue.addTextChangedListener(sourceCurrencyValueWatcher);
			} else {
				sourceCurrencyValue.removeTextChangedListener(sourceCurrencyValueWatcher);
			}
		} else if (v.equals(findViewById(R.id.target_currency_value))) {
			if (hasFocus) {
				targetCurrencyValue.addTextChangedListener(targetCurrencyValueWatcher);
			} else {
				targetCurrencyValue.removeTextChangedListener(targetCurrencyValueWatcher);
			}
		}
	}

}