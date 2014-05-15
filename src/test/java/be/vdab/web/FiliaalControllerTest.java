package be.vdab.web;

import java.util.Collections;

import org.junit.*;
import org.mockito.Mockito;

import be.vdab.entities.Filiaal;
import be.vdab.services.FiliaalService;

public class FiliaalControllerTest {
	private FiliaalController filiaalController;
	private Iterable<Filiaal> filialen;
	private FiliaalService filiaalService;
	
	@Before
	public void setUp() {
		filialen = Collections.emptyList();
		filiaalService = Mockito.mock(FiliaalService.class);
		Mockito.when(filiaalService.findAll()).thenReturn(filialen);
		filiaalController = new FiliaalController(filiaalService);
	}
	
	@Test
	public void findAllActiveertJuisteView() {
		Assert.assertEquals("filialen/filialen", 
			filiaalController.findAll().getViewName());
	}
	
	@Test
	public void findAllMaaktRequestAttribuutFilialen() {
		Assert.assertSame(filialen, 
			filiaalController.findAll().getModelMap().get("filialen"));
	}
}
