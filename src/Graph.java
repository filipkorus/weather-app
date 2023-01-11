import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import swing.JFrameTemplate;

import javax.swing.*;

public class Graph extends App {
	private final Logger logger = Logger.getLogger(Graph.class);
	private final ImageIcon icon;
	private final String windowTitle, xAxisLabel, yAxisLabel;
	private final DefaultCategoryDataset dataset;

	public Graph(String windowTitle, String xAxisLabel, String yAxisLabel, DefaultCategoryDataset dataset, ImageIcon icon) {
		this.windowTitle = windowTitle;
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel;
		this.dataset = dataset;
		this.icon = icon;
	}

	public void show() {
		JFrameTemplate graphWindow = new JFrameTemplate(windowTitle, 1000, 600, icon, false);
		graphWindow.setResizable(true);

		JFreeChart chart = ChartFactory.createLineChart(
				  windowTitle,
				  xAxisLabel,
				  yAxisLabel,
				  dataset,
				  PlotOrientation.VERTICAL,
				  false,true,false
		);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinesVisible(false);

		ChartPanel graphPanel = new ChartPanel(chart);
		graphPanel.setBounds(0,0,graphWindow.getWidth() - 30, graphWindow.getHeight() - 45);

		graphWindow.add(graphPanel);
		graphWindow.setVisible(true);

		logger.info("\"" + windowTitle + "\" graph window showed");
	}
}
