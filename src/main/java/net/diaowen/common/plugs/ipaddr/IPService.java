package net.diaowen.common.plugs.ipaddr;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IPService {

	@Autowired
	private IPLocationService ipLocationService;

	public IPLocation getIpLocation(String ip) {
		if(ip==null){
			return null;
		}
		try{
			return ipLocationService.getLocationByIp(ip);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据ip取得所在地区
	 * @param ip
	 * @return
	 */
	public String getCountry(String ip) {
		if(ip==null){
			return "";
		}
		return ipLocationService.getLocationByIp(ip).getProvince();
	}

	/**
	 * 根据IP，查出此ip所在的城市
	 *
	 * @param ip
	 * @return
	 */
	public String getCurCity(String ip) {
		//空实现
		return null;
	}

	/**
	 *
	 * @param country
	 * @return
	 */
	public String getCurCityByCountry(String country) {
		return null;
	}

	public String getIp(HttpServletRequest request) {

		//Http Header:X-Forwarded-For
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
//		ip=replaceIPv6LocalIp(ip);
		ip=checkLocalIp(ip);
		if(ip!=null && ip.indexOf(",")>0){
			ip=ip.substring(0,ip.indexOf(","));
		}
		return ip;
	}

	/**
	 * 检查以localhost,127.0.0.1访问时得到真实IP
	 * @param ip
	 * @return
	 */
	public String checkLocalIp(String ip){
		if("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1%0".equals(ip)){
			try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				ip=inetAddress.getHostAddress();

				if(ip!=null && ip.length()>15){ //"***.***.***.***".length() = 15
					if(ip.indexOf(",")>0){
						ip = ip.substring(0,ip.indexOf(","));
					}
				}
			} catch (UnknownHostException e) {
//				e.printStackTrace();2019000MA6TG48K
			}
		}
		return ip;
	}

	public String replaceIPv6LocalIp(String ip){
		if("0:0:0:0:0:0:0:1".equals(ip)){
			ip="127.0.0.1";
		}
		return ip;
	}

}
