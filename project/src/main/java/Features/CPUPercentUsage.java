package Features;

import BuildingBlocks.DataTimestamp;

/**
 * Created by jorl17 on 18/07/15.
 */
public class CPUPercentUsage extends Feature {
    private double usage;

    public CPUPercentUsage(DataTimestamp time, double usage) {
        super(time);
        this.usage = usage;
    }

    public CPUPercentUsage(double usage) {
        super();
        this.usage = usage;
    }

    @Override
    public String getFeatureName() {
        return "CPU Usage";
    }

    @Override
    public String getUnits() {
        return "%";
    }

    @Override
    public String getRepresentation() {
        return "" + usage;
    }
}
