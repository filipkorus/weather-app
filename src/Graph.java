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

/**
 * creates graph window
 */
public class Graph extends App {
	/**
	 * logger for App class
 	 */
	private final Logger logger = Logger.getLogger(Graph.class);

	/**
	 * icon for newly created windows
 	 */
	private final ImageIcon icon;

	/**
	 * @variable windowTitle xd
	 */
	private final String windowTitle;

	/**
	 * axis label for graph
	 */
	private final String xAxisLabel, yAxisLabel;

	/**
	 * dataset to create graph from
	 */
	private final DefaultCategoryDataset dataset;

	/**
	 *
	 * @param windowTitle title of window with graph
	 * @param xAxisLabel x axis label
	 * @param yAxisLabel y axis label
	 * @param dataset dataset to create graph from
	 * @param icon icon form window with graph
	 */
	public Graph(String windowTitle, String xAxisLabel, String yAxisLabel, DefaultCategoryDataset dataset, ImageIcon icon) {
		this.windowTitle = windowTitle;
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel;
		this.dataset = dataset;
		this.icon = icon;
	}

	/**
	 * shows window with graph
	 */
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
