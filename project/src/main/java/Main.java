import DataSamplers.CPUSampler;
import DataSamplers.FileSystemSampler;
import DataSamplers.MemorySampler;
import DataSamplers.TCPSampler;
import Features.Feature;
import Util.Utils;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SigarException {
        Sigar sigar = new Sigar();
        CPUSampler cpuPercentSampler = new CPUSampler(sigar);
        MemorySampler memorySampler = new MemorySampler(sigar);
        FileSystemSampler diskSampler = new FileSystemSampler(sigar);
        TCPSampler tcp = new TCPSampler(sigar);

        // Test that the CPU combined usage works
        for (int i = 0; i < 60000; i++) {
            ArrayList<Feature> allFeatures = new ArrayList<Feature>();
            cpuPercentSampler.sample();
            memorySampler.sample();
            diskSampler.sample();
            tcp.sample();
            allFeatures.addAll(cpuPercentSampler.getProvidedFeatures());
            allFeatures.addAll(memorySampler.getProvidedFeatures());
            allFeatures.addAll(diskSampler.getProvidedFeatures());
            allFeatures.addAll(tcp.getProvidedFeatures());
            for ( Feature f : allFeatures)
                System.out.print(f.getFeatureName() + ": " + f.getRepresentation() + " " + f.getUnits() + ", ");
            System.out.println();
            Utils.sleepNoInterrupt(1000);
        }
    }
}
