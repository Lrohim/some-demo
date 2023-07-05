package entity;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

public class FeignInterceptor implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Enumeration<String> headerNames = requestAttributes.getRequest().getHeaderNames();
        while (headerNames.hasMoreElements()){
            String element = headerNames.nextElement();
            String header = requestAttributes.getRequest().getHeader(element);
            requestTemplate.header(element,header);
        }
    }
}
