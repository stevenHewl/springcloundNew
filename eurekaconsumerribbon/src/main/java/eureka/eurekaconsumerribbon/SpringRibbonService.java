package eureka.eurekaconsumerribbon;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
spring-ribbon-service 只提供一个客服端负载均衡作用，轮流调用其它服务的多个实例，本身并不提供功能服务。

通过spring-cloud-zipkin 进行链路追踪查看
 * */
@Service
public class SpringRibbonService {
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	LoadBalancerClient loadBalancerClient;

	/*
	 * restTemplate 调用服务接口的方法不是仅此一种，API 大致如下，具体怎么选择应用，可以查看相关 API 文档， 我们直接使用
	 * getForObject 第一种，在这里我们直接用的程序名替代了具体的 url 地址， 在 Ribbon中它会根据服务名来选择具体的服务实例，根据服务实例在请求的时候会用具体的 url 替换掉服务名
	 */
	// 在 port 方法上加上 @HystrixCommand 注解，对此方法开启熔断器功能，用 fallbackMethod 属性指定熔断回调方法。
	@HystrixCommand(fallbackMethod = "portFallback")
	public String port(String msg) {
		this.loadBalancerClient.choose("eureka-provider"); // 随机访问策略
		return restTemplate.getForObject("http://eureka-provider/provider/port?msg=" + msg, String.class);
	}

	public String portFallback(String msg) {
		return "sorry " + msg +", call eureka-provider error!";
	}
}
