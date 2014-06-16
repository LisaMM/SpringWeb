package be.vdab.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import be.vdab.entities.Werknemer;

public interface WerknemerDAO extends JpaRepository<Werknemer, Long> {

    public Iterable<Werknemer> findMetHoogsteWedde();
	//Iterable<Werknemer> findMetHoogsteWedde();
}
