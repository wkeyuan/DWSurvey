package net.diaowen.common.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

public class UserAgentUtils {

    public static UserAgent userAgent(HttpServletRequest request){
//        String agent=request.getHeader("User-Agent");
//        如下，我们获取了一个agent的字符串：
//        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36"
//        由此，通过User-agent-utils解析：
        String agent=request.getHeader("User-Agent");
        //解析agent字符串
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        return userAgent;
    }

    public static int[] userAgentInt(HttpServletRequest request){
        int[] result = new int[]{0,0,0};
        try{
            String agent=request.getHeader("User-Agent");
            if(agent!=null){
                UserAgent userAgentObj = UserAgent.parseUserAgentString(agent);
                Browser browser = userAgentObj.getBrowser();
                OperatingSystem operatingSystem = userAgentObj.getOperatingSystem();

                Browser browserGroup = browser.getGroup();
                OperatingSystem sysGroup = operatingSystem.getGroup();
                DeviceType deviceType = operatingSystem.getDeviceType();

                Integer sys = 0;
                if(OperatingSystem.ANDROID == sysGroup){
                    sys=1;
                }else if(OperatingSystem.WINDOWS == sysGroup ){
                    sys=2;
                }else if(OperatingSystem.IOS == sysGroup){
                    sys=3;
                }else if(OperatingSystem.MAC_OS == sysGroup || OperatingSystem.MAC_OS_X == sysGroup){
                    sys=4;
                }
                result[0] = sys;

                Integer bro = 0;
                if(browserGroup.IE == browserGroup){
                    bro=1;
                }else if(browserGroup.SAFARI == browserGroup){
                    bro=2;
                }else if(browserGroup.FIREFOX == browserGroup){
                    bro=3;
                }else if(browserGroup.OPERA == browserGroup){
                    bro=4;
                }else if(browserGroup.CHROME == browserGroup){
                    bro=5;
                }
                result[1] = bro;

                Integer dt=0;
                if(DeviceType.COMPUTER == deviceType){
                    dt=1;
                }else if(DeviceType.MOBILE == deviceType){
                    dt=2;
                }
                result[2] = dt;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
