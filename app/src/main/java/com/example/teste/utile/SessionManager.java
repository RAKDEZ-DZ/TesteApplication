package com.example.teste.utile;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    // Constantes pour les préférences
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_LOGIN = "userLogin";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    // Constructeur
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Sauvegarder la session
    public void setLogin(String login, boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(KEY_USER_LOGIN, login);
        editor.apply(); // apply() est asynchrone, commit() est synchrone
    }

    // Vérifier si l'utilisateur est connecté
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Récupérer le login
    public String getUserLogin() {
        return pref.getString(KEY_USER_LOGIN, "");
    }

    // Déconnexion
    public void logout() {
        editor.clear();
        editor.apply();
    }
}