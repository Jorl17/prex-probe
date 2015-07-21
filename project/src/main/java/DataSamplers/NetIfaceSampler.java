package DataSamplers;

import Features.Data;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Created by jorl17 on 21/07/15.
 */
public class NetIfaceSampler extends SimpleSamplerAdapter {
    private NetInterfaceStat[] ifaceList;
    private String[] ifaceNames;
    public NetIfaceSampler(Sigar sigar) {
        super(sigar);

        // Store now so changes at run-time don't screw us over
        try {
            ifaceNames = sigar.getNetInterfaceList();
            ifaceList = new NetInterfaceStat[ifaceNames.length];
            for (int i = 0; i < ifaceNames.length; i++)
                ifaceList[i] = sigar.getNetInterfaceStat(ifaceNames[i]);
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void sampleData() {
        assert(ifaceList != null);

        for (int i = 0; i < ifaceList.length; i++) {
            NetInterfaceStat iface = ifaceList[i];
            String ifaceName = ifaceNames[i];
            addFeature(new Data("Iface RX Bytes (" + ifaceName + ")", "B", iface.getRxBytes()));
            addFeature(new Data("Iface RX Dropped (" + ifaceName + ")", "packets", iface.getRxDropped()));
            addFeature(new Data("Iface RX Errors (" + ifaceName + ")", "errors", iface.getRxErrors()));
            addFeature(new Data("Iface RX Overruns (" + ifaceName + ")", "overruns", iface.getRxOverruns()));
            addFeature(new Data("Iface RX Packets (" + ifaceName + ")", "packets", iface.getRxPackets()));
            addFeature(new Data("Iface TX Bytes (" + ifaceName + ")", "B", iface.getTxBytes()));
            addFeature(new Data("Iface TX Dropped (" + ifaceName + ")", "packets", iface.getTxDropped()));
            addFeature(new Data("Iface TX Errors (" + ifaceName + ")", "errors", iface.getTxErrors()));
            addFeature(new Data("Iface TX Overruns (" + ifaceName + ")", "overruns", iface.getTxOverruns()));
            addFeature(new Data("Iface TX Collisions (" + ifaceName + ")", "collisions", iface.getTxCollisions()));
            addFeature(new Data("Iface TX Carrier (" + ifaceName + ")", "", iface.getTxCarrier()));
            addFeature(new Data("Iface Speed (" + ifaceName + ")", "B/s (?)", iface.getSpeed()));
        }
    }
}
