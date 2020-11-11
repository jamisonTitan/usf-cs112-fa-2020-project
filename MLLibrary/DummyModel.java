package MLLibrary;

import java.util.*;

public class DummyModel extends Model {
    private ArrayList<DataPoint> generatedData = new ArrayList<>(2);

    public void train(ArrayList<DataPoint> data) {
        data.forEach((dp) -> {
            double guess = (Math.random() * (dp.getF2() - dp.getF1()) + dp.getF1());
            generatedData.add(new DataPoint(dp.getF1(), dp.getF2(), Double.toString(guess), "generated"));
        });
        this.test(data);
    }

    public String test(ArrayList<DataPoint> data) {
        String accuracy = Double.toString((int) Math.floor(getAccuracy(data)));
        String precision = Double.toString(getPrecision(data));
        return "Accuracy: ~" + accuracy + "%<br> Precision: " + precision;
    }

    public Double getAccuracy(ArrayList<DataPoint> data) {
        /*
         * To determine if a value is accurate compare it to the accepted value. As
         * these values can be anything a concept called percent error has been
         * developed. Find the difference (subtract) between the accepted value and the
         * experimental value, then divide by the accepted value.
         */
        double avgAccuracy = 0;
        for (int i = 0; i < data.size(); i++) {
            double testingValue = 1;
            double generatedValue = 1;
            try {
                testingValue = Double.parseDouble(data.get(i).getLabel());
                generatedValue = Double.parseDouble(this.generatedData.get(i).getLabel());
            } catch (Exception NumberFormatException) {
                System.out.println("invalid label");
            }
            double percentError = (testingValue - generatedValue) / testingValue * 100;
            avgAccuracy += Math.abs(percentError);
        }
        avgAccuracy /= data.size();
        return avgAccuracy;
    }

    public Double getPrecision(ArrayList<DataPoint> data) {
        /*
         * To determine if a value is precise I find the average of my data, then
         * subtract each measurement from it. This gives me a table of deviations. Then
         * I average the deviations. This will gives me a value called uncertainty. A
         * plus or minus value that says how precise a measurement is. I then average
         * the precision values to get a single average precision value.
         */
        double avgPrecision = 0;
        double stdDeviation = 0;
        for (int i = 0; i < data.size(); i++) {
            try {
                double testingValue = Double.parseDouble(data.get(i).getLabel());
                double generatedValue = Double.parseDouble(this.generatedData.get(i).getLabel());
                stdDeviation += Math.abs(testingValue - generatedValue);
            } catch (Exception NumberFormatException) {
                System.out.println("invalid label");
            }
        }
        stdDeviation /= data.size();
        for (DataPoint dp : this.generatedData) {
            try {
                avgPrecision += Math.abs(Double.parseDouble(dp.getLabel()) - stdDeviation);
            } catch (Exception NumberFormatException) {
                System.out.println("invalid label");
            }
        }
        avgPrecision /= data.size();
        return avgPrecision;
    }

    public ArrayList<DataPoint> getGeneratedData() {
        return this.generatedData;
    }
}
