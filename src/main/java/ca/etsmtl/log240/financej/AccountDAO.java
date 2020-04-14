package ca.etsmtl.log240.financej;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccountDAO implements DAO {

    private Connection conn = null;
    private String[] columnNames = {"Name", "Description"};

    public AccountDAO(Connection DBConn) {
        conn = DBConn;
    }

    public boolean delete(String field, String value){
        Statement s;
        String SQLString;
        if (conn != null) {
            try {
                s = conn.createStatement();
                SQLString = "DELETE FROM account WHERE "+field+" LIKE '" + value + "'";
                System.out.println(SQLString);
                s.execute(SQLString);
                return true;
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountDAO DeleteAccount");
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public int create(String[] data){
        int ErrorCode = 0;
        PreparedStatement psInsert;
        try {
            psInsert = conn.prepareStatement("insert into account(name, description) values(?,?)");
            psInsert.setString(1, data[0]);
            psInsert.setString(2, data[1]);
            psInsert.executeUpdate();
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: AddAccount");
            e.printStackTrace();
            ErrorCode = 1;
        }
        return ErrorCode;
    }

    public boolean update(String fieldToUpdate, String valueToUpdate, String fieldWhere, String valueWhere){
        String SQLString;
        try {
            Statement s = conn.createStatement();
            SQLString = "update account set " + fieldToUpdate + " ='" + valueToUpdate + "' where " + fieldWhere + " LIKE '" + valueWhere + "'";
            System.out.println(SQLString);
            s.execute(SQLString);
            return true;
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: in update in AccountDAO.java");
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet readAll() {
        Statement s;
        if (conn != null) {
            try {
                s = conn.createStatement();
                ResultSet result = s.executeQuery("select * from account order by name");
                return result;
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountDAO readAll");
                e.printStackTrace();
            }
        }
        return null;
    }

    public ResultSet read(int row) {
        ResultSet AccountResult;
        try {
            int CurrentRow = 0;
            AccountResult = readAll();
            while (AccountResult.next()) {
                if (CurrentRow == row) {
                    return AccountResult;
                }
                CurrentRow++;
            }
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: in AccountDAO read");
            e.printStackTrace();
        }
        return null;
    }

    public Object getValueAt(int row, int col) {
        ResultSet AccountResult;
        AccountResult = read(row);
        try {
            String result = AccountResult.getString(col + 1);
            return result;
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: in AccountDAO getValueAt");
            e.printStackTrace();
        }
        return "";
    }

    public int count(){
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
                System.out.println(" . . . exception thrown: in AccountDAO getRowCount");
                e.printStackTrace();
            }
        }
        return 0;
    }
}
