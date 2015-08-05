package DataLoggers;

import BuildingBlocks.DataPoint;
import Util.Utils;

import java.io.*;

/**
 * Created by jorl17 on 02/08/15.
 */
public class CsvLogger extends SeparatorLoggerAdapter {
    private BufferedWriter writer;

    public CsvLogger(String outfile, String separator) {
        super(separator);
        if ( outfile == null ) {
            this.writer = null;
            return;
        }
        try {
            this.writer = new BufferedWriter(new FileWriter(outfile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public CsvLogger(String outfile) {
        this(outfile, ", ");
    }

    @Override
    public void logHeader(DataPoint point) {
        if ( this.writer == null ) return;
        try {
            writer.write("Timestamp" + separator + Utils.join(separator, point.getHeaders()));
            writer.newLine();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void log(DataPoint point) {
        if ( this.writer == null ) return;
        try {
            writer.write(""+point.getFeatureString(separator));
            writer.newLine();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void cleanup() {
        if ( this.writer == null ) return;
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
