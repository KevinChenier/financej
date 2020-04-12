package ca.etsmtl.log240.financej;

import ca.etsmtl.log240.db.DerbyUtils;

import java.sql.*;

public class CategoryDAO {
    private Connection conn = null;

    public CategoryDAO() { conn = DerbyUtils.getInstance().getConnection(); }

    public void add(String name, String description, float budget) throws SQLException {
        PreparedStatement psInsert;
        psInsert = conn.prepareStatement("insert into category(name, description, budget) values(?,?,?)");
        psInsert.setString(1, name);
        psInsert.setString(2, description);
        psInsert.setFloat(3, budget);
        psInsert.executeUpdate();
    }

    public void updateDescription(String desc, String categoryName) throws SQLException {
        PreparedStatement psInsert;
        psInsert = conn.prepareStatement("update category set description = ? where name = ?");
        psInsert.setString(1, desc);
        psInsert.setString(2, categoryName);
        psInsert.executeUpdate();
    }

    public void updateBudget(String budget, String categoryName) throws SQLException {
        PreparedStatement psInsert;
        psInsert = conn.prepareStatement("update category set budget = ? where name = ?");
        psInsert.setString(1, budget);
        psInsert.setString(2, categoryName);
        psInsert.executeUpdate();
    }

    public void delete(String categoryName) throws SQLException {
        Statement s;
        String SQLString;
        if (isConnection()) {
            s = conn.createStatement();
            SQLString = "DELETE FROM category WHERE name = '" + categoryName + "'";
            System.out.println(SQLString);
            s.executeUpdate(SQLString);
        }
    }

    public int getNameCount() throws SQLException {
        ResultSet AccountResult;
        Statement s;
        int nameCount = 0;

        if (conn != null) {
            s = conn.createStatement();
            AccountResult = s.executeQuery("select count(name) from category");
            while (AccountResult.next()) {
                nameCount = AccountResult.getInt(1);
            }
        }

        return nameCount;
    }

    public String getCategoryDescriptionByName(String categoryName) throws SQLException {
        ResultSet AccountResult;
        Statement s;
        String description = "";
        if (isConnection()) {
            s = conn.createStatement();
            AccountResult = s.executeQuery("select description from category where name = '" + categoryName + "'");
            while (AccountResult.next()) {
                description = AccountResult.getString(1);
            }
        }
        return description;
    }

    public String getCategoryBudgetByName(String categoryName) throws SQLException {
        ResultSet AccountResult;
        Statement s;
        String budget = "";
        if (isConnection()) {
            s = conn.createStatement();
            AccountResult = s.executeQuery("select budget from category where name = '" + categoryName + "'");
            while (AccountResult.next()) {
                budget = AccountResult.getString(1);
            }
        }
        return budget;
    }

    public ResultSet getAllCategoryOrderByName() throws SQLException {
        return conn.createStatement().executeQuery("select * from category order by name");
    }

    public boolean isConnection() {
        return conn != null;
    }

}
