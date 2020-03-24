package ca.etsmtl.log240.financej;
import org.uispec4j.Table;
import org.junit.Ignore;
import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;
import sun.awt.windows.WPrinterJob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class AccountTest extends FinancejAbstractTest {
    private static String CONNECTION_URL = "jdbc:derby:FinanceJDB;create=true";
    private final int MAX_FIELD_LENGTH = 50;
    private Table accountsTable;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // clear table before each test
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        Statement statement = conn.createStatement();
        statement.executeUpdate("DELETE FROM account");
    }

    public void testCreerAccountValide() throws Exception {
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();
                        // ajouter un compte (valide)
                        final String NAME_INPUT = "Nom Name";
                        final String DESC_INPUT = "Description";
                        window.getTextBox("NAME_TEXT_FIELD").setText(NAME_INPUT);
                        window.getTextBox("DESC_TEXT_FIELD").setText(DESC_INPUT);
                        window.getButton("Add Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount+1);
                        // supprimer le compte cree precedemment en cherchant le nom
                        accountsTable.selectRowsWithText(0, "Nom Name");
                        window.getButton("Delete Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // retourner un "trigger" qui ferme la fenêtre modale
                        return window.getButton("Close").triggerClick();
                    }
                }).run();
    }



    public void testCreerAccountNomVide() throws Exception {
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();
                        // ajouter un compte
                        window.getTextBox("NAME_TEXT_FIELD").setText("");
                        window.getTextBox("DESC_TEXT_FIELD").setText("Description");
                        window.getButton("Add Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // supprimer le compte cree precedemment en cherchant le nom
                        accountsTable.selectRowsWithText(0, "");
                        window.getButton("Delete Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // retourner un "trigger" qui ferme la fenêtre modale
                        return window.getButton("Close").triggerClick();
                    }
                }).run();
    }

    public void testCreerAccountDescriptionVide() throws Exception {
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();
                        // ajouter un compte
                        window.getTextBox("NAME_TEXT_FIELD").setText("Nom Name");
                        window.getTextBox("DESC_TEXT_FIELD").setText("");
                        window.getButton("Add Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // supprimer le compte cree precedemment en cherchant le nom
                        accountsTable.selectRowsWithText(0, "Nom Name");
                        window.getButton("Delete Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // retourner un "trigger" qui ferme la fenêtre modale
                        return window.getButton("Close").triggerClick();
                    }
                }).run();
    }

    public void testCreerAccountNomLessThreeChar() throws Exception {
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();
                        // ajouter un compte
                        window.getTextBox("NAME_TEXT_FIELD").setText("ab");
                        window.getTextBox("DESC_TEXT_FIELD").setText("Description");
                        window.getButton("Add Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // supprimer le compte cree precedemment en cherchant le nom
                        accountsTable.selectRowsWithText(0, "ab");
                        window.getButton("Delete Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // retourner un "trigger" qui ferme la fenêtre modale
                        return window.getButton("Close").triggerClick();
                    }
                }).run();
    }

    public void testCreerAccountDescriptionLessThreeChar() throws Exception {
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();
                        // ajouter un compte
                        window.getTextBox("NAME_TEXT_FIELD").setText("Nom Name");
                        window.getTextBox("DESC_TEXT_FIELD").setText("de");
                        window.getButton("Add Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // supprimer le compte cree precedemment en cherchant le nom
                        accountsTable.selectRowsWithText(0, "Nom Name");
                        window.getButton("Delete Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // retourner un "trigger" qui ferme la fenêtre modale
                        return window.getButton("Close").triggerClick();
                    }
                }).run();
    }

    public void testCreerAccountNomMore50Char() throws Exception {
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();

                        // créer une chaine de charactère de longueur de 51
                        StringBuffer bufferChaine = new StringBuffer(MAX_FIELD_LENGTH + 1);
                        for (int i = 0; i < MAX_FIELD_LENGTH + 1; i++) {
                            bufferChaine.append("n");
                        }

                        // ajouter un compte
                        window.getTextBox("NAME_TEXT_FIELD").setText(bufferChaine.toString());
                        window.getTextBox("DESC_TEXT_FIELD").setText("Description");
                        window.getButton("Add Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // supprimer le compte cree precedemment en cherchant le nom
                        accountsTable.selectRowsWithText(0, bufferChaine.toString());
                        window.getButton("Delete Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // retourner un "trigger" qui ferme la fenêtre modale
                        return window.getButton("Close").triggerClick();
                    }
                }).run();
    }

    public void testCreerAccountDescMore50Char() throws Exception {
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();

                        // créer une chaine de charactère de longueur de 51
                        StringBuffer bufferChaine = new StringBuffer(MAX_FIELD_LENGTH + 1);
                        for (int i = 0; i < MAX_FIELD_LENGTH + 1; i++) {
                            bufferChaine.append("n");
                        }

                        // ajouter un compte
                        window.getTextBox("NAME_TEXT_FIELD").setText("Nom Name");
                        window.getTextBox("DESC_TEXT_FIELD").setText(bufferChaine.toString());
                        window.getButton("Add Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // supprimer le compte cree precedemment en cherchant le nom
                        accountsTable.selectRowsWithText(0, "Nom Name");
                        window.getButton("Delete Account").click();
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // retourner un "trigger" qui ferme la fenêtre modale
                        return window.getButton("Close").triggerClick();
                    }
                }).run();
    }

}