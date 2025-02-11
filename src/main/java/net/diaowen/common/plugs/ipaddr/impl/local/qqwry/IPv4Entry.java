/**
 *
 */
package net.diaowen.common.plugs.ipaddr.impl.local.qqwry;

import org.springframework.util.Assert;

/**
 * IPv4 范围条目
 *
 * @author liulongbiao
 *
 */
public class IPv4Entry {
	public final IPv4 begin;
	public final IPv4 end;
	public final IPv4Loc loc;

	public IPv4Entry(IPv4 begin, IPv4 end, IPv4Loc loc) {
		super();
		this.begin = begin;
		this.end = end;
		this.loc = loc;
	}

	@Override
	public String toString() {
		return "[" + begin.toString() + "~" + end.toString() + "](" + loc.toString() + ")";
	}

	public boolean contains(IPv4 ip) {
		Assert.notNull(ip);
		return begin.compareTo(ip) <= 0 && end.compareTo(ip) >= 0;
	}
}
