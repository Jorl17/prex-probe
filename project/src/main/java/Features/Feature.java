package Features;

import BuildingBlocks.DataTimestamp;

/**
 * Created by jorl17 on 17/07/15.
 */
public abstract class Feature {
    private DataTimestamp timestamp;

    public abstract String getRepresentation();

    public Feature() {
        this.timestamp = new DataTimestamp(); //Initialized to current time
    }

    public DataTimestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DataTimestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "(" + timestamp + ", " + getRepresentation() + ")";
    }
}
