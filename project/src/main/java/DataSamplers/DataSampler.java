package DataSamplers;

import Features.Feature;
import org.hyperic.sigar.Sigar;

/**
 * Created by jorl17 on 18/07/15.
 */
public abstract class DataSampler<F extends Feature> {
    protected F feature;
    protected final Sigar sigar;

    public DataSampler(Sigar sigar) {
        assert(sigar != null);
        this.sigar = sigar;
    }

    public DataSampler() {
        this(new Sigar());
    }

    public abstract void start();
    public abstract void stop();
    public abstract void sample();

    public F getFeature() {
        return feature;
    }
}
