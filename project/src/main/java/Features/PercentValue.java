package Features;

import BuildingBlocks.DataTimestamp;

/**
 * Created by jorl17 on 18/07/15.
 */

// Value is in range 0-1. Only gets converted to 0-100% for display purposes
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

    @Override
    public float asFloat() {
        return (float) value;
    }
}
