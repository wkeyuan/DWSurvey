/**
 *
 */
package net.diaowen.common.plugs.ipaddr.impl.local.qqwry;

import org.springframework.core.io.ClassPathResource;

/**
 * @author liulongbiao
 *
 */
public class IPv4LocatorTest {

	private IPv4Locator locator;

	public void init() throws Exception {
		ClassPathResource datFile = new ClassPathResource("data/qqwry.dat");
		IPv4LocatorFactoryBean fb = new IPv4LocatorFactoryBean();
		fb.setDatFile(datFile);
		fb.afterPropertiesSet();
		this.locator = fb.getObject();
	}

	public void testLocate() {
		IPv4 ip = IPv4.parse("39.184.174.243");
		IPv4Loc loc = locator.locate(ip);
	}

}
