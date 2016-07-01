package DataSamplers;

import BuildingBlocks.DataTimestamp;
import Features.Feature;
import org.hyperic.sigar.Sigar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 01/08/15.
 */
// This uses the composite pattern to merge several samplers into one big sample. E.g. if we want to sample CPU and Memory
// without manually "merging" everything into the same timestamp
public class MultiFeatureSampler extends SimpleSamplerAdapter {
    private ArrayList<DataSampler> samplers;
    private boolean forceMatchingTimestamps;

    public MultiFeatureSampler(Sigar sigar, boolean forceMatchingTimestamps, ArrayList<DataSampler> samplers) {
        super(sigar);
        this.samplers = samplers;
        this.forceMatchingTimestamps = forceMatchingTimestamps;
    }
    public MultiFeatureSampler(Sigar sigar, ArrayList<DataSampler> samplers) {
        this(sigar, true, samplers);
    }

    public MultiFeatureSampler(Sigar sigar, boolean forceMatchingTimestamps, DataSampler... samplers) {
        super(sigar);
        this.samplers = new ArrayList<>();
        Collections.addAll(this.samplers, samplers);
        this.forceMatchingTimestamps = forceMatchingTimestamps;
    }

    public MultiFeatureSampler(Sigar sigar, DataSampler... samplers) {
        this(sigar, true, samplers);
    }

    public void addSampler(DataSampler sampler) {
        this.samplers.add(sampler);
    }

    @Override
    protected void sampleData() {
        DataTimestamp now = new DataTimestamp();
        for ( DataSampler sampler : samplers ) {
            sampler.sample();
            ArrayList<Feature> providedFeatures = sampler.getProvidedFeatures();

            if ( forceMatchingTimestamps )
                for ( Feature f : providedFeatures )
                    f.setTimestamp(now);

            addFeatures(providedFeatures);
        }
    }

}
