package net.diaowen.common.plugs.json.jackson.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

public class MyRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    protected Logger logger = LoggerFactory.getLogger(getClass());

}
