package be.vdab.web;

import javax.validation.constraints.*;

class VanTotPostcodeForm {
	@NotNull
	@Min(MIN_POSTCODE)
	@Max(MAX_POSTCODE)
	private Integer vanpostcode;
	@NotNull
	@Min(MIN_POSTCODE)
	@Max(MAX_POSTCODE)
	private Integer totpostcode;
	private final static int MIN_POSTCODE = 1000;
	private final static int MAX_POSTCODE = 9999;
	
	VanTotPostcodeForm() {}
	
	VanTotPostcodeForm(Integer vanpostcode, Integer totpostcode) {
		this.vanpostcode = vanpostcode;
		this.totpostcode = totpostcode;
	}
	
	public Integer getVanpostcode() {
		return vanpostcode;
	}
	
	public Integer getTotpostcode() {
		return totpostcode;
	}
	
	public void setVanpostcode(Integer vanpostcode) {
		this.vanpostcode = vanpostcode;
	}

	public void setTotpostcode(Integer totpostcode) {
		this.totpostcode = totpostcode;
	}
	
	@Override
	public String toString() {
		return String.format("%s-%s", vanpostcode, totpostcode);
	}
	
	public boolean isValid() {
		if (vanpostcode == null || totpostcode == null) {
			return false;
		}
		return vanpostcode <= totpostcode;
	}
}
