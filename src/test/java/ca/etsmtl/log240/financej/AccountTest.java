package ca.etsmtl.log240.financej;
import org.uispec4j.Table;
import org.junit.Ignore;
import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;


public class AccountTest extends FinancejAbstractTest {
    private final int MAX_SIZE_NAME= 50;
    private final int MAX_SIZE_DESCRIPTION= 250;
    private Table accountsTable;

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
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
                        StringBuffer bufferChaine = new StringBuffer(MAX_SIZE_NAME + 1);
                        for (int i = 0; i < MAX_SIZE_NAME + 1; i++) {
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

    public void testCreerAccountDescMore250Char() throws Exception {
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();

                        // créer une chaine de charactère de longueur de 251
                        StringBuffer bufferChaine = new StringBuffer(MAX_SIZE_DESCRIPTION + 1);
                        for (int i = 0; i < MAX_SIZE_DESCRIPTION + 1; i++) {
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

    public void testDeleteAccount() throws Exception {
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();
                        int initialLedgerTableCount = ledgerTable.getRowCount();
                        // ajouter un compte (valide)
                        final String NAME_INPUT = "Nom Name";
                        final String DESC_INPUT = "Description";
                        window.getTextBox("NAME_TEXT_FIELD").setText(NAME_INPUT);
                        window.getTextBox("DESC_TEXT_FIELD").setText(DESC_INPUT);
                        window.getButton("Add Account").click();
                        // supprimer le compte cree precedemment en cherchant le nom
                        accountsTable.selectRowsWithText(0, "Nom Name");
                        window.getButton("Delete Account").click();
                        // account deleted
                        assertEquals(accountsTable.getRowCount(), initialRowCount);
                        // retourner un "trigger" qui ferme la fenetre
                        return window.getButton("Close").triggerClick();
                    }
                }).run();
    }

}