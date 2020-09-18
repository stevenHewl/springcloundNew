package eureka.eurekazuul.filiter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/*
 Zuul 允许开发者在 API 网关上通过定义过滤器来实现对请求的拦截与过滤，我们只需继承 ZuulFilter 抽象类并实现它定义的 4 个 抽象函数
 就可以完成对请求的拦截和过滤了。Zuul 定义了下面四种过滤器
 前置（Pre）
 路由（Route）
 后置（Post）
 错误（Error）
 * */

@Component
public class PreFilter extends ZuulFilter {

    private static Logger log =  Logger.getLogger(PreFilter.class);

    // 过滤器的类型，它决定过滤器在请求的哪个生命周期中执行。这里定义为 pre，代表会在请求被路由之前执行。
    @Override
    public String filterType() {
        return PRE_TYPE; // 过滤器类型 : pre
    }

    // 过滤器的执行顺序。当请求在一个阶段中存在多个过滤器时，需要根据该方法返回值来依次执行。
    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 5; // 过滤器执行顺序，数字越小，优先级越高，越靠前,这里设置为 0
    }

    // 判断该过滤器是否需要被执行。这里直接返回了 true，因此该过滤器对所有请求都会生效。
    // 实际运用中可以利用该函数来指定过滤器的有效范围。
    @Override
    public boolean shouldFilter() {
        return true; // 返回 true，拦截所有 URL
    }

    /*
     * 过滤器的具体逻辑 在请求被路由之前检查 HttpServletRequest 中是否有 token 参数，若有就进行路由，
     * 若没有就拒绝访问，返回401Unauthorized 错误
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        log.info(String.format("send {%s} request to {%s}", request.getMethod(), request.getRequestURL().toString()));

        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            log.warn("token is empty");
            requestContext.setSendZuulResponse(false); // 令 Zuul 过滤该请求，不对其进行路由
            // 设置了其返回的错误码，
            // 当然也可以对返回的结果进行优化，比如，通过 requestContext.setResponseBody(body) 对返回的 body 内容进行编辑等。
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            try {
                requestContext.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }
        log.info("token is ok");
        log.error("err info: this is test filebeat");
        return null;
    }
}
