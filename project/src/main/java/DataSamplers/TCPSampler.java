package DataSamplers;

import Features.Data;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Tcp;

/**
 * Created by jorl17 on 21/07/15.
 */
public class TCPSampler extends SimpleSamplerAdapter {
    private Tcp tcp;
    public TCPSampler(Sigar sigar) {
        super(sigar);
        try {
            tcp = sigar.getTcp();
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void sampleData() {
        try {
            tcp.gather(sigar);
        } catch (SigarException e) {
            e.printStackTrace();
        }
        addFeature(new Data("TCP Active Opens", "opens", tcp.getActiveOpens()));
        addFeature(new Data("TCP Passive Opens", "opens", tcp.getPassiveOpens()));
        addFeature(new Data("TCP Attempt Fails", "fails", tcp.getAttemptFails()));
        addFeature(new Data("TCP Current Established", "connections", tcp.getCurrEstab()));
        addFeature(new Data("TCP In Errors", "errors", tcp.getInErrs()));
        addFeature(new Data("TCP In Segments", "segments", tcp.getInSegs()));
        addFeature(new Data("TCP Out Segs", "segments", tcp.getOutSegs()));
        addFeature(new Data("TCP Out Rsts", "rsts (?)", tcp.getOutRsts()));
        addFeature(new Data("TCP Retrans  Segs", "segments", tcp.getOutRsts()));
    }
}
