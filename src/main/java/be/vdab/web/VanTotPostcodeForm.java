package be.vdab.web;

import javax.validation.constraints.NotNull;
import be.vdab.constraints.Postcode;

class VanTotPostcodeForm {
	@NotNull
	@Postcode
	private Integer vanpostcode;
	@NotNull
	@Postcode
	private Integer totpostcode;
	
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
