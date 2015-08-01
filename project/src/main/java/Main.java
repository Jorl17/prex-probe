import DataSamplers.*;
import Features.Feature;
import Util.Utils;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SigarException {
        Sigar sigar = new Sigar();
        MultiFeatureSampler fullSampler = new MultiFeatureSampler(sigar, new CPUSampler(sigar), new MemorySampler(sigar), new FileSystemSampler(sigar), new TCPSampler(sigar), new NetIfaceSampler(sigar));

        // Test that the CPU combined usage works
        for (int i = 0; i < 60000; i++) {
            fullSampler.sample();
            ArrayList<Feature> allFeatures = fullSampler.getProvidedFeatures();
            for ( Feature f : allFeatures )
                //System.out.print(f.getFeatureName() + ": " + f.getRepresentation() + " " + f.getUnits() + ", ");
                System.out.print(f.getRepresentation() + ", ");
            System.out.println();
            Utils.sleepNoInterrupt(1000);
        }
    }
}
