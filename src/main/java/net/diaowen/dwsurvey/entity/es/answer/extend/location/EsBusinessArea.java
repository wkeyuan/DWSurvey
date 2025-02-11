package net.diaowen.dwsurvey.entity.es.answer.extend.location;

public class EsBusinessArea {

    //商圈id
    private String  id;
    //商圈名称
    private String  name;
    //商圈中心点经纬度
    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
