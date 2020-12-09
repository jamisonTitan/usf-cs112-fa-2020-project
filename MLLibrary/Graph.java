package MLLibrary;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Graph extends JPanel {

    private static final long serialVersionUID = 1L;
    private int labelPadding = 40;
    private Color lineColor = new Color(255, 255, 254);
    static final int MIN = 0;
    static final int MAX = 30;
    static final int INIT = 15;
    static String accuracy, precision;

    // TODO: Add point colors for each type of data point
    private Color yellow = new Color(255, 255, 0);
    private Color blue = new Color(95, 158, 160);
    private Color cyan = new Color(0, 255, 255);
    private Color red = new Color(200, 15, 0);

    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    // TODO: Change point width as needed
    private static int pointWidth = 10;

    // Number of grids and the padding width
    private int numXGridLines = 6;
    private int numYGridLines = 6;
    private int padding = 40;

    // TODO: Add a private KNNModel variable
    static ArrayList<DataPoint> data = new ReadData("titanic.csv").getData();
    private static KNNModel model = null;

    private static ArrayList<DataPoint> loadTestData() {
        ArrayList<DataPoint> data = new ReadData("titanic.csv").getData();
        ArrayList<DataPoint> testData = data;
        testData.removeIf(dp -> (dp.getType() == "TRAIN"));
        return testData;
    }

    private static ArrayList<DataPoint> loadTrainData() {
        ArrayList<DataPoint> data = new ReadData("titanic.csv").getData();
        ArrayList<DataPoint> trainData = data;
        trainData.removeIf(dp -> (dp.getType() == "TEST"));
        return trainData;
    }

    // private static String loadAccuracy() {
    // return accuracy = Double.toString(model.getAccuracy(data));
    // }
    // private static String load
    /**
     * Constructor method
     */
    public Graph(ArrayList<DataPoint> testData, ArrayList<DataPoint> trainData) {
        model = new KNNModel(101);
        model.train(trainData);
        accuracy = Double.toString(model.getAccuracy(data));
        precision = Double.toString(model.getPrecision(data));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minF1 = getMinF1Data();
        double maxF1 = getMaxF1Data();
        double minF2 = getMinF2Data();
        double maxF2 = getMaxF2Data();

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding,
                getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLUE);

        double yGridRatio = (maxF2 - minF2) / numYGridLines;
        for (int i = 0; i < numYGridLines + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight()
                    - ((i * (getHeight() - padding * 2 - labelPadding)) / numYGridLines + padding + labelPadding);
            int y1 = y0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = String.format("%.2f", (minF2 + (i * yGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        double xGridRatio = (maxF1 - minF1) / numXGridLines;
        for (int i = 0; i < numXGridLines + 1; i++) {
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0 - pointWidth;
            int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numXGridLines) + padding + labelPadding;
            int x1 = x0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                g2.setColor(Color.BLACK);
                String xLabel = String.format("%.2f", (minF1 + (i * xGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // Draw the main axis
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding,
                getHeight() - padding - labelPadding);

        // Draw the points
        paintPoints(g2, minF1, maxF1, minF2, maxF2);
    }

    private void paintPoints(Graphics2D g2, double minF1, double maxF1, double minF2, double maxF2) {
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        double xScale = ((double) getWidth() - (3 * padding) - labelPadding) / (maxF1 - minF1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxF2 - minF2);
        g2.setStroke(oldStroke);
        for (int i = 0; i < this.data.size(); i++) {
            int x1 = (int) ((this.data.get(i).getF1() - minF1) * xScale + padding + labelPadding);
            int y1 = (int) ((maxF2 - this.data.get(i).getF2()) * yScale + padding);
            int x = x1 - pointWidth / 2;
            int y = y1 - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;

            // TODO: Depending on the type of data and how it is tested, change color here.
            // You need to test your data here using the model to obtain the test value
            // and compare against the true label.
            DataPoint p = this.data.get(i);
            String testRes = null;
            if (p.getType() == "TEST") {
                ArrayList<DataPoint> dataToTest = new ArrayList<DataPoint>();
                dataToTest.add(p);
                if (model.test(dataToTest) != null)
                    testRes = model.test(dataToTest);
                if (testRes.equals(p.getLabel())) {
                    switch (p.getLabel()) {
                        case "0":
                            // true neg; color red
                            g2.setColor(red);
                            break;
                        case "1":
                            // true pos; color blue
                            g2.setColor(blue);
                            break;
                    }
                } else {
                    switch (p.getLabel()) {
                        case "0":
                            // false neg; color yellow
                            g2.setColor(yellow);
                            break;
                        case "1":
                            // false pos; color cyan
                            g2.setColor(cyan);

                            break;
                    }
                }
            } else {
            }

            g2.fillOval(x, y, ovalW, ovalH);

        }

    }

    /*
     * @Return the min values
     */
    private double getMinF1Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF1());
        }
        return minData;
    }

    private double getMinF2Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF2());
        }
        return minData;
    }

    /*
     * @Return the max values;
     */
    private double getMaxF1Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF1());
        }
        return maxData;
    }

    private double getMaxF2Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF2());
        }
        return maxData;
    }

    /* Mutator */
    public void setData(ArrayList<DataPoint> data) {
        this.data = data;
        invalidate();
        this.repaint();
    }

    /* Accessor */
    public List<DataPoint> getData() {
        return data;
    }

    /*
     * Run createAndShowGui in the main method, where we create the frame too and
     * pack it in the panel
     */
    private static void createAndShowGui(ArrayList<DataPoint> testData, ArrayList<DataPoint> trainData) {
        JFrame frame = new JFrame("CS 112 Lab Part 3");
        /* Main panel */
        Graph mainPanel = new Graph(testData, trainData);
        JButton runButton = new JButton("Run Test");

        final int MIN = 2;
        final int MAX = 25;
        final int INIT = 5;
        // Create the slider.
        JSlider slider = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INIT);
        JLabel outputLabel = new JLabel();
        JLabel sliderLabel = new JLabel("Random Slider", JLabel.CENTER);
        String accAndPrec = "Accuracy " + accuracy + "<br> Precision" + precision;
        final JLabel textLabel = new JLabel("<html>DATA<br> <hr>" + accAndPrec);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        Font font = new Font("Serif", Font.ITALIC, 15);
        slider.setFont(font);
        slider.setAlignmentX(Component.CENTER_ALIGNMENT);
        slider.addChangeListener(new ChangeListener() {
            @Override
            /** Listen to the slider. */
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                int value = source.getValue();
                outputLabel.setText("Value is: " + value);
            }
        });

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = (slider.getValue() * 2) + 1;
                model = new KNNModel(value);
                model.train(trainData);
                accuracy = Double.toString(model.getAccuracy(data));
                precision = Double.toString(model.getPrecision(data));
                System.out.println(accuracy + " " + precision);
                textLabel.setText("<html>DATA<br> <hr>" + "Accuracy " + accuracy + "<br> Precision" + precision);
                frame.validate();
                frame.repaint();
            }
        });

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());

        // Feel free to change the size of the panel
        mainPanel.setPreferredSize(new Dimension(700, 600));

        /* creating the frame */

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.getContentPane().add(textLabel);
        frame.getContentPane().add(sliderLabel);
        frame.getContentPane().add(slider);
        frame.getContentPane().add(outputLabel);
        frame.getContentPane().add(runButton);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    /* The main method runs createAndShowGui */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // TODO: Pass in the testData and trainData separately
                // Be careful with the order of the variables.
                createAndShowGui(loadTestData(), loadTrainData());
            }
        });
    }
}
