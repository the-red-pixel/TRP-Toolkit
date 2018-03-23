package work.erio.toolkit.gui;

import net.minecraft.util.math.BlockPos;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Erioifpud on 2018/3/24.
 */
public class FrameChart extends JFrame {
    private static FrameChart instance;
    private JPanel panel;
    private XYChartBuilder builder;

    public FrameChart() {
        createPanel();
        builder = new XYChartBuilder().xAxisTitle("Time").yAxisTitle("Power").theme(Styler.ChartTheme.GGPlot2);
    }

    public static FrameChart getInstance() {
        if (instance == null) {
            instance = new FrameChart();
        }
        return instance;
    }

    private void createPanel() {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(400, 400);
        setResizable(true);
        panel = new JPanel();
        setContentPane(panel);
        getContentPane().setLayout(
                new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)
        );
        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(e -> {
            for (Component c : panel.getComponents()) {
                if (c instanceof XChartPanel) {
                    panel.remove(c);
                }
            }
            panel.updateUI();
        });
        panel.add(clearButton);
    }

    public void addChart(BlockPos pos, double[] xData, double[] yData) {
        XYChart chart = builder.title(String.format("Monitor [%d, %d, %d]", pos.getX(), pos.getY(), pos.getZ())).build();
        chart.addSeries("power(time)", xData, yData);
        chart.getSeriesMap().get("power(time)").setMarker(SeriesMarkers.SQUARE);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setToolTipType(Styler.ToolTipType.xAndYLabels);
        XChartPanel<XYChart> chartPanel = new XChartPanel<>(chart);
        panel.add(chartPanel);
    }
}
