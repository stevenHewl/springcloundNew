package eureka.eurekaconsumerribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer/")
public class consumerController {

	@Autowired
	SpringRibbonService springRibbonService;

	@RequestMapping(value = "port", method = RequestMethod.GET)
	public String port(String msg) {
		return springRibbonService.port(msg) + ", from rebbonService.";
	}
}
