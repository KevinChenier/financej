package ca.etsmtl.log240.financej;
import org.uispec4j.Button;
import org.uispec4j.Table;
import org.uispec4j.UISpec4J;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class FinancejAbstractTest extends UISpecTestCase {
    private static String connectionURL = "jdbc:derby:" + "FinanceJDB" + ";create=true";
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
        UISpec4J.setWindowInterceptionTimeLimit(300);
    }

    protected void tearDown() throws Exception {
        Connection conn = DriverManager.getConnection(connectionURL);
        conn.close();
        exitButton.click();
        super.tearDown();
    }

}