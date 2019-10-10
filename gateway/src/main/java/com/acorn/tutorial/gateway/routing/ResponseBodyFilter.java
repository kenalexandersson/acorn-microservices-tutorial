package com.acorn.tutorial.gateway.routing;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class ResponseBodyFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseBodyFilter.class);

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext requestContext = RequestContext.getCurrentContext();
        try (final InputStream responseDataStream = requestContext.getResponseDataStream()) {
            final String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));

            String line = String.format("Response body: %s", responseData);
            LOGGER.info(line);

            requestContext.setResponseBody(responseData);
        } catch (IOException e) {
            LOGGER.error("Error reading response body", e);
        }

        return null;
    }
}
