package DataSamplers;

import Features.Feature;
import org.hyperic.sigar.Sigar;

/**
 * Created by jorl17 on 18/07/15.
 */

// Simple adapter that does nothing on startup or shutdown
public abstract class SimpleSamplerAdapter extends DataSampler {
    public SimpleSamplerAdapter(Sigar sigar) {
        super(sigar);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }
}
