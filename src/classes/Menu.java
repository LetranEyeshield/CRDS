package classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Menu extends JFrame implements ActionListener, MenuListener{
	
	JLabel title;
	
	JButton birth, marriage, death;
	

	JMenuBar bar;
	JMenu about, staffs, exit;

	JMenuItem creator, system, dedications;

	JMenuItem Eyeshield, Melvin, Danny, Marie, Jobert, Norylyn, Shiela;
	
	Info infos = new Info();
	
	public Menu() {
		super("MCR DATABASE SYSTEM");
		setLayout(null);
		
		setTitle();
		setButtons();
		setMenus();
		setBackground();
	}

	public void setTitle(){
		
		title = new JLabel("                                  " +
				"              MCR DATABASE SYSTEM");
		title.setBounds(0,0,900,45);
		title.setOpaque(true);
		title.setBackground(Color.blue);
		title.setFont(new Font("OLD ENGLISH MT",Font.BOLD,20));
		title.setForeground(Color.WHITE);
		add(title);
	}
	
	public void setBackground(){

		JLabel background = new JLabel(new ImageIcon("res/M_Logo.jpg"));
		 background.setBounds(0,-100,800,550);
		// background.setBackground(new Color(0,0,255));
		// background.setBackground(Color.LIGHT_GRAY);
		 background.setOpaque(true);
		 add(background);
	}
	
	public void setButtons() {
		
		birth = new JButton("BIRTH RECORDS");
		birth.setFont(new Font("Tahoma",Font.BOLD,15));
		birth.setBounds(50,330,200,50);
		birth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				BirthGui B = new BirthGui();
				B.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				B.setSize(1100,680);
				B.setVisible(true);
				B.setLocation(100,0);
				B.setResizable(false);
			}
			
		});
		add(birth);
		
		marriage = new JButton("MARRIAGE RECORDS");
		marriage.setFont(new Font("Tahoma",Font.BOLD,15));
		marriage.setBounds(290,330,200,50);
		marriage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MarriageGui M = new MarriageGui();
				M.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				M.setSize(1100,700);
				M.setVisible(true);
				M.setLocation(100,0);
				M.setResizable(false);
			}
			
		});
		add(marriage);
		
		death = new JButton("DEATH RECORDS");
		death.setFont(new Font("Tahoma",Font.BOLD,15));
		death.setBounds(530,330,200,50);
		death.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				DeathGui D = new DeathGui();
				D.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				D.setSize(1100,680);
				D.setVisible(true);
				D.setLocation(100,0);
				D.setResizable(false);
			}
			
		});
		add(death);
		
		
	}
	
	public void setMenus(){
		bar = new JMenuBar();
		setJMenuBar(bar);

		about = new JMenu("About");
		staffs = new JMenu("MCR Staffs");
		exit = new JMenu("Exit");
		exit.addMenuListener(this);


		Eyeshield = new JMenuItem("Michael Cris Rosalin");
		Eyeshield.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent j){

				infos.Eyeshield();

			}
		});

		
		staffs.add(Eyeshield);
		

		bar.add(about);
		bar.add(staffs);
		bar.add(exit);


	}
	
	public void closeMe() {
		this.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuCanceled(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuSelected(MenuEvent arg0) {
		// TODO Auto-generated method stub
		closeMe();
	}

}
