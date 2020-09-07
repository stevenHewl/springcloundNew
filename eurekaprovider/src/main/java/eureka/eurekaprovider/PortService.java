package eureka.eurekaprovider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider/")
public class PortService {
 
    @Value("${server.port}")
    String port;
 
    @RequestMapping("port")
    public String getPort(String msg) {
        return "Hello " + msg + ", I'm from port : " + port;
    }
}