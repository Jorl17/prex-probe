package DataSamplers;

import Features.Feature;
import Features.Memory;
import Features.PercentValue;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Created by jorl17 on 18/07/15.
 */
public class MemorySampler extends SimpleSamplerAdapter {
    private Mem mem;
    public MemorySampler(Sigar sigar) {
        super(sigar);
        try {
            mem = this.sigar.getMem();
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void sampleData() {
        try {
            mem.gather(sigar);
        } catch (SigarException e) {
            e.printStackTrace();
        }
        //addFeature(new Memory("RAM", "MB", mem.getRam()));
        addFeature(new Memory("Used", "B", mem.getUsed()));
        addFeature(new Memory("ActualUsed", "B",  mem.getActualUsed()));
        addFeature(new Memory("Free", "B",  mem.getFree()));
        addFeature(new Memory("ActualFree", "B",  mem.getActualFree()));
        addFeature(new PercentValue("Percent Free",  mem.getFreePercent()));
        addFeature(new PercentValue("Percent Used",  mem.getUsedPercent()));
    }
}
