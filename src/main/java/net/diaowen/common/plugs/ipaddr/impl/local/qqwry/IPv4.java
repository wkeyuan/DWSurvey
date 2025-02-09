/**
 *
 */
package net.diaowen.common.plugs.ipaddr.impl.local.qqwry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;


/**
 * IPv4 地址
 *
 * @author liulongbiao
 *
 */
public class IPv4 implements Comparable<IPv4> {
	private static final int IP_BYTES = 4; // 字节长度
	private static final int BYTE_MASK = 0xFF;
	private static final String GRP_BYTE = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";
	private static final Pattern PAT_IPV4 = Pattern.compile("^" + GRP_BYTE + "\\." + GRP_BYTE
			+ "\\." + GRP_BYTE + "\\." + GRP_BYTE + "$");

	/**
	 * 根据四字节数组构造 IPv4 地址
	 *
	 * @param bytes
	 * @return
	 */
	public static IPv4 of(byte[] bytes) {
		return new IPv4(toUnsignedInt(bytes));
	}

	/**
	 * 解析 IPv4 地址
	 *
	 * @param address
	 * @return
	 */
	public static IPv4 parse(String address) {
		Assert.hasText(address, "ipv4 address is empty.");
		Matcher mat = PAT_IPV4.matcher(address);
		Assert.isTrue(mat.matches(), "ipv4 address is not of IPv4 format.");
		byte[] bytes = new byte[IP_BYTES];
		for(int i = 0; i < IP_BYTES; i++) {
			bytes[i] = parseUnsignedByte(mat.group(i + 1));
		}
		return IPv4.of(bytes);
	}

	private static int toUnsignedInt(byte[] bytes) {
		Assert.notNull(bytes);
		Assert.isTrue(bytes.length == IP_BYTES, "invalid ipv4 address");
		return ((((int)bytes[0] & BYTE_MASK) << 24) |
	            (((int)bytes[1] & BYTE_MASK) << 16) |
	            (((int)bytes[2] & BYTE_MASK) <<  8) |
	            (((int)bytes[3] & BYTE_MASK)      ));
	}

	private static byte parseUnsignedByte(String string) {
		Assert.hasText(string);
		int parse = Integer.parseInt(string);

		if (parse >> Byte.SIZE == 0) {
			return (byte) parse;
		} else {
			throw new NumberFormatException("out of range: " + parse);
		}
	}

	private static byte[] toBytes(int int32) {
		int val = int32;
		byte[] b4 = new byte[IP_BYTES];
		for(int i = IP_BYTES - 1; i >= 0; i--) {
			b4[i] = (byte) (val & BYTE_MASK);
			val >>>= Byte.SIZE;
		}
		return b4;
	}

	private static String toIPv4Address(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < IP_BYTES; i++) {
			if (i != 0) {
				sb.append('.');
			}
			sb.append(Byte.toUnsignedInt(bytes[i]));
		}
		return sb.toString();
	}

	private final int int32;

	private IPv4(int int32) {
		this.int32 = int32;
	}

	@Override
	public int compareTo(IPv4 that) {
		return Integer.compareUnsigned(this.int32, that.int32);
	}

	@Override
	public String toString() {
		return toIPv4Address(toBytes(this.int32));
	}
}
