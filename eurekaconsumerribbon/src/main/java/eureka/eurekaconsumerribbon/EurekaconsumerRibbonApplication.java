package eureka.eurekaconsumerribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableHystrixDashboard // 开启hystrix-dashboard对此组件进行监控
public class EurekaconsumerRibbonApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaconsumerRibbonApplication.class, args);
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public IRule ribbonRule() {
		return new RandomRule(); // 这里配置策略，和配置文件对应
		// return new RoundRobinRule(); //轮询
	}
}
