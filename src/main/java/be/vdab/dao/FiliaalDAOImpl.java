package be.vdab.dao;

import java.math.BigDecimal;
import java.text.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.*;
import org.springframework.stereotype.Repository;

import be.vdab.entities.Filiaal;
import be.vdab.valueobjects.Adres;

@Repository
class FiliaalDAOImpl implements FiliaalDAO {
	private final Logger logger = LoggerFactory.getLogger(FiliaalDAOImpl.class);
	private final Map<Long, Filiaal> filialen = new ConcurrentHashMap<>();

	public FiliaalDAOImpl() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			filialen.put(1L, new Filiaal(1L, "Andros", true,
				new BigDecimal(1000), dateFormat.parse("2009-01-31"), 
				new Adres("Keizerslaan", "11", 1000, "Brussel")));
			filialen.put(2L, new Filiaal(2L, "Delos", false, 
				new BigDecimal(2000), dateFormat.parse("2009-02-28"), 
				new Adres("Gasthuisstraat", "31", 1000, "Brussel")));
			filialen.put(3L, new Filiaal(3L, "Gavdos", false, 
				new BigDecimal(3000), dateFormat.parse("2009-03-31"), 
				new Adres("Koestraat", "44", 9700, "Oudenaarde")));
		} catch (ParseException ex) {
			logger.error("Verkeerde datum ingebruikname");
		}
	}

	@Override
	public void create(Filiaal filiaal) {
		// simulatie autonummering: 
		//	nr. nieuwe filiaal = grootste nr. alle filialen + 1
		filiaal.setId(Collections.max(filialen.keySet()) + 1);
		filialen.put(filiaal.getId(), filiaal);
	}

	@Override
	public Filiaal read(long id) {
		return filialen.get(id);
	}

	@Override
	public void update(Filiaal filiaal) {
		filialen.put(filiaal.getId(), filiaal);
	}

	@Override
	public void delete(long id) {
		filialen.remove(id);
	}

	@Override
	public Iterable<Filiaal> findAll() {
		return filialen.values();
	}

	@Override
	public Iterable<Filiaal> findByPostcodeBetween(int van, int tot) {
		List<Filiaal> vanTotFilialen = new ArrayList<>();
		for (Filiaal filiaal : filialen.values()) {
			int postcode = filiaal.getAdres().getPostcode();
			if (postcode >= van && postcode <= tot) {
				vanTotFilialen.add(filiaal);
			}
		}
		return vanTotFilialen;
	}

	@Override
	public long findAantalFilialen() {
		return filialen.size();
	}

}
