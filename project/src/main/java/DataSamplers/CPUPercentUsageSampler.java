package DataSamplers;

import Features.PercentValue;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Created by jorl17 on 18/07/15.
 */
public class CPUPercentUsageSampler extends SimpleSamplerAdapter {
    private CpuPerc cpuPerc;
    public CPUPercentUsageSampler(Sigar sigar) {
        super(sigar);
    }

    @Override
    protected void sampleData() {
        try {
            cpuPerc = this.sigar.getCpuPerc();
        } catch (SigarException e) {
            e.printStackTrace();
        }
        addFeature(new PercentValue("CPU", cpuPerc.getCombined()));
    }
}
