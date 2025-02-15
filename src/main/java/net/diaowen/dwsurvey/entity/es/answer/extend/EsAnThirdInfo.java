package net.diaowen.dwsurvey.entity.es.answer.extend;

/***
 * 外部答卷信息
 */
public class EsAnThirdInfo {

    //外部参数字段
    private String op;
    //第三方平台答题用户标识
    private String anUserKey;

    //第三方平台标记的 编辑用户ID
    private String thirdEditUid;
    //第三方平台标记的 编辑用户姓名
    private String thirdEditUname;
    //第三方平台标记的 编辑用户ID 是否经过认证 0 或 null 经过认证（通过session会话获取）, 1 未经过认证（通过request获取）
    private Integer thirdEditAuth;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getAnUserKey() {
        return anUserKey;
    }

    public void setAnUserKey(String anUserKey) {
        this.anUserKey = anUserKey;
    }

    public String getThirdEditUid() {
        return thirdEditUid;
    }

    public void setThirdEditUid(String thirdEditUid) {
        this.thirdEditUid = thirdEditUid;
    }

    public String getThirdEditUname() {
        return thirdEditUname;
    }

    public void setThirdEditUname(String thirdEditUname) {
        this.thirdEditUname = thirdEditUname;
    }

    public Integer getThirdEditAuth() {
        return thirdEditAuth;
    }

    public void setThirdEditAuth(Integer thirdEditAuth) {
        this.thirdEditAuth = thirdEditAuth;
    }
}
