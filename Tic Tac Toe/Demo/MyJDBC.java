import java.sql.*;

public class MyJDBC {
    public static void main(String[] args) {
        try{
            Connection koneksi = DriverManager.getConnection(
                    "jdbc:mysql://mysql-21c75f56-bp-001.f.aivencloud.com:28622/defaultdb",
                    "avnadmin",
                    "AVNS_CM-W653DJq1UXikQyNt"


                    );

            Statement statement = koneksi.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while(resultSet.next()){
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("password"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
