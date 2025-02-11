/**
 *
 */
package net.diaowen.common.plugs.ipaddr.impl.local.qqwry;

/**
 * IP 地址
 *
 * @author liulongbiao
 *
 */
public class IPv4Loc {

	/**
	 * 未知网络常量
	 */
	public static final IPv4Loc UNKNOWN = new IPv4Loc("未知网络", "");

	public final String country;
	public final String area;

	public IPv4Loc(String country, String area) {
		this.country = locString(country);
		this.area = locString(area);
	}

	@Override
	public String toString() {
		return country + " " + area;
	}

	private static String locString(String str) {
		return (str == null || str.trim().equals("CZ88.NET")) ? "本地网络" : str;
	}
}
