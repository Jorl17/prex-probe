package DataSamplers;

import Features.Data;
import Features.PercentValue;
import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Created by jorl17 on 18/07/15.
 */
public class CPUSampler extends SimpleSamplerAdapter {
    private CpuPerc cpuPerc;
    private Cpu cpu;
    public CPUSampler(Sigar sigar) {
        super(sigar);
        try {
            cpu = sigar.getCpu();
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void sampleData() {
        try {
            cpuPerc = this.sigar.getCpuPerc();
            cpu.gather(sigar);
        } catch (SigarException e) {
            e.printStackTrace();
        }

        addFeature(new PercentValue("CPU Usage", cpuPerc.getCombined()));
        addFeature(new Data("CPU Idle time", "s", cpu.getIdle()));
        addFeature(new Data("CPU Nice time", "s", cpu.getNice()));
        addFeature(new Data("CPU Soft IRQ time", "s", cpu.getSoftIrq()));
        addFeature(new Data("CPU Stolen time", "s", cpu.getStolen()));
        addFeature(new Data("CPU Wait time", "s", cpu.getWait()));
        addFeature(new Data("CPU User time", "s", cpu.getUser()));
        addFeature(new Data("CPU Sys time", "s", cpu.getSys()));
        addFeature(new Data("CPU Total time", "s", cpu.getTotal()));
        addFeature(new Data("CPU IRQ", "interrupts", cpu.getIrq()));
    }
}
