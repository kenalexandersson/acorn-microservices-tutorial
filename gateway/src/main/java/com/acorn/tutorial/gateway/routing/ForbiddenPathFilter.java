package com.acorn.tutorial.gateway.routing;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@Component
public class ForbiddenPathFilter extends ZuulFilter {

    @Override
    public String filterType() {
        /*
         * The filter type decides when in the routing cycle the filter triggers.
         * - PRE_TYPE: filters are executed before the request is routed
         * - ROUTE_TYPE: route filters can handle the actual routing of the request
         * - POST_TYPE: filters are executed after the request has been routed
         * - ERROR_TYPE: filters execute if an error occurs in the course of handling the request
         */
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // filter order decides where in the chain of Spring's predefined Zuul filters this filter should be placed.
        // You can access http://localhost:20202/actuator/filters to see the filters in effect,
        // We want to access the serviceId, which is populated by the inbuilt pre-decoration filter, so this filter must execute after that
        return PRE_DECORATION_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {
        // This decides if the filter should be executed in the current context
        String serviceId = (String) RequestContext.getCurrentContext().get(SERVICE_ID_KEY);
        return !isAllowedService(serviceId);
    }

    @Override
    public Object run() {

        // This method is only executed if shouldFilter() returns true

        // Halt the process and return 404
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.unset();
        requestContext.getResponse().setContentType("text/html");
        requestContext.setResponseStatusCode(404);
        requestContext.setSendZuulResponse(false);

        return null;
    }

    private boolean isAllowedService(String serviceId) {
        List<String> allowedServices = Collections.singletonList("webapi");
        return serviceId != null && allowedServices.contains(serviceId);
    }
}
