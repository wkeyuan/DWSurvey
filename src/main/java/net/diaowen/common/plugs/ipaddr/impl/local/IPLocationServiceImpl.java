package net.diaowen.common.plugs.ipaddr.impl.local;

import com.alibaba.fastjson.JSON;
import net.diaowen.common.plugs.ipaddr.IPLocation;
import net.diaowen.common.plugs.ipaddr.IPLocationService;
import net.diaowen.common.plugs.ipaddr.impl.local.qqwry.IPv4;
import net.diaowen.common.plugs.ipaddr.impl.local.qqwry.IPv4Loc;
import net.diaowen.common.plugs.ipaddr.impl.local.qqwry.IPv4Locator;
import net.diaowen.common.plugs.ipaddr.impl.local.qqwry.IPv4LocatorFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IPLocationServiceImpl implements IPLocationService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    //    private final String filePath = "conf/ip/qqwry.dat";
    private final String filePath = "/dwfile/resource/ip/qqwry.dat";

    private IPv4Locator locator;

    @PostConstruct
    public void init() {
        ApplicationHome applicationHome =new ApplicationHome();
        String homeDirPath = applicationHome.getDir().getPath();
        String ipFilePath = homeDirPath+filePath;
        try{
            IPv4LocatorFactoryBean fb = new IPv4LocatorFactoryBean();
//            fb.setFile(new File(filePath));
//            fb.setFile(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+filePath));
            logger.info("ipFilePath:"+ipFilePath);
            fb.setFile(new File(ipFilePath));
            fb.afterPropertiesSet();
            this.locator = fb.getObject();
        }catch (Exception e){
//            e.printStackTrace();
            logger.warn("没有IP数据文件，请确认"+ipFilePath+"有这个文件");
        }
    }

    @Override
    public IPLocation getLocationByIp(String ip) {
        try{
            IPv4 ipv4 = IPv4.parse(ip);
            IPv4Loc loc = locator.locate(ipv4);
            String country = loc.country;
//                logger.info("loc {}", JSON.toJSONString(loc));
            //解析省市
            IPLocation  ipLocation= addressResolution(country);
            ipLocation.setIp(ip);
            ipLocation.setAddress(country);
            return ipLocation;
        }catch (Exception e){
            logger.error(" getLocationByIp {} {}",ip,e.getMessage());
        }
        return null;
    }


    public static IPLocation addressResolution(String address){
        IPLocation ipLocation = new IPLocation();
//        ("(?<province>(\\S+省|自治区)|北京市|上海市|天津市|重庆市)?(?<city>\\S+市|地区)?(?<district>\\S+[市区县])?");
//        String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)?(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)";
        String regex="(?<province>[^特别行政区]+特别行政区|[^自治区]+自治区|[^省]+省|北京市|上海市|天津市|重庆市)(?<city>.*?行政单位|省属虚拟市|市辖县|市辖区|县|自治州|[^地区]+地区|[^市]+市|[^州]+州|[^盟]+盟|)?(?<county>[^旗]+旗|[^市]+市|[^区]+区|[^县]+县)?(?<town>[^县]+县|[^区]+区|[^乡]+乡|[^村]+村|[^镇]+镇|[^街道]+街道)?(?<village>.*)";
        Matcher m = Pattern.compile(regex).matcher(address);
        String province=null, city=null, county = null, town=null, village=null;
        while (m.find()){
            province = m.group("province");
            city = m.group("city");
            county = m.group("county");
            town = m.group("town");
            village = m.group("village");
        }
        if(province==null && address.indexOf("省")>0) province = address.substring(0,address.indexOf("省")+1);
        if(city==null && address.indexOf("市")>0) city = address.substring(0,address.indexOf("市")+1);
        province = province!=null ? province: "未知";
        city = city!=null ? city: "未知";
        county = county!=null ? county: "未知";
        town = town!=null ? town: "未知";
        village = village!=null ? village: "未知";
        if (province.equals("北京市") || province.equals("上海市") || province.equals("天津市") || province.equals("重庆市")) city = province;
        ipLocation.setProvince(province);
        ipLocation.setCity(city);
        ipLocation.setCounty(county);
        ipLocation.setTown(town);
        ipLocation.setVillage(village);
        ipLocation.setAddress(address);
        return ipLocation;
    }

    public static void main(String[] args) {
        IPLocationServiceImpl ipLocationService = new IPLocationServiceImpl();
        try {
            ipLocationService.init();
            ArrayList<String> ips = new ArrayList<>();
            ips.add("58.67.160.0");
            ips.add("123.125.71.38");
            ips.add("61.235.70.98");
            ips.add("219.72.225.254");
            ips.add("202.101.103.55");
            ips.add("202.101.224.69");
            ips.add("202.99.192.66");
            for (String ip:ips) {
//              IPLocation ipLocation = ipLocationService.getLocationByIp(ip);
                System.out.println(JSON.toJSONString(ipLocationService.getLocationByIp(ip)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
