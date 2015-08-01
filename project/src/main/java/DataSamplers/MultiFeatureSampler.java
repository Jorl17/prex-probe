package DataSamplers;

import DataSamplers.DataSampler;
import DataSamplers.SimpleSamplerAdapter;
import org.hyperic.sigar.Sigar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 01/08/15.
 */
public class MultiFeatureSampler extends SimpleSamplerAdapter {
    private ArrayList<DataSampler> samplers;

    public MultiFeatureSampler(Sigar sigar, ArrayList<DataSampler> samplers) {
        super(sigar);
        this.samplers = samplers;
    }

    public MultiFeatureSampler(Sigar sigar, DataSampler... samplers) {
        super(sigar);
        this.samplers = new ArrayList<DataSampler>();
        Collections.addAll(this.samplers, samplers);
    }

    @Override
    protected void sampleData() {
        for ( DataSampler sampler : samplers ) {
            sampler.sample();
            addFeatures(sampler.getProvidedFeatures());
        }
    }

}
