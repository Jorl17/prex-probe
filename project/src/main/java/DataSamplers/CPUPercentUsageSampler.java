package DataSamplers;

import Features.CPUPercentUsage;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Created by jorl17 on 18/07/15.
 */
public class CPUPercentUsageSampler<F> extends SimpleSamplerAdapter<CPUPercentUsage> {
    private CpuPerc cpuPerc;
    public CPUPercentUsageSampler(Sigar sigar) {
        super(sigar);
    }

    @Override
    public void sample() {
        try {
            cpuPerc = this.sigar.getCpuPerc();
        } catch (SigarException e) {
            e.printStackTrace();
        }
        this.feature = new CPUPercentUsage(cpuPerc.getCombined());
    }
}
