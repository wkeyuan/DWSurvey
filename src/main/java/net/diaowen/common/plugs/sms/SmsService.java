package net.diaowen.common.plugs.sms;

public interface SmsService {

    public boolean sendCode(final String phone,final String code);

}
