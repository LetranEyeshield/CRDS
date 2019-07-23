package classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;

public class MarriageGui extends JFrame implements ActionListener, MouseListener, KeyListener, Printable{
	
	JLabel title, labels;
	
	String mid;
	
	JTextField page_no,
			book_no,
			reg_no,
			reg_date,
			marriage_date,
			marriage_place;
	
	//husband info
	JTextField H_first_name,
			H_middle_name,
			H_last_name,
			H_age,
			H_citizen,
			H_Status,
			H_Father,
			H_Mother;

	//wife info
	JTextField W_first_name,
			W_middle_name,
			W_last_name,
			W_age,
			W_citizen,
			W_Status,
			W_Father,
			W_Mother;
	
	
	JTable table;
	DefaultTableModel model;

	JTextArea remarks;
	JScrollPane scroll;

	//VARS PARA SA PRINTING
	Graphics2D g2;
	Image logo;
	Font font1, font2, font3, font4, font5;
	String issueTo="", local_abroad, certType, certTypePayment, or_no, signer,signer2,getSigner,forAndBy,position1,position2,line1,line2;

	JLabel panelLine1,panelLine2,panelLine3;

	JTextField search_husband,search_wife;

	String driver = "org.gjt.mm.mysql.Driver";
	String url = "jdbc:mysql://localhost:3306/crds";
	String username = "root";
	String password = "";
	Connection connection;
	Statement stmt;
	ResultSet rs;
	PreparedStatement ps;

	JButton add, edit, clear, exit, print;
	
	Marriage marriageEntity = new Marriage();
	
	public MarriageGui() {
		super("MCR DATABASE SYSTEM");
		setLayout(null);
		
		setTitle();
		setLabels();
		setFields();
		tableModel();
		setButtons();
		
		setPanelLines();
	}
	
	public void setTitle(){
		
		title = new JLabel("                                  " +
				"                              "
				+ "        MARRIAGE RECORDS");
		title.setBounds(0,0,1100,25);
		title.setOpaque(true);
		title.setBackground(Color.blue);
		title.setFont(new Font("OLD ENGLISH MT",Font.BOLD,20));
		title.setForeground(Color.WHITE);
		add(title);
	}

	
	public void prepareLabels(String jammi, int xpos, int ypos, int width, int height){
		
		labels = new JLabel(jammi);
		labels.setBounds(xpos,ypos,width,height);
		labels.setOpaque(true);
		labels.setBackground(Color.CYAN);
		labels.setFont(new Font("Tahoma",Font.PLAIN,14));
		add(labels);
		
		
	}


	public void setLabels(){
		
		prepareLabels(" Page No:",30,50,140,18);
		prepareLabels(" Book No:",30,80,140,18);
		prepareLabels(" LCR Registration No:",30,110,140,18);
		prepareLabels(" Date of Registration:",30,140,140,18);
		prepareLabels(" Date of Marriage:",30,170,140,18);
		prepareLabels(" Place of Marriage:",30,200,140,18);
		prepareLabels(" Remarks:",30,230,140,18);
		
		//husband
		prepareLabels("                    "
				+ "          HUSBAND:",430,40,300,18);
		prepareLabels(" First Name:",430,70,90,18);
		prepareLabels(" Middle Name:",430,100,90,18);
		prepareLabels(" Last Name:",430,130,90,18);
		prepareLabels(" Age:",430,160,90,18);
		prepareLabels(" Citizenship:",430,190,90,18);
		prepareLabels(" Civil Status:",430,220,90,18);
		prepareLabels(" Father:",430,250,90,18);
		prepareLabels(" Mother:",430,280,90,18);
		
		//wife
		
		prepareLabels("                    "
				+ "             WIFE:",770,40,300,18);
		prepareLabels(" First Name:",770,70,90,18);
		prepareLabels(" Middle Name:",770,100,90,18);
		prepareLabels(" Last Name:",770,130,90,18);
		prepareLabels(" Age:",770,160,90,18);
		prepareLabels(" Citizenship:",770,190,90,18);
		prepareLabels(" Civil Status:",770,220,90,18);
		prepareLabels(" Father:",770,250,90,18);
		prepareLabels(" Mother:",770,280,90,18);
		
		prepareLabels(" Search by Husband:",30,350,230,18);
		prepareLabels(" Search by Wife:",30,380,230,18);
		
	}
	
