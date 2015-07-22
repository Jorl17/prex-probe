package DataSamplers;

import Features.Data;
import Features.Feature;
import Features.Memory;
import Features.PercentValue;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

/**
 * Created by jorl17 on 18/07/15.
 */
public class MemorySampler extends SimpleSamplerAdapter {
    private Mem mem;
    private Swap swap;
    public MemorySampler(Sigar sigar) {
        super(sigar);
        try {
            mem = this.sigar.getMem();
            swap = this.sigar.getSwap();
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void sampleData() {
        try {
            mem.gather(sigar);
            swap.gather(sigar);
        } catch (SigarException e) {
            e.printStackTrace();
        }
        //addFeature(new Memory("RAM", "MB", mem.getRam()));
        addFeature(new Memory("Used", "B", mem.getUsed()));
        addFeature(new Memory("ActualUsed", "B",  mem.getActualUsed()));
        addFeature(new Memory("Free", "B",  mem.getFree()));
        addFeature(new Memory("ActualFree", "B",  mem.getActualFree()));
        addFeature(new PercentValue("Percent Free",  mem.getFreePercent()/100.0f));
        addFeature(new PercentValue("Percent Used",  mem.getUsedPercent()/100.0f));
        addFeature(new Memory("Free Swap", "B", swap.getFree()));
        addFeature(new Memory("Used Swap", "B", swap.getUsed()));
        addFeature(new Memory("Total Swap", "B", swap.getTotal()));
        addFeature(new Data("Pages in", "pages", swap.getPageIn()));
        addFeature(new Data("Pages out", "pages", swap.getPageOut()));
    }
}
