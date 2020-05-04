package hitk;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;
import org.jfree.data.category.*;

public class semViewer{
	private JFrame semFrame;
	private JLabel graphTypeLabel;
	private JPanel selectPanel,graphTypePanel,containerPanel,chartPanel;
	private JButton okBtn,printBtn;
	public void showSemResult(String[][] all_res,int sem){
		semParser parse = new semParser();
		choiceActivity chact = new choiceActivity();
		int tmp_i = sem+1;
		String indlbl = "";
		if(tmp_i==1){
			indlbl = tmp_i+"st";
		}else if(tmp_i==2){
			indlbl = tmp_i+"nd";
		}else if(tmp_i==3){
			indlbl = tmp_i+"rd";
		}else{
			indlbl = tmp_i+"th";
		}
		Font f = new Font("Serif",Font.BOLD,14);
		semFrame = new JFrame(indlbl+" Semester Result");
		semFrame.setSize(446,453);
		semFrame.setLayout(new BorderLayout());
		semFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int option = JOptionPane.showConfirmDialog(semFrame,"Are you sure to close this window?","Semester Result",JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION){
					semFrame.dispose();
				}else{
					semFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
				}
			}
		});
		semFrame.addComponentListener(new ComponentListener(){
			@Override
			public void componentHidden(ComponentEvent e){}
			@Override
			public void componentShown(ComponentEvent e){}
			@Override
			public void componentMoved(ComponentEvent e){

			}
			@Override
			public void componentResized(ComponentEvent e){
				semFrame.setSize(446,456);
			}
		});
		ImageIcon background_image = new ImageIcon(getClass().getResource("../res/sample.jpeg"));
		Image img = background_image.getImage();
		Image tmp_img = img.getScaledInstance(semFrame.getSize().width,semFrame.getSize().height,Image.SCALE_SMOOTH);
		ImageIcon temp_img = new ImageIcon(tmp_img);
		semFrame.setContentPane(new JLabel(temp_img));
		semFrame.setLayout(null);

		okBtn = new JButton("OK");
		okBtn.setBounds(25,382,106,28);
		okBtn.setForeground(Color.WHITE);
		okBtn.setBackground(new Color(0,0,0,120));
		okBtn.setOpaque(true);
		okBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				semFrame.dispose();
			}
		});

		printBtn = new JButton("Print");
		printBtn.setBounds(315,382,106,28);
		printBtn.setForeground(Color.WHITE);
		printBtn.setBackground(new Color(0,0,0,120));
		printBtn.setOpaque(true);
		printBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){

			}
		});

		selectPanel = new JPanel();
		selectPanel.setBackground(new Color(0,0,0,120));
		selectPanel.setLayout(null);
		selectPanel.setBounds(25,25,396,84);

		graphTypeLabel = new JLabel("Graph Type : ");
		graphTypeLabel.setFont(f);
		graphTypeLabel.setForeground(Color.WHITE);
		graphTypeLabel.setBounds(35,28,106,28);

		containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setBackground(new Color(0,0,0,120));
		containerPanel.setBounds(25,25,396,344);

		semViewer chartObj1 = new semViewer();

		containerPanel.removeAll();
		containerPanel.setSize(0,0);
		chartPanel = chartObj1.showBarGraph(all_res,sem,chact.par[sem]);
		chartPanel.setSize(396,259);
		containerPanel.setSize(chartPanel.getSize());
		containerPanel.setBounds(25,25,396,344);
		chartPanel.setBounds(0,84,396,259);
		chartPanel.setBackground(new Color(0,0,0,120));
		containerPanel.add(chartPanel,null);
		containerPanel.revalidate();

		DefaultComboBoxModel<String> chartName = new DefaultComboBoxModel<>();
		chartName.addElement("Bar Chart");
		chartName.addElement("3D Bar Chart");
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
				semViewer chartObj = new semViewer();
				if(choice.equals("Bar Chart")){
					containerPanel.removeAll();
					containerPanel.setSize(0,0);
					chartPanel = chartObj.showBarGraph(all_res,sem,chact.par[sem]);
					chartPanel.setSize(396,259);
					containerPanel.setSize(chartPanel.getSize());
					containerPanel.setBounds(25,25,396,344);
					chartPanel.setBounds(0,84,396,259);
					chartPanel.setBackground(new Color(0,0,0,120));
					containerPanel.add(chartPanel,null);
					containerPanel.revalidate();
				}
				if(choice.equals("3D Bar Chart")){
					containerPanel.removeAll();
					containerPanel.setSize(0,0);
					chartPanel = chartObj.showBarGraph3D(all_res,sem,chact.par[sem]);
					chartPanel.setSize(396,259);
					containerPanel.setSize(chartPanel.getSize());
					containerPanel.setBounds(25,25,396,344);
					chartPanel.setBounds(0,84,396,259);
					chartPanel.setBackground(new Color(0,0,0,120));
					containerPanel.add(chartPanel,null);
					containerPanel.revalidate();
				}
			}
		});

		selectPanel.add(graphTypeLabel);
		selectPanel.add(graphScrollPane);

		semFrame.add(selectPanel);
		semFrame.add(containerPanel);
		semFrame.add(okBtn);
		semFrame.add(printBtn);
		semFrame.setVisible(true);
	}

	public JPanel showBarGraph(String[][] all_res,int sm,int indx){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;i<indx-1;i++){
			try{
				dataset.addValue(Double.parseDouble(all_res[i][3]),all_res[i][0],all_res[i][0]);
			}catch(Exception exception){
				//JOptionPane.showMessageDialog(semFrame,""+all_res[i][3]);
			}
		}
		/*dataset.addValue(8.1,"Semester","1st");
		dataset.addValue(9.4,"Semester","2nd");
		dataset.addValue(7.7,"Semester","3rd");
		dataset.addValue(8.6,"Semester","4th");
		dataset.addValue(6.9,"Semester","5th");
		dataset.addValue(8.9,"Semester","6th");*/

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

	public JPanel showBarGraph3D(String[][] all_res,int sm,int indx){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;i<indx-1;i++){
			try{
				dataset.addValue(Double.parseDouble(all_res[i][3]),all_res[i][0],all_res[i][0]);
			}catch(Exception exception){
				
			}
		}
		/*dataset.addValue(8.1,"Semester","1st");
		dataset.addValue(9.4,"Semester","2nd");
		dataset.addValue(7.7,"Semester","3rd");
		dataset.addValue(8.6,"Semester","4th");
		dataset.addValue(6.9,"Semester","5th");
		dataset.addValue(8.9,"Semester","6th");*/

		JFreeChart chart = ChartFactory.createBarChart3D("Semester Result","Sem","SGPA",dataset,PlotOrientation.VERTICAL,true,true,false);
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
}
