package currency.mastercard.modals;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by SKY on 9/21/2017.
 */

public class Currency implements Serializable {

	private String code;
	private String country;
	private String name;
	private String flagPath;
	private double rate;

	private static final String FULL_NAME_FORMAT = "%s - %s";

	public Currency(String code, String country, String name, String flagPath, double rate) {
		this.code = code;
		this.country = country;
		this.name = name;
		this.flagPath = flagPath;
		this.rate = rate;
	}

	public String getCode() {
		return code;
	}

	public String getCountry() {
		return country;
	}

	public String getName() {
		return name;
	}

	public String getFlagPath() {
		return flagPath;
	}

	public double getRate() {
		return rate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFlagPath(String flagPath) {
		this.flagPath = flagPath;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getFullName() {
		return String.format(FULL_NAME_FORMAT, code, name);
	}

	public boolean matches(String pattern) {
		boolean matches = false;
		pattern = pattern.toLowerCase();
		if (code.toLowerCase().contains(pattern) || name.toLowerCase().contains(pattern) || country
				.toLowerCase().contains(pattern))
			matches = true;
		return matches;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Currency))
			return false;

		Currency currency = (Currency) o;

		if (Double.compare(currency.rate, rate) != 0)
			return false;
		if (code != null ? !code.equals(currency.code) : currency.code != null)
			return false;
		if (country != null ? !country.equals(currency.country) : currency.country != null)
			return false;
		if (name != null ? !name.equals(currency.name) : currency.name != null)
			return false;
		if (flagPath != null ? !flagPath.equals(currency.flagPath) : currency.flagPath != null)
			return false;
		return true;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = code != null ? code.hashCode() : 0;
		result = 31 * result + (country != null ? country.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (flagPath != null ? flagPath.hashCode() : 0);
		temp = Double.doubleToLongBits(rate);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "Currency{" + "code='" + code + '\'' + ", country='" + country + '\'' + ", name='"
				+ name + '\'' + ", flagPath='" + flagPath + '\'' + ", rate=" + rate + '}';
	}
}
