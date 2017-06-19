package com.key.common.plugs.email;

import com.key.common.base.entity.User;

public interface MailService {
    
    //发送邮件
    public boolean sendMail(String to,String title,String content);
    //发送注册激活邮件
    public boolean sendRegisterMailByAsync(User user);
    //发送找回密码邮件
    public boolean sendFindPwdMailByAsync(User user);
    //发送邀请邮件
    public String sendSurveyInviteMail(final String dwSubject,final String substitutionVars);
    
}
