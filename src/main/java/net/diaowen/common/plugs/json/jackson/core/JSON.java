package net.diaowen.common.plugs.json.jackson.core;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(JSONS.class)   // 让方法支持多重@JSON 注解
public @interface JSON {
    Class<?> type();
    String include() default "";
    String filter() default "";
    //如果有多个JSON注解时，只有在是后一个JSON设置有效，因为这个是全局配置
    JsonInclude.Include jsonInclue() default JsonInclude.Include.ALWAYS;
}
