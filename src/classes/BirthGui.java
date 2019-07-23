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
import javax.swing.ImageIcon;
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
import javax.swing.text.Style;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;

public class BirthGui extends JFrame implements ActionListener, MouseListener, KeyListener, Printable{
	
	JLabel title, labels;
	
	String bid;
	
	JTextField page_no,
			   book_no,
			   reg_no,
			   first_name,
			   middle_name,
			   last_name,
			   reg_date,
			   birth_date,
			   birth_place,
			   nationality,
			   father_name,
			   father_nationality,
			   mother_name,
			   mother_nationality,
			   parent_marriage_date,
			   parent_marriage_place;
	JTextArea remarks;
	
	String[] G= {"","Male","Female"};
	JComboBox gender;
	
	JTable table;
	DefaultTableModel model;
	
	JTextArea desc;
	JScrollPane scroll;
	
	//VARS PARA SA PRINTING
	Graphics2D g2;
	Image logo;
	Font font1, font2, font3, font4, font5;
	String issueTo, local_abroad, certType, certTypePayment, or_no, signer,signer2,getSigner,forAndBy,position1,position2,line1,line2;
	
	JLabel panelLine1,panelLine2,panelLine3;
	
	JTextField search,searchLast;
	
	String driver = "org.gjt.mm.mysql.Driver";
	String url = "jdbc:mysql://localhost:3306/crds";
	String username = "root";
	String password = "";
	Connection connection;
	Statement stmt;
	ResultSet rs;
	PreparedStatement ps;
	
	JButton add, edit, clear, exit, print;
	
	Birth birthEntity = new Birth();
	
	String getFirst;
	
	
	
