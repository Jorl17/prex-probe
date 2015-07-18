import DataSamplers.CPUPercentUsageSampler;
import DataSamplers.MemorySampler;
import Util.Utils;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class Main {

    public static void main(String[] args) throws SigarException {
        Sigar sigar = new Sigar();
        CPUPercentUsageSampler cpuPercentSampler = new CPUPercentUsageSampler(sigar);
        MemorySampler memorySampler = new MemorySampler(sigar);

        // Test that the CPU combined usage works
        for (int i = 0; i < 60000; i++) {
            cpuPercentSampler.sample();
            memorySampler.sample();
            System.out.println(cpuPercentSampler.getProvidedFeatures());
            System.out.println(memorySampler.getProvidedFeatures());
            Utils.sleepNoInterrupt(100);
        }
    }
}
