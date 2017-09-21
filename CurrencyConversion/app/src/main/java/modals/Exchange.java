package modals;

/**
 * Created by SKY on 9/21/2017.
 */

public class Exchange {

	private String baseCode;
	private String targetCode;
	private double rate;

	public Exchange(String baseCode, String targetCode, double rate) {
		this.baseCode = baseCode;
		this.targetCode = targetCode;
		this.rate = rate;
	}

	public String getBaseCode() {
		return baseCode;
	}

	public String getTargetCode() {
		return targetCode;
	}

	public double getRate() {
		return rate;
	}

	public void setBaseCode(String baseCode) {
		this.baseCode = baseCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
}