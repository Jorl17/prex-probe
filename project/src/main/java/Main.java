import BuildingBlocks.DataTimestamp;
import DataSamplers.CPUPercentUsageSampler;
import Util.Utils;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class Main {

    public static void main(String[] args) throws SigarException {
        Sigar sigar = new Sigar();
        CPUPercentUsageSampler cpuPercentSampler = new CPUPercentUsageSampler(sigar);

        // Test that the CPU combined usage works
        for (int i = 0; i < 100; i++) {
            cpuPercentSampler.sample();
            System.out.println(cpuPercentSampler.getFeature());
            Utils.sleepNoInterrupt(100);
        }
    }
}
