package DataSamplers;

import Features.Feature;
import org.hyperic.sigar.Sigar;

import java.util.ArrayList;

/**
 * Created by jorl17 on 18/07/15.
 */
public abstract class DataSampler {
    private ArrayList<Feature> features;
    protected final Sigar sigar;

    public DataSampler(Sigar sigar) {
        assert(sigar != null);
        this.sigar = sigar;
        this.features = new ArrayList<Feature>();
    }

/*    public DataSampler() {
        this(new Sigar());
    }
*/

    public abstract void start();
    public abstract void stop();
    protected abstract void sampleData();

    public void sample() {
        assert(this.features != null);
        this.features.clear();
        sampleData();
    }

    protected void addFeature(Feature f) {
        this.features.add(f);
    }

    public ArrayList<Feature> getProvidedFeatures() {
        return features;
    }
}
