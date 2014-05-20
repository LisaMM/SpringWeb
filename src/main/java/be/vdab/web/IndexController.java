package be.vdab.web;

import java.util.Locale;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import be.vdab.services.FiliaalService;

@Controller
@RequestMapping("/")
class IndexController {
	private final FiliaalService filiaalService;
	private final LocaleResolver localeResolver;
	
	@Autowired
	public IndexController(FiliaalService filiaalService,
		LocaleResolver localeResolver) {
		this.filiaalService = filiaalService;
		this.localeResolver = localeResolver;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("aantalFilialen", 
			filiaalService.findAantalFilialen());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, params = {"locale"})
	public String index(HttpServletRequest request, 
		HttpServletResponse response, @RequestParam String locale) {
		String[] onderdelen = locale.split("_");
		localeResolver.setLocale(request, response, new Locale(
			onderdelen[0], onderdelen[1]));
		return "redirect:/";
	}
}
