package hitk;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class choiceActivity{
	private JFrame optionFrame;
	private JLabel semOption,semOptionLabel;
	private JLabel[] semLbl = new JLabel[8];
	private JPanel optionPanel,radioPanel,semYearPanel1,semYearPanel2;
	private JRadioButton regRadio,latRadio,backRadio;
	private JButton okBtn,cancelBtn;
	public int myr_1 = 0,myr = 0,i1 = 0,lat = 0,pubr = 0;
	private boolean is_reg = false,is_back = false,is_lat = false;
	public static int[] par = new int[8];
	private String[][] i_res = new String[20][7];

	public void prepareOption(String auto_roll,String yr,int la,boolean is_check){
		Font f = new Font("Serif",Font.BOLD,14);
		optionFrame = new JFrame("Configure Options");
		optionFrame.setSize(630,630);
		optionFrame.setLayout(new BorderLayout());
		
		optionFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				int option = JOptionPane.showConfirmDialog(optionFrame,"Are you sure to return to the main window?","Multiple Graph",JOptionPane.YES_NO_CANCEL_OPTION);
				if(option == JOptionPane.YES_OPTION){
					multiGraph3 m_g2 = new multiGraph3();
					choiceActivity cct = new choiceActivity();
					m_g2.multiGraphChoice(m_g2.sgpa,cct.myr_1);
					optionFrame.dispose();
				}else if(option == JOptionPane.NO_OPTION){
					System.exit(0);
				}else{
					optionFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
				}
			}
		});
		ImageIcon background_image = new ImageIcon(getClass().getResource("../res/sample.jpeg"));
		Image img = background_image.getImage();
		Image tmp_img = img.getScaledInstance(optionFrame.getSize().width,optionFrame.getSize().height,Image.SCALE_SMOOTH);
		ImageIcon temp_img = new ImageIcon(tmp_img);
		optionFrame.setContentPane(new JLabel(temp_img));

		semOption = new JLabel("You are ",JLabel.CENTER);
		semOption.setFont(f);
		semOption.setForeground(Color.WHITE);
		semOption.setBounds(22,27,229,22);

		semOptionLabel = new JLabel("Enter the corresponding year of each semester");
		semOptionLabel.setFont(f);
		semOptionLabel.setForeground(Color.WHITE);
		semOptionLabel.setBounds(67,67,540,22);

		regRadio = new JRadioButton("Regular");
		regRadio.setOpaque(false);
		regRadio.setForeground(Color.WHITE);
		regRadio.setFont(f);
		regRadio.setBounds(0,0,100,22);
		regRadio.setSelected(!is_check);
		regRadio.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange()==1){
					is_reg = true;
					is_lat = false;
					lat = 0;
					choiceActivity cct = new choiceActivity();
					cct.prepareOption(auto_roll,yr,lat,is_lat);
					optionFrame.dispose();
				};
			}
		});
		latRadio = new JRadioButton("Lateral");
		latRadio.setOpaque(false);
		latRadio.setForeground(Color.WHITE);
		latRadio.setFont(f);
		latRadio.setBounds(0,103,100,22);
		latRadio.setSelected(is_check);
		latRadio.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange()==1){
					is_reg = false;
					is_lat = true;
					lat = 2;
					choiceActivity cct = new choiceActivity();
					cct.prepareOption(auto_roll,yr,lat,is_lat);
					optionFrame.dispose();
				};
			}
		});
		backRadio = new JRadioButton("Backlog");
		backRadio.setOpaque(false);
		backRadio.setForeground(Color.WHITE);
		backRadio.setFont(f);
		backRadio.setBounds(0,207,100,22);
		backRadio.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e){
				is_back = (e.getStateChange()==1?true:false);
			}
		});

		ButtonGroup group = new ButtonGroup();
		group.add(regRadio);
		group.add(latRadio);

		radioPanel = new JPanel();
		radioPanel.setBackground(new Color(0,0,0,0));
		radioPanel.setBounds(252,22,310,22);
		radioPanel.setLayout(new FlowLayout());

		radioPanel.add(regRadio);
		radioPanel.add(latRadio);
		radioPanel.add(backRadio);

		semYearPanel1 = new JPanel();
		semYearPanel1.setBounds(22,112,264,360);
		semYearPanel1.setBackground(new Color(0,0,0,0));
		semYearPanel1.setLayout(new GridLayout(4,2));
		semYearPanel2 = new JPanel();
		semYearPanel2.setBounds(297,112,264,360);
		semYearPanel2.setBackground(new Color(0,0,0,0));
		semYearPanel2.setLayout(new GridLayout(4,2));

		DefaultComboBoxModel[] semTh = new DefaultComboBoxModel[8];
		for(int j=0;j<8;j++){
			semTh[j] = new DefaultComboBoxModel<String>();
			int[] semYr = {2000+Integer.parseInt(yr),2000+Integer.parseInt(yr)+1,2000+Integer.parseInt(yr)+2,2000+Integer.parseInt(yr)+3,2000+Integer.parseInt(yr)+4,2000+Integer.parseInt(yr)+5,2000+Integer.parseInt(yr)+6,2000+Integer.parseInt(yr)+7};
			myr = 0;
			for(int i=0;i < 8;i++){
				if(semYr[i]>Calendar.getInstance().get(Calendar.YEAR)){
					break;
				}
				semTh[j].addElement(""+semYr[i]);
				myr++;
			}
		}
		JComboBox[] semCombo = new JComboBox[8];
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
			}if(i<la){
				JLabel tmpLbl1 = new JLabel(btnlbl,JLabel.CENTER);
				tmpLbl1.setFont(f);
				tmpLbl1.setBackground(new Color(0,0,0,0));
				tmpLbl1.setForeground(Color.WHITE);
				JLabel tmpLbl2 = new JLabel("N/A",JLabel.CENTER);
				tmpLbl2.setFont(f);
				tmpLbl2.setBackground(new Color(0,0,0,120));
				tmpLbl2.setForeground(Color.WHITE);
				semYearPanel1.add(tmpLbl1);
				semYearPanel1.add(tmpLbl2);
			}else if(i<4){
				if(i>=2*myr-2){
					JLabel tmpLbl1 = new JLabel(btnlbl,JLabel.CENTER);
					tmpLbl1.setFont(f);
					tmpLbl1.setBackground(new Color(0,0,0,0));
					tmpLbl1.setForeground(Color.WHITE);
					JLabel tmpLbl2 = new JLabel("N/A",JLabel.CENTER);
					tmpLbl2.setFont(f);
					tmpLbl2.setBackground(new Color(0,0,0,120));
					tmpLbl2.setForeground(Color.WHITE);
					semYearPanel1.add(tmpLbl1);
					semYearPanel1.add(tmpLbl2);
				}else{
					semLbl[i] = new JLabel(btnlbl,JLabel.CENTER);
					semLbl[i].setFont(f);
					semLbl[i].setForeground(Color.WHITE);
		
					semCombo[i] = new JComboBox<String>(semTh[i]);
					if(i>0){
						semCombo[i].setSelectedIndex((i+1)/2);
					}
					semCombo[i].setBackground(new Color(0,0,0,120));
					semCombo[i].setForeground(Color.WHITE);
					semCombo[i].addItemListener(new ItemListener(){
						@Override
						public void itemStateChanged(ItemEvent e){
							try{
								semCombo[i1].setSelectedIndex(semCombo[i1].getSelectedIndex());
							}catch(Exception e1){
								//System.out.println(semCombo[i1].getSelectedIndex());
								JOptionPane.showMessageDialog(optionFrame,"index : "+semCombo[i1].getSelectedIndex());
							}
							/*if(i1>0){
								if(Integer.parseInt(""+semCombo[i1-1].getItemAt(semCombo[i1-1].getSelectedIndex()))>Integer.parseInt(""+semCombo[i1-1].getItemAt(semCombo[i1-1].getSelectedIndex()))){
									JOptionPane.showMessageDialog(optionFrame,"Please make sure whether you have any backlog or not.");
								}
							}*/
						}
					});
					JPanel tempPanel = new JPanel();
					tempPanel.setBackground(new Color(0,0,0,120));
					tempPanel.setLayout(null);
					JScrollPane tempScrollPane = new JScrollPane(semCombo[i]);
					tempPanel.add(tempScrollPane);
					tempScrollPane.setBounds(18,33,126,22);
	
					semYearPanel1.add(semLbl[i]);
					semYearPanel1.add(tempPanel);
				}
			}else{
				if(i>=2*myr-2){
					int ii = 2*myr-2;
					JLabel tmpLbl1 = new JLabel(btnlbl,JLabel.CENTER);
					tmpLbl1.setFont(f);
					tmpLbl1.setBackground(new Color(0,0,0,0));
					tmpLbl1.setForeground(Color.WHITE);
					JLabel tmpLbl2 = new JLabel("N/A",JLabel.CENTER);
					tmpLbl2.setFont(f);
					tmpLbl2.setBackground(new Color(0,0,0,120));
					tmpLbl2.setForeground(Color.WHITE);
					semYearPanel2.add(tmpLbl1);
					semYearPanel2.add(tmpLbl2);
				}else{
					semLbl[i] = new JLabel(btnlbl,JLabel.CENTER);
					semLbl[i].setFont(f);
					semLbl[i].setForeground(Color.WHITE);
		
					semCombo[i] = new JComboBox<String>(semTh[i]);
					semCombo[i].setSelectedIndex((i+1)/2);
					semCombo[i].setBackground(new Color(0,0,0,120));
					semCombo[i].setForeground(Color.WHITE);
					semCombo[i].addItemListener(new ItemListener(){
						@Override
						public void itemStateChanged(ItemEvent e){
							if(i1>0){
							//	if(Integer.parseInt(""+semCombo[i1-1].getItemAt(semCombo[i1-1].getSelectedIndex()))>Integer.parseInt(""+semCombo[i1-1].getItemAt(semCombo[i1-1].getSelectedIndex()))){
									//JOptionPane.showMessageDialog(optionFrame,"Please make sure whether you have any backlog or not.");
								//}
							}
						}
					});
					JPanel tempPanel = new JPanel();
					tempPanel.setBackground(new Color(0,0,0,120));
					tempPanel.setLayout(null);
					JScrollPane tempScrollPane = new JScrollPane(semCombo[i]);
					tempPanel.add(tempScrollPane);
					tempScrollPane.setBounds(18,33,126,22);

					semYearPanel2.add(semLbl[i]);
					semYearPanel2.add(tempPanel);
				}
			}
		}
		okBtn = new JButton("OK");
		okBtn.setForeground(Color.WHITE);
		okBtn.setBackground(new Color(0,0,0,120));
		okBtn.setOpaque(false);
		okBtn.setBounds(22,495,132,22);
		okBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				multiGraph3 m_g = new multiGraph3();
				choiceActivity cact = new choiceActivity();
				semParser parse = new semParser();
				for(int i=0+la;i<2*myr-2;i++){
					parse.showResult(auto_roll,i+1,""+semCombo[i].getItemAt(semCombo[i].getSelectedIndex()));
					i_res = parse.setIntent();
					pubr = parse.expNum();
					if(!parse.exception){
						m_g.all_result[i] = "";
						for(int j=1;j<pubr-1;j++){
							for(int k=0;k<6;k++){
								m_g.all_result[i] += i_res[j][k]+";";
							}m_g.all_result[i]+="\n";
						}
						if((Integer.parseInt(""+semCombo[i].getItemAt(semCombo[i].getSelectedIndex()))==2019 && ((i+1)%2==1)) || Integer.parseInt	(""+semCombo[i].getItemAt(semCombo[i].getSelectedIndex()))>2019){
							cact.par[i] = pubr;
							try{
								m_g.sgpa[i] = Double.parseDouble(i_res[pubr-1][5])/Double.parseDouble(i_res[pubr-1][4]);
							}catch(Exception eee){
								m_g.sgpa[i] = 0.0;
							}
						}else{
							cact.par[i] = pubr;
							try{
								m_g.sgpa[i] = Double.parseDouble(i_res[pubr-1][5])/Double.parseDouble(i_res[pubr-1][4]);
							}catch(Exception eee){
								m_g.sgpa[i] = 0.0;
							}
						}
					}else{
						m_g.all_result[i] = "";
						for(int j=1;j<pubr-1;j++){
							for(int k=0;k<6;k++){
								m_g.all_result[i] += i_res[j][k]+";";
							}m_g.all_result[i]+="\n";
						}
						if((Integer.parseInt(""+semCombo[i].getItemAt(semCombo[i].getSelectedIndex()))==2019 && ((i+1)%2==1)) || Integer.parseInt	(""+semCombo[i].getItemAt(semCombo[i].getSelectedIndex()))>2019){
							cact.par[i] = pubr;
							try{
								m_g.sgpa[i] = Double.parseDouble(i_res[pubr-1][5])/Double.parseDouble(i_res[pubr-1][4]);
							}catch(Exception eee){
								m_g.sgpa[i] = 0.0;
							}
						}else{
							cact.par[i] = pubr;
							try{
								m_g.sgpa[i] = Double.parseDouble(i_res[pubr-1][5])/Double.parseDouble(i_res[pubr-1][4]);
							}catch(Exception eee){
								m_g.sgpa[i] = 0.0;
							}
						}
					}
				}
				cact.myr_1 = 2*myr-2;
				
				m_g.multiGraphChoice(m_g.sgpa,cact.myr_1);
				//JOptionPane.showMessageDialog(optionFrame,"par[5] : "+cact.par[5]+" par[6] : "+cact.par[6]);
				optionFrame.dispose();
			}
		});
		cancelBtn = new JButton("Cancel");
		cancelBtn.setForeground(Color.WHITE);
		cancelBtn.setBackground(new Color(0,0,0,120));
		cancelBtn.setOpaque(false);
		cancelBtn.setBounds(421,495,132,22);
		cancelBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				multiGraph3 m_g = new multiGraph3();
				choiceActivity cact = new choiceActivity();
				m_g.multiGraphChoice(m_g.sgpa,cact.myr_1);
				optionFrame.dispose();
			}
		});

		optionPanel = new JPanel();
		optionPanel.setBackground(new Color(0,0,0,120));
		optionPanel.setBounds(22,22,585,562);
		optionPanel.setLayout(null);

		optionPanel.add(semOption);
		optionPanel.add(semOptionLabel);
		optionPanel.add(radioPanel);
		optionPanel.add(semYearPanel1);
		optionPanel.add(semYearPanel2);
		optionPanel.add(okBtn);
		optionPanel.add(cancelBtn);
		JOptionPane.showMessageDialog(optionFrame,"Autonomy roll : "+auto_roll);

		optionFrame.add(optionPanel);
		optionFrame.setVisible(true);
	}
}
