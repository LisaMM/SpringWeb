package be.vdab.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import be.vdab.services.FiliaalService;

@Controller
@RequestMapping("/")
class IndexController {
	private final FiliaalService filiaalService;
	
	@Autowired
	public IndexController(FiliaalService filiaalService) {
		this.filiaalService = filiaalService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("aantalFilialen", 
			filiaalService.findAantalFilialen());
		return modelAndView;
	}
}
