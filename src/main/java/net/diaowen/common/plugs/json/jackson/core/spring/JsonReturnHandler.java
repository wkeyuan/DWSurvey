package net.diaowen.common.plugs.json.jackson.core.spring;

import net.diaowen.common.plugs.json.jackson.core.CustomerJsonSerializer;
import net.diaowen.common.plugs.json.jackson.core.JSON;
import net.diaowen.common.plugs.json.jackson.core.JSONS;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;


public class JsonReturnHandler implements HandlerMethodReturnValueHandler{

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        // 如果有我们自定义的 JSON 注解 就用我们这个Handler 来处理
        boolean hasJsonAnno= returnType.getMethodAnnotation(JSON.class) != null;
        if(!hasJsonAnno){
            hasJsonAnno= returnType.getMethodAnnotation(JSONS.class) != null;
        }
        /*
        Annotation[] annos = returnType.getMethodAnnotations();
        Arrays.asList(annos).forEach(a -> { // 解析注解，设置过滤条件
            System.out.println("a.annotationType()a.annotationType()a.annotationType()a.annotationType()a.annotationType()a.annotationType():"+a.annotationType());
            if (a instanceof JSON) {
                JSON json = (JSON) a;
                System.out.println("JSONJSONJSONJSONJSONJSONJSONJSONJSONJSJSONJSONJSONJSONONJSONJSON"+json.type()+":"+json.include());
            } else if (a instanceof JSONS) { // 使用多重注解时，实际返回的是 @Repeatable(JSONS.class) 内指定的 @JSONS 注解
                JSONS jsons = (JSONS) a;
                Arrays.asList(jsons.value()).forEach(json -> {
                    System.out.println("JSONSJSONSJSONSJSONSJSONSJSONSJSONSJSONSJSONS---JSONSJSONSJSONS"+json.type()+":"+json.include());
                });
            }
        });
        System.out.println("hasJsonAnnohasJsonAnnohasJsonAnnohasJsonAnnohasJsonAnnohasJsonAnnohasJsonAnno:"+hasJsonAnno);
         */
        return hasJsonAnno;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest) throws Exception {
        // 设置这个就是最终的处理类了，处理完不再去找下一个类进行处理
        mavContainer.setRequestHandled(true);

        // 获得注解并执行filter方法 最后返回
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Annotation[] annos = returnType.getMethodAnnotations();
        CustomerJsonSerializer jsonSerializer = new CustomerJsonSerializer();
        Arrays.asList(annos).forEach(a -> { // 解析注解，设置过滤条件
            if (a instanceof JSON) {
                JSON json = (JSON) a;
                jsonSerializer.filter(json);
            } else if (a instanceof JSONS) { // 使用多重注解时，实际返回的是 @Repeatable(JSONS.class) 内指定的 @JSONS 注解
                JSONS jsons = (JSONS) a;
                Arrays.asList(jsons.value()).forEach(json -> {
                    jsonSerializer.filter(json);
                });
            }
        });

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String json = jsonSerializer.toJson(returnValue);
        response.getWriter().write(json);
    }
}
