package be.vdab.web;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.services.FiliaalService;

@Controller
@RequestMapping("/filialen")
class FiliaalController {
	private final Logger logger = 
		LoggerFactory.getLogger(FiliaalController.class);
	private final FiliaalService filiaalService;
	
	@Autowired
	public FiliaalController(FiliaalService filiaalService) {
		this.filiaalService = filiaalService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView findAll() {
		return new ModelAndView("filialen/filialen", 
			"filialen", filiaalService.findAll());
	}
	
	@RequestMapping(value = "toevoegen", method = RequestMethod.GET)
	public String createForm() {
		return "filialen/toevoegen";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String create() {
		logger.info("filiaal record toevoegen aan database");
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "id")
	public ModelAndView read(@RequestParam long id) {
		return new ModelAndView("filialen/filiaal", "filiaal", 
			filiaalService.read(id));
	}
}
