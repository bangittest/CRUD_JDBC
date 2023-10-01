import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Students {
    private JTextField studentsTextField;
    private JTextField txtname;
    private JTextField txtage;
    private JTextField txtphone;
    private JTextField txtaddress;
    private JTextField txtsalary;
    private JButton addbtn;
    private JButton update;
    private JButton delete;
    private JButton seach;
    private JTable table1;
    private JTextField txtid;
    private JPanel Main;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Students");
        frame.setContentPane(new Students().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/student", "root", "");
            System.out.println("susscess");

        } catch (ClassNotFoundException ex) {
            ;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void table_load() {
        try {
            pst = con.prepareStatement("select *from tbl_sinhvien");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Students() {
        connect();
        table_load();
        addbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, age, phone, address, salary;

                name = txtname.getText();
                age = txtage.getText();
                phone = txtphone.getText();
                address = txtaddress.getText();
                salary = txtsalary.getText();

                try {
                    pst = con.prepareStatement("INSERT INTO tbl_sinhvien(name, age, phone, address, salary) VALUES (?, ?, ?, ?, ?)");
                    pst.setString(1, name);
                    pst.setString(2, age);
                    pst.setString(3, phone);
                    pst.setString(4, address);
                    pst.setString(5, salary);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record inserted");
                    table_load();
                    txtname.setText("");
                    txtage.setText("");
                    txtphone.setText("");
                    txtaddress.setText("");
                    txtsalary.setText("");
                    txtname.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        seach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id=txtid.getText();
                    pst=con.prepareStatement("SELECT name, age, phone, address, salary FROM tbl_sinhvien WHERE id=?");
                    pst.setString(1,id);
                    ResultSet rs=pst.executeQuery();
                    if(rs.next()){
                        String name=rs.getString("name");
                        String age=rs.getString("age");
                        String phone=rs.getString("phone");
                        String address=rs.getString("address");
                        String salary=rs.getString("salary");

                        txtname.setText(name);
                        txtage.setText(age);
                        txtphone.setText(phone);
                        txtaddress.setText(address);
                        txtsalary.setText(salary);
                    }else {
                        txtname.setText("");
                        txtage.setText("");
                        txtphone.setText("");
                        txtaddress.setText("");
                        txtsalary.setText("");
                        JOptionPane.showConfirmDialog(null,"invalid Employee No");
                    }
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id, name, age, phone, address, salary;

                name = txtname.getText();
                age = txtage.getText();
                phone = txtphone.getText();
                address = txtaddress.getText();
                salary = txtsalary.getText();
                id=txtid.getText();
                try {
                    pst = con.prepareStatement("UPDATE tbl_sinhvien SET name=?,age=?,phone=?,address=?,salary=? WHERE id=?");
                    pst.setString(1, name);
                    pst.setString(2, age);
                    pst.setString(3, phone);
                    pst.setString(4, address);
                    pst.setString(5, salary);
                    pst.setString(6,id);
                    pst.executeUpdate();
                    JOptionPane.showConfirmDialog(null,"update");
                    table_load();
                    txtname.setText("");
                    txtage.setText("");
                    txtphone.setText("");
                    txtaddress.setText("");
                    txtsalary.setText("");
                    txtname.requestFocus();
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;
                id=txtid.getText();
                try{
                    pst=con.prepareStatement("DELETE FROM tbl_sinhvien WHERE id=?");
                    pst.setString(1,id);
                    pst.executeUpdate();
                    JOptionPane.showConfirmDialog(null,"Record Delete");


                    table_load();
                    txtname.setText("");
                    txtage.setText("");
                    txtphone.setText("");
                    txtaddress.setText("");
                    txtsalary.setText("");
                    txtname.requestFocus();

                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
    }
}
