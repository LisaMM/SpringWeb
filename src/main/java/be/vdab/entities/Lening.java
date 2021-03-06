package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import javax.validation.constraints.*;

import org.springframework.format.annotation.*;
import org.springframework.format.annotation.NumberFormat.Style;

public class Lening implements Serializable {
	public interface Stap1 {}
	public interface Stap2 {}
	private static final long serialVersionUID = 1L;
	@NotNull(groups = Stap1.class)
	@Size(min = 1, max = 50, message = "{Size.tekst}", groups = Stap1.class)
	private String voornaam;
	@NotNull(groups = Stap1.class)
	@Size(min = 1, max = 50, message = "{Size.tekst}", groups = Stap1.class)
	private String familienaam;
	@NotNull(groups = Stap1.class)
	@Size(min = 1, max = 50, message = "{Size.tekst}", groups = Stap1.class)
	private String doel;
	@NotNull(groups = Stap2.class)
	@DateTimeFormat(style = "S-")
	private Date beginDatum;
	@NotNull(groups = Stap2.class)
	@Min(value = 1, groups = Stap2.class)
	@NumberFormat(style = Style.NUMBER)
	private BigDecimal bedrag;
	@NotNull(groups = Stap2.class)
	@Min(value = 1, groups = Stap2.class)
	private Integer aantalMaanden;
	private List<String> telefoonNrs = new ArrayList<>();
	private Map<Long, Integer> aantalPersonenTenLastePerJaar = new LinkedHashMap<>();
	private final static int AANTAL_JAREN = 3;
	
	public Lening() {
		beginDatum = new Date();
		telefoonNrs.add("");
		long jaar = Calendar.getInstance().get(Calendar.YEAR) - AANTAL_JAREN;
		for (int i = 0; i != AANTAL_JAREN; i++) {
			aantalPersonenTenLastePerJaar.put(jaar++, 0);
		}
	}
	
	@Override
	public String toString() {
		return voornaam + " " + familienaam + " : " + doel;
	}
	
	public void nogEenTelefoonNr() {
		telefoonNrs.add("");
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getFamilienaam() {
		return familienaam;
	}

	public void setFamilienaam(String familienaam) {
		this.familienaam = familienaam;
	}

	public String getDoel() {
		return doel;
	}

	public void setDoel(String doel) {
		this.doel = doel;
	}

	public Date getBeginDatum() {
		return beginDatum;
	}

	public void setBeginDatum(Date beginDatum) {
		this.beginDatum = beginDatum;
	}

	public BigDecimal getBedrag() {
		return bedrag;
	}

	public void setBedrag(BigDecimal bedrag) {
		this.bedrag = bedrag;
	}

	public Integer getAantalMaanden() {
		return aantalMaanden;
	}

	public void setAantalMaanden(Integer aantalMaanden) {
		this.aantalMaanden = aantalMaanden;
	}

	public List<String> getTelefoonNrs() {
		return telefoonNrs;
	}

	public void setTelefoonNrs(ArrayList<String> telefoonNrs) {
		this.telefoonNrs = telefoonNrs;
	}

	public Map<Long, Integer> getAantalPersonenTenLastePerJaar() {
		return aantalPersonenTenLastePerJaar;
	}

	public void setAantalPersonenTenLastePerJaar(
			Map<Long, Integer> aantalPersonenTenLastePerJaar) {
		this.aantalPersonenTenLastePerJaar = aantalPersonenTenLastePerJaar;
	}
}
