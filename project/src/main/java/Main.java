import BuildingBlocks.DataPoint;
import DataLoggers.CsvLogger;
import DataLoggers.DataLogger;
import DataLoggers.StdoutAndCsvLogger;
import DataLoggers.StdoutLogger;
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
        DataLogger logger = new StdoutAndCsvLogger("test.log");
        Runtime.getRuntime().addShutdownHook(new Thread(logger::cleanup));
        MultiFeatureSampler fullSampler = new MultiFeatureSampler(sigar, new CPUSampler(sigar), new MemorySampler(sigar), new FileSystemSampler(sigar), new TCPSampler(sigar), new NetIfaceSampler(sigar));

        fullSampler.sample();
        ArrayList<Feature> allFeatures = fullSampler.getProvidedFeatures();
        logger.logHeader(new DataPoint(allFeatures));
        for (int i = 0; i < 60000; i++) {
            fullSampler.sample();
            allFeatures = fullSampler.getProvidedFeatures();
            logger.log(new DataPoint(allFeatures));
            //for ( Feature f : allFeatures )
                //System.out.print(f.getFeatureName() + ": " + f.getRepresentation() + " " + f.getUnits() + ", ");
                //System.out.print(f.getRepresentation() + ", ");
            //System.out.println();
            Utils.sleepNoInterrupt(100);
        }
    }
}
