package ca.etsmtl.log240.financej;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.sql.Connection;
import static ca.etsmtl.log240.financej.FinanceJ.SQLExceptionPrint;

public class AccountDAOTest extends TestCase {

    // invalid DB settings
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String dbName = "FinanceJDB";
    private static String connectionURL = "jdbc:derby:" + dbName + ";create=true";

    // valid DB settings
    private static DerbyUtils derbyConnection;
    private static Connection conn = null;
    private static AccountDAO dao;

    @BeforeAll
    public static void init() {
        derbyConnection = derbyConnection.init();
        conn = derbyConnection.getConnection();
        dao = new AccountDAO(conn);
        dao.create("Jean", "Description");
        dao.create("Jean2", "Description2");
    }

    @Test public void testCreateAccount() {
        int responseFirstAccount = dao.create("Jean", "Description");
        assertEquals( 0, responseFirstAccount);
        int responseSecondAccount = dao.create("Jean2", "Description2");
        assertEquals( 0, responseSecondAccount);
    }

    @Test public void testRowCount() {
        assertEquals(2, dao.rowCount());
    }

    @Test
    public void testGetValueAt_Name_Row0() {
        assertEquals(dao.getValueAt(0,0), "Jean");
    }

    @Test
    public void testGetValueAt_Description_Row0() {
        assertEquals(dao.getValueAt(1,1), "Description2");
    }

    @Test public void testUpdateAccountValid() {
        assertEquals(dao.update( "description", "Description3", "name","Jean"), true);
        assertEquals( "Description3", dao.getValueAt(0,1));
    }

    @Test public void testUpdateAccountInvalid() {
        assertEquals(dao.update( "description", "Description5", "name","Paul"), false);
    }

    @Test public void testUpdateDeleteAccountByName() {
        assertEquals(true, dao.delete( "name", "Jean"));
    }

    @Test public void testUpdateDeleteAccountByDescription() {
        assertEquals(true, dao.delete( "description", "Description2"));
    }

    @Test public void testUpdateDeleteAccountWithInexistantName() {
        assertEquals(false, dao.delete( "name", "Paul"));
    }

    @Test
    public void testGetValueAt_nullConnexion() {
        AccountDAO dao2 = new AccountDAO(null);
        String result = (String)dao2.getValueAt(0,0);
        assertEquals(result, "");
    }

}