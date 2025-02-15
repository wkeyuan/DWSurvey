package net.diaowen.dwsurvey.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.PageResult;
import net.diaowen.common.plugs.httpclient.ResultUtils;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.plugs.security.FormAuthenticationWithLockFilter;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Controller
@RequestMapping("/api/dwsurvey/admin/user")
@Api(tags = "用户管理")
public class UserAdminController {

    @Autowired
    private UserManager userManager;
    @Autowired
    private FormAuthenticationWithLockFilter formAuthFilter;

    @RequestMapping(value = "/list.do",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageSize",value="页数据数",paramType = "query",dataType = "java.lang.Integer"),
            @ApiImplicitParam(name="current",value="当前页码",paramType = "query",dataType = "java.lang.Integer"),
    })
    public PageResult<User> list(@ApiIgnore PageResult<User> pageResult, Integer status, String loginName, String name, String email, String cellphone) {
        Page page = ResultUtils.getPageByPageResult(pageResult);
        page = userManager.findPage(page, status, loginName, name, email, cellphone);
        pageResult = ResultUtils.getPageResultByPage(page,pageResult);
        return pageResult;
    }

    /**
     * 添加
     * @param user
     * @return
     */
    @RequestMapping(value = "/add.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加用户")
    public HttpResult add(@RequestBody User user) {
        try{
//            if("demo".equals(DWSurveyConfig.DWSURVEY_SITE)) return HttpResult.FAILURE("DEMO环境不允许操作");
            User result = userManager.adminSave(user);
            if(result!=null) return HttpResult.SUCCESS();
        }catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.FAILURE();
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @RequestMapping(value = "/edit.do",method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "修改用户信息")
    public HttpResult up(@RequestBody User user) {
        try{
            if("demo".equals(DWSurveyConfig.DWSURVEY_SITE)) return HttpResult.FAILURE("DEMO环境不允许操作");
            HttpResult httpResult = userManager.upData(user);
            return httpResult;
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.FAILURE(e.getMessage());
        }
    }

    /**
     * 修改分组
     * @return
     */
    @RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除用户")
    public HttpResult delete(@RequestBody Map<String, String[]> map) {
        try{
            if("demo".equals(DWSurveyConfig.DWSURVEY_SITE)) return HttpResult.FAILURE("DEMO环境不允许操作");
            if(map!=null){
                if(map.containsKey("id")){
                    String[] ids = map.get("id");
                    if(ids!=null){
                        userManager.deleteData(ids);
                    }
                }
            }
            return HttpResult.SUCCESS();
        }catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.FAILURE();
    }


    /**
     * 修改
     * @param user
     * @return
     */
    @RequestMapping(value = "/reset-login.do",method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "重置登录状态")
    public HttpResult resetLogin(@RequestBody User user) {
        try{
            formAuthFilter.resetAccountLock(user.getLoginName());
            return HttpResult.SUCCESS();
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.FAILURE(e.getMessage());
        }
    }

}
