package be.vdab.services;

import be.vdab.dao.FiliaalDAO;
import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalMetDezeNaamBestaatAlException;
import be.vdab.valueobjects.Adres;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.*;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;

public class FiliaalServiceImplTest {
	private FiliaalService filiaalService;
	private Filiaal filiaal;
	
	@Before
	public void before() {
		filiaal = new Filiaal("TestNaam", true, BigDecimal.ONE, new Date(),
			new Adres("Straat", "HuisNr", 1000, "Gemeente"));
		FiliaalDAO filiaalDAO = Mockito.mock(FiliaalDAO.class);
		Mockito.when(filiaalDAO.findByNaam(filiaal.getNaam())).thenReturn(filiaal);
		filiaalService = new FiliaalServiceImpl(filiaalDAO, 
                    Mockito.mock(JavaMailSender.class));
	}
	
	@Test(expected = FiliaalMetDezeNaamBestaatAlException.class)
	public void createWerptFiliaalMetDezeNaamException() {
		filiaalService.create(filiaal);
	}
}
