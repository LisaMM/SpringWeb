package be.vdab.dao;

import be.vdab.entities.Werknemer;

public interface WerknemerDAO {
	Iterable<Werknemer> findAll();
}
