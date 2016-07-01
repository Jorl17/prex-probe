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
    private ArrayList<String> headers;

    public DataPoint(ArrayList<Feature> features) {
        this.features = features;
    }

    public DataTimestamp getTime() {
        if ( features != null && !features.isEmpty() )
            return features.get(0).getTimestamp();
        return null;
    }

    public String getFeatureString(String sep) {
        return getTime() + sep + Utils.join(sep, features, Feature::getRepresentation);
    }

    public String getFeatureString() {
        return getFeatureString(", ");
    }

    @Override
    public String toString() {
        return getFeatureString();
    }

    public ArrayList<String> getHeaders() {
        ArrayList<String> h = new ArrayList<>();
        for ( Feature f : features )
            h.add(f.getFeatureName());
        return h;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }
}
