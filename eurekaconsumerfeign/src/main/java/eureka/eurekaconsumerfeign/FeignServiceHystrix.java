package eureka.eurekaconsumerfeign;

import org.springframework.stereotype.Component;

/**
 * 创建SpringFeignService中指定的 FeignServiceHystrix 类，实现 FeignService 接口，注入容器中
 */
@Component
public class FeignServiceHystrix implements FeignService {
    @Override
    public String port(String msg) {
		return "sorry, feign service call test service error!";
    }
}
