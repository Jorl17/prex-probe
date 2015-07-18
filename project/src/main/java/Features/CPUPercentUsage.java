package Features;

/**
 * Created by jorl17 on 18/07/15.
 */
public class CPUPercentUsage extends Feature {
    private double usage;
    public CPUPercentUsage(double usage) {
        this.usage = usage;
    }

    @Override
    public String getRepresentation() {
        return "" + usage;
    }
}
