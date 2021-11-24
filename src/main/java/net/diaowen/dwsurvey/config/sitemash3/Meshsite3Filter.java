package net.diaowen.dwsurvey.config.sitemash3;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class Meshsite3Filter extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/sy/**", "/sitemesh/main")//拦截规则该路径会被转发
                .addExcludedPath("/static/**")                              //白名单
                .addTagRuleBundle(new Sitemesh3TagRuleBundle());       //自定义标签


    }
}
