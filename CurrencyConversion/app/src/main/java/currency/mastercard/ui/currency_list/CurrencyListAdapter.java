package currency.mastercard.ui.currency_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.currency.currencyconversion.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import currency.mastercard.modals.Currency;
import currency.mastercard.services.CurrencyServiceImpl;
import currency.mastercard.ui.CurrencySelectionActivity;

/**
 * Created by SKY on 9/23/2017.
 */

public class CurrencyListAdapter
		extends RecyclerView.Adapter<CurrencyListAdapter.CurrencySelectionViewHolder> {

	private List<Currency> currencies;
	private Context context;

	public CurrencyListAdapter(Context context, List<Currency> currencies) {
		this.context = context;
		this.currencies = currencies;
	}

	@Override
	public int getItemCount() {
		return currencies.size();
	}

	@Override
	public CurrencySelectionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.currency_selection_card_view, viewGroup, false);
		CurrencySelectionViewHolder currencySelectionViewHolder =
				new CurrencySelectionViewHolder(v);
		return currencySelectionViewHolder;
	}

	@Override
	public void onBindViewHolder(CurrencySelectionViewHolder currencySelectionViewHolder,
			final int i) {
		currencySelectionViewHolder.currencySelectionLongName
				.setText(currencies.get(i).getFullName());

		Transformation transformation = new RoundedTransformationBuilder().borderColor(Color.BLACK)
				.borderWidthDp(R.integer.flag_border_width).cornerRadiusDp(R.integer.flag_radius)
				.oval(false).build();

		Picasso.with(context).load(CurrencyServiceImpl.getCurrencyService()
				.getCurrencyFlagUrl(currencies.get(i))).error(R.mipmap.ic_error).fit()
				.transform(transformation).into(currencySelectionViewHolder.currencySelectionFlag);

		currencySelectionViewHolder.currencySelectionCard
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent returnIntent = new Intent();
						returnIntent.putExtra(CurrencySelectionActivity.SELECTED_CURRENCY_KEY,
								currencies.get(i));
						((CurrencySelectionActivity) context)
								.setResult(Activity.RESULT_OK, returnIntent);

						((CurrencySelectionActivity) context).finish();

					}
				});

	}

	public static class CurrencySelectionViewHolder extends RecyclerView.ViewHolder {

		@Bind(R.id.currency_selection_card)
		CardView currencySelectionCard;

		@Bind(R.id.currency_selection_flag)
		ImageView currencySelectionFlag;

		@Bind(R.id.currency_selection_text)
		TextView currencySelectionLongName;

		CurrencySelectionViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

	}

}
