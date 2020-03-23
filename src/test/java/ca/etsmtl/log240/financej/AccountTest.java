package ca.etsmtl.log240.financej;
import org.uispec4j.Table;
import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;
import sun.awt.windows.WPrinterJob;

public class AccountTest extends FinancejAbstractTest {
    private final int MAX_FIELD_LENGTH = 50;
    private Table accountsTable;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // clear table before each test
        accountsTable = getMainWindow().getTable();
        accountsTable.clearSelection();
    }

    public void testCreerAccountValide() throws Exception {
        /* Voici comment traiter une fenêtre modale avec uispec4j.
        * Voir "Intercepting windows and dialogs" dans la documentation en
        ligne.
        */
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();
                        // ajouter un compte (valide)
                        window.getTextBox("NAME_TEXT_FIELD").setText("Nom Name");
                        window.getTextBox("DESC_TEXT_FIELD").setText("Description");
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

    public void testCreerAccountNomEtDescriptionVide() throws Exception {
        /* Voici comment traiter une fenêtre modale avec uispec4j.
        * Voir "Intercepting windows and dialogs" dans la documentation en
        ligne.
        */
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();
                        // ajouter un compte
                        window.getTextBox("NAME_TEXT_FIELD").setText("");
                        window.getTextBox("DESC_TEXT_FIELD").setText("");
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

    public void testCreerAccountNomEtDescriptionLessThreeChar() throws Exception {
        /* Voici comment traiter une fenêtre modale avec uispec4j.
        * Voir "Intercepting windows and dialogs" dans la documentation en
        ligne.
        */
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();
                        // ajouter un compte
                        window.getTextBox("NAME_TEXT_FIELD").setText("ab");
                        window.getTextBox("DESC_TEXT_FIELD").setText("de");
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

    public void testCreerAccountNomEtDescriptionMore50Char() throws Exception {
        /* Voici comment traiter une fenêtre modale avec uispec4j.
        * Voir "Intercepting windows and dialogs" dans la documentation en
        ligne.
        */
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        accountsTable = window.getTable();
                        int initialRowCount = accountsTable.getRowCount();

                        // créer une chaine de charactère de longueur de 50
                        StringBuffer bufferChaine = new StringBuffer(MAX_FIELD_LENGTH);
                        for (int i = 0; i < MAX_FIELD_LENGTH; i++) {
                            bufferChaine.append("n");
                        }

                        // ajouter un compte
                        window.getTextBox("NAME_TEXT_FIELD").setText(bufferChaine.toString());
                        window.getTextBox("DESC_TEXT_FIELD").setText(bufferChaine.toString());
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
}