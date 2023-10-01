import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Employee {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtSary;
    private JTextField txtMobile;
    private JButton saveButton;
    private JTable table1;
    private JButton Update;
    private JButton Delete;
    private JButton Seach;
    private JTextField txtid;
    private JScrollPane table_1;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/rbcompany", "root", "");
            System.out.println("success");
        } catch (ClassNotFoundException ex) {
           ;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void table_load() {
        try {
            pst = con.prepareStatement("select * from employee");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Employee() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empname,salary,mobile;

                empname=txtName.getText();
                salary=txtSary.getText();
                mobile=txtMobile.getText();


                try {
                    pst=con.prepareStatement("insert into employee(empname,salary,mobile)values(?,?,?)");
                    pst.setString(1,empname);
                    pst.setString(2,salary);
                    pst.setString(3,mobile);
                    pst.executeUpdate();
                    JOptionPane.showConfirmDialog(null,"Record ");
                    table_load();
                    txtName.setText("");
                    txtSary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();


                }catch (SQLException e1){}


            }
        });
        //seach
        Seach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String empid = txtid.getText();
                    pst = con.prepareStatement("SELECT empname, salary, mobile FROM employee WHERE id = ?");
                    pst.setString(1, empid);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        String empname = rs.getString("empname");
                        String salary = rs.getString("salary");
                        String mobile = rs.getString("mobile");

                        txtName.setText(empname);
                        txtSary.setText(salary);
                        txtMobile.setText(mobile);
                    } else {
                        txtName.setText("");
                        txtSary.setText("");
                        txtMobile.setText("");
                        JOptionPane.showConfirmDialog(null,"invalid Employee No");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //update
        Update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empid,empname,salary,mobile;

                empname=txtName.getText();
                salary=txtSary.getText();
                mobile=txtMobile.getText();
                empid=txtid.getText();

                try {
                    pst=con.prepareStatement("update employee set empname=?,salary=?,mobile=? where id=?");
                    pst.setString(1,empname);
                    pst.setString(2,salary);
                    pst.setString(3,mobile);
                    pst.setString(4,empid);

                    pst.executeUpdate();
                    JOptionPane.showConfirmDialog(null,"update");
                    table_load();
                    txtName.setText("");
                    txtSary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }catch (SQLException e1){
                    e1.printStackTrace();
                }



            }
        });

        //xoa
        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empid;
                empid=txtid.getText();
                try {
                    pst=con.prepareStatement("delete from employee where id=?");
                    pst.setString(1,empid);
                    pst.executeUpdate();
                    JOptionPane.showConfirmDialog(null,"Record Update");

                    table_load();
                    txtName.setText("");
                    txtSary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
    }
}