	public void setFields(){
		
		page_no = new JTextField();
		page_no.setBounds(180,50,200,18);
		add(page_no);
		
		book_no = new JTextField();
		book_no.setBounds(180,80,200,18);
		add(book_no);
		
		reg_no = new JTextField();
		reg_no.setBounds(180,110,200,18);
		add(reg_no);
		
		reg_date = new JTextField();
		reg_date.setBounds(180,140,200,18);
		add(reg_date);
		
		marriage_date = new JTextField();
		marriage_date.setBounds(180,170,200,18);
		add(marriage_date);
		
		marriage_place = new JTextField();
		marriage_place.setBounds(180,200,200,18);
		add(marriage_place);
		
		remarks=new JTextArea();
		//remarks.setToolTipText("Please use 'Enter Key' to use line break!");
		remarks.setLineWrap(true);
		remarks.setWrapStyleWord(true);
		scroll=new JScrollPane(remarks);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollRemarks.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(180,230,200,50);
		add(scroll);
		
		//husband info
		H_first_name = new JTextField();
		H_first_name.setBounds(530,70,200,18);
		add(H_first_name);
		
		H_middle_name = new JTextField();
		H_middle_name.setBounds(530,100,200,18);
		add(H_middle_name);
		
		H_last_name = new JTextField();
		H_last_name.setBounds(530,130,200,18);
		add(H_last_name);
		
		H_age = new JTextField();
		H_age.setBounds(530,160,200,18);
		add(H_age);
		
		H_citizen = new JTextField();
		H_citizen.setBounds(530,190,200,18);
		add(H_citizen);
		
		H_Status = new JTextField();
		H_Status.setBounds(530,220,200,18);
		add(H_Status);
		
		H_Father = new JTextField();
		H_Father.setBounds(530,250,200,18);
		add(H_Father);
		
		H_Mother = new JTextField();
		H_Mother.setBounds(530,280,200,18);
		add(H_Mother);
		
		
		//wife info
		W_first_name = new JTextField();
		W_first_name.setBounds(870,70,200,18);
		add(W_first_name);

		W_middle_name = new JTextField();
		W_middle_name.setBounds(870,100,200,18);
		add(W_middle_name);

		W_last_name = new JTextField();
		W_last_name.setBounds(870,130,200,18);
		add(W_last_name);

		W_age = new JTextField();
		W_age.setBounds(870,160,200,18);
		add(W_age);

		W_citizen = new JTextField();
		W_citizen.setBounds(870,190,200,18);
		add(W_citizen);

		W_Status = new JTextField();
		W_Status.setBounds(870,220,200,18);
		add(W_Status);

		W_Father = new JTextField();
		W_Father.setBounds(870,250,200,18);
		add(W_Father);

		W_Mother = new JTextField();
		W_Mother.setBounds(870,280,200,18);
		add(W_Mother);
		
		search_husband = new JTextField();
		search_husband.setBounds(270,350,250,18);
		search_husband.addKeyListener(this);
		add(search_husband);
		
		search_wife = new JTextField();
		search_wife.setBounds(270,380,250,18);
		search_wife.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				SearchWife();
			}
			
		});
		add(search_wife);
	
		
	}
	
	public void setPanelLines() {
		
		panelLine1 = new JLabel();
		panelLine1.setBounds(10,35,380,290);
		panelLine1.setOpaque(true);
		panelLine1.setBorder(BorderFactory.createLineBorder(Color.RED));
		add(panelLine1);
		
		panelLine2 = new JLabel();
		panelLine2.setBounds(420,35,320,290);
		panelLine2.setOpaque(true);
		panelLine2.setBorder(BorderFactory.createLineBorder(Color.RED));
		add(panelLine2);
		
		panelLine3 = new JLabel();
		panelLine3.setBounds(760,35,320,290);
		panelLine3.setOpaque(true);
		panelLine3.setBorder(BorderFactory.createLineBorder(Color.RED));
		add(panelLine3);
		
		JLabel panelLine4 = new JLabel();
		panelLine4.setBounds(10,340,730,320);
		panelLine4.setOpaque(true);
		panelLine4.setBorder(BorderFactory.createLineBorder(Color.RED));
		add(panelLine4);
		
		JLabel panelLine5 = new JLabel();
		panelLine5.setBounds(760,340,320,320);
		panelLine5.setOpaque(true);
		panelLine5.setBorder(BorderFactory.createLineBorder(Color.RED));
		add(panelLine5);
		
	}
	
	
	public void setButtons() {
		
		add = new JButton("ADD");
		add.setBounds(840,370,150,30);
		add.setFont(new Font("Tahoma",Font.BOLD,15));
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				checkRecordExist();
			}
			
		});
		add(add);
		
		edit = new JButton("EDIT");
		edit.setBounds(840,420,150,30);
		edit.setFont(new Font("Tahoma",Font.BOLD,15));
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				getFieldValues();
				EditSQL();
			}
			
		});
		add(edit);
		
		print = new JButton("PRINT");
		print.setBounds(840,470,150,30);
		print.setFont(new Font("Tahoma",Font.BOLD,15));
		print.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				checkStuffsBeforePrinting();
			}
			
		});
		add(print);
		
		clear = new JButton("CLEAR FIELDS");
		clear.setBounds(840,520,150,30);
		clear.setFont(new Font("Tahoma",Font.BOLD,15));
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				clearFields();
			}
			
		});
		add(clear);
		
		exit = new JButton("EXIT");
		exit.setBounds(840,570,150,30);
		exit.setFont(new Font("Tahoma",Font.BOLD,15));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				closeMe();
			}
			
		});
		add(exit);
	}
	
	public void getFieldValues() {
		
		marriageEntity.setPage_no(page_no.getText().toString());
		marriageEntity.setBook_no(book_no.getText().toString());
		marriageEntity.setReg_no(reg_no.getText().toString());
		marriageEntity.setH_first_name(H_first_name.getText().toString());
		marriageEntity.setH_middle_name(H_middle_name.getText().toString());
		marriageEntity.setH_last_name(H_last_name.getText().toString());
		marriageEntity.setH_age(H_age.getText().toString());
		marriageEntity.setH_citizen(H_citizen.getText().toString());
		marriageEntity.setH_Status(H_Status.getText().toString());
		marriageEntity.setH_Father(H_Father.getText().toString());
		marriageEntity.setH_Mother(H_Mother.getText().toString());
		marriageEntity.setW_first_name(W_first_name.getText().toString());
		marriageEntity.setW_middle_name(W_middle_name.getText().toString());
		marriageEntity.setW_last_name(W_last_name.getText().toString());
		marriageEntity.setW_age(W_age.getText().toString());
		marriageEntity.setW_citizen(W_citizen.getText().toString());
		marriageEntity.setW_Status(W_Status.getText().toString());
		marriageEntity.setW_Father(W_Father.getText().toString());
		marriageEntity.setW_Mother(W_Mother.getText().toString());
		marriageEntity.setReg_date(reg_date.getText().toString());
		marriageEntity.setMarriage_date(marriage_date.getText().toString());
		marriageEntity.setMarriage_place(marriage_place.getText().toString());
		marriageEntity.setRemarks(remarks.getText().toString());
		
	}
	
	//CODE PARA SA JTABLE NATIN NAHIRAPAN TALAGA AKO DITO CHIKUSHO!
	public void tableModel(){
		
		String query;
	  
		 try  {
	   	
			 connection = (Connection) DriverManager.getConnection( url, username, password );
			 stmt = (Statement) connection.createStatement();

			 query = "SELECT * FROM marriage";
			 rs = stmt.executeQuery(query);
			 table = new JTable(model);
			 JScrollPane scrollPane = new JScrollPane(table);
			 table.setPreferredScrollableViewportSize(new Dimension(500, 70));
			 table.setFillsViewportHeight(true);
			 table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			 table.setBackground(Color.LIGHT_GRAY);
			 scrollPane.setBounds(30,420,690,230);
			 scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			 scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			 add(scrollPane);
			 table.setModel(DbUtils.resultSetToTableModel(rs));
			 table.addMouseListener(this);
	       
	  
		 }catch(Exception moriel){
			 
			 JOptionPane.showMessageDialog(null,moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);
			 
		 }
	  
	}


	public void SearchHusband(){

		String query;

		try  {

			connection = (Connection) DriverManager.getConnection( url, username, password );
			stmt = (Statement) connection.createStatement();

			query = "SELECT * FROM marriage WHERE husband_first_name LIKE "+"\""+search_husband.getText().toString()+"%\" OR husband_last_name LIKE "+"\""+search_husband.getText().toString()+"%\"";
			rs = stmt.executeQuery(query);
			table.setModel(DbUtils.resultSetToTableModel(rs));

		}catch(Exception moriel){

			JOptionPane.showMessageDialog(null,moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);

		}

		
	}
	
	public void SearchWife(){

		String query;

		try  {

			connection = (Connection) DriverManager.getConnection( url, username, password );
			stmt = (Statement) connection.createStatement();

			query = "SELECT * FROM marriage WHERE wife_first_name LIKE "+"\""+search_wife.getText().toString()+"%\" OR wife_last_name LIKE "+"\""+search_wife.getText().toString()+"%\"";
			rs = stmt.executeQuery(query);
			table.setModel(DbUtils.resultSetToTableModel(rs));

		}catch(Exception moriel){

			JOptionPane.showMessageDialog(null,moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);

		}

		
	}
	
	public void checkRecordExist(){
		  
		try{

			//access natin ang java mysql driver library na ating inimport para magamit
			Class.forName(driver);

			//set natin ung user at pass ng sql appz, server, host or kung anong tawag jan natin, mine is xammp 

			String user = "root";
			String pass = "";

			//create na natin ung connection para sating database 
			connection = (Connection) DriverManager.getConnection(url,user,pass);

			//set the sql query

			String check = "SELECT page_no, book_no, reg_no FROM marriage WHERE page_no="+"\""+page_no.getText().toString()+"\""
					+ "AND book_no="+"\""+book_no.getText().toString()+"\""
					+ "AND reg_no="+"\""+reg_no.getText().toString()+"\"";

			stmt = (Statement) connection.createStatement();

			rs = stmt.executeQuery(check);
	 
		
			if(rs.first()){

				JOptionPane.showMessageDialog(null,"Record Already Exist!"+"\n"+"Page No, Book No, LCR Reg. No. the same","MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);


			}else{

				getFieldValues();
				InsertSQL(); 

			}

			connection.close();

		}catch (Exception moriel){

			JOptionPane.showMessageDialog(null,"ERROR!!!\n"+"Error! Please Try Again! \n\n"+moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE); 

		} 




	} 
	
	
	public void InsertSQL(){
		
		try{

			Class.forName(driver);

			//create na natin ung connection para sating database 
			connection = (Connection) DriverManager.getConnection(url,username,password);

			//set the sql query
			String query = "INSERT INTO marriage ("
							+ "page_no,book_no,reg_no,husband_first_name,husband_middle_name,husband_last_name,"
							+ "husband_age,husband_status,husband_citizenship,husband_father,husband_mother,"
							+ "wife_first_name,wife_middle_name,wife_last_name,wife_age,wife_status,wife_citizenship,"
							+ "wife_father,wife_mother,reg_date,marriage_date,marriage_place,remarks)" +
							"values("
							+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


			stmt = (Statement) connection.createStatement();


			ps = (PreparedStatement) connection.prepareStatement(query);

			ps.setString(1,marriageEntity.getPage_no());
			ps.setString(2,marriageEntity.getBook_no());
			ps.setString(3,marriageEntity.getReg_no());
			ps.setString(4,marriageEntity.getH_first_name());
			ps.setString(5,marriageEntity.getH_middle_name());
			ps.setString(6,marriageEntity.getH_last_name());
			ps.setString(7,marriageEntity.getH_age());
			ps.setString(8,marriageEntity.getH_Status());
			ps.setString(9,marriageEntity.getH_citizen());
			ps.setString(10,marriageEntity.getH_Father());
			ps.setString(11,marriageEntity.getH_Mother());
			ps.setString(12,marriageEntity.getW_first_name());
			ps.setString(13,marriageEntity.getW_middle_name());
			ps.setString(14,marriageEntity.getW_last_name());
			ps.setString(15,marriageEntity.getW_age());
			ps.setString(16,marriageEntity.getW_Status());
			ps.setString(17,marriageEntity.getW_citizen());
			ps.setString(18,marriageEntity.getW_Father());
			ps.setString(19,marriageEntity.getW_Mother());
			ps.setString(20,marriageEntity.getReg_date());
			ps.setString(21,marriageEntity.getMarriage_date());
			ps.setString(22,marriageEntity.getMarriage_place());
			ps.setString(23,marriageEntity.getRemarks());
			
			
			ps.execute();

			JOptionPane.showMessageDialog(null,"Record Saved!","MCR DATABASE SYSTEM",JOptionPane.INFORMATION_MESSAGE); 


			connection.close();

		}catch (Exception moriel){

			JOptionPane.showMessageDialog(null,"ERROR!!!\n"+"PLEASE TRY AGAIN! \n\n"+moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE); 
		}
		
	} 
	
	
	
	
	public void tableClickSearch(){
		  
		
		String query = "SELECT * FROM marriage WHERE marriage_id="+"\""+table.getValueAt(table.getSelectedRow(), 0)+"\"";

		
		try{

			//access natin ang java mysql driver library na ating inimport para magamit
			Class.forName(driver);

			//create na natin ung connection para sating database 
			connection = (Connection) DriverManager.getConnection(url,username,password);

			stmt = (Statement) connection.createStatement();

			rs = stmt.executeQuery(query);

			int counter = 0;

			while(rs.next()){

				mid = rs.getString("marriage_id");
				page_no.setText(rs.getString("page_no"));
				book_no.setText(rs.getString("book_no"));
				reg_no.setText(rs.getString("reg_no"));
				reg_date.setText(rs.getString("reg_date"));
				marriage_date.setText(rs.getString("marriage_date"));
				marriage_place.setText(rs.getString("marriage_place"));
				H_first_name.setText(rs.getString("husband_first_name"));
				H_middle_name.setText(rs.getString("husband_middle_name"));
				H_last_name.setText(rs.getString("husband_last_name"));
				H_age.setText(rs.getString("husband_age"));
				H_citizen.setText(rs.getString("husband_citizenship"));
				H_Status.setText(rs.getString("husband_status"));
				H_Father.setText(rs.getString("husband_father"));
				H_Mother.setText(rs.getString("husband_mother"));
				W_first_name.setText(rs.getString("wife_first_name"));
				W_middle_name.setText(rs.getString("wife_middle_name"));
				W_last_name.setText(rs.getString("wife_last_name"));
				W_age.setText(rs.getString("wife_age"));
				W_citizen.setText(rs.getString("wife_citizenship"));
				W_Status.setText(rs.getString("wife_status"));
				W_Father.setText(rs.getString("wife_father"));
				W_Mother.setText(rs.getString("wife_mother"));
				remarks.setText(rs.getString("remarks"));
				
				counter++;
			}
				 

			if(counter<1){

				JOptionPane.showMessageDialog(null,"RECORD NOT FOUND!","MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);

			}

			connection.close();

		}catch (Exception moriel){

			JOptionPane.showMessageDialog(null,"ERROR!!!\n"+"PLEASE TRY AGAIN! \n\n"+moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);


		} 

	} 
	
	public void EditSQL(){

		String query;

		int ans = JOptionPane.showConfirmDialog(null,"Save Changes?");
		
		
		if(ans == JOptionPane.YES_OPTION){
			
			try{

				//access natin ang java mysql driver library na ating inimport para magamit
				Class.forName(driver);

				//create na natin ung connection para sating database 
				connection = (Connection) DriverManager.getConnection(url,username,password);

				//set the sql query
				query = "UPDATE marriage SET page_no=?,book_no=?,reg_no=?,reg_date=?,marriage_date=?,marriage_place=?,"
						+ "husband_first_name=?,husband_middle_name=?,husband_last_name=?,husband_age=?,"
						+ "husband_citizenship=?,husband_status=?,husband_father=?,husband_mother=?,"
						+ "wife_first_name=?,wife_middle_name=?,wife_last_name=?,wife_age=?,"
						+ "wife_citizenship=?,wife_status=?,wife_father=?,wife_mother=?,"
						+ "remarks=? WHERE marriage_id = "+"\""+mid+"\"";

				
				ps = (PreparedStatement) connection.prepareStatement(query);
				
				ps.setString(1,marriageEntity.getPage_no());
				ps.setString(2,marriageEntity.getBook_no());
				ps.setString(3,marriageEntity.getReg_no());
				ps.setString(4,marriageEntity.getReg_date());
				ps.setString(5,marriageEntity.getMarriage_date());
				ps.setString(6,marriageEntity.getMarriage_place());
				ps.setString(7,marriageEntity.getH_first_name());
				ps.setString(8,marriageEntity.getH_middle_name());
				ps.setString(9,marriageEntity.getH_last_name());
				ps.setString(10,marriageEntity.getH_age());
				ps.setString(11,marriageEntity.getH_citizen());
				ps.setString(12,marriageEntity.getH_Status());
				ps.setString(13,marriageEntity.getH_Father());
				ps.setString(14,marriageEntity.getH_Mother());
				ps.setString(15,marriageEntity.getW_first_name());
				ps.setString(16,marriageEntity.getW_middle_name());
				ps.setString(17,marriageEntity.getW_last_name());
				ps.setString(18,marriageEntity.getW_age());
				ps.setString(19,marriageEntity.getW_citizen());
				ps.setString(20,marriageEntity.getW_Status());
				ps.setString(21,marriageEntity.getW_Father());
				ps.setString(22,marriageEntity.getW_Mother());
				ps.setString(23,marriageEntity.getRemarks());	
				
				ps.execute();

				JOptionPane.showMessageDialog(null,"Record Saved!","MCR DATABASE SYSTEM",JOptionPane.INFORMATION_MESSAGE); 

				connection.close();

			}catch (Exception moriel){

				JOptionPane.showMessageDialog(null,"ERROR!!!\n"+"PLEASE TRY AGAIN! \n\n"+moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE); 

			} 
			
			
		}else if(ans==JOptionPane.NO_OPTION){
			
			
		}else if(ans ==JOptionPane.CANCEL_OPTION){
			
		}else if(ans ==JOptionPane.CLOSED_OPTION){
			
		}
		  
		
	} 
	
	public void clearFields() {
		
		page_no.setText("");
		book_no.setText("");
		reg_no.setText("");
		reg_date.setText("");
		marriage_date.setText("");
		marriage_place.setText("");
		H_first_name.setText("");
		H_middle_name.setText("");
		H_last_name.setText("");
		H_age.setText("");
		H_citizen.setText("");
		H_Status.setText("");
		H_Father.setText("");
		H_Mother.setText("");
		W_first_name.setText("");
		W_middle_name.setText("");
		W_last_name.setText("");
		W_age.setText("");
		W_citizen.setText("");
		W_Status.setText("");
		W_Father.setText("");
		W_Mother.setText("");
		remarks.setText("");
	}
	
	public void closeMe() {
		this.dispose();
	}
	
	public void checkStuffsBeforePrinting() {
		
		issueTo = JOptionPane.showInputDialog(null,"Issued To?","",JOptionPane.QUESTION_MESSAGE);
		
		 
		 //check if local or abroad
		 local_abroad = JOptionPane.showInputDialog(null,"Local Or Abroad? \n\n"
		 		+ "Enter '1' if local \n"+
				 "Enter '2' if abroad","MCR DATABASE SYSTEM",JOptionPane.QUESTION_MESSAGE);
		 
		if(local_abroad.equals("1")) {
			
			certType = "NOT Valid For Travel Abroad";
			certTypePayment = "Php.45.00";
			
		}if(local_abroad.equals("2")) {
			
			certType = "Valid For Travel Abroad";
			certTypePayment = "Php.60.00";
		}else {
			
			certType = "NOT Valid For Travel Abroad";
			certTypePayment = "Php.45.00";
		}
		
		
		//check OR No
		
		or_no = JOptionPane.showInputDialog(null,"Enter OR No","MCR DATABASE SYSTEM",JOptionPane.QUESTION_MESSAGE);
		
		
		//check who will sign the certificate
		
		getSigner = JOptionPane.showInputDialog(null,"Choose Who Will Verify \n\n"
				+ "Enter '1' for Norylyn T. Ijiran \n"
				+ "Enter '2' for Anna Marie D. Baguio \n"
				+ "Enter '3' for Shiela E. Garcia \n"
				+ "Enter '4' for Norylyn T. Ijiran and Shiela E. Garcia","MCR DATABASE SYSTEM",JOptionPane.QUESTION_MESSAGE);
		
		if(getSigner.equals("1")) {
			
			signer="NORYLYN T. IJIRAN";
			position1 = "ASST.REG.OFFICER DESIGNATE";
			forAndBy = "For and By the Authority of MCR:";
			signer2="NORYLYN T. IJIRAN";
			position2="ASST.REG.OFFICER DESIGNATE";
			line1="____________________";
			line2="____________________";
			
		}else if(getSigner.equals("2")) {
			
			signer="ANNA MARIE D. BAGUIO";
			position1="                            CLERK";
			forAndBy="For and By the Authority of MCR";
			signer2="ANNA MARIE D. BAGUIO";
			position2="                            CLERK";
			line1="_________________________";
			line2="_________________________";
			
		}else if(getSigner.equals("3")) {
			
			signer="SHIELA ENRIQUEZ GARCIA";
			position1="                 Municipal Civil Registrar";
			signer2="";
			forAndBy="";
			position2="";
			line1="___________________________";
			line2="";
		}else if(getSigner.equals("4")) {
			
			signer="NORYLYN T. IJIRAN";
			position1 = "ASST.REG.OFFICER DESIGNATE";
			signer2="";
			forAndBy="";
			position2="";
			line1="____________________";
			line2="";
		}else{
			
			signer="SHIELA ENRIQUEZ GARCIA";
			position1="Municipal Civil Registrar";
			signer2="";
			forAndBy="";
			position2="";
			line1="___________________________";
			line2="";
		}
		
		printMe();
	}


	@Override
	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		// TODO Auto-generated method stub
		
		 logo = Toolkit.getDefaultToolkit().getImage("res/logo.jpg");
		 
		 Graphics2D g2d = (Graphics2D)g;
	     g2d.translate(pf.getImageableX(), pf.getImageableY());
	     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	     ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 
	     
	     //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	     DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	     DateFormat dateFormat2 = new SimpleDateFormat("MMMM-dd-yyyy");
	     Date date = new Date();
	     
	     AttributedString as = new AttributedString(dateFormat2.format(date));
	     as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON,0,dateFormat2.format(date).length());
	     AttributedCharacterIterator aci = as.getIterator();
	     
	     AttributedString as2 = new AttributedString(page_no.getText().toString());
	     as2.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON,0,page_no.getText().toString().length());
	     AttributedCharacterIterator aci2 = as2.getIterator();
	     
	     AttributedString as3 = new AttributedString(book_no.getText().toString()+".");
	     as3.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON,0,book_no.getText().toString().length());
	     AttributedCharacterIterator aci3 = as3.getIterator();
	     
	     AttributedString as4 = new AttributedString(issueTo.toUpperCase()+"   Upon his/her request.");
	     as4.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON,0,issueTo.length());
	     as4.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD,0,issueTo.length());
	     AttributedCharacterIterator aci4 = as4.getIterator();
		
		
		 if (page > 0) { /* We have only one page, and 'page' is zero-based */
	            return NO_SUCH_PAGE;
	        }

	        /* User (0,0) is typically outside the imageable area, so we must
	         * translate by the X and Y values in the PageFormat to avoid clipping
	         */
		 
	       
	        /* Now we perform our rendering */
	        g.drawImage(logo, 270, 40, 60, 60, this);

	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Republic of the Philippines", 240, 110);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,14));
	        g.setColor(Color.BLACK);
	        g.drawString("MUNICIPALITY OF MANAOAG", 195, 125);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Province of Pangasinan", 250, 137);
	        
	        g.setFont(new Font("Times New Roman",Font.ITALIC,20));
	        g.setColor(Color.BLACK);
	        g.drawString("Office of the Municipal Civil Registrar", 140, 170);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,11));
	        g.setColor(Color.BLACK);
	        g.drawString(aci, 465,195);
	        //g.drawString("___________", 465, 197);
	        g.drawString("Date", 495, 207);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("TO WHOM IT MAY CONCERN:",70,240);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("We CERTIFY that, among others the following facts of marriage appear in our "+"\""+"REGISTER OF ",75,260);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("MARRIAGES on page",80,280);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,12));
	        g.setColor(Color.BLACK);
	        g.drawString(aci2,200,280);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("of book no",230,280);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,12));
	        g.setColor(Color.BLACK);
	        g.drawString(aci3,300,280);
	       
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("HUSBAND",175,310);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("__________",174,312);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("WIFE",420,310);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("_____",420,312);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Name",65,340);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,11));
	        g.setColor(Color.BLACK);
	        g.drawString(H_first_name.getText().toString().toUpperCase()+" "+H_middle_name.getText().toString().toUpperCase()+" "+H_last_name.getText().toString().toUpperCase(),140,340);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("__________________________________",139,342);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,11));
	        g.setColor(Color.BLACK);
	        g.drawString(W_first_name.getText().toString().toUpperCase()+" "+W_middle_name.getText().toString().toUpperCase()+" "+W_last_name.getText().toString().toUpperCase(),360,340);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("_________________________________",359,342);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Age",65,357);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(H_age.getText().toString(),140,357);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("__________________________________",139,359);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(W_age.getText().toString(),360,357);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("_________________________________",359,359);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Citizenship",65,374);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(H_citizen.getText().toString(),139,374);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("__________________________________",139,376);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(W_citizen.getText().toString(),360,374);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("_________________________________",359,376);
	        
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Civil Status",65,391);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(H_Status.getText().toString(),139,391);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("__________________________________",139,393);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(W_Status.getText().toString(),360,391);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("_________________________________",359,393);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Mother",65,408);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(H_Mother.getText().toString(),139,408);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("__________________________________",139,410);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(W_Mother.getText().toString(),360,408);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("_________________________________",359,410);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Father",65,425);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(H_Father.getText().toString(),139,425);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("__________________________________",139,427);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(W_Father.getText().toString(),360,425);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("_________________________________",359,427);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Register Number",70,450);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(reg_no.getText().toString(),182,450);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("________________________________________________________",179,452);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Date Of Registration",70,467);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(reg_date.getText().toString(),182,467);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("________________________________________________________",179,469);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Date Of Marriage",70,484);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(marriage_date.getText().toString(),182,484);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("________________________________________________________",179,486);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Place Of Marriage",70,501);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString(marriage_place.getText().toString(),182,501);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("________________________________________________________",179,503);
	        
	        
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("This CERTIFICATION is Issued To ",70,535);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,12));
	        g.setColor(Color.BLACK);
	        g.drawString(aci4,257,535);
	        
	        g.setFont(new Font("Times New Roman",Font.ITALIC,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Verified By: ",85,565);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,12));
	        g.setColor(Color.BLACK);
	        g.drawString(signer,100,600);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,12));
	        g.setColor(Color.BLACK);
	        g.drawString(line1,100,602);
	        
	        g.setFont(new Font("Times New Roman",Font.ITALIC,10));
	        g.setColor(Color.BLACK);
	        g.drawString(position1,87,615);
	        
	        g.setFont(new Font("Times New Roman",Font.ITALIC,12));
	        g.setColor(Color.BLACK);
	        g.drawString(forAndBy,380,565);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,12));
	        g.setColor(Color.BLACK);
	        g.drawString(signer2,390,600);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,12));
	        g.setColor(Color.BLACK);
	        g.drawString(line2,390,602);
	        
	        g.setFont(new Font("Times New Roman",Font.ITALIC,10));
	        g.setColor(Color.BLACK);
	        g.drawString(position2,377,615);
	        
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Amount Paid... "+certTypePayment,105,640);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("OR Number... "+or_no,105,655);
	        
	        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Date Paid... "+dateFormat.format(date),105,670);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,12));
	        g.setColor(Color.BLACK);
	        g.drawString(certType,105,685);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,12));
	        g.setColor(Color.BLACK);
	        g.drawString("SHIELA ENRIQUEZ GARCIA",378,670);
	        
	        g.setFont(new Font("Times New Roman",Font.BOLD,12));
	        g.setColor(Color.BLACK);
	        g.drawString("___________________________",378,672);
	        
	        g.setFont(new Font("Times New Roman",Font.ITALIC,12));
	        g.setColor(Color.BLACK);
	        g.drawString("Municipal Civil Registrar",395,685);
	        
	        g.setFont(new Font("Times New Roman",Font.ITALIC,10));
	        g.setColor(Color.BLACK);
	        g.drawString("NOTE: This CERTIFICATION is not valid if it has mark of erasure or alteration.",150,715);
	       
	        
	   

	        /* tell the caller that this page is part of the printed document */
	        return PAGE_EXISTS;

	}

	//ITO UNG PRINT METHOD HEHE...
	public void printMe(){ 
		
		  PrinterJob job = PrinterJob.getPrinterJob();
	         job.setPrintable(this);
	         boolean ok = job.printDialog();
	         if (ok) {
	             try {
	                  job.print();
	             } catch (PrinterException morielKim) {
	              JOptionPane.showMessageDialog(null, morielKim.getMessage());
	             }
	         }
		
	}

	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		tableClickSearch();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		SearchHusband();
	}

}
