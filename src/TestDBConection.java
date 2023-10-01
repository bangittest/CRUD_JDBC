import java.sql.*;

public class TestDBConection {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/ecommerce3";
        String user="root";
        String password="";
        String sql="select * from tbl_product";
        try(Connection conn= DriverManager.getConnection(url,user,password)){
            System.out.println(conn.getCatalog());
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
            showInfo(resultSet);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
    private static void showInfo(ResultSet rs){
        try {
            while (rs.next()){
                System.out.println(rs.getInt(1)+ " - " +rs.getString(2)
                        + " - " +rs.getString(3)+ " - " + rs.getFloat(4));
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
