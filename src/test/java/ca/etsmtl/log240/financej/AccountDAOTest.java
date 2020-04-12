package ca.etsmtl.log240.financej;

import ca.etsmtl.log240.db.DerbyUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class AccountDAOTest {
    private AccountDAO accountDAO = new AccountDAO();
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        deleteAllAccounts();
    }

    private void deleteAllAccounts() throws Exception {
        Connection conn = DerbyUtils.getInstance().getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate("DELETE FROM account");
    }

    @Test
    public void addTest() {
        try {
            String name = "Kevin";
            String description = "Etudiant";

            accountDAO.add(name, description);

            assertEquals(description, accountDAO.getAccount(name).getDescription());
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void addLessThanMinimumNameTest() throws SQLException {
        String shortAccountName = new String(new char[Account.MINIMUM_NAME_LENGTH - 1]);
        String description = "Etudiant";
        accountDAO.add(shortAccountName, description);
    }

    @Test
    public void addLessThanMinimumDescriptionTest() throws SQLException {
        String name = "Kevin";
        String shortAccountDescription = new String(new char[Account.MINIMUM_DESCRIPTION_LENGTH - 1]);
        accountDAO.add(name, shortAccountDescription);
    }

    @Test
    public void addMoreThanMaximumNameTest() throws SQLException {
        String longAccountName = new String(new char[Account.MAXIMUM_NAME_LENGTH + 1]);
        String description = "Etudiant";
        accountDAO.add(longAccountName, description);
    }

    @Test
    public void addMoreThanMaximumDescriptionTest() throws SQLException {
        String name = "Kevin";
        String longAccountDescription = new String(new char[Account.MAXIMUM_DESCRIPTION_LENGTH + 1]);
        accountDAO.add(name, longAccountDescription);
    }

    @Test
    public void removeTest() {
        try{
            String name = "Kevin";
            String description = "Etudiant";

            accountDAO.add(name, description);

            assertEquals(description, accountDAO.getAccount(name).getDescription());

            accountDAO.remove(name);
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void removeInvalidAccountTest() {
        try{
            String name = "Kevin";
            String description = "Etudiant";

            accountDAO.add(name, description);
            accountDAO.remove("Mauvais");
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void updateTest() {
        try{
            String name = "Kevin";
            String oldDescription = "Etudiant";
            String newDescription = "Professeur";

            accountDAO.add(name, oldDescription);
            accountDAO.update(name, newDescription);

            assertEquals(newDescription, accountDAO.getAccount(name).getDescription());

            accountDAO.remove(name);
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void updateInvalidDescriptionTest() {
        try{
            String name = "Kevin";
            String oldDescription = "Etudiant";
            String newDescription = new String(new char[Account.MINIMUM_DESCRIPTION_LENGTH - 1]);

            accountDAO.add(name, oldDescription);
            accountDAO.update(name, newDescription);

            assertEquals(newDescription, accountDAO.getAccount(name).getDescription());
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void getAllAccountsTest() {
        try {
            String account1Name = "Kevin";
            String account1Description = "Etudiant";
            String account2Name = "John";
            String account2Description = "Professeur";

            accountDAO.add(account1Name, account1Description);
            accountDAO.add(account2Name, account2Description);

            List<Account> accounts = accountDAO.getAllAccounts();

            assertEquals(account1Name, accounts.get(0).getName());
            assertEquals(account2Name, accounts.get(1).getName());
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    public void getAccountTest() {
        try {
            String name = "Kevin";
            String description = "Etudiant";

            accountDAO.add(name, description);

            Account account = accountDAO.getAccount(name);

            assertEquals(account.getName(), name);
        } catch (SQLException e) {
            fail();
        }
    }

    // TODO fix later
    @Test(expected = SQLException.class)
    public void getInvalidAccountTest() {
        try {
            String name = "Kevin";
            String description = "Etudiant";

            accountDAO.add(name, description);

            Account account = accountDAO.getAccount("Mauvais");

            assertEquals(account.getName(), name);
        } catch (SQLException e) {
            fail();
        }
    }


}