package net.diaowen.common.plugs.ipaddr.impl.local;

import net.diaowen.common.plugs.ipaddr.IPLocation;
import net.diaowen.common.plugs.ipaddr.IPLocationService;
import org.springframework.stereotype.Service;

@Service
public class IpLocationServiceImpl implements IPLocationService {

    @Override
    public IPLocation getLocationByIp(String ip) {
        IPLocation ipLocation = new IPLocation();
        return ipLocation;
    }
}
