package MLLibrary;

import java.util.ArrayList;
import java.util.Arrays;

public class KNNModel extends Model {
    ArrayList<DataPoint> trainingSet;
    private int k, numOfSurvivors = 0, numOfDeceased = 0;

    public KNNModel(int k) {
        this.k = k;
        this.trainingSet = new ArrayList<DataPoint>();
    }

    private double getDistance(DataPoint p1, DataPoint p2) {
        double x1 = p1.getF1(), y1 = p1.getF2(), x2 = p2.getF1(), y2 = p2.getF2();
        return Math.sqrt((Math.pow(x2 - x1, 2)) + (Math.pow(y2 - y1, 2)));
    }

    void train(ArrayList<DataPoint> data) {
        ArrayList<DataPoint> trainingData = new ArrayList<DataPoint>();
        for (DataPoint d : data) {
            if (d.getType() == "TRAIN")
                trainingData.add(d);
        }
        for (DataPoint d : trainingData) {
            if (d.getLabel() == "0")
                this.numOfDeceased += 1;
            else
                this.numOfSurvivors += 1;
        }
        this.trainingSet = trainingData;
    }

    String test(ArrayList<DataPoint> data) {
        double[][] res = new double[this.trainingSet.size()][2];
        if (data.get(0).getType() == "TEST") {
            for (int i = 0; i < this.trainingSet.size(); i++) {
                res[i][0] = getDistance(data.get(0), trainingSet.get(i));
                res[i][1] = Double.parseDouble(trainingSet.get(i).getLabel());
            }
            Arrays.sort(res, (a, b) -> Double.valueOf(a[0]).compareTo(b[0]));
            String modeOfLabels = null;
            int deceased = 0, survived = 0;
            for (int i = 0; i < k; i++) {
                if (res[i][1] == 0) {
                    deceased += 1;
                } else {
                    survived += 1;
                }
            }
            if (deceased > survived)
                modeOfLabels = "0";
            else if (deceased < survived)
                modeOfLabels = "1";
            else
                modeOfLabels = "0"; // deceased == survived (°-°;)???
            return modeOfLabels;
        } else
            return null;
    }

    Double getAccuracy(ArrayList<DataPoint> data) {
        String testRes = null;
        int truePositive = 0, falsePositive = 0, trueNegative = 0, falseNegative = 0;
        for (DataPoint p : data) {
            if (p.getType() == "TEST") {
                ArrayList<DataPoint> dataToTest = new ArrayList<DataPoint>();
                dataToTest.add(p);
                if (test(dataToTest) != null)
                    testRes = test(dataToTest);
                boolean isTestEqualToReal = false;
                isTestEqualToReal = testRes.equals(p.getLabel());
                if (isTestEqualToReal) {
                    switch (p.getLabel()) {
                        case "0":
                            trueNegative += 1;
                            break;
                        case "1":
                            truePositive += 1;
                            break;
                    }
                } else {
                    switch (p.getLabel()) {
                        case "0":
                            falseNegative += 1;
                            break;
                        case "1":
                            falsePositive += 1;
                            break;
                    }
                }
            } else {

            }
        }

        return (double) (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
    }

    Double getPrecision(ArrayList<DataPoint> data) {
        // TODO copy + pasted code!! bad bad
        String testRes = null;
        int truePositive = 0, falsePositive = 0, trueNegative = 0, falseNegative = 0;
        for (DataPoint p : data) {
            if (p.getType() == "TEST") {
                ArrayList<DataPoint> dataToTest = new ArrayList<DataPoint>();
                dataToTest.add(p);
                if (test(dataToTest) != null)
                    testRes = test(dataToTest);
                boolean isTestEqualToReal = false;
                isTestEqualToReal = testRes.equals(p.getLabel());
                if (isTestEqualToReal) {
                    switch (p.getLabel()) {
                        case "0":
                            trueNegative += 1;
                            break;
                        case "1":
                            truePositive += 1;
                            break;
                    }
                } else {
                    switch (p.getLabel()) {
                        case "0":
                            falseNegative += 1;
                            break;
                        case "1":
                            falsePositive += 1;
                            break;
                    }
                }
            } else {

            }
        }

        return (double) truePositive / (truePositive + falseNegative);
    }
}
