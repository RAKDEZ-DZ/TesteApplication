package com.example.teste;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import com.example.teste.utile.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class SessionManagerTest {

    private SessionManager sessionManager;
    private Context context;

    @Before
    public void setUp() {
        // Initialiser avant chaque test
        context = ApplicationProvider.getApplicationContext();
        sessionManager = new SessionManager(context);

        // Nettoyer les préférences avant les tests
        sessionManager.logout();
    }

    @Test
    public void testInitialState() {
        // Vérifier qu'au début l'utilisateur n'est pas connecté
        assertFalse("L'utilisateur ne devrait pas être connecté au début",
                sessionManager.isLoggedIn());

        assertEquals("Le login devrait être vide",
                "", sessionManager.getUserLogin());
    }

    @Test
    public void testSetLogin() {
        // Tester la connexion
        String testLogin = "testuser";
        sessionManager.setLogin(testLogin, true);

        // Vérifier
        assertTrue("L'utilisateur devrait être connecté",
                sessionManager.isLoggedIn());

        assertEquals("Le login devrait être 'testuser'",
                testLogin, sessionManager.getUserLogin());
    }

    @Test
    public void testLogout() {
        // D'abord connecter
        sessionManager.setLogin("testuser", true);
        assertTrue(sessionManager.isLoggedIn());

        // Tester la déconnexion
        sessionManager.logout();

        // Vérifier
        assertFalse("L'utilisateur ne devrait plus être connecté",
                sessionManager.isLoggedIn());

        assertEquals("Le login devrait être vide après déconnexion",
                "", sessionManager.getUserLogin());
    }

    @Test
    public void testClear() {
        // Connecter et vérifier
        sessionManager.setLogin("testuser", true);
        assertTrue(sessionManager.isLoggedIn());

        // Tester clear
        sessionManager.logout();

        // Vérifier
        assertFalse("clear() devrait déconnecter",
                sessionManager.isLoggedIn());
    }
}