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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Exchange))
			return false;

		Exchange exchange = (Exchange) o;

		if (Double.compare(exchange.rate, rate) != 0)
			return false;
		if (baseCode != null ? !baseCode.equals(exchange.baseCode) : exchange.baseCode != null)
			return false;
		return targetCode != null ? targetCode.equals(exchange.targetCode) :
				exchange.targetCode == null;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = baseCode != null ? baseCode.hashCode() : 0;
		result = 31 * result + (targetCode != null ? targetCode.hashCode() : 0);
		temp = Double.doubleToLongBits(rate);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "Exchange{" + "baseCode='" + baseCode + '\'' + ", targetCode='" + targetCode + '\''
				+ ", rate=" + rate + '}';
	}
}