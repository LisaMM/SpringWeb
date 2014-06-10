package be.vdab.web;

import java.io.*;
import java.net.URI;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.validation.Valid;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import be.vdab.entities.Filiaal;
import be.vdab.exceptions.*;
import be.vdab.rest.*;
import be.vdab.services.FiliaalService;

@Controller
@RequestMapping("/filialen")
class FiliaalController {
	private final FiliaalService filiaalService;
	private final ServletContext servletContext;
	private final Logger logger = LoggerFactory
			.getLogger(FiliaalController.class);

	@Autowired
	public FiliaalController(FiliaalService filiaalService,
			ServletContext servletContext) {
		this.filiaalService = filiaalService;
		this.servletContext = servletContext;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody
	Filiaal readREST(@PathVariable long id) {
		Filiaal filiaal = filiaalService.read(id);
		if (filiaal == null) {
			throw new FiliaalNietGevondenException();
		}
		return new FiliaalREST(filiaal);
	}

	@ExceptionHandler(FiliaalNietGevondenException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody
	String filiaalNietGevonden() {
		return "filiaal bestaat niet";
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView findAll() {
		return new ModelAndView("filialen/filialen", "filialen",
				filiaalService.findAll());
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody FiliaalListREST findAllREST() {
		return new FiliaalListREST(filiaalService.findAll());
	}

	@RequestMapping(value = "toevoegen", method = RequestMethod.GET)
	public ModelAndView createForm() {
		return new ModelAndView("filialen/toevoegen", "filiaal", new Filiaal());
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Filiaal filiaal, BindingResult bindingResult,
			@RequestParam("foto") Part part) {
		if (part != null && part.getSize() != 0) {
			String contentType = part.getContentType();
			if (!"image/jpeg".equals(contentType)
					&& "image/pjpeg".equals(contentType)) {
				bindingResult.reject("fotoFout");
			}
		}
		if (!bindingResult.hasErrors()) {
			try {
				filiaalService.create(filiaal);
				if (part != null && part.getSize() != 0) {
					String filiaalFotosPad = servletContext
							.getRealPath("/images");
					part.write(filiaalFotosPad + '/' + filiaal.getId() + ".jpg");
				}
				return "redirect:/";
			} catch (IOException ex) {
				logger.error("fouten bij opslaan foto" + ex.getStackTrace());
			} catch (FiliaalMetDezeNaamBestaatAlException ex) {
				bindingResult
						.rejectValue("naam", "filiaalMetDezeNaamBestaatAl");
			}
		}
		return "filialen/toevoegen";
	}

	@RequestMapping(method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	void createREST(@RequestBody @Valid Filiaal filiaal,
			HttpServletResponse response) {
		filiaalService.create(filiaal);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("filialen/{id}").buildAndExpand(filiaal.getId()).toUri();
		response.setHeader("Location", uri.toString());
	}

	@ExceptionHandler(FiliaalMetDezeNaamBestaatAlException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody
	String filiaalMetDezeNaamBestaatAl() {
		return "filiaal met deze naam bestaat al";
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody
	String filiaalMetVerkeerdeProperties(MethodArgumentNotValidException ex) {
		StringBuffer fouten = new StringBuffer();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			fouten.append(error.getField()).append(" : ")
					.append(error.getDefaultMessage()).append("\n");
		}
		fouten.deleteCharAt(fouten.length() - 1);
		return fouten.toString();
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView read(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView("filialen/filiaal");
		Filiaal filiaal = filiaalService.read(id);
		if (filiaal != null) {
			modelAndView.addObject("filiaal", filiaal);
			String filiaalFotoPad = servletContext.getRealPath("/images") + '/'
					+ filiaal.getId() + ".jpg";
			File file = new File(filiaalFotoPad);
			modelAndView.addObject("heeftFoto", file.exists());
		}
		return modelAndView;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.TEXT_HTML_VALUE)
	public String delete(@PathVariable long id,
			RedirectAttributes redirectAttributes) {
		Filiaal filiaal = filiaalService.read(id);
		if (filiaal == null) {
			return "redirect:/";
		}
		try {
			filiaalService.delete(id);
			String filiaalFotoPad = servletContext.getRealPath("/images") + '/'
					+ filiaal.getId() + ".jpg";
			File file = new File(filiaalFotoPad);
			if (file.exists()) {
				file.delete();
			}
			redirectAttributes.addAttribute("id", id);
			redirectAttributes.addAttribute("naam", filiaal.getNaam());
			return ("redirect:/filialen/verwijderd");
		} catch (FiliaalHeeftNogWerknemersException ex) {
			redirectAttributes.addAttribute("fout",
					"Filiaal is niet verwijderd, het bevat nog werknemers");
			return "redirect:/filialen/{id}";
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	void deleteREST(@PathVariable long id) {
		Filiaal filiaal = filiaalService.read(id);
		if (filiaal == null) {
			throw new FiliaalNietGevondenException();
		}
		filiaalService.delete(id);
	}

	@ExceptionHandler(FiliaalHeeftNogWerknemersException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public @ResponseBody
	String filiaalHeeftNogWerknemers() {
		return "filiaal heeft nog werknemers";
	}

	@RequestMapping(value = "verwijderd", method = RequestMethod.GET, params = {
			"id", "naam" })
	public String deleted() {
		return "filialen/verwijderd";
	}

	@RequestMapping(value = "vantotpostcode", method = RequestMethod.GET)
	public ModelAndView findByPostcodeForm() {
		return new ModelAndView("filialen/vantotpostcode",
				"vanTotPostcodeForm", new VanTotPostcodeForm());
	}

	@RequestMapping(method = RequestMethod.GET, params = { "vanpostcode",
			"totpostcode" })
	public ModelAndView findByPostcodeBetween(
			@Valid VanTotPostcodeForm vanTotPostcodeForm,
			BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("filialen/vantotpostcode");
		if (!bindingResult.hasErrors() && !vanTotPostcodeForm.isValid()) {
			bindingResult.reject("fouteVanTotPostcode",
					new Object[] { vanTotPostcodeForm.getVanpostcode(),
							vanTotPostcodeForm.getTotpostcode() }, "");
		}
		if (!bindingResult.hasErrors()) {
			modelAndView.addObject("filialen", filiaalService
					.findByPostcodeBetween(vanTotPostcodeForm.getVanpostcode(),
							vanTotPostcodeForm.getTotpostcode()));
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

	@RequestMapping(value = "{id}/wijzigen", method = RequestMethod.GET)
	public ModelAndView updateForm(@PathVariable long id) {
		Filiaal filiaal = filiaalService.read(id);
		if (filiaal == null) {
			return new ModelAndView("redirect:/filialen");
		}
		return new ModelAndView("filialen/wijzigen", "filiaal", filiaal);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public String update(@PathVariable Long id, @Valid Filiaal filiaal,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "filialen/wijzigen";
		}
		try {
			filiaalService.update(filiaal);
			return "redirect:/";
		} catch (FiliaalMetDezeNaamBestaatAlException ex) {
			bindingResult.rejectValue("naam", "filiaalMetDezeNaamBestaatAl");
			return "filialen/wijzigen";
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody
	void updateREST(@PathVariable long id, @RequestBody @Valid Filiaal filiaal) {
		if (filiaalService.read(id) == null) {
			throw new FiliaalNietGevondenException();
		}
		filiaalService.update(filiaal);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.OPTIONS)
	public @ResponseBody
	void filiaalOptions(@PathVariable long id, HttpServletResponse response) {
		Filiaal filiaal = filiaalService.read(id);
		if (filiaal == null) {
			throw new FiliaalNietGevondenException();
		}
		String allow = "GET,PUT";
		if (filiaal.getWerknemers().isEmpty()) {
			allow += ",DELETE";
		}
		response.setHeader("Allow", allow);
	}
}
