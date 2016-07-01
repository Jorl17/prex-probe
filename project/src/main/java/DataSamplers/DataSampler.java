package DataSamplers;

import Features.Feature;
import org.hyperic.sigar.Sigar;

import java.util.ArrayList;

/**
 * Created by jorl17 on 18/07/15.
 */
// Generic DataSampler that delegates sampling responsibility down the hierarchy
// The addFeature and addFeatures methods provide a quick way to feed the value of the features to the DataSampler
//
// Thus, the DataSampler is an aggregator for features that pertain some common subject. Note that it does not force
// features to have matching timestamps. This is done in MultiFeatureSampler.
public abstract class DataSampler {
    private ArrayList<Feature> features;
    protected final Sigar sigar;

    public DataSampler(Sigar sigar) {
        assert(sigar != null);
        this.sigar = sigar;
        this.features = new ArrayList<>();
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
    protected void addFeatures(ArrayList<Feature> f) {
        this.features.addAll(f);
    }

    public ArrayList<Feature> getProvidedFeatures() {
        return features;
    }
}
