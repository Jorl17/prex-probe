package Features;

import BuildingBlocks.DataTimestamp;

/**
 * Created by jorl17 on 18/07/15.
 */
public class PercentValue extends Feature {
    private double usage;
    private String featureName;

    public PercentValue(DataTimestamp time, String featureName, double usage) {
        super(time);
        this.featureName = featureName;
        this.usage = usage;
    }

    public PercentValue(String featureName, double usage) {
        super();
        this.featureName = featureName;
        this.usage = usage;
    }

    @Override
    public String getFeatureName() {
        return featureName;
    }

    @Override
    public String getUnits() {
        return "%";
    }

    @Override
    public String getRepresentation() {
        return "" + usage*100.0f;
    }
}
