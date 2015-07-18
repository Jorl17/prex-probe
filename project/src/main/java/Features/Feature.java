package Features;

import BuildingBlocks.DataTimestamp;

/**
 * Created by jorl17 on 17/07/15.
 */
public abstract class Feature {
    private DataTimestamp timestamp;

    public abstract String getFeatureName();
    public abstract String getUnits();
    public abstract String getRepresentation();

    public Feature(DataTimestamp time) {
        this.timestamp = time;
    }

    public Feature() {
        this(new DataTimestamp()); //Initialized to current time
    }

    public DataTimestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DataTimestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "(" + timestamp + ", " + getFeatureName() + ", " + getRepresentation() + " " + getUnits() + ")";
    }
}
