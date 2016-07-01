package Features;

import BuildingBlocks.DataTimestamp;

/**
 * Created by jorl17 on 18/07/15.
 */
public class Memory extends Feature {
    private long mem;
    private String memoryType;
    private String unit;

    @Override
    public String getFeatureName() {
        return memoryType + " (mem)";
    }

    @Override
    public String getRepresentation() {
        return "" + mem;
    }

    @Override
    public String getUnits() {
        return unit;
    }

    public Memory(DataTimestamp time, String memoryType, String unit, long mem) {
        super(time);
        this.memoryType = memoryType;
        this.unit = unit;
        this.mem = mem;
    }

    public Memory(String memoryType, String unit, long mem) {
        super();
        this.memoryType = memoryType;
        this.unit = unit;
        this.mem = mem;
    }

    @Override
    public float asFloat() {
        return (float) mem;
    }
}
