package be.vdab.web;

import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/leningen")
class LeningController {
	private final Logger logger = 
		LoggerFactory.getLogger(LeningController.class);

	@RequestMapping(value = "toevoegen", method = RequestMethod.GET)
	public String createForm1() {
		return "leningen/toevoegen1";
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "van1naar2")
	public String createForm1naar2() {
		return "leningen/toevoegen2";
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "van2naar1")
	public String createForm2naar1() {
		return "leningen/toevoegen1";
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "bevestigen")
	public String create() {
		logger.info("Lening record toevoegen aan de database");
		return "redirect:/";
	}
}
