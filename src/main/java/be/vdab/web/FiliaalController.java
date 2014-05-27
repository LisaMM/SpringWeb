package be.vdab.web;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import be.vdab.entities.Filiaal;
import be.vdab.services.FiliaalService;

@Controller
@RequestMapping("/filialen")
class FiliaalController {
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
	public ModelAndView createForm() {
		return new ModelAndView("filialen/toevoegen", "filiaal", new Filiaal());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Filiaal filiaal, BindingResult bindingResult) {
		if (! bindingResult.hasErrors()) {
			filiaalService.create(filiaal);
			return "redirect:/";
		}
		return "filialen/toevoegen";
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "id")
	public ModelAndView read(@RequestParam long id) {
		return new ModelAndView("filialen/filiaal", "filiaal", 
			filiaalService.read(id));
	}
	
	@RequestMapping(value = "verwijderen", method = RequestMethod.POST, params = "id")
	public String delete(@RequestParam long id, RedirectAttributes redirectAttributes) {
		Filiaal filiaal = filiaalService.read(id);
		if (filiaal == null) {
			return "redirect:/";
		}
		filiaalService.delete(id);
		redirectAttributes.addAttribute("id", id);
		redirectAttributes.addAttribute("naam", filiaal.getNaam());
		return("redirect:/filialen/verwijderd");
	}
	
	@RequestMapping(value = "verwijderd", method = RequestMethod.GET, params = {"id", "naam"})
	public String deleted() {
		return "filialen/verwijderd";
	}
	
	@RequestMapping(value = "vantotpostcode", method = RequestMethod.GET)
	public ModelAndView findByPostcodeForm() {
		return new ModelAndView("filialen/vantotpostcode", 
			"vanTotPostcodeForm", new VanTotPostcodeForm());
	}
	
	@RequestMapping(method = RequestMethod.GET, params = {"vanpostcode", "totpostcode"})
	public ModelAndView findByPostcodeBetween(
			@Valid VanTotPostcodeForm vanTotPostcodeForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("filialen/vantotpostcode");
		if (! bindingResult.hasErrors() && ! vanTotPostcodeForm.isValid()) {
			bindingResult.reject("fouteVanTotPostcode", new Object [] {
				vanTotPostcodeForm.getVanpostcode(),
				vanTotPostcodeForm.getTotpostcode()}, "");
		}
		if (! bindingResult.hasErrors()) {
			modelAndView.addObject("filialen", filiaalService.findByPostcodeBetween(
					vanTotPostcodeForm.getVanpostcode(), vanTotPostcodeForm.getTotpostcode()));
		}
		return modelAndView;
	}
	
	@InitBinder("vanTotPostcodeForm")
	public void initBinderVanTotPostcodeForm(DataBinder dataBinder) {
		dataBinder.initDirectFieldAccess();
	}
	
	@InitBinder("filiaal")
	public void initBinderFiliaal(DataBinder dataBinder) {
		Filiaal filiaal = (Filiaal) dataBinder.getTarget();
		if (filiaal.getAdres() == null) {
			filiaal.setAdres(new AdresForm());
		} else {
			filiaal.setAdres(new AdresForm(filiaal.getAdres()));
		}
	}
	
	@RequestMapping(value = "wijzigen", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam long id) {
		Filiaal filiaal = filiaalService.read(id);
		if (filiaal == null) {
			return new ModelAndView("redirect:/filialen");
		}
		return new ModelAndView("filialen/wijzigen", "filiaal", filiaal);
	}
	
	@RequestMapping(value = "wijzigen", method = RequestMethod.POST)
	public String update(@Valid Filiaal filiaal, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "filialen/wijzigen";
		}
		filiaalService.update(filiaal);
		return "redirect:/";
	}
}
