package payment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class paymentmain {
	
	static Connection con;
	static PreparedStatement pst;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

/////////////////////// Data base Connection ////////////
		
try {
Class.forName("com.mysql.cj.jdbc.Driver");
con = DriverManager.getConnection("jdbc:mysql://localhost/billingsystem","root","0000");
JOptionPane.showMessageDialog(null, "Connection Success");
}

catch (Exception ex) 
{
 ex.printStackTrace();
}

///////////////////////////////////////////////////////////////


JFrame CRUD = new JFrame("Bill Table Management");
CRUD.setResizable(false);
CRUD.setBounds(100, 100, 800, 600);
CRUD.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
CRUD.setLayout(null);

// عنوان الإطار
JLabel title = new JLabel("Bill Table Management");
title.setBounds(300, 20, 200, 30);
CRUD.add(title);

// العمود الأول (الحقول اليسرى)
JLabel billIDLabel = new JLabel("Bill ID:");
billIDLabel.setBounds(50, 70, 100, 30);
CRUD.add(billIDLabel);

JTextField billIDField = new JTextField();
billIDField.setBounds(150, 70, 200, 30);
CRUD.add(billIDField);

JLabel billPayerLabel = new JLabel("Bill Payer:");
billPayerLabel.setBounds(50, 120, 100, 30);
CRUD.add(billPayerLabel);

JTextField billPayerField = new JTextField();
billPayerField.setBounds(150, 120, 200, 30);
CRUD.add(billPayerField);

JLabel roleLabel = new JLabel("Role:");
roleLabel.setBounds(50, 170, 100, 30);
CRUD.add(roleLabel);

JTextField roleField = new JTextField();
roleField.setBounds(150, 170, 200, 30);
CRUD.add(roleField);

// العمود الثاني (الحقول اليمنى)
JLabel phoneLabel = new JLabel("Phone:");
phoneLabel.setBounds(400, 70, 100, 30);
CRUD.add(phoneLabel);

JTextField phoneField = new JTextField();
phoneField.setBounds(500, 70, 200, 30);
CRUD.add(phoneField);

JLabel amountLabel = new JLabel("Amount:");
amountLabel.setBounds(400, 120, 100, 30);
CRUD.add(amountLabel);

JTextField amountField = new JTextField();
amountField.setBounds(500, 120, 200, 30);
CRUD.add(amountField);

JLabel restOfBillLabel = new JLabel("Rest of Bill:");
restOfBillLabel.setBounds(400, 170, 100, 30);
CRUD.add(restOfBillLabel);

JTextField restOfBillField = new JTextField();
restOfBillField.setBounds(500, 170, 200, 30);
CRUD.add(restOfBillField);





/////////////////////////search by Bill ID ///////////////////

JButton search = new JButton("Search");
search.setBounds(580,250, 100, 30);
CRUD.add(search);
search.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) 
{
try {
// الحصول على Bill ID من الحقل
int billID = Integer.parseInt(billIDField.getText());

// إعداد استعلام البحث
pst = con.prepareStatement("SELECT BillPayer, Role, Phone, Amount, RestOfBill FROM Bill WHERE BillID = ?");
pst.setInt(1, billID);
ResultSet rs = pst.executeQuery();

// التحقق من وجود نتيجة
if(rs.next()) {
// تعيين القيم إلى الحقول
billPayerField.setText(rs.getString("BillPayer"));
roleField.setText(rs.getString("Role"));
phoneField.setText(rs.getString("Phone"));
amountField.setText(String.valueOf(rs.getDouble("Amount")));
restOfBillField.setText(String.valueOf(rs.getDouble("RestOfBill")));
} else {
// إذا لم يتم العثور على الفاتورة
JOptionPane.showMessageDialog(null, "No Bill found with ID: " + billID);
billPayerField.setText("");
roleField.setText("");
phoneField.setText("");
amountField.setText("");
restOfBillField.setText("");
}
} catch (SQLException ex) {
ex.printStackTrace();
JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
} catch (NumberFormatException nfe) {
JOptionPane.showMessageDialog(null, "Please enter a valid Bill ID.");
}
}
});
////////////////////////ADD Action ////////////////////
JButton add = new JButton("ADD");
add.setBounds(50,250, 100, 30);
CRUD.add(add);
add.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) 
{
// الحصول على القيم من الحقول
String billPayer = billPayerField.getText();
String role = roleField.getText();
String phone = phoneField.getText();
double amount;
double restOfBill;

try {
// تحويل القيم إلى عدد صحيح أو عشري
amount = Double.parseDouble(amountField.getText());
restOfBill = Double.parseDouble(restOfBillField.getText());

// إعداد استعلام الإدخال
pst = con.prepareStatement("INSERT INTO Bill (BillPayer, Role, Phone, Amount, RestOfBill) VALUES (?, ?, ?, ?, ?)");
pst.setString(1, billPayer);
pst.setString(2, role);
pst.setString(3, phone);
pst.setDouble(4, amount);
pst.setDouble(5, restOfBill);

// تنفيذ الاستعلام
pst.executeUpdate();
JOptionPane.showMessageDialog(null, "Bill data Added");

// إعادة تعيين الحقول
billPayerField.setText("");
roleField.setText("");
phoneField.setText("");
amountField.setText("");
restOfBillField.setText("");

} catch (SQLException e1) {
e1.printStackTrace();
JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
} catch (NumberFormatException nfe) {
JOptionPane.showMessageDialog(null, "Please enter valid numbers for Amount and Rest of Bill.");
}
}
});


