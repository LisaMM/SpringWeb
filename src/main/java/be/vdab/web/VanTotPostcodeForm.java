package be.vdab.web;

class VanTotPostcodeForm {
	private int vanpostcode;
	private int totpostcode;
	
	VanTotPostcodeForm() {}
	
	VanTotPostcodeForm(int vanpostcode, int totpostcode) {
		this.vanpostcode = vanpostcode;
		this.totpostcode = totpostcode;
	}
	
	public int getVanpostcode() {
		return vanpostcode;
	}
	
	public int getTotpostcode() {
		return totpostcode;
	}
	
	public void setVanpostcode(int vanpostcode) {
		this.vanpostcode = vanpostcode;
	}
	
	public void setTotpostcode(int totpostcode) {
		this.totpostcode = totpostcode;
	}
	
	@Override
	public String toString() {
		return String.format("%s-%s", vanpostcode, totpostcode);
	}
}
