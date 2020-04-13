package ca.etsmtl.log240.financej;

import ca.etsmtl.log240.db.DerbyUtils;

import java.sql.*;
import java.util.*;

public class AccountDAO {
    private Connection conn = null;
    public AccountDAO() { conn = DerbyUtils.getInstance().getConnection(); }

    public void add(String name, String description) throws SQLException {
        PreparedStatement psInsert = null;
        psInsert = conn.prepareStatement("insert into account(name, description) values(?,?)");
        psInsert.setString(1, name);
        psInsert.setString(2, description);
        psInsert.executeUpdate();

        if (psInsert != null)
            psInsert.close();
    }

    public void remove(String name) throws SQLException {
        PreparedStatement psRemove = null;
        psRemove = conn.prepareStatement("delete from account where name = ?");
        psRemove.setString(1, name);
        psRemove.execute();

        if (psRemove != null)
            psRemove.close();
    }

    public void update(String name, String description) throws SQLException {
        PreparedStatement psUpdate = null;
        psUpdate = conn.prepareStatement("update account set description = ? where name = ?");
        psUpdate.setString(1, description);
        psUpdate.setString(2, name);
        psUpdate.execute();

        if (psUpdate != null)
            psUpdate.close();
    }

    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<Account>();
        PreparedStatement psAccounts = null;
        ResultSet resultSet = null;

        psAccounts = conn.prepareStatement("select * from account");
        resultSet = psAccounts.executeQuery();

        while (resultSet.next()) {
            accounts.add(new Account(resultSet.getString(1), resultSet.getString(2)));
        }

        if (resultSet != null)
            resultSet.close();
        if (psAccounts != null)
            psAccounts.close();

        return accounts;
    }

    public Account getAccount(String name) throws SQLException {
        Account account = null;
        PreparedStatement psAccount = null;
        ResultSet resultSet = null;
        psAccount = conn.prepareStatement("select * from account where name = ?");
        psAccount.setString(1, name);
        resultSet = psAccount.executeQuery();

        if (resultSet.next()) {
            account = new Account(resultSet.getString(1), resultSet.getString(2));
        }

        if (resultSet != null)
            resultSet.close();
        if (psAccount != null)
            psAccount.close();

        return account;
    }
}
