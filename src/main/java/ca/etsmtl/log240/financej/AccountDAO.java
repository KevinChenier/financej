package ca.etsmtl.log240.financej;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccountDAO {

    private Connection conn = null;

    public AccountDAO(Connection DBConn) {
        conn = DBConn;
    }

    public int create(String Name, String Description){
        int ErrorCode = 0;
        PreparedStatement psInsert;
        // TODO
        try {
            psInsert = conn.prepareStatement("insert into account(name, description) values(?,?)");
            psInsert.setString(1, Name);
            psInsert.setString(2, Description);

            psInsert.executeUpdate();
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: AddAccount");
            e.printStackTrace();
            ErrorCode = 1;
        }

        return ErrorCode;
    }

    public int rowCount(){
        ResultSet AccountResult;
        Statement s;
        if (conn != null) {
            try {
                s = conn.createStatement();
                AccountResult = s.executeQuery("select count(name) from account");
                while (AccountResult.next()) {
                    return AccountResult.getInt(1);
                }
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountTableModel getRowCount");
                e.printStackTrace();
            }
        }
        return 0;
    }

    public Object getValueAt(int row, int col) {
        ResultSet AccountResult;
        Statement s;
        int CurrentRow = 0;
        if (conn != null) {
            try {
                s = conn.createStatement();
                AccountResult = s.executeQuery("select * from account order by name");
                while (AccountResult.next()) {
                    if (CurrentRow == row) {
                        if (col == 0) {
                            return AccountResult.getString(1);
                        } else if (col == 1) {
                            return AccountResult.getString(2);
                        }
                    }
                    CurrentRow++;
                }
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountTableModel getValueAt");
                e.printStackTrace();
            }
        }
        return "";
    }

    public void read(){}

    public boolean update(String fieldToUpdate, String valueToUpdate, String fieldWhere, String valueWhere){
        String SQLString;
        String AccountName;
        try {
            Statement s = conn.createStatement();
            SQLString = "update account set '" + fieldToUpdate + "' ='" + valueToUpdate + "' where '" + fieldWhere + "' = '" + valueWhere + "'";
            System.out.println(SQLString);
            s.execute(SQLString);
            return true;
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: in setValueAt in Account.java");
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String field, String value){
        Statement s;
        String SQLString;
        if (conn != null) {
            try {
                s = conn.createStatement();
                SQLString = "DELETE FROM account WHERE '"+field+"' = '" + value + "'";
                System.out.println(SQLString);
                s.executeUpdate(SQLString);
                return true;
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountTableModel DeleteAccount");
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
