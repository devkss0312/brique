package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Q4 {

    private List<Data> dataList;
    private DataTableModel tableModel;
    private JTable table;
    private XYSeries tempSeries;
    private XYSeries humSeries;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private Random random = new Random();

    private final String[] monthLabels = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    public Q4() {

        dataList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            dataList.add(new Data(20 + i, 50 + i));
        }

        tableModel = new DataTableModel(dataList);
        table = new JTable(tableModel);

        tempSeries = new XYSeries("Temperature");
        humSeries = new XYSeries("Humidity");

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(tempSeries);
        dataset.addSeries(humSeries);

        chart = ChartFactory.createXYLineChart(
                "Monthly Temperature & Humidity Chart",
                "Month",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chartPanel = new ChartPanel(chart);

        Font font = new Font("Arial", Font.PLAIN, 12);
        chart.getTitle().setFont(font);
        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(font);
        }
        XYPlot plot = chart.getXYPlot();
        plot.getDomainAxis().setLabelFont(font);
        plot.getDomainAxis().setTickLabelFont(font);
        plot.getRangeAxis().setLabelFont(font);
        plot.getRangeAxis().setTickLabelFont(font);

        SymbolAxis domainAxis = new SymbolAxis("Month", monthLabels);
        domainAxis.setRange(-0.5, 11.5);
        plot.setDomainAxis(domainAxis);

        updateChartData();

        JButton randomButton = new JButton("Random");
        randomButton.addActionListener(e -> {
            for (Data d : dataList) {
                d.setTemperature(10 + random.nextInt(26));
                d.setHumidity(30 + random.nextInt(61));
            }
            tableModel.fireTableDataChanged();
            updateChartData();
        });

        JFrame frame = new JFrame("Monthly Data Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        leftPanel.add(randomButton, BorderLayout.SOUTH);

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(chartPanel, BorderLayout.CENTER);

        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateChartData() {
        tempSeries.clear();
        humSeries.clear();
        for (int i = 0; i < dataList.size(); i++) {
            Data d = dataList.get(i);
            tempSeries.add(i, d.getTemperature());
            humSeries.add(i, d.getHumidity());
        }
        chart.fireChartChanged();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Q4());
    }

    static class Data {
        private double temperature;
        private double humidity;

        public Data(double temperature, double humidity) {
            this.temperature = temperature;
            this.humidity = humidity;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }
    }

    class DataTableModel extends AbstractTableModel {
        private final List<Data> dataList;
        private final String[] columnNames = {"Month", "Temperature", "Humidity"};

        public DataTableModel(List<Data> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getRowCount() {
            return dataList.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Data data = dataList.get(rowIndex);
            if (columnIndex == 0) {
                return monthLabels[rowIndex];
            } else if (columnIndex == 1) {
                return data.getTemperature();
            } else {
                return data.getHumidity();
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 0) return;
            Data data = dataList.get(rowIndex);
            try {
                double value = Double.parseDouble(aValue.toString());
                if (columnIndex == 1) {
                    data.setTemperature(value);
                } else {
                    data.setHumidity(value);
                }
                fireTableCellUpdated(rowIndex, columnIndex);
            } catch (NumberFormatException e) {
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex != 0;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }
}
