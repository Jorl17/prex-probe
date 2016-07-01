package Features;

import BuildingBlocks.DataTimestamp;

/**
 * Created by jorl17 on 17/07/15.
 */
// An association of timestamp, feature name, units and a representation
// There can be several generic features. For instance, several features can be of type "Data", pertaining time or quantities

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

    public abstract float asFloat();
}
