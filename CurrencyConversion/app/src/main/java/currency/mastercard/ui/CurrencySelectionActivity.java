package currency.mastercard.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.currency.currencyconversion.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import currency.mastercard.ThisApplication;
import currency.mastercard.modals.Currency;
import currency.mastercard.ui.currency_list.CurrencyListAdapter;

/**
 * Created by SKY on 9/23/2017.
 */

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

	@OnClick(R.id.currency_search_button)
	public void onClickSearch() {
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
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		currencySelectionRv.setLayoutManager(linearLayoutManager);
		CurrencyListAdapter currencyListAdapter = new CurrencyListAdapter(this,
				getMatchingCurrencies(currencySearchText.getText().toString()));
		currencySelectionRv.setAdapter(currencyListAdapter);

	}

	public void init() {
		currencies = ThisApplication.getCurrencyList();
	}

}
