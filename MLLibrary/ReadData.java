package MLLibrary;

import java.io.File;
import java.util.*;

public class ReadData {
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try {
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
            rowScanner.close();
        } catch (Exception e) {
            throw (e);
            // TODO do something with the exception
        }
        return values;
    }

    ArrayList<DataPoint> data = new ArrayList<DataPoint>();

    private void populateandLabelData(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            int typeDeterminer;
            while (fileScanner.hasNext()) {
                List<String> record = getRecordFromLine(fileScanner.nextLine());
                if (!record.get(5).isEmpty() && !record.get(6).isEmpty()) {
                    typeDeterminer = (int) (Math.random() * 100);
                    String type = "UNASSIGNED";
                    if (typeDeterminer >= 90)
                        type = "TRAIN";
                    else
                        type = "TEST";
                    try {
                        DataPoint point = new DataPoint(Double.parseDouble(record.get(5)),
                                Double.parseDouble(record.get(6)), record.get(1), type);
                        this.data.add(point);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("ioutobounds");
                    }
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            // System.out.println(e);
        }
    }

    public ReadData(String fileName) {
        populateandLabelData(fileName);
    }

    public ArrayList<DataPoint> getData() {
        return this.data;
    }
}
