package currency.mastercard.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.currency.currencyconversion.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import currency.mastercard.modals.Currency;
import currency.mastercard.ui.currency_list.CurrencyListAdapter;

/**
 * Created by SKY on 9/23/2017.
 */

public class CurrencySelectionActivity extends AppCompatActivity {

	@Bind(R.id.currency_selection_recycler_view)
	RecyclerView currencySelectionRv;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency_selection);

		ButterKnife.bind(this);

		LinearLayoutManager llm = new LinearLayoutManager(this);
		currencySelectionRv.setLayoutManager(llm);

		List<Currency> c = new ArrayList<>();

		for(int i=65;i<=70;i++)
		{
			Currency ob = new Currency((char)i + "","","","",0);
			c.add(ob);
		}



		CurrencyListAdapter currencyListAdapter = new CurrencyListAdapter(this, c);
		currencySelectionRv.setAdapter(currencyListAdapter);


	}

}
