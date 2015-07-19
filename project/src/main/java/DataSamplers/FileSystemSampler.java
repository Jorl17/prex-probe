package DataSamplers;

import Features.Data;
import Features.PercentValue;
import org.hyperic.sigar.*;

/**
 * Created by jorl17 on 18/07/15.
 */
public class FileSystemSampler extends SimpleSamplerAdapter {
    public FileSystemSampler(Sigar sigar) {
        super(sigar);
    }

    @Override
    protected void sampleData() {
        FileSystemUsage usage;
        try {
            for (FileSystem f : sigar.getFileSystemList()) {
                usage = sigar.getFileSystemUsage(f.getDirName());
                addFeature(new Data("# Reads (" + f.getDirName() + ")", "physical disk reads", usage.getDiskReads()));
                addFeature(new Data("# Writes (" + f.getDirName() + ")", "physical disk writes", usage.getDiskWrites()));
                addFeature(new Data("Reads (" + f.getDirName() + ")", "B", usage.getDiskReads()));
                addFeature(new Data("Writes (" + f.getDirName() + ")", "B", usage.getDiskWrites()));
                addFeature(new Data("# Files (" + f.getDirName() + ")", "File nodes", usage.getFiles()));
                addFeature(new Data("Free (" + f.getDirName() + ")", "KBytes", usage.getFree()));
                addFeature(new Data("Total (" + f.getDirName() + ")", "KBytes", usage.getTotal()));
                addFeature(new PercentValue("Used (" + f.getDirName() + ")", usage.getUsePercent()));
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }


    }
}
