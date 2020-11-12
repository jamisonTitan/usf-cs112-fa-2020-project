package MLLibrary;

import java.io.File;
import java.util.*;

public class ReadData {
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(",");
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next());
        }
        rowScanner.close();
        return values;
    }

    ArrayList<DataPoint> data = new ArrayList<DataPoint>();

    private void populateandLabelData(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            int typeDeterminer;
            while (fileScanner.hasNextLine()) {
                List<String> record = getRecordFromLine(fileScanner.nextLine());
                if (!record.get(5).isEmpty() && !record.get(6).isEmpty()) { // check if row has age and fare
                    typeDeterminer = (int) (Math.random() * 100);
                    String type = "UNASSIGNED";
                    if (typeDeterminer < 90) {
                        type = "TRAIN";
                    } else {
                        type = "TEST";
                    }
                    DataPoint point = new DataPoint(Double.parseDouble(record.get(5)),
                            Double.parseDouble(record.get(6)), record.get(1), type);
                    this.data.add(point);
                }
            }
            fileScanner.close();
        } catch (Exception fileNotFoundException) {
            System.out.println("File does not exist or wrong file name");
        }
    }

    public ReadData(String fileName) {
        populateandLabelData(fileName);
    }

    public ArrayList<DataPoint> getData() {
        return this.data;
    }
}
