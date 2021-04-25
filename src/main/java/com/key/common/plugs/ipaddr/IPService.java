package com.key.common.plugs.ipaddr;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IPService {
	@Autowired
	private IPSeeker ipSeeker;

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
//				e.printStackTrace();
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

	public String getCountry(String ip) {
		if(ip==null){
			return "";
		}
		return ipSeeker.getCountry(ip);
	}

	/**
	 * 根据IP，查出此ip所在的城市
	 *
	 * @param ip
	 * @return
	 */
	public String getCurCity(String ip) {
		String city = ipSeeker.getCountry(ip);
		city = city.replaceAll("\\S+省|市.*|[内蒙古|广西]", "");
		city = city.replaceAll("清华大学.*", "北京");
		return city;
	}

	/**
	 * 根据 getCountry(ip);得到的地址得到城市
	 *
	 * @param country
	 * @return
	 */
	public String getCurCityByCountry(String country) {
		// 省..市
		// ..市
		// ..省..市..
		// ..省..
		// ..省
		// 清华大学=北京
		// 内蒙古..市
		// 内蒙古..
		// 广西..市
		String city = country;
		city = city.replaceAll("\\S+省|市.*|[内蒙古|广西]", "");
		city = city.replaceAll("清华大学.*", "北京");
		return city;
	}

	/**
	 * 根据 request对象得到数据请求者所在的城市
	 *
	 * @param request
	 * @return
	 */
	public String getCurCity(HttpServletRequest request) {
		String ip = getIp(request);
		// ip="202.106.46.151";
		String city = ipSeeker.getCountry(ip);
		city = city.replaceAll("\\S+省|市.*|[内蒙古|广西]", "");
		city = city.replaceAll("清华大学.*", "北京");
		return city;
	}

	public static void main(String[] args) {
		String ip="111.206.20.59, 123.151.42.46, 121.42.17.215，";
		System.out.println(ip.substring(0,ip.indexOf(",")));
	}
}
