package Features;

import BuildingBlocks.DataTimestamp;

/**
 * Created by jorl17 on 18/07/15.
 */

//
public class Data extends Feature {
    private long data;
    private String dataType;
    private String unit;

    @Override
    public String getFeatureName() {
        return dataType + " (data)";
    }

    @Override
    public String getRepresentation() {
        return "" + data;
    }

    @Override
    public String getUnits() {
        return unit;
    }

    public Data(DataTimestamp time, String dataType, String unit, long data) {
        super(time);
        this.dataType = dataType;
        this.unit = unit;
        this.data = data;
    }

    public Data(String dataType, String unit, long data) {
        super();
        this.dataType = dataType;
        this.unit = unit;
        this.data = data;
    }

    @Override
    public float asFloat() {
        return (float) data;
    }
}
