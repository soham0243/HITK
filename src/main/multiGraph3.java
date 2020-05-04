package hitk;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;
import org.jfree.data.category.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.*;

public class multiGraph3 {
	private JFrame mainFrame;
	private JLabel graphTypeLabel,resultTypeLabel,rollLabel;
	private JLabel[] semLbl = new JLabel[8];
	private JPanel chartPanel,selectPanel,containerPanel;
	private JPanel inputPanel,semesterPanel;
	private JButton[] semBtn = new JButton[8];
	public double[] sgpa = new double[8];
	public String[] all_result = new String[8];
	private String[][] tmp_result = new String[50][7];
	private Color hoverBackgroundColor,pressedBackgroundColor;

	public multiGraph3(){
		//prepareGUI();
	}

	public static void main(String[] args){
		multiGraph3 graph = new multiGraph3();
		choiceActivity cAct = new choiceActivity();
		graph.multiGraphChoice(graph.sgpa,cAct.myr_1);
	}

	public void multiGraphChoice(double[] s_sample,int index){
		choiceActivity myGr = new choiceActivity();
		Font f = new Font("Serif",Font.BOLD,14);
		mainFrame = new JFrame("HITK Semester Result Viewer");
		mainFrame.setSize(707,707);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int option = JOptionPane.showConfirmDialog(mainFrame,"Are you sure to exit?","Multiple Graph",JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION){
					System.exit(0);
				}else{
					mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
				}
			}
		});
		mainFrame.addComponentListener(new ComponentListener(){
			@Override
			public void componentHidden(ComponentEvent e){}
			@Override
			public void componentShown(ComponentEvent e){}
			@Override
			public void componentMoved(ComponentEvent e){

			}
			@Override
			public void componentResized(ComponentEvent e){
				mainFrame.setSize(706,706);
			}
		});
		ImageIcon background_image = new ImageIcon(getClass().getResource("../res/sample.jpeg"));
		Image img = background_image.getImage();
		Image tmp_img = img.getScaledInstance(mainFrame.getSize().width,mainFrame.getSize().height,Image.SCALE_SMOOTH);
		ImageIcon temp_img = new ImageIcon(tmp_img);
		mainFrame.setContentPane(new JLabel(temp_img));
		mainFrame.setLayout(null);
		inputPanel = new JPanel();
		inputPanel.setBackground(new Color(0,0,0,120));
		inputPanel.setBounds(141,58,424,141);
		inputPanel.setLayout(null);
		rollLabel = new JLabel("Autonomy Roll : ");
		rollLabel.setFont(f);
		rollLabel.setForeground(Color.WHITE);
		rollLabel.setBounds(35,28,141,28);
		
		JTextField userText = new JTextField(10);
		userText.setBounds(233,28,155,28);
		userText.setBackground(new Color(66,245,236,90));
		userText.setForeground(Color.WHITE);
		userText.setOpaque(false);

		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(35,85,106,28);
		submitButton.setForeground(Color.WHITE);
		submitButton.setBackground(new Color(66,245,236,90));
		submitButton.setOpaque(false);
		submitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if((userText.getText()+"").equals("")){
					JOptionPane.showMessageDialog(mainFrame,"Please enter Autonomy Roll No.");
				}else{
					if(userText.getText().length()==11 && userText.getText().charAt(0)=='1' && userText.getText().charAt(1)=='2' && userText.getText().charAt(2)=='6'){
						try{
							long tlong = Long.parseLong(userText.getText());
							myGr.prepareOption(userText.getText(),""+userText.getText().charAt(3)+userText.getText().charAt(4),0,false);
							mainFrame.dispose();
						}catch(Exception ex){
							JOptionPane.showMessageDialog(mainFrame,"Autonomy roll must not contain alphabet");
							ex.printStackTrace();
						}
					}else{
						JOptionPane.showMessageDialog(mainFrame,"Please enter roll correctly");
					}
				}
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
				userText.setText("");
			}
		});

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

		multiGraph3 chartObj1 = new multiGraph3();

		containerPanel.removeAll();
		containerPanel.setSize(0,0);
		chartPanel = chartObj1.showBarChart(s_sample,index);
		//JOptionPane.showMessageDialog(mainFrame,"Passed var : "+all_result[0][1][0]+" index "+all_result[0][1][3]);
		chartPanel.setSize(396,259);
		containerPanel.setSize(chartPanel.getSize());
		containerPanel.setBounds(56,248,396,401);
		chartPanel.setBounds(0,141,396,259);
		chartPanel.setBackground(new Color(0,0,0,120));
		containerPanel.add(chartPanel,null);
		containerPanel.revalidate();

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
				multiGraph3 chartObj = new multiGraph3();
				if(choice.equals("Bar Chart")){
					containerPanel.removeAll();
					containerPanel.setSize(0,0);
					chartPanel = chartObj.showBarChart(s_sample,index);
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
					chartPanel = chartObj.showBarChart3D(s_sample,index);
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
					chartPanel = chartObj.showLineChart(s_sample,index);
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
					chartPanel = chartObj.showXYLineChart(s_sample,index);
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
			/*semBtn[i].addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					semViewer sview = new semViewer();
					try{
						sview.showSemResult(var_i[var_i1]);
					}catch(Exception ex){
						JOptionPane.showMessageDialog(mainFrame,"index : "+var_i1);
					}
				}
			});*/

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

			if(i<index){
				semLbl[i] = new JLabel(String.format("%.2f",s_sample[i]),JLabel.CENTER);
			}else{
				semLbl[i] = new JLabel("N/A",JLabel.CENTER);
			}
			semLbl[i].setFont(f);
			semLbl[i].setForeground(Color.WHITE);

			semesterPanel.add(semBtn[i]);
			semesterPanel.add(semLbl[i]);
		}

		semBtn[0].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				semViewer sview = new semViewer();
				try{
					for(int i=0;i<20;i++){
						for(int j=0;j<7;j++){
							tmp_result[i][j] = "";
						}
					}
					Scanner sc = new Scanner(all_result[0]);
					int in = 0;
					while(sc.hasNextLine()){
						String str = sc.nextLine();
						String str1="";
						int c=0;
						for(int i=0;i<str.length();i++){
							if(str.charAt(i)!=';'){
								str1=str1+str.charAt(i);
							}else{
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
							if(i==str.length()-1){
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
						}
						in++;
					}
					sview.showSemResult(tmp_result,0);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(mainFrame,"1st semester result is not available!");
					ex.printStackTrace();
				}
			}
		});
		semBtn[1].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				semViewer sview = new semViewer();
				try{
					for(int i=0;i<20;i++){
						for(int j=0;j<7;j++){
							tmp_result[i][j] = "";
						}
					}
					Scanner sc = new Scanner(all_result[1]);
					int in = 0;
					while(sc.hasNextLine()){
						String str = sc.nextLine();
						String str1="";
						int c=0;
						for(int i=0;i<str.length();i++){
							if(str.charAt(i)!=';'){
								str1=str1+str.charAt(i);
							}else{
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
							if(i==str.length()-1){
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
						}
						in++;
					}
					sview.showSemResult(tmp_result,1);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(mainFrame,"2nd semester result is not available!");
					//ex.printStackTrace();
				}
			}
		});
		semBtn[2].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				semViewer sview = new semViewer();
				try{
					for(int i=0;i<20;i++){
						for(int j=0;j<7;j++){
							tmp_result[i][j] = "";
						}
					}
					Scanner sc = new Scanner(all_result[2]);
					int in = 0;
					while(sc.hasNextLine()){
						String str = sc.nextLine();
						String str1="";
						int c=0;
						for(int i=0;i<str.length();i++){
							if(str.charAt(i)!=';'){
								str1=str1+str.charAt(i);
							}else{
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
							if(i==str.length()-1){
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
						}
						in++;
					}
					sview.showSemResult(tmp_result,2);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(mainFrame,"3rd semester result is not available!");
					//ex.printStackTrace();
				}
			}
		});
		semBtn[3].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				semViewer sview = new semViewer();
				try{
					for(int i=0;i<20;i++){
						for(int j=0;j<7;j++){
							tmp_result[i][j] = "";
						}
					}
					Scanner sc = new Scanner(all_result[3]);
					int in = 0;
					while(sc.hasNextLine()){
						String str = sc.nextLine();
						String str1="";
						int c=0;
						for(int i=0;i<str.length();i++){
							if(str.charAt(i)!=';'){
								str1=str1+str.charAt(i);
							}else{
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
							if(i==str.length()-1){
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
						}
						in++;
					}
					sview.showSemResult(tmp_result,3);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(mainFrame,"4th semester result is not available!");
					//ex.printStackTrace();
				}
			}
		});
		semBtn[4].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				semViewer sview = new semViewer();
				try{
					for(int i=0;i<20;i++){
						for(int j=0;j<7;j++){
							tmp_result[i][j] = "";
						}
					}
					Scanner sc = new Scanner(all_result[4]);
					int in = 0;
					while(sc.hasNextLine()){
						String str = sc.nextLine();
						String str1="";
						int c=0;
						for(int i=0;i<str.length();i++){
							if(str.charAt(i)!=';'){
								str1=str1+str.charAt(i);
							}else{
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
							if(i==str.length()-1){
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
						}
						in++;
					}
					sview.showSemResult(tmp_result,4);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(mainFrame,"5th semester result is not available!");
					//ex.printStackTrace();
				}
			}
		});
		semBtn[5].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				semViewer sview = new semViewer();
				try{
					for(int i=0;i<20;i++){
						for(int j=0;j<7;j++){
							tmp_result[i][j] = "";
						}
					}
					Scanner sc = new Scanner(all_result[5]);
					int in = 0;
					while(sc.hasNextLine()){
						String str = sc.nextLine();
						String str1="";
						int c=0;
						for(int i=0;i<str.length();i++){
							if(str.charAt(i)!=';'){
								str1=str1+str.charAt(i);
							}else{
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
							if(i==str.length()-1){
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
						}
						in++;
					}
					sview.showSemResult(tmp_result,5);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(mainFrame,"6th semester result is not available!");
					//ex.printStackTrace();
				}
			}
		});
		semBtn[6].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				semViewer sview = new semViewer();
				try{
					for(int i=0;i<20;i++){
						for(int j=0;j<7;j++){
							tmp_result[i][j] = "";
						}
					}
					Scanner sc = new Scanner(all_result[6]);
					int in = 0;
					while(sc.hasNextLine()){
						String str = sc.nextLine();
						String str1="";
						int c=0;
						for(int i=0;i<str.length();i++){
							if(str.charAt(i)!=';'){
								str1=str1+str.charAt(i);
							}else{
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
							if(i==str.length()-1){
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
						}
						in++;
					}
					sview.showSemResult(tmp_result,6);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(mainFrame,"7th semester result is not available!");
					//ex.printStackTrace();
				}
			}
		});
		semBtn[7].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				semViewer sview = new semViewer();
				try{
					for(int i=0;i<20;i++){
						for(int j=0;j<7;j++){
							tmp_result[i][j] = "";
						}
					}
					Scanner sc = new Scanner(all_result[7]);
					int in = 0;
					while(sc.hasNextLine()){
						String str = sc.nextLine();
						String str1="";
						int c=0;
						for(int i=0;i<str.length();i++){
							if(str.charAt(i)!=';'){
								str1=str1+str.charAt(i);
							}else{
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
							if(i==str.length()-1){
								tmp_result[in][c]=str1;
								c++;
								str1="";
							}
						}
						in++;
					}
					sview.showSemResult(tmp_result,7);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(mainFrame,"8th semester result is not available!");
					//ex.printStackTrace();
				}
			}
		});
		

		mainFrame.add(selectPanel);
		mainFrame.add(containerPanel);
		mainFrame.add(semesterPanel);
		mainFrame.add(inputPanel);
		mainFrame.setVisible(true);
	}

	private JPanel showBarChart(double[] dset,int indx){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;i<indx;i++){
			int tmp_i = i+1;
			String indlbl = "";
			if(tmp_i==1){
				indlbl = tmp_i+"st";
			}else if(tmp_i==2){
				indlbl = tmp_i+"nd";
			}else if(tmp_i==3){
				indlbl = tmp_i+"rd";
			}else{
				indlbl = tmp_i+"th";
			}if(dset[i]>0.001){
				dataset.addValue(dset[i],"Semester",indlbl);
			}
		}

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

	private JPanel showBarChart3D(double[] dset,int indx){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;i<indx;i++){
			int tmp_i = i+1;
			String indlbl = "";
			if(tmp_i==1){
				indlbl = tmp_i+"st";
			}else if(tmp_i==2){
				indlbl = tmp_i+"nd";
			}else if(tmp_i==3){
				indlbl = tmp_i+"rd";
			}else{
				indlbl = tmp_i+"th";
			}if(dset[i]>0.001){
				dataset.addValue(dset[i],"Semester",indlbl);
			}
		}

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

	private JPanel showLineChart(double[] dset,int indx){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;i<indx;i++){
			int tmp_i = i+1;
			String indlbl = "";
			if(tmp_i==1){
				indlbl = tmp_i+"st";
			}else if(tmp_i==2){
				indlbl = tmp_i+"nd";
			}else if(tmp_i==3){
				indlbl = tmp_i+"rd";
			}else{
				indlbl = tmp_i+"th";
			}if(dset[i]>0.001){
				dataset.addValue(dset[i],"Semester",indlbl);
			}
		}

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

	private JPanel showXYLineChart(double[] dset,int indx){
		XYSeries sem = new XYSeries("Semester");
		for(int i=0;i<indx;i++){
			int tmp_i = i+1;
			if(dset[i]>0.001){
				sem.add(tmp_i,dset[i]);
			}
		}

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
