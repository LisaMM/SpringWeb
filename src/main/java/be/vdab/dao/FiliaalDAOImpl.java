package be.vdab.dao;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import be.vdab.entities.Filiaal;
import be.vdab.valueobjects.Adres;

@Repository
class FiliaalDAOImpl implements FiliaalDAO {
	private final Map<Long, Filiaal> filialen = new ConcurrentHashMap<>();
	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final FiliaalRowMapper filiaalRowMapper = new FiliaalRowMapper();
	private static final String SQL_FIND_ALL =
			"select id, naam, hoofdFiliaal, straat, huisNr, postcode, gemeente," +
			"inGebruikName, waardeGebouw from filialen order by naam";
	private static final String SQL_FIND_BY_POSTCODE =
			"select id, naam, hoofdFiliaal, straat, huisNr, postcode, gemeente, " +
			"inGebruikName, waardeGebouw from filialen" +
			" where postcode between :van and :tot order by naam";
	private static final String SQL_READ =
			"select id, naam, hoofdFiliaal, straat, huisNr, postcode, gemeente," +
			"inGebruikName, waardeGebouw from filialen where id = ?";

	@Autowired
	public FiliaalDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
		try {
			return jdbcTemplate.queryForObject(SQL_READ, filiaalRowMapper, id);
		} catch (IncorrectResultSizeDataAccessException ex) {
			return null; //record niet gevonden
		}
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
		return jdbcTemplate.query(SQL_FIND_ALL, filiaalRowMapper);
	}

	@Override
	public Iterable<Filiaal> findByPostcodeBetween(int van, int tot) {
		Map<String, Integer> parameters = new HashMap<>();
		parameters.put("van", van);
		parameters.put("tot", tot);
		return namedParameterJdbcTemplate.query(
				SQL_FIND_BY_POSTCODE, parameters, filiaalRowMapper);
	}

	@Override
	public long findAantalFilialen() {
		return filialen.size();
	}

	private static class FiliaalRowMapper implements RowMapper<Filiaal> {
		@Override
		public Filiaal mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new Filiaal(
					resultSet.getLong("id"), 
					resultSet.getString("naam"),
					resultSet.getBoolean("hoofdFiliaal"),
					resultSet.getBigDecimal("waardeGebouw"),
					resultSet.getDate("inGebruikName"),
					new Adres(
							resultSet.getString("straat"),
							resultSet.getString("huisNr"),
							resultSet.getInt("postcode"),
							resultSet.getString("gemeente")));
		}
		
	}
}