	public BirthGui(){
		super("MCR DATABASE SYSTEM");
		setLayout(null);
		
		setTitle();
		setLabels();
		setFields();
		tableModel();
		setButtons();
		setPanelLines();
		
		//setBackground();
		
		getFirst=search.getText().toString();
		
	}
	
	
public void setTitle(){
		
	title = new JLabel("                                  " +
			"                              "
			+ "               BIRTH RECORDS");
	title.setBounds(0,0,1100,25);
	title.setOpaque(true);
	title.setBackground(Color.blue);
	title.setFont(new Font("OLD ENGLISH MT",Font.BOLD,20));
	title.setForeground(Color.WHITE);
	add(title);
}

public void setPanelLines() {
	
	panelLine1 = new JLabel();
	panelLine1.setBounds(10,35,470,590);
	panelLine1.setOpaque(true);
	panelLine1.setBorder(BorderFactory.createLineBorder(Color.RED));
	add(panelLine1);
	
	panelLine2 = new JLabel();
	panelLine2.setBounds(510,35,560,590);
	panelLine2.setOpaque(true);
	panelLine2.setBorder(BorderFactory.createLineBorder(Color.RED));
	add(panelLine2);
	
	/*panelLine3 = new JLabel();
	panelLine3.setBounds(0,635,1200,1);
	panelLine3.setOpaque(true);
	panelLine3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	add(panelLine3);*/
}

public void setBackground(){

	JLabel background = new JLabel(new ImageIcon("res/M_Logo.jpg"));
	 background.setBounds(0,0,900,550);
	// background.setBackground(new Color(0,0,255));
	// background.setBackground(Color.LIGHT_GRAY);
	 background.setOpaque(true);
	 add(background);
	 
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
	
	prepareLabels(" Page No:",30,50,185,18);
	prepareLabels(" Book No:",30,80,185,18);
	prepareLabels(" LCR Registration No:",30,110,185,18);
	prepareLabels(" First Name:",30,140,185,18);
	prepareLabels(" Middle Name:",30,170,185,18);
	prepareLabels(" Last Name:",30,200,185,18);
	prepareLabels(" Date of Registration:",30,230,185,18);
	prepareLabels(" Date of Birth:",30,260,185,18);
	prepareLabels(" Place of Birth:",30,290,185,18);
	prepareLabels(" Gender:",30,320,185,18);
	prepareLabels(" Nationality:",30,350,185,18);
	prepareLabels(" Father's Name:",30,380,185,18);
	prepareLabels(" Father's Nationality:",30,410,185,18);
	prepareLabels(" Mother's Name:",30,440,185,18);
	prepareLabels(" Mother's Nationality:",30,470,185,18);	
	prepareLabels(" Date of Marriage of Parents:",30,500,185,18);
	prepareLabels(" Place of Marriage of Parents:",30,530,185,18);
	prepareLabels(" Remarks:",30,560,185,18);
	
	//panel 2
	prepareLabels(" Search:",530,50,60,18);
	
}

public void setFields() {
	
	page_no = new JTextField();
	page_no.setBounds(230,50,230,18);
	add(page_no);
	
	book_no = new JTextField();
	book_no.setBounds(230,80,230,18);
	add(book_no);
	
	reg_no = new JTextField();
	reg_no.setBounds(230,110,230,18);
	add(reg_no);
	
	first_name = new JTextField();
	first_name.setBounds(230,140,230,18);
	add(first_name);
	
	middle_name = new JTextField();
	middle_name.setBounds(230,170,230,18);
	add(middle_name);
	
	last_name = new JTextField();
	last_name.setBounds(230,200,230,18);
	add(last_name);
	
	reg_date = new JTextField();
	reg_date.setBounds(230,230,230,18);
	add(reg_date);
	
	birth_date = new JTextField();
	birth_date.setBounds(230,260,230,18);
	add(birth_date);
	
	birth_place = new JTextField();
	birth_place.setBounds(230,290,230,18);
	add(birth_place);
	
	gender = new JComboBox(G);
	gender.setBounds(230,320,100,18);
	gender.setSelectedIndex(0);
	add(gender);
	
	nationality = new JTextField();
	nationality.setBounds(230,350,230,18);
	add(nationality);
	
	father_name = new JTextField();
	father_name.setBounds(230,380,230,18);
	add(father_name);
	
	father_nationality = new JTextField();
	father_nationality.setBounds(230,410,230,18);
	add(father_nationality);
	
	mother_name = new JTextField();
	mother_name.setBounds(230,440,230,18);
	add(mother_name);
	
	mother_nationality = new JTextField();
	mother_nationality.setBounds(230,470,230,18);
	add(mother_nationality);
	
	parent_marriage_date = new JTextField();
	parent_marriage_date.setBounds(230,500,230,18);
	add(parent_marriage_date);
	
	parent_marriage_place = new JTextField();
	parent_marriage_place.setBounds(230,530,230,18);
	add(parent_marriage_place);
	
	
	remarks=new JTextArea();
	//remarks.setToolTipText("Please use 'Enter Key' to use line break!");
	remarks.setLineWrap(true);
	remarks.setWrapStyleWord(true);
	scroll=new JScrollPane(remarks);
	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	//scrollRemarks.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	scroll.setBounds(230,560,230,50);
	add(scroll);
	
	search = new JTextField();
	search.setToolTipText("Enter First Name Here To Be Search");
	search.setBounds(600,50,120,18);
	search.addKeyListener(this);
	add(search);
	
	searchLast = new JTextField();
	searchLast.setToolTipText("Enter Last Name Here");
	searchLast.setBounds(730,50,120,18);
	searchLast.addKeyListener(new KeyListener() {
		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			alternateSearch();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	});
	add(searchLast);
	
	
}


public void setButtons() {
	
	add = new JButton("ADD");
	add.setBounds(535,560,80,35);
	add.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			checkRecordExist();
		}
		
	});
	add(add);
	
	edit = new JButton("EDIT");
	edit.setBounds(635,560,80,35);
	edit.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			fieldSetters();
			EditSQL();
		}
		
	});
	add(edit);
	
	print = new JButton("PRINT");
	print.setBounds(735,560,80,35);
	print.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			checkStuffsBeforePrinting();
		}
		
	});
	add(print);
	
	clear = new JButton("CLEAR FIELDS");
	clear.setBounds(835,560,120,35);
	clear.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			clearFields();
		}
		
	});
	add(clear);
	
	exit = new JButton("EXIT");
	exit.setBounds(975,560,80,35);
	exit.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			CloseMe();
		}
		
	});
	add(exit);
	
}

public void fieldSetters() {
	
	birthEntity.setPage_no(page_no.getText().toString());
	birthEntity.setBook_no(book_no.getText().toString());
	birthEntity.setReg_no(reg_no.getText().toString());
	birthEntity.setFirst_name(first_name.getText().toString());
	birthEntity.setMiddle_name(middle_name.getText().toString());
	birthEntity.setLast_name(last_name.getText().toString());
	birthEntity.setReg_date(reg_date.getText().toString());
	birthEntity.setBirth_date(birth_date.getText().toString());
	birthEntity.setBirth_place(birth_place.getText().toString());
	birthEntity.setNationality(nationality.getText().toString());
	birthEntity.setGender(gender.getSelectedItem().toString());
	birthEntity.setFather_name(father_name.getText().toString());
	birthEntity.setFather_nationality(father_nationality.getText().toString());
	birthEntity.setMother_name(mother_name.getText().toString());
	birthEntity.setMother_nationality(mother_nationality.getText().toString());
	birthEntity.setParent_marriage_date(parent_marriage_date.getText().toString());
	birthEntity.setParent_marriage_place(parent_marriage_place.getText().toString());
	birthEntity.setRemarks(remarks.getText().toString());
	
}
	

