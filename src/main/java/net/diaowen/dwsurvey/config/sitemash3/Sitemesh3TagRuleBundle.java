package net.diaowen.dwsurvey.config.sitemash3;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

public class Sitemesh3TagRuleBundle implements TagRuleBundle {

    public static String SITEMESH_TAG_CONTENT = "content";

    @Override
    public void install(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        state.addRule(SITEMESH_TAG_CONTENT,new ExportTagToContentRule(siteMeshContext,contentProperty.getChild(SITEMESH_TAG_CONTENT),false));
    }

    @Override
    public void cleanUp(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        if (!(contentProperty.getChild(SITEMESH_TAG_CONTENT)).hasValue()) {
            (contentProperty.getChild(SITEMESH_TAG_CONTENT)).setValue(contentProperty.getValue());
        }
    }
}
