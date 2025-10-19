
package payment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.sql.ResultSet;
import java.sql.SQLException;

public class New_form 
{
    public New_form()
    {
        JFrame form = new JFrame("CRUD Form");
        form.setResizable(true);
        form.setBounds(100, 100, 600, 800);
        form.setLayout(null);
        
        JLabel payerIDLabel = new JLabel("Payer ID :");
        payerIDLabel.setBounds(50, 90, 100, 30);
        form.add(payerIDLabel);
        
        JTextField payerIDField = new JTextField();
        payerIDField.setBounds(160, 90, 200, 30);
        form.add(payerIDField);
        
        JLabel nameLabel = new JLabel("Name :");
        nameLabel.setBounds(50, 140, 100, 30);
        form.add(nameLabel);
        
        JTextField nameField = new JTextField();
        nameField.setBounds(160, 140, 200, 30);
        form.add(nameField);
        
        JLabel phoneLabel = new JLabel("Phone :");
        phoneLabel.setBounds(50, 190, 100, 30);
        form.add(phoneLabel);
        
        JTextField phoneField = new JTextField();
        phoneField.setBounds(160, 190, 200, 30);
        form.add(phoneField);
        
        JLabel roleLabel = new JLabel("Role :");
        roleLabel.setBounds(50, 240, 100, 30);
        form.add(roleLabel);
        
        JTextField roleField = new JTextField();
        roleField.setBounds(160, 240, 200, 30);
        form.add(roleField);
        
        JLabel departmentLabel = new JLabel("Department :");
        departmentLabel.setBounds(50, 290, 100, 30);
        form.add(departmentLabel);
        
        JTextField departmentField = new JTextField();
        departmentField.setBounds(160, 290, 200, 30);
        form.add(departmentField);
        
        JLabel amountLabel = new JLabel("Amount :");
        amountLabel.setBounds(50, 340, 100, 30);
        form.add(amountLabel);
        
        JTextField amountField = new JTextField();
        amountField.setBounds(160, 340, 200, 30);
        form.add(amountField);
        
        JLabel restOfBillLabel = new JLabel("Rest of Bill :");
        restOfBillLabel.setBounds(50, 390, 100, 30);
        form.add(restOfBillLabel);
        
        JTextField restOfBillField = new JTextField();
        restOfBillField.setBounds(160, 390, 200, 30);
        form.add(restOfBillField);
        
        
        //////// إضافة زر ADD//////////////////////////
        JButton addButton = new JButton("ADD");
        addButton.setBounds(50, 450, 100, 30);
        form.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // الحصول على القيم من الحقول
                String name = nameField.getText();
                String phone = phoneField.getText();
                String role = roleField.getText();
                String department = departmentField.getText();
                String amount = amountField.getText();
                String restOfBill = restOfBillField.getText();

                if (name.isEmpty() || phone.isEmpty() || role.isEmpty() || department.isEmpty() || amount.isEmpty() || restOfBill.isEmpty()) {
                    System.out.println("All fields are required.");
                    return;
                }

                try {
                    // إعداد الاستعلام لإضافة البيانات إلى الجدول
                    paymentmain.pst = paymentmain.con.prepareStatement(
                        "INSERT INTO Payer (Name, Phone, Role, Department, Amount, RestOfBill) VALUES (?, ?, ?, ?, ?, ?)"
                    );
                    paymentmain.pst.setString(1, name);
                    paymentmain.pst.setString(2, phone);
                    paymentmain.pst.setString(3, role);
                    paymentmain.pst.setString(4, department);
                    paymentmain.pst.setDouble(5, Double.parseDouble(amount));
                    paymentmain.pst.setDouble(6, Double.parseDouble(restOfBill));

                    // تنفيذ الاستعلام
                    paymentmain.pst.executeUpdate();

                    // إعادة تعيين الحقول
                    nameField.setText("");
                    phoneField.setText("");
                    roleField.setText("");
                    departmentField.setText("");
                    amountField.setText("");
                    restOfBillField.setText("");

                    System.out.println("Record added successfully.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Error while adding the record.");
                } catch (NumberFormatException nfe) {
                    System.out.println("Amount and Rest of Bill must be valid numbers.");
                }
            }
        });

        
        
        
        //////////// ///إضافة زر UPDATE////////////////////////////
        JButton updateButton = new JButton("UPDATE");
        updateButton.setBounds(160, 450, 100, 30);
        form.add(updateButton);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String payerID = payerIDField.getText();
                String name = nameField.getText();
                String phone = phoneField.getText();
                String role = roleField.getText();
                String department = departmentField.getText();
                String amount = amountField.getText();
                String restOfBill = restOfBillField.getText();

                // التحقق من أن الحقول المطلوبة ليست فارغة
                if (payerID.isEmpty() || name.isEmpty() || phone.isEmpty() || role.isEmpty() || department.isEmpty() || amount.isEmpty() || restOfBill.isEmpty()) {
                    System.out.println("All fields are required for update.");
                    return;
                }

                try {
                    // إعداد استعلام SQL لتحديث السجل
                    paymentmain.pst = paymentmain.con.prepareStatement(
                        "UPDATE Payer SET Name = ?, Phone = ?, Role = ?, Department = ?, Amount = ?, RestOfBill = ? WHERE PayerID = ?"
                    );
                    paymentmain.pst.setString(1, name);
                    paymentmain.pst.setString(2, phone);
                    paymentmain.pst.setString(3, role);
                    paymentmain.pst.setString(4, department);
                    paymentmain.pst.setDouble(5, Double.parseDouble(amount));
                    paymentmain.pst.setDouble(6, Double.parseDouble(restOfBill));
                    paymentmain.pst.setInt(7, Integer.parseInt(payerID));

                    // تنفيذ الاستعلام
                    int rowsUpdated = paymentmain.pst.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("Record updated successfully.");
                    } else {
                        System.out.println("No record found with the given Payer ID.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Error while updating the record.");
                } catch (NumberFormatException nfe) {
                    System.out.println("Amount, Rest of Bill, and Payer ID must be valid numbers.");
                }
            }
        });

        
        
        
       
        ///////////// إضافة زر DELETE///////////////////////////////
        JButton deleteButton = new JButton("DELETE");
        deleteButton.setBounds(270, 450, 100, 30);
        form.add(deleteButton);
  
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int courseID = Integer.parseInt(payerIDField.getText()); // Replace course_id with the appropriate JTextField for CourseID.

                try {
                   paymentmain.pst = paymentmain.con.prepareStatement("DELETE FROM Payer WHERE PayerID = ?");
                   paymentmain.pst.setInt(1, courseID);
                   paymentmain.pst.executeUpdate();

                    
                     //   JOptionPane.showMessageDialog(null, "Course Deleted Successfully");
                        payerIDField.setText(""); // Clear the CourseID field after deletion.
                      
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            
        });
     
                   
        
        
        ////////// إضافة زر SEARCH/////////////////////
        JButton searchButton = new JButton("SEARCH");
        searchButton.setBounds(380, 450, 100, 30);
        form.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String payerID = payerIDField.getText(); // الحصول على PayerID من الحقل

                // التحقق من أن الحقل ليس فارغًا
                if (payerID.isEmpty()) {
                    System.out.println("Payer ID is required for search.");
                    return;
                }

                try {
                    // إعداد استعلام SQL للبحث
                    paymentmain.pst = paymentmain.con.prepareStatement("SELECT * FROM Payer WHERE PayerID = ?");
                    paymentmain.pst.setInt(1, Integer.parseInt(payerID));

                    // تنفيذ الاستعلام
                    ResultSet rs = paymentmain.pst.executeQuery();

                    if (rs.next()) {
                        // تعبئة الحقول بالقيم المسترجعة
                        nameField.setText(rs.getString("Name"));
                        phoneField.setText(rs.getString("Phone"));
                        roleField.setText(rs.getString("Role"));
                        departmentField.setText(rs.getString("Department"));
                        amountField.setText(rs.getString("Amount"));
                        restOfBillField.setText(rs.getString("RestOfBill"));

                        System.out.println("Record found and loaded.");
                    } else {
                        System.out.println("No record found with the given Payer ID.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Error while searching for the record.");
                } catch (NumberFormatException nfe) {
                    System.out.println("Payer ID must be a valid integer.");
                }
            }
        });

        
        
        
        ////////close////////////////
        JButton close = new JButton("Close");
        close.setBounds(120, 500, 100, 30);
        form.add(close);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        /////////back///////////
        JButton back = new JButton("Back");
        back.setBounds(300, 500, 100, 30);
        form.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentmain.main(null);
                form.setVisible(false);
            }
        });

        form.setVisible(true);
    }
}



