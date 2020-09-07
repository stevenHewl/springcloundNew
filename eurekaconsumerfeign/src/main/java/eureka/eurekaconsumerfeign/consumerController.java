package eureka.eurekaconsumerfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer/")
public class consumerController {

	@Autowired
	FeignService feignService;

	@RequestMapping(value = "port", method = RequestMethod.GET)
	public String portFeign(String msg) {
		return feignService.port(msg) + ", from feginService";
	}
}
