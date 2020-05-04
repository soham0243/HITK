import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;
import org.jfree.data.category.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class multiGraph2 {
	private JFrame mainFrame;
	private JLabel graphTypeLabel,resultTypeLabel,rollLabel;
	private JPanel chartPanel,selectPanel,containerPanel;
	private JPanel inputPanel,semesterPanel;
	private JButton[] semBtn = new JButton[8];
	private JLabel[] semLbl = new JLabel[8];
	private String[] semres = {"8.96","9.1","8.74","7.23","8.37","8.98","6.84","7.62"};
	private Color hoverBackgroundColor,pressedBackgroundColor;

	public multiGraph2(){
		//prepareGUI();
	}

	public static void main(String[] args){
		multiGraph2 graph = new multiGraph2();
		graph.multiGraphChoice();
	}

	private void multiGraphChoice(){
		Font f = new Font("Serif",Font.BOLD,14);
		mainFrame = new JFrame("Multiple Graph 2");
		mainFrame.setSize(707,707);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int option = JOptionPane.showConfirmDialog(mainFrame,"Are you sure to exit?","Multiple Graph",JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION){
					mainFrame.dispose();
				}else{
					mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
				}
			}
		});
		mainFrame.setContentPane(new JLabel(new ImageIcon("../res/sample.jpeg")));
		mainFrame.setLayout(null);
		inputPanel = new JPanel();
		inputPanel.setBackground(new Color(0,0,0,120));
		inputPanel.setBounds(141,58,424,141);
		inputPanel.setLayout(null);
		rollLabel = new JLabel("Autonomy Roll : ");
		rollLabel.setFont(f);
		rollLabel.setForeground(Color.WHITE);
		rollLabel.setBounds(35,28,141,28);
		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(35,85,106,28);
		submitButton.setForeground(Color.WHITE);
		submitButton.setBackground(new Color(66,245,236,90));
		submitButton.setOpaque(false);
		submitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){

			}
		});
		JButton resetButton = new JButton("Reset");
		resetButton.setBounds(283,85,106,28);
		resetButton.setForeground(Color.WHITE);
		resetButton.setBackground(new Color(66,245,236,90));
		resetButton.setOpaque(false);
		resetButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){

			}
		});
		JTextField userText = new JTextField(10);
		userText.setBounds(233,28,155,28);
		userText.setBackground(new Color(66,245,236,90));
		userText.setForeground(Color.WHITE);
		userText.setOpaque(false);

		inputPanel.add(rollLabel);
		inputPanel.add(userText);
		inputPanel.add(submitButton);
		inputPanel.add(resetButton);

		selectPanel = new JPanel();
		selectPanel.setBackground(new Color(0,0,0,120));
		selectPanel.setLayout(null);
		selectPanel.setBounds(56,248,396,141);

		graphTypeLabel = new JLabel("Graph Type : ");
		graphTypeLabel.setFont(f);
		graphTypeLabel.setForeground(Color.WHITE);
		graphTypeLabel.setBounds(35,28,106,28);

		resultTypeLabel = new JLabel("Result Type : ");
		resultTypeLabel.setFont(f);
		resultTypeLabel.setForeground(Color.WHITE);
		resultTypeLabel.setBounds(35,85,150,28);

		containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBackground(new Color(0,0,0,120));
		containerPanel.setBounds(56,248,396,401);

		DefaultComboBoxModel<String> chartName = new DefaultComboBoxModel<>();
		chartName.addElement("Bar Chart");
		chartName.addElement("3D Bar Chart");
		chartName.addElement("Line Chart");
		chartName.addElement("X-Y Line Chart");
		JComboBox<String> chartCombo = new JComboBox<>(chartName);
		chartCombo.setSelectedIndex(0);

		JScrollPane graphScrollPane = new JScrollPane(chartCombo);
		graphScrollPane.setBounds(205,28,155,28);
		chartCombo.setBackground(new Color(0,0,0,120));
		chartCombo.setForeground(Color.WHITE);
		chartCombo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e){
				String choice = chartCombo.getItemAt(chartCombo.getSelectedIndex())+"";
				multiGraph2 chartObj = new multiGraph2();
				if(choice.equals("Bar Chart")){
					containerPanel.removeAll();
					containerPanel.setSize(0,0);
					chartPanel = chartObj.showBarChart();
					chartPanel.setSize(396,259);
					containerPanel.setSize(chartPanel.getSize());
					containerPanel.setBounds(56,248,396,401);
					chartPanel.setBounds(0,141,396,259);
					chartPanel.setBackground(new Color(0,0,0,120));
					containerPanel.add(chartPanel,null);
					containerPanel.revalidate();
				}
				if(choice.equals("3D Bar Chart")){
					containerPanel.removeAll();
					containerPanel.setSize(0,0);
					chartPanel = chartObj.showBarChart3D();
					chartPanel.setSize(396,259);
					containerPanel.setSize(chartPanel.getSize());
					containerPanel.setBounds(56,248,396,401);
					chartPanel.setBounds(0,141,396,259);
					chartPanel.setBackground(new Color(0,0,0,120));
					containerPanel.add(chartPanel,null);
					containerPanel.revalidate();
				}
				if(choice.equals("Line Chart")){
					containerPanel.removeAll();
					containerPanel.setSize(0,0);
					chartPanel = chartObj.showLineChart();
					chartPanel.setSize(396,259);
					containerPanel.setSize(chartPanel.getSize());
					containerPanel.setBounds(56,248,396,401);
					chartPanel.setBounds(0,141,396,259);
					chartPanel.setBackground(new Color(0,0,0,120));
					containerPanel.add(chartPanel,null);
					containerPanel.revalidate();
				}
				if(choice.equals("X-Y Line Chart")){
					containerPanel.removeAll();
					containerPanel.setSize(0,0);
					chartPanel = chartObj.showXYLineChart();
					chartPanel.setSize(396,259);
					containerPanel.setSize(chartPanel.getSize());
					containerPanel.setBounds(56,248,396,401);
					chartPanel.setBounds(0,141,396,259);
					chartPanel.setBackground(new Color(0,0,0,120));
					containerPanel.add(chartPanel,null);
					containerPanel.revalidate();
				}
			}
		});

		selectPanel.add(graphTypeLabel);
		selectPanel.add(resultTypeLabel);
		selectPanel.add(graphScrollPane);

		semesterPanel = new JPanel();
		semesterPanel.setBackground(new Color(0,0,0,120));
		semesterPanel.setLayout(new GridLayout(8,2));
		semesterPanel.setBounds(509,248,141,401);

		for(int i=0;i<8;i++){
			int j = i+1;
			String btnlbl = "";
			if(j == 1){
				btnlbl = j+"st";
			}else if(j == 2){
				btnlbl = j+"nd";
			}else if(j == 3){
				btnlbl = j+"rd";
			}else{
				btnlbl = j+"th";
			}
			semBtn[i] = new JButton(btnlbl);
			semBtn[i].setForeground(Color.WHITE);
			semBtn[i].setBackground(new Color(66,245,236,90));
			semBtn[i].setOpaque(false);
			semBtn[i].addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){

				}
			});

			semBtn[i].addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent e){
					//semBtn[i].setBackground(new Color(255,255,255,50));
					//semBtn[i].setOpaque(false);
					//semBtn[i].repaint();
				}
				@Override
				public void mouseExited(MouseEvent e){
					//semBtn[i].setBackground(new Color(0,0,0,120));
					//semBtn[i].setOpaque(false);
					//semBtn[i].repaint();
				}
				@Override
				public void mouseClicked(MouseEvent e){
					//semBtn[i].setBackground(new Color(0,0,0,120));
					//semBtn[i].setOpaque(false);
					//semBtn[i].repaint();
				}
			});

			semLbl[i] = new JLabel("   "+semres[i]);
			semLbl[i].setFont(f);
			semLbl[i].setForeground(Color.WHITE);

			semesterPanel.add(semBtn[i]);
			semesterPanel.add(semLbl[i]);
		}

		mainFrame.add(selectPanel);
		mainFrame.add(containerPanel);
		mainFrame.add(semesterPanel);
		mainFrame.add(inputPanel);
		mainFrame.setVisible(true);
	}

	private JPanel showBarChart(){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(8.96,"Semester","1st");
		dataset.addValue(9.1,"Semester","2nd");
		dataset.addValue(8.74,"Semester","3rd");
		dataset.addValue(7.23,"Semester","4th");
		dataset.addValue(8.37,"Semester","5th");
		dataset.addValue(8.98,"Semester","6th");
		dataset.addValue(6.84,"Semester","7th");
		dataset.addValue(7.62,"Semester","8th");

		JFreeChart chart = ChartFactory.createBarChart("Semester Result","Sem","SGPA",dataset,PlotOrientation.VERTICAL,true,true,false);
		Plot plot = chart.getPlot();
		chart.getTitle().setPaint(Color.WHITE);
		chart.setBackgroundPaint(new Color(0,0,0,120));
		plot.setBackgroundPaint(new Color(255,255,255,50));
		if(plot instanceof CategoryPlot){
			((CategoryPlot) plot).getRenderer().setSeriesPaint(0,new Color(255,255,0,150));
			((CategoryPlot) plot).setDomainGridlinePaint(new Color(255,255,255,120));
			((CategoryPlot) plot).setRangeGridlinePaint(new Color(255,255,255,120));
			((CategoryPlot) plot).getDomainAxis().setTickLabelPaint(new Color(255,255,255,255)); //setting color of coord points
			((CategoryPlot) plot).getRangeAxis().setTickLabelPaint(new Color(255,255,255,255)); //setting color of coord points
			((CategoryPlot) plot).getDomainAxis().setLabelPaint(new Color(255,255,255,255)); //setting color of coord labels
			((CategoryPlot) plot).getRangeAxis().setLabelPaint(new Color(255,255,255,255)); //setting color of coord labels
		}
		return new ChartPanel(chart);
	}

	private JPanel showBarChart3D(){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(8.96,"Semester","1st");
		dataset.addValue(9.1,"Semester","2nd");
		dataset.addValue(8.74,"Semester","3rd");
		dataset.addValue(7.23,"Semester","4th");
		dataset.addValue(8.37,"Semester","5th");
		dataset.addValue(8.98,"Semester","6th");
		dataset.addValue(6.84,"Semester","7th");
		dataset.addValue(7.62,"Semester","8th");

		JFreeChart chart = ChartFactory.createBarChart3D("Semester Result","Sem","SGPA",dataset,PlotOrientation.VERTICAL,true,true,false);
		Plot plot = chart.getPlot();
		chart.getTitle().setPaint(Color.WHITE);
		chart.setBackgroundPaint(new Color(0,0,0,120));
		plot.setBackgroundPaint(new Color(255,255,255,50));
		if(plot instanceof CategoryPlot){
			((CategoryPlot) plot).getRenderer().setSeriesPaint(0,Color.YELLOW);
			((CategoryPlot) plot).setDomainGridlinePaint(new Color(255,255,255,30));
			((CategoryPlot) plot).setRangeGridlinePaint(new Color(255,255,255,30));
			((CategoryPlot) plot).getDomainAxis().setTickLabelPaint(new Color(255,255,255,255)); //setting color of coord points
			((CategoryPlot) plot).getRangeAxis().setTickLabelPaint(new Color(255,255,255,255)); //setting color of coord points
			((CategoryPlot) plot).getDomainAxis().setLabelPaint(new Color(255,255,255,255)); //setting color of coord labels
			((CategoryPlot) plot).getRangeAxis().setLabelPaint(new Color(255,255,255,255)); //setting color of coord labels
		}
		return new ChartPanel(chart);
	}

	private JPanel showLineChart(){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(8.96,"Semester","1st");
		dataset.addValue(9.1,"Semester","2nd");
		dataset.addValue(8.74,"Semester","3rd");
		dataset.addValue(7.23,"Semester","4th");
		dataset.addValue(8.37,"Semester","5th");
		dataset.addValue(8.98,"Semester","6th");
		dataset.addValue(6.84,"Semester","7th");
		dataset.addValue(7.62,"Semester","8th");

		JFreeChart chart = ChartFactory.createLineChart("Semester Result","Sem","SGPA",dataset,PlotOrientation.VERTICAL,true,true,false);
		Plot plot = chart.getPlot();
		chart.getTitle().setPaint(Color.WHITE);
		chart.setBackgroundPaint(new Color(0,0,0,120));
		plot.setBackgroundPaint(new Color(255,255,255,50));
		if(plot instanceof CategoryPlot){
			((CategoryPlot) plot).getRenderer().setSeriesPaint(0,Color.YELLOW);
			((CategoryPlot) plot).setDomainGridlinePaint(new Color(255,255,255,100));
			((CategoryPlot) plot).setRangeGridlinePaint(new Color(255,255,255,100));
			((CategoryPlot) plot).getDomainAxis().setTickLabelPaint(new Color(255,255,255,255)); //setting color of coord points
			((CategoryPlot) plot).getRangeAxis().setTickLabelPaint(new Color(255,255,255,255)); //setting color of coord points
			((CategoryPlot) plot).getDomainAxis().setLabelPaint(new Color(255,255,255,255)); //setting color of coord labels
			((CategoryPlot) plot).getRangeAxis().setLabelPaint(new Color(255,255,255,255)); //setting color of coord labels
		}
		return new ChartPanel(chart);
	}

	private JPanel showXYLineChart(){
		XYSeries sem = new XYSeries("Semester");
		sem.add(1,8.96);
		sem.add(2,9.1);
		sem.add(3,8.74);
		sem.add(4,7.23);
		sem.add(5,8.37);
		sem.add(6,8.98);
		sem.add(7,6.84);
		sem.add(8,7.62);

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(sem);

		JFreeChart chart = ChartFactory.createXYLineChart("Semester Result","Sem","SGPA",dataset,PlotOrientation.VERTICAL,true,true,false);
		Plot plot = chart.getPlot();
		chart.getTitle().setPaint(Color.WHITE);
		chart.setBackgroundPaint(new Color(0,0,0,120));
		plot.setBackgroundPaint(new Color(255,255,255,50));
		if(plot instanceof XYPlot){
			((XYPlot) plot).getRenderer().setSeriesPaint(0,Color.YELLOW);
			((XYPlot) plot).setDomainGridlinePaint(new Color(255,255,255,100));
			((XYPlot) plot).setRangeGridlinePaint(new Color(255,255,255,100));
			((XYPlot) plot).getDomainAxis().setTickLabelPaint(new Color(255,255,255,255)); //setting color of coord points
			((XYPlot) plot).getRangeAxis().setTickLabelPaint(new Color(255,255,255,255)); //setting color of coord points
			((XYPlot) plot).getDomainAxis().setLabelPaint(new Color(255,255,255,255)); //setting color of coord labels
			((XYPlot) plot).getRangeAxis().setLabelPaint(new Color(255,255,255,255)); //setting color of coord labels
		}
		return new ChartPanel(chart);
	}
}
