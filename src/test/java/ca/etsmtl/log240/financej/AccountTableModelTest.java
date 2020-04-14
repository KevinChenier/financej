package ca.etsmtl.log240.financej;

import junit.framework.TestCase;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import java.sql.Connection;

public class AccountTableModelTest extends TestCase {

    // invalid DB settings
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String dbName = "FinanceJDB";
    private static String connectionURL = "jdbc:derby:" + dbName + ";create=true";

    // valid DB settings
    private static DerbyUtils derbyConnection;
    private static Connection conn = null;
    private static AccountTableModel acTable;

    @BeforeAll
    public static void init() {
        derbyConnection = derbyConnection.init();
        conn = derbyConnection.getConnection();
        acTable = new AccountTableModel(conn);
    }

    @Test
    public void testGetColumnCount() {
        assertEquals(2, acTable.getColumnCount());
    }

    @Test
    @Order(19)
    public void testGetRowCount() {
        assertEquals(1, acTable.getRowCount());
    }

    @Test
    public void testGetColumnName_0() {
        assertEquals("Name", acTable.getColumnName(0));
    }

    @Test
    public void testGetColumnName_1() {
        assertEquals("Description", acTable.getColumnName(1));
    }

    public void testGetColumnClass() {
    }

    @Test
    public void testIsCellEditable_0() {
        assertFalse(acTable.isCellEditable(0,0));
    }

    @Test
    public void testIsCellEditable_1() {
        assertTrue(acTable.isCellEditable(1,1));
    }

    @Test
    @Order(19)
    public void testAddAccount() {
        assertEquals(0, acTable.AddAccount("Jean-Claude","Description"));
    }

    @Test
    @Order(20)
    public void testSetValueAt_getValueAt() {
        acTable.setValueAt("Jean-Claude2",0, 0);
        assertEquals("Jean-Claude2", acTable.getValueAt(0,0));
    }
}