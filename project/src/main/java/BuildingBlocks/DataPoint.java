package BuildingBlocks;

import Features.Feature;
import Util.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jorl17 on 17/07/15.
 */
public class DataPoint {
    private final ArrayList<Feature> features;
    private ArrayList<Object> headers;

    public DataPoint(ArrayList<Feature> features) {
        this.features = features;
    }

    public DataTimestamp getTime() {
        if ( features != null && !features.isEmpty() )
            return features.get(0).getTimestamp();
        return null;
    }

    @Override
    public String toString() {
        return getTime() + ", " + Utils.join(", ", features, Feature::getRepresentation);
    }

    public ArrayList<String> getHeaders() {
        ArrayList<String> h = new ArrayList<>();
        for ( Feature f : features )
            h.add(f.getFeatureName());
        return h;
    }
}
