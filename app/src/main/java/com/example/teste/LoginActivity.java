package com.example.teste;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.teste.databinding.ActivityLoginBinding;
import com.example.teste.utile.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private static final int MIN_LOGIN_LENGTH = 3;
    private static final int MAX_LOGIN_LENGTH = 20;

    private ActivityLoginBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            openPostsActivity();
            return;
        }

        setupListeners();
    }

    private void setupListeners() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        String login = binding.etLogin.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        // Réinitialiser les erreurs
        binding.textInputLayoutLogin.setError(null);
        binding.textInputLayoutPassword.setError(null);

        // Vérifier les champs vides
        if (TextUtils.isEmpty(login)) {
            binding.textInputLayoutLogin.setError("Login requis");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            binding.textInputLayoutPassword.setError("Mot de passe requis");
            return;
        }

        // Valider login
        if (login.length() < MIN_LOGIN_LENGTH) {
            binding.textInputLayoutLogin.setError("Le login doit contenir au moins " + MIN_LOGIN_LENGTH + " caractères");
            return;
        }

        if (login.length() > MAX_LOGIN_LENGTH) {
            binding.textInputLayoutLogin.setError("Le login ne peut pas dépasser " + MAX_LOGIN_LENGTH + " caractères");
            return;
        }

        if (!login.matches("^[a-zA-Z0-9._-]+$")) {
            binding.textInputLayoutLogin.setError("Caractères autorisés : lettres, chiffres, . _ -");
            return;
        }

        // Valider password
        if (password.length() < 3) {
            binding.textInputLayoutPassword.setError("Le mot de passe doit contenir au moins 3 caractères");
            return;
        }

        // Tout est valide
        sessionManager.setLogin(login, true);
        Toast.makeText(this, "Connexion réussie !", Toast.LENGTH_SHORT).show();
        openPostsActivity();
    }

    private void openPostsActivity() {
        Intent intent = new Intent(LoginActivity.this, PostsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}