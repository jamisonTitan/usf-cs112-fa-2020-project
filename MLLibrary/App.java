package MLLibrary;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class App {
    private static String results, accuracy, precision;
    private static DataPoint dp1 = new DataPoint(1, 2, "1.5", "training"), dp2 = new DataPoint(2, 3, "2.5", "training");

    public static void main(String args[]) {
        ArrayList<DataPoint> data = new ReadData("titanic.csv").getData();
        KNNModel model = new KNNModel(70);
        model.train(data);
        model.test(data);
        accuracy = Double.toString(model.getAccuracy(data));
        precision = Double.toString(model.getPrecision(data));
        System.out.println("Acc " + accuracy + "Prec " + precision);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                render();
            }
        });
    }

    private static void render() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("The worst nueral net ever");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());
        String realData = "dp1x: " + dp1.getF1() + "<br>dp1y: " + dp1.getF2() + "dp1Label: " + dp1.getLabel() + "dp2x: "
                + dp2.getF1() + "<br>dp2y: " + dp2.getF2() + "<br>" + "dp2Label: " + dp2.getLabel() + "<br>";
        JLabel textLabel = new JLabel("<html>DATA<br>" + realData + "<br>RESULTS<br><hr>" + results + "<html>");
        contentPane.add(textLabel);

        frame.pack();
        frame.setVisible(true);
    }
}
