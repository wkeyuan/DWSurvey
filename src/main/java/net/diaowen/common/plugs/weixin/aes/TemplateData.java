package net.diaowen.common.plugs.weixin.aes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TemplateData {

    private String touser;
    private String template_id;
    private String url;
    private String topcolor;
    private TemplateItem data;

    public static TemplateData New() {
        return new TemplateData();
    }

    private TemplateData() {
        this.data = new TemplateItem();
    }

    public String getTouser() {
        return touser;
    }

    public TemplateData setTouser(String touser) {
        this.touser = touser;
        return this;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public TemplateData setTemplate_id(String template_id) {
        this.template_id = template_id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public TemplateData setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public TemplateData setTopcolor(String topcolor) {
        this.topcolor = topcolor;
        return this;
    }

    public TemplateItem getData() {
        return data;
    }

    public TemplateData add(String key, String value, String color){
        data.put(key, new Item(value, color));
        return this;
    }

    /**
     * 直接转化成jsonString
     * @return {String}
     */
    public String build() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String userJson = mapper.writeValueAsString(this);
            return userJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class TemplateItem extends HashMap<String, Item> {

        private static final long serialVersionUID = -3728490424738325020L;

        public TemplateItem() {}

        public TemplateItem(String key, Item item) {
            this.put(key, item);
        }
    }

    public class Item {
        private Object value;
        private String color;

        public Object getValue() {
            return value;
        }
        public void setValue(Object value) {
            this.value = value;
        }
        public String getColor() {
            return color;
        }
        public void setColor(String color) {
            this.color = color;
        }

        public Item(Object value, String color) {
            this.value = value;
            this.color = color;
        }
    }


    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        TemplateData templateData = TemplateData.New();
        templateData.setTouser("xxxxxxxx");
        templateData.setTemplate_id("ygyUQ13JdYPMk9dV9_VZwyKzIRny38Qd82iLJ5AyIgQ");
//        templateData.setUrl("");
        templateData.setTopcolor("#07c160");
        templateData.add("first","您好，您的帐号 ABC 被登录","#353535");
        templateData.add("time",simpleDateFormat.format(new Date()),"#353535");
        templateData.add("ip","132.89.22.3","#353535");
        templateData.add("reason","备注：如果本次登录不是您本人所为，说明您的帐号已经被盗！为减少您的损失，请联系我们处理。","#353535");

    }
}
