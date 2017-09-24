package currency.mastercard.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.currency.currencyconversion.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import currency.mastercard.ThisApplication;
import currency.mastercard.modals.Currency;
import currency.mastercard.ui.currency_list.CurrencyListAdapter;

public class CurrencySelectionActivity extends AppCompatActivity {

	public static final String SELECTED_CURRENCY_KEY = "SELECTED_CURRENCY_KEY";

	@Bind(R.id.currency_selection_recycler_view)
	RecyclerView currencySelectionRv;
	@Bind(R.id.currency_search_text)
	EditText currencySearchText;

	private List<Currency> currencies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency_selection);
		ButterKnife.bind(this);
		init();
		createView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		createView();
	}

	@OnClick(R.id.currency_search_button)
	public void onClickSearch() {
		createView();
	}

	@OnTextChanged(R.id.currency_search_text)
	public void onTextChange() {
		if (currencySearchText.getText().toString().isEmpty())
			createView();
	}

	public List<Currency> getMatchingCurrencies(String pattern) {
		if (pattern == null || pattern.isEmpty())
			return currencies;
		List<Currency> matchingCurrencies = new ArrayList<>();
		for (Currency currency : currencies) {
			if (currency.matches(pattern))
				matchingCurrencies.add(currency);
		}
		return matchingCurrencies;
	}

	public void createView() {
		initActionBar();
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		currencySelectionRv.setLayoutManager(linearLayoutManager);
		CurrencyListAdapter currencyListAdapter = new CurrencyListAdapter(this,
				getMatchingCurrencies(currencySearchText.getText().toString()));
		currencySelectionRv.setAdapter(currencyListAdapter);

	}

	public void initActionBar() {
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			getSupportActionBar().setCustomView(R.layout.action_bar_currency_selection_activity);
			getSupportActionBar().getCustomView().findViewById(R.id.currency_selection_cancel)
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							onClickCancel();
						}
					});
		}
	}

	public void onClickCancel() {
		onBackPressed();
	}

	public void init() {
		currencies = ThisApplication.getCurrencyList();
	}

}
