package MLLibrary;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class App {
    private static String accuracy, precision;

    public static void main(String args[]) {
        ArrayList<DataPoint> data = new ReadData("titanic.csv").getData();
        KNNModel model = new KNNModel(111);
        model.train(data);
        accuracy = Double.toString(model.getAccuracy(data));
        precision = Double.toString(model.getPrecision(data));
        System.out.println("Acc " + accuracy + " Prec " + precision);
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
        String data = "Accuracy " + accuracy + " Precision" + precision;
        JLabel textLabel = new JLabel("<html>DATA<br>" + data);
        contentPane.add(textLabel);

        frame.pack();
        frame.setVisible(true);
    }
}
