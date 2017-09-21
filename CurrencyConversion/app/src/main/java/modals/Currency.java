package modals;

/**
 * Created by SKY on 9/21/2017.
 */

public class Currency {

	private String code;
	private String country;
	private String name;
	private String flagPath;
	private double rate;

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

}
