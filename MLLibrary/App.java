package MLLibrary;

import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class App {
    private static String text;
    private static DataPoint dp1 = new DataPoint(1, 2, "1.5", "training"), dp2 = new DataPoint(2, 3, "2.5", "training");
    // private static ArrayList<DataPoint> generatedData;

    public static void main(String args[]) {
        ArrayList<DataPoint> data = new ArrayList<>(2);
        data.add(dp1);
        data.add(dp2);
        DummyModel model = new DummyModel();
        // generatedData = model.getGeneratedData();
        model.train(data);
        text = model.test(data);
        System.out.println(text);
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
        String realData = "dp1Label: " + dp1.getLabel() + "<br>" + "dp2Label: " + dp2.getLabel();
        JLabel textLabel = new JLabel("<html>" + text + "<html>");
        contentPane.add(textLabel);

        frame.pack();
        frame.setVisible(true);
    }
}
