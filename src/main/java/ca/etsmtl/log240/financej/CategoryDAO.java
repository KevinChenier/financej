package ca.etsmtl.log240.financej;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CategoryDAO implements DAO {

    private Connection conn = null;

    public CategoryDAO(Connection DBConn) {
        conn = DBConn;
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

    public ResultSet readAll(){
        Statement s;
        if (conn != null) {
            try {
                s = conn.createStatement();
                ResultSet result = s.executeQuery("select * from category order by name");
                return result;
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountDAO readAll");
                e.printStackTrace();
            }
        }
        return null;
    };
    public int create(String[] data){
        int ErrorCode = 0;
        PreparedStatement psInsert;

        try {
            psInsert = conn.prepareStatement("insert into category(name, description, budget) values(?,?,?)");
            psInsert.setString(1, data[0]);
            psInsert.setString(2, data[1]);
            psInsert.setString(3, data[2]);

            psInsert.executeUpdate();
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: AddCategory");
            e.printStackTrace();
            ErrorCode = 1;
        }

        return ErrorCode;
    };

    public boolean update(String fieldToUpdate, String valueToUpdate, String fieldWhere, String valueWhere){
        String SQLString;
        try {
            Statement s = conn.createStatement();
            SQLString = "update category set " + fieldToUpdate + " ='" + valueToUpdate + "' where " + fieldWhere + " LIKE '" + valueWhere + "'";
            System.out.println(SQLString);
            s.execute(SQLString);
            return true;
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: in setValueAt in CategoryDAO.java");
            e.printStackTrace();
            return false;
        }
    };

    public boolean delete(String field, String value){
        Statement s;
        String SQLString;
        if (conn != null) {
            try {
                s = conn.createStatement();
                SQLString = "DELETE FROM category WHERE "+field+" LIKE '" + value + "'";
                System.out.println(SQLString);
                s.execute(SQLString);
                return true;
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in CategoryDAO delete");
                e.printStackTrace();
                return false;
            }
        }
        return false;
    };

    public Object getValueAt(int row, int col) {
        ResultSet CategoryResult;
        CategoryResult = read(row);
        try {
            String result = CategoryResult.getString(col + 1);
            return result;
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: in CategoryTableDAO getValueAt");
            e.printStackTrace();
        }
        return "";
    }

    public int count(){
        ResultSet CategoryResult;
        Statement s;
        if (conn != null) {
            try {
                s = conn.createStatement();
                CategoryResult = s.executeQuery("select count(name) from category");
                while (CategoryResult.next()) {
                    return CategoryResult.getInt(1);
                }
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in CategoryDAO count");
                e.printStackTrace();
            }
        }
        return 0;
    };
}
