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
        
        addFeature(new PercentValue("CPU Combined Usage", cpuPerc.getCombined()));
        addFeature(new PercentValue("CPU Idle", cpuPerc.getIdle()));
        addFeature(new PercentValue("CPU Nice", cpuPerc.getNice()));
        addFeature(new PercentValue("CPU Soft IRQ", cpuPerc.getSoftIrq()));
        addFeature(new PercentValue("CPU Stolen", cpuPerc.getStolen()));
        addFeature(new PercentValue("CPU Wait", cpuPerc.getWait()));
        addFeature(new PercentValue("CPU User", cpuPerc.getUser()));
        addFeature(new PercentValue("CPU Sys", cpuPerc.getSys()));
        addFeature(new Data("CPU Idle time", "1e-4 seconds", cpu.getIdle()));
        addFeature(new Data("CPU Nice time", "1e-4seconds", cpu.getNice()));
        addFeature(new Data("CPU Soft IRQ time", "1e-4 seconds", cpu.getSoftIrq()));
        addFeature(new Data("CPU Stolen time", "1e-4 seconds", cpu.getStolen()));
        addFeature(new Data("CPU Wait time", "1e-4 seconds", cpu.getWait()));
        addFeature(new Data("CPU User time", "1e-4 seconds", cpu.getUser()));
        addFeature(new Data("CPU Sys time", "1e-4 seconds", cpu.getSys()));
        addFeature(new Data("CPU Total time", "1e-4 seconds", cpu.getTotal()));
        addFeature(new Data("CPU IRQ", "interrupts", cpu.getIrq()));
    }
}
