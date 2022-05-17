package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtilities {
    private static Connection connection;
    protected static Statement statement;

    public static void DBConnectionOpen() {
        String url = "jdbc:mysql://test.medis.mersys.io:33306/ts_demirel";
        String user = "technostudy";
        String password = "zhTPis0l9#$&";
        try {

            connection= DriverManager.getConnection(url, user, password);// bağlantı sağlandı.

            statement = connection.createStatement(); // sorgularımı çalıştırabilmek için bir yol oluştur.
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public static void DBConnectionClose() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void createTable(String query) {
        DBConnectionOpen();
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DBConnectionClose();
    }
    public static void insertIntoTable(String query) {
        DBConnectionOpen();

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DBConnectionClose();
    }
    public static List<String> getNamesOfTables(String query)  {
        DBConnectionOpen();
        List<List<String>> tableNames=new ArrayList<>();
        List<String> names=new ArrayList<>();
        try
        {
            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                List<String> rowList = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    rowList.add(rs.getString(i));
             }
                tableNames.add(rowList);
            }
        }
        catch (Exception ex){
            System.out.println("ex.getMessage() = " + ex.getMessage());
        }
        DBConnectionClose();

        for (int i = 0; i < tableNames.size(); i++) {
                names.add(tableNames.get(i).get(0));
        }
        return names;
    }

    public static List<List<String>> getlistData(String query)  {
        DBConnectionOpen();
        List<List<String>> returnList=new ArrayList<>();
        try
        {
            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                List<String> rowList = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    rowList.add(rs.getString(i));
                }
                returnList.add(rowList);
            }
        }
        catch (Exception ex){
            System.out.println("ex.getMessage() = " + ex.getMessage());
        }
        DBConnectionClose();

        return returnList;
    }


    public static String[][] getList2DArray(String query){
        DBConnectionOpen();
        String[][] dataTablo=null;

        try
        {
            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            rs.last();
            int rowCount= rs.getRow()+1; // baslik dahil oldugu icin yaptim
            rs.first();
            dataTablo=new String[rowCount][columnCount];

            for (int i =0 ; i < rowCount; i++) {
                rs.absolute(i);
                for (int j = 1; j <=columnCount ; j++) {
                    if(i==0){
                        dataTablo[i][j-1]=rsmd.getColumnName(j);
                    }
                    else {
                        dataTablo[i][j-1]=rs.getString(j);
                    }
                }
            }

        }
        catch (Exception ex){
            System.out.println("ex.getMessage() = " + ex.getMessage());
        }

        DBConnectionClose();
        return dataTablo;
    }

    public static void main(String[] args) {
        // yukaridaki listeyi yan yana yaziyordu. Bunu alt alta yapmak icin
      List<String> namesList= getNamesOfTables("show tables");
        System.out.println("namesList = " + namesList);

        System.out.println("namesList.contains = " + namesList.contains("States"));

        List<String> tableName = DBUtilities.getNamesOfTables("show tables");







    }

}
