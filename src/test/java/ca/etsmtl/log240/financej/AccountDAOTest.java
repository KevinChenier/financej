package ca.etsmtl.log240.financej;

import junit.framework.TestCase;
import org.junit.jupiter.api.*;

import java.sql.Connection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    }

    @Test
    @Order(1)
    public void testCreateAccount() {
        int responseFirstAccount = dao.create("Jean", "Description");
        assertEquals( 0, responseFirstAccount);
        int responseSecondAccount = dao.create("Jean2", "Description2");
        assertEquals( 0, responseSecondAccount);
    }

    @Test
    @Order(2)
    public void testRowCount() {
        assertEquals(2, dao.rowCount());
    }

    @Test
    @Order(3)
    public void testGetRowCount() {
        assertEquals(2, dao.rowCount());
    }

    @Test
    @Order(5)
    public void testGetValueAt_Name_Row0() {
        assertEquals(dao.getValueAt(0,0), "Jean");
    }

    @Test
    @Order(6)
    public void testGetValueAt_Description_Row0() {
        assertEquals(dao.getValueAt(1,1), "Description2");
    }

    @Test
    @Order(7)
    public void testUpdateAccountValid() {
        assertTrue(dao.update("description", "Description3", "name", "Jean"));
        assertEquals( "Description3", dao.getValueAt(0,1));
    }

    @Test
    @Order(8)
    public void testUpdateAccountInvalid() {
        assertFalse(dao.update( "description", "Description5", "name","Paul"));
    }

    @Test
    @Order(9)
    public void testSetValueAt() {
        dao.setValueAt("Description4", 0, 1);
        assertEquals(dao.getValueAt(0,1), "Description4");
    }

    @Test
    @Order(10)
    public void testDeleteAccountByName() {
        assertTrue(dao.delete( "name", "Jean"));
    }

    @Test
    @Order(11)
    public void testUpdateDeleteAccountByDescription() {
        assertTrue(dao.delete( "description", "Description2"));
    }

    @Test
    @Order(12)
    public void testUpdateDeleteAccountWithInexistantName() {
        assertFalse(dao.delete( "name", "Paul"));
    }

    @Test
    @Order(13)
    public void testGetValueAt_nullConnexion() {
        AccountDAO dao2 = new AccountDAO(null);
        String result = (String)dao2.getValueAt(0,0);
        assertEquals(result, "");
    }

}