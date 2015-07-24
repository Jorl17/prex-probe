package Features;

import BuildingBlocks.DataTimestamp;

/**
 * Created by jorl17 on 18/07/15.
 */
public class PercentValue extends Feature {
    private double value;
    private String featureName;

    public PercentValue(DataTimestamp time, String featureName, double value) {
        super(time);
        this.featureName = featureName;
        this.value = value;
    }

    public PercentValue(String featureName, double value) {
        super();
        this.featureName = featureName;
        this.value = value;
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
        return "" + value *100.0f;
    }
}
