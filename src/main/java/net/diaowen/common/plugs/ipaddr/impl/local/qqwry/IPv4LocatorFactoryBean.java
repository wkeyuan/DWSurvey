/**
 *
 */
package net.diaowen.common.plugs.ipaddr.impl.local.qqwry;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * IPv4Locator 工厂 Bean
 *
 * @author liulongbiao
 *
 */
public class IPv4LocatorFactoryBean implements FactoryBean<IPv4Locator>, InitializingBean {
	private static final int IP_RECORD_LENGTH = 7; // 索引记录长度，4 字节起始IP，3字节位置偏移
	private static final byte REDIRECT_MODE_1 = 0x01;
	private static final byte REDIRECT_MODE_2 = 0x02;
	public static final Charset GBK = Charset.forName("GBK");

	private byte[] b4 = new byte[4];
	private byte[] buf = new byte[32];

	private Resource datFile;
	private File file;
	private MappedByteBuffer mbb;
	private ArrayList<IPv4Entry> entries;
	private IPv4Locator locator;

	public void setDatFile(Resource datFile) {
		this.datFile = datFile;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isTrue(file.exists(), "ip dat file not exist!");
		try (RandomAccessFile raf = new RandomAccessFile(file, "r");
				FileChannel fc = raf.getChannel()) {
			MappedByteBuffer mbb = fc.map(MapMode.READ_ONLY, 0, file.length());
			mbb.order(ByteOrder.LITTLE_ENDIAN);
			this.mbb = mbb;
			readIPv4Entries();
			this.locator = new IPv4Locator(this.entries);
		}
	}

	private void readIPv4Entries() {
		int start = mbb.getInt();
		int end = mbb.getInt();
		if (start == -1 || end == -1) {
			entries = new ArrayList<>(0);
			return;
		}
		entries = new ArrayList<>((end - start) / IP_RECORD_LENGTH);

		for (int offset = start; offset <= end; offset += IP_RECORD_LENGTH) {
			mbb.position(offset);
			IPv4Entry entry = readIPv4Entry();
			if (entry != null) {
				entries.add(entry);
			}
		}
	}

	private IPv4Entry readIPv4Entry() {
		IPv4 start = readIPv4();
		int locOffset = readInt3();
		if (locOffset == -1) {
			return null;
		}
		mbb.position(locOffset);
		IPv4 end = readIPv4();
		IPv4Loc loc = readIPv4Loc(mbb);
		return new IPv4Entry(start, end, loc);
	}

	private IPv4 readIPv4() {
		mbb.get(b4);
		reverse(b4);
		return IPv4.of(b4);
	}

	private static void reverse(byte[] arr) {
		int size = arr.length;
		for (int i = 0, mid = size >> 1, j = size - 1; i < mid; i++, j--) {
			byte tmp = arr[i];
			arr[i] = arr[j];
			arr[j] = tmp;
		}
	}

	private int readInt3() {
		mbb.get(b4, 0, 3);
		return ((((int) b4[2] & 0xFF) << 16) | (((int) b4[1] & 0xFF) << 8) | (((int) b4[0] & 0xFF)));
	}

	private IPv4Loc readIPv4Loc(MappedByteBuffer mbb) {
		String country = null;
		String area = null;

		int endIPPos = mbb.position();

		byte flag = mbb.get();
		if (flag == REDIRECT_MODE_1) {
			int countryOffset = readInt3();
			mbb.position(countryOffset);
			flag = mbb.get();
			if (flag == REDIRECT_MODE_2) {
				mbb.position(readInt3());
				country = readString();
				mbb.position(countryOffset + 4);
				area = readArea();
			} else {
				mbb.position(countryOffset);
				country = readString();
				area = readArea();
			}
		} else if (flag == REDIRECT_MODE_2) {
			mbb.position(readInt3());
			country = readString();
			mbb.position(endIPPos + 4);
			area = readArea();
		} else {
			mbb.position(mbb.position() - 1);
			country = readString();
			area = readArea();
		}

		return new IPv4Loc(country, area);
	}

	private String readArea() {
		byte b = mbb.get();
		if (b == REDIRECT_MODE_1 || b == REDIRECT_MODE_2) {
			int areaOffset = readInt3();
			if (areaOffset == 0) {
				return "";
			} else {
				mbb.position(areaOffset);
				return readString();
			}
		} else {
			mbb.position(mbb.position() - 1);
			return readString();
		}
	}

	private String readString() {
		int count = 0;
		byte b;
		do {
			b = mbb.get();
			ensureCapacity(count + 1);
			buf[count] = b;
			count++;
		} while (b != 0);
		return count <= 1 ? "" : new String(buf, 0, count - 1, GBK);
	}

	private void ensureCapacity(int minCapacity) {
		if (minCapacity <= buf.length) {
			return;
		}

		int newCapacity = Math.max(buf.length * 2 + 2, minCapacity);
		buf = Arrays.copyOf(buf, newCapacity);
	}

	@Override
	public IPv4Locator getObject() throws Exception {
		return locator;
	}

	@Override
	public Class<?> getObjectType() {
		return IPv4Locator.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