//METHOD PARA SA EXIT BUTTON
public void CloseMe(){
	this.dispose();
}


//CODE PARA SA JTABLE NATIN NAHIRAPAN TALAGA AKO DITO CHIKUSHO!
public void tableModel(){
	
	String query;
  
	 try  {
   	
		 connection = (Connection) DriverManager.getConnection( url, username, password );
		 stmt = (Statement) connection.createStatement();

		 query = "SELECT * FROM birth";
		 rs = stmt.executeQuery(query);
		 table = new JTable(model);
		 JScrollPane scrollPane = new JScrollPane(table);
		 table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		 table.setFillsViewportHeight(true);
		 table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		 table.setBackground(Color.LIGHT_GRAY);
		 scrollPane.setBounds(530,100,520,430);
		 scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		 scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 add(scrollPane);
		 table.setModel(DbUtils.resultSetToTableModel(rs));
		 table.addMouseListener(this);
      	
       
  
	 }catch(Exception moriel){
		 
		 JOptionPane.showMessageDialog(null,moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);
		 
	 }
  
}


public void Search(){

	String query;

	try  {

		connection = (Connection) DriverManager.getConnection( url, username, password );
		stmt = (Statement) connection.createStatement();

		query = "SELECT * FROM birth WHERE first_name LIKE "+"\""+search.getText().toString()+"%\"";
		rs = stmt.executeQuery(query);
		table.setModel(DbUtils.resultSetToTableModel(rs));

	}catch(Exception moriel){

		JOptionPane.showMessageDialog(null,moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);

	}

	
}

