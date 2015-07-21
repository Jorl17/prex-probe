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
                addFeature(new Data("FS # Reads (" + f.getDirName() + ")", "physical disk reads", usage.getDiskReads()));
                addFeature(new Data("FS # Writes (" + f.getDirName() + ")", "physical disk writes", usage.getDiskWrites()));
                addFeature(new Data("FS Reads (" + f.getDirName() + ")", "B", usage.getDiskReads()));
                addFeature(new Data("FS Writes (" + f.getDirName() + ")", "B", usage.getDiskWrites()));
                addFeature(new Data("FS # Files (" + f.getDirName() + ")", "File nodes", usage.getFiles()));
                addFeature(new Data("FS Free (" + f.getDirName() + ")", "KBytes", usage.getFree()));
                addFeature(new Data("FS Total (" + f.getDirName() + ")", "KBytes", usage.getTotal()));
                addFeature(new PercentValue("FS Used (" + f.getDirName() + ")", usage.getUsePercent()));
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }


    }
}
