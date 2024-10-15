/**
 *
 */
package net.diaowen.common.plugs.ipaddr.impl.local.qqwry;

import java.util.ArrayList;

import org.springframework.util.Assert;

/**
 * IPv4 地址定位器
 *
 * @author liulongbiao
 *
 */
public class IPv4Locator {

	private final ArrayList<IPv4Entry> entries; // 有序的IP地址范围条目

	public IPv4Locator(ArrayList<IPv4Entry> entries) {
		Assert.notNull(entries);
		this.entries = entries;
	}

	/**
	 * 根据 IPv4 地址字符串查找对应的地址和运营商信息
	 *
	 * @param ipv4Address
	 * @return
	 */
	public IPv4Loc locate(String ipv4Address) {
		return locate(IPv4.parse(ipv4Address));
	}

	/**
	 * 找到 IP 所对应的地址和运营商信息
	 *
	 * @param ip
	 * @return
	 */
	public IPv4Loc locate(IPv4 ip) {
		Assert.notNull(ip);
		int idx = binarySearch(entries, ip);
		return idx >= 0 ? entries.get(idx).loc : IPv4Loc.UNKNOWN;
	}

	/*
	 * 二叉搜索包含该IP的IP范围条目索引值
	 */
	private static int binarySearch(ArrayList<IPv4Entry> entries, IPv4 ip) {
		int low = 0;
		int high = entries.size() - 1;

		while (low <= high) {
			int mid = (low + high) >>> 1;
			IPv4Entry midEntry = entries.get(mid);

			if (ip.compareTo(midEntry.begin) < 0) {
				high = mid - 1;
			} else if (ip.compareTo(midEntry.end) > 0) {
				low = mid + 1;
			} else {
				return mid; // key found
			}
		}
		return -(low + 1); // key not found
	}
}