public void alternateSearch(){

	String query;

	try  {

		connection = (Connection) DriverManager.getConnection( url, username, password );
		stmt = (Statement) connection.createStatement();

		query = "SELECT * FROM birth WHERE first_name LIKE "+"\""+search.getText().toString()+"%\" AND last_name LIKE "+"\""+searchLast.getText().toString()+"%\"";
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

		String check = "SELECT page_no, book_no, reg_no FROM birth WHERE page_no="+"\""+page_no.getText().toString()+"\""
				+ "AND book_no="+"\""+book_no.getText().toString()+"\""
				+ "AND reg_no="+"\""+reg_no.getText().toString()+"\"";

		stmt = (Statement) connection.createStatement();

		rs = stmt.executeQuery(check);
 
	
		if(rs.first()){

			JOptionPane.showMessageDialog(null,"Record Already Exist!"+"\n"+"Page No, Book No, LCR Reg. No. the same","MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);


		}else{

			fieldSetters();
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
		String query = "INSERT INTO birth ("
						+ "page_no,book_no,reg_no,first_name,middle_name,last_name,reg_date,birth_date,"
						+ "birth_place,gender,nationality,father_name,father_nationality,"
						+ "mother_name,mother_nationality,parent_marriage_date,parent_marriage_place,remarks)" +
						"values("
						+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


		stmt = (Statement) connection.createStatement();


		ps = (PreparedStatement) connection.prepareStatement(query);

		ps.setString(1, birthEntity.getPage_no());
		ps.setString(2, birthEntity.getBook_no());
		ps.setString(3, birthEntity.getReg_no());
		ps.setString(4, birthEntity.getFirst_name());
		ps.setString(5, birthEntity.getMiddle_name());
		ps.setString(6, birthEntity.getLast_name());
		ps.setString(7, birthEntity.getReg_date());
		ps.setString(8, birthEntity.getBirth_date());
		ps.setString(9,birthEntity.getBirth_place());
		ps.setString(10,birthEntity.getGender());
		ps.setString(11,birthEntity.getNationality());
		ps.setString(12,birthEntity.getFather_name());
		ps.setString(13,birthEntity.getFather_nationality());
		ps.setString(14,birthEntity.getMother_name());
		ps.setString(15,birthEntity.getMother_nationality());
		ps.setString(16,birthEntity.getParent_marriage_date());
		ps.setString(17,birthEntity.getParent_marriage_place());
		ps.setString(18,birthEntity.getRemarks());

		

		ps.execute();

		JOptionPane.showMessageDialog(null,"Record Saved!","MCR DATABASE SYSTEM",JOptionPane.INFORMATION_MESSAGE); 


		connection.close();

	}catch (Exception moriel){

		JOptionPane.showMessageDialog(null,"ERROR!!!\n"+"PLEASE TRY AGAIN! \n\n"+moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE); 
	}
	
} 


public void tableClickSearch(){
	  
	
	String query = "SELECT * FROM birth WHERE birth_id="+"\""+table.getValueAt(table.getSelectedRow(), 0)+"\"";

	
	try{

		//access natin ang java mysql driver library na ating inimport para magamit
		Class.forName(driver);

		//create na natin ung connection para sating database 
		connection = (Connection) DriverManager.getConnection(url,username,password);

		stmt = (Statement) connection.createStatement();

		rs = stmt.executeQuery(query);

		int counter = 0;

		while(rs.next()){

			bid = rs.getString("birth_id");
			page_no.setText(rs.getString("page_no"));
			book_no.setText(rs.getString("book_no"));
			reg_no.setText(rs.getString("reg_no"));
			first_name.setText(rs.getString("first_name"));
			middle_name.setText(rs.getString("middle_name"));
			last_name.setText(rs.getString("last_name"));
			reg_date.setText(rs.getString("reg_date"));
			birth_date.setText(rs.getString("birth_date"));
			birth_place.setText(rs.getString("birth_place"));
			gender.setSelectedItem(rs.getString("gender"));
			nationality.setText(rs.getString("nationality"));
			father_name.setText(rs.getString("father_name"));
			father_nationality.setText(rs.getString("father_nationality"));
			mother_name.setText(rs.getString("mother_name"));
			mother_nationality.setText(rs.getString("mother_nationality"));
			parent_marriage_date.setText(rs.getString("parent_marriage_date"));
			parent_marriage_place.setText(rs.getString("parent_marriage_place"));
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
			query = "UPDATE birth SET page_no=?,book_no=?,reg_no=?,first_name=?,middle_name=?,last_name=?,"
					+ "reg_date=?,birth_date=?,"
					+ "birth_place=?,gender=?,nationality=?,father_name=?,"
					+ "father_nationality=?,mother_name=?,mother_nationality=?,parent_marriage_date=?,"
					+ "parent_marriage_place=?,remarks=? WHERE birth_id = "+"\""+bid+"\"";

			
			ps = (PreparedStatement) connection.prepareStatement(query);
			
			ps.setString(1, birthEntity.getPage_no());
			ps.setString(2, birthEntity.getBook_no());
			ps.setString(3, birthEntity.getReg_no());
			ps.setString(4, birthEntity.getFirst_name());
			ps.setString(5, birthEntity.getMiddle_name());
			ps.setString(6, birthEntity.getLast_name());
			ps.setString(7, birthEntity.getReg_date());
			ps.setString(8, birthEntity.getBirth_date());
			ps.setString(9,birthEntity.getBirth_place());
			ps.setString(10,birthEntity.getGender());
			ps.setString(11,birthEntity.getNationality());
			ps.setString(12,birthEntity.getFather_name());
			ps.setString(13,birthEntity.getFather_nationality());
			ps.setString(14,birthEntity.getMother_name());
			ps.setString(15,birthEntity.getMother_nationality());
			ps.setString(16,birthEntity.getParent_marriage_date());
			ps.setString(17,birthEntity.getParent_marriage_place());
			ps.setString(18,birthEntity.getRemarks());
			
			
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
	first_name.setText("");
	middle_name.setText("");
	last_name.setText("");
	reg_date.setText("");
	birth_date.setText("");
	birth_place.setText("");
	gender.setSelectedItem("");
	nationality.setText("");
	father_name.setText("");
	father_nationality.setText("");
	mother_name.setText("");
	mother_nationality.setText("");
	parent_marriage_date.setText("");
	parent_marriage_place.setText("");
	remarks.setText("");
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
        g.drawString("We CERTIFY that, among others the following facts of birth appear in our "+"\""+"REGISTER OF",75,260);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("BIRTH on page",80,280);
        
        g.setFont(new Font("Times New Roman",Font.BOLD,12));
        g.setColor(Color.BLACK);
        g.drawString(aci2,170,280);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("of book no",200,280);
        
        g.setFont(new Font("Times New Roman",Font.BOLD,12));
        g.setColor(Color.BLACK);
        g.drawString(aci3,270,280);
        
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("LCR Register Number ",95,310);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString(reg_no.getText().toString(),255,320);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("_______________________________________________",250,322);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("Date Of Registration ",85,338);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString(reg_date.getText().toString(),255,338);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("_______________________________________________",250,340);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("Name Of Child ",85,358);
        
        g.setFont(new Font("Times New Roman",Font.BOLD,12));
        g.setColor(Color.BLACK);
        g.drawString(first_name.getText().toString().toUpperCase()+" "+middle_name.getText().toString().toUpperCase()+" "+last_name.getText().toString().toUpperCase(),255,358);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("_______________________________________________",250,360);
      
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("Sex ",85,375);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString(gender.getSelectedItem().toString(),255,375);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("_______________________________________________",250,377);
      
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("Date Of Birth ",85,393);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString(birth_date.getText().toString(),255,393);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("_______________________________________________",250,395);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("Place Of Birth ",85,410);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString(birth_place.getText().toString(),255,410);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("_______________________________________________",250,412);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("Name Of Mother ",85,427);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString(mother_name.getText().toString(),255,427);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("_______________________________________________",250,429);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("Nationality Of Mother ",85,444);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString(mother_nationality.getText().toString(),255,444);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("_______________________________________________",250,446);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("Name Of Father ",85,461);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString(father_name.getText().toString(),255,461);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("_______________________________________________",250,463);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("Date Of Marriage Of Parents ",85,478);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString(parent_marriage_date.getText().toString(),255,478);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("_______________________________________________",250,480);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("Place Of Marriage Of Parents ",85,495);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString(parent_marriage_place.getText().toString(),255,495);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("_______________________________________________",250,497);
        
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("This CERTIFICATION is Issued To ",70,530);
        
        g.setFont(new Font("Times New Roman",Font.BOLD,12));
        g.setColor(Color.BLACK);
        g.drawString(aci4,257,530);
        
        g.setFont(new Font("Times New Roman",Font.ITALIC,12));
        g.setColor(Color.BLACK);
        g.drawString("Verified By: ",85,560);
        
        g.setFont(new Font("Times New Roman",Font.BOLD,12));
        g.setColor(Color.BLACK);
        g.drawString(signer,100,595);
        
        g.setFont(new Font("Times New Roman",Font.BOLD,12));
        g.setColor(Color.BLACK);
        g.drawString(line1,100,597);
        
        g.setFont(new Font("Times New Roman",Font.ITALIC,10));
        g.setColor(Color.BLACK);
        g.drawString(position1,87,610);
        
        g.setFont(new Font("Times New Roman",Font.ITALIC,12));
        g.setColor(Color.BLACK);
        g.drawString(forAndBy,380,560);
        
        g.setFont(new Font("Times New Roman",Font.BOLD,12));
        g.setColor(Color.BLACK);
        g.drawString(signer2,390,595);
        
        g.setFont(new Font("Times New Roman",Font.BOLD,12));
        g.setColor(Color.BLACK);
        g.drawString(line2,390,597);
        
        g.setFont(new Font("Times New Roman",Font.ITALIC,10));
        g.setColor(Color.BLACK);
        g.drawString(position2,377,610);
        
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("Amount Paid... "+certTypePayment,105,635);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("OR Number... "+or_no,105,650);
        
        g.setFont(new Font("Times New Roman",Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString("Date Paid... "+dateFormat.format(date),105,665);
        
        g.setFont(new Font("Times New Roman",Font.BOLD,12));
        g.setColor(Color.BLACK);
        g.drawString(certType,105,680);
        
        g.setFont(new Font("Times New Roman",Font.BOLD,12));
        g.setColor(Color.BLACK);
        g.drawString("SHIELA ENRIQUEZ GARCIA",378,665);
        
        g.setFont(new Font("Times New Roman",Font.BOLD,12));
        g.setColor(Color.BLACK);
        g.drawString("___________________________",378,667);
        
        g.setFont(new Font("Times New Roman",Font.ITALIC,12));
        g.setColor(Color.BLACK);
        g.drawString("Municipal Civil Registrar",395,680);
        
        g.setFont(new Font("Times New Roman",Font.ITALIC,10));
        g.setColor(Color.BLACK);
        g.drawString("NOTE: This CERTIFICATION is not valid if it has mark of erasure or alteration.",150,710);
       
        
   

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
		Search();
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		Search();
	}


}
