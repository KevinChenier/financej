package ca.etsmtl.log240.financej;
import org.uispec4j.Button;
import org.uispec4j.Table;
import org.uispec4j.UISpec4J;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class FinancejAbstractTest extends UISpecTestCase {
    private static String CONNECTION_URL = "jdbc:derby:" + "FinanceJDB" + ";create=true";

    protected Table ledgerTable;
    protected Button ledgerButton;
    protected Button categoriesButton;
    protected Button accountsButton;
    protected Button reportsButton;
    protected Button exitButton;

    static {
        UISpec4J.init();
    }

    protected void setUp() throws Exception {
        super.setUp();
        setAdapter(new MainClassAdapter(FinanceJ.class));
        Window window = getMainWindow();
        ledgerTable = window.getTable();
        ledgerButton = window.getButton("Ledger");
        categoriesButton = window.getButton("Categories");
        accountsButton = window.getButton("Accounts");
        reportsButton = window.getButton("Reports");
        exitButton = window.getButton("Exit");
        deleteAllTablesForTests();
        UISpec4J.setWindowInterceptionTimeLimit(300);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        deleteAllTablesForTests();
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        conn.close();
        exitButton.click();
    }

    private void deleteAllTablesForTests() throws SQLException {
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        Statement statement = conn.createStatement();
        statement.executeUpdate("DELETE FROM account");
        statement.executeUpdate("DELETE FROM category");
        statement.executeUpdate("DELETE FROM ledger");
    }

}
