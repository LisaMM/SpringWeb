package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import org.springframework.format.annotation.*;
import org.springframework.format.annotation.NumberFormat.Style;

import be.vdab.valueobjects.Adres;

@Entity
@Table(name = "filialen")
public class Filiaal implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	@NotNull
	@Size(min = 1, max = 50, message = "{Size.tekst}")
	private String naam;
	private boolean hoofdFiliaal;
	@NumberFormat(style = Style.NUMBER)
	@NotNull
	@Min(0)
	@Digits(integer = 10, fraction = 2)
	private BigDecimal waardeGebouw;
	@DateTimeFormat(style = "S-")
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date inGebruikName;
	@Valid
	@Embedded
	private Adres adres;
	@OneToMany(mappedBy = "filiaal")
	private Set<Werknemer> werknemers;

	public Filiaal() {
	}

	public Filiaal(String naam, boolean hoofdFiliaal, BigDecimal waardeGebouw,
			Date inGebruikName, Adres adres) {
		setNaam(naam);
		setHoofdFiliaal(hoofdFiliaal);
		setWaardeGebouw(waardeGebouw);
		setInGebruikName(inGebruikName);
		setAdres(adres);
		this.werknemers = new LinkedHashSet<>();
	}

	public Filiaal(long id, String naam, boolean hoofdFiliaal,
			BigDecimal waardeGebouw, Date inGebruikName, Adres adres) {
		this(naam, hoofdFiliaal, waardeGebouw, inGebruikName, adres);
		setId(id);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public boolean isHoofdFiliaal() {
		return hoofdFiliaal;
	}

	public void setHoofdFiliaal(boolean hoofdFiliaal) {
		this.hoofdFiliaal = hoofdFiliaal;
	}

	public BigDecimal getWaardeGebouw() {
		return waardeGebouw;
	}

	public void setWaardeGebouw(BigDecimal waardeGebouw) {
		this.waardeGebouw = waardeGebouw;
	}

	public Date getInGebruikName() {
		return inGebruikName;
	}

	public void setInGebruikName(Date inGebruikName) {
		this.inGebruikName = inGebruikName;
	}

	public Adres getAdres() {
		return adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	public Set<Werknemer> getWerknemers() {
		return Collections.unmodifiableSet(werknemers);
	}

	public void addWerknemer(Werknemer werknemer) {
		werknemers.add(werknemer);
		if (werknemer.getFiliaal() != this) {
			werknemer.setFiliaal(this);
		}
	}
	
	public void removeWerknemer(Werknemer werknemer) {
		if (werknemer.getFiliaal() == this) {
			werknemers.remove(werknemer);
			werknemer.setFiliaal(null);
		}
	}

	@Override
	public String toString() {
		return String.format("%s:%d", naam, id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((naam == null) ? 0 : naam.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filiaal other = (Filiaal) obj;
		if (naam == null) {
			if (other.naam != null)
				return false;
		} else if (!naam.equals(other.naam))
			return false;
		return true;
	}
}