////////////////////////update Action ////////////////////

JButton update = new JButton("UPDATE");
update.setBounds(180,250, 100, 30);
CRUD.add(update);
update.addActionListener(new ActionListener() 
{

@Override
public void actionPerformed(ActionEvent e) 
{
// الحصول على القيم من الحقول
int billID = Integer.parseInt(billIDField.getText());
String billPayer = billPayerField.getText();
String role = roleField.getText();
String phone = phoneField.getText();
double amount;
double restOfBill;

try {
// تحويل القيم إلى عدد عشري
amount = Double.parseDouble(amountField.getText());
restOfBill = Double.parseDouble(restOfBillField.getText());

// إعداد استعلام التحديث
pst = con.prepareStatement("UPDATE Bill SET BillPayer = ?, Role = ?, Phone = ?, Amount = ?, RestOfBill = ? WHERE BillID = ?");
pst.setString(1, billPayer);
pst.setString(2, role);
pst.setString(3, phone);
pst.setDouble(4, amount);
pst.setDouble(5, restOfBill);
pst.setInt(6, billID);

// تنفيذ الاستعلام
int rowsUpdated = pst.executeUpdate();
if (rowsUpdated > 0) {
JOptionPane.showMessageDialog(null, "Bill data updated successfully");
} else {
JOptionPane.showMessageDialog(null, "No Bill found with ID: " + billID);
}

// إعادة تعيين الحقول
billIDField.setText("");
billPayerField.setText("");
roleField.setText("");
phoneField.setText("");
amountField.setText("");
restOfBillField.setText("");

} catch (SQLException e1) {
e1.printStackTrace();
JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
} catch (NumberFormatException nfe) {
JOptionPane.showMessageDialog(null, "Please enter valid numbers for Amount and Rest of Bill.");
}
}
});


////////////////////////delete Action ////////////////////
JButton delete = new JButton("DELETE");
delete.setBounds(310,250, 100, 30);
CRUD.add(delete);
delete.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) 
{
// الحصول على Bill ID من الحقل
int billID = Integer.parseInt(billIDField.getText());

try {
// إعداد استعلام الحذف
pst = con.prepareStatement("DELETE FROM Bill WHERE BillID = ?");
pst.setInt(1, billID);
int rowsDeleted = pst.executeUpdate();

if (rowsDeleted > 0) {
JOptionPane.showMessageDialog(null, "Bill data deleted successfully");
} else {
JOptionPane.showMessageDialog(null, "No Bill found with ID: " + billID);
}

// إعادة تعيين الحقول
billIDField.setText("");
billPayerField.setText("");
roleField.setText("");
phoneField.setText("");
amountField.setText("");
restOfBillField.setText("");

} catch (SQLException e1) {
e1.printStackTrace();
JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
} catch (NumberFormatException nfe) {
JOptionPane.showMessageDialog(null, "Please enter a valid Bill ID.");
}
}
});

////////////////////////close Action ////////////////////
JButton close = new JButton("CLOSE");
close.setBounds(440,250, 100, 30);
CRUD.add(close);
close.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) {
	System.exit(0);
	
}
});
//////////////////////////////////////////////////////////



String[] rows = {"id","name","phone"}; 
String[][] cols= {};
JTable data = new JTable(cols,rows);
data.setBounds(50,310, 500, 300);
//DefaultTableModel dm = new DefaultTableModel(cols,rows);
//data.setModel(dm);
JScrollPane sp = new JScrollPane(data);
CRUD.add(data);


///////////////////////next form //////////////////////////

JButton next = new JButton("NEXT");
next.setBounds(580,300, 100, 30);
CRUD.add(next);
next.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) {
	New_form x = new New_form();
	CRUD.setVisible(false);
	
}
});




CRUD.setVisible(true);

		
		
		
	}

}

	
	
	
	
	




