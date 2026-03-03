package com.example.teste;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.teste.R;
import com.example.teste.databinding.ActivityLoginBinding;
import com.example.teste.utile.SessionManager;

public class LoginActivity extends AppCompatActivity {

    // ViewBinding
    private ActivityLoginBinding binding;

    // Gestionnaire de session
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser le ViewBinding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialiser le SessionManager
        sessionManager = new SessionManager(this);

        // VÉRIFICATION IMPORTANTE : Si déjà connecté, aller directement aux posts
        if (sessionManager.isLoggedIn()) {
            openPostsActivity();
            return; // Important : arrêter l'exécution ici
        }

        // Configurer les listeners
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
        // Récupérer les valeurs des champs
        String login = binding.etLogin.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        // Vérifier si les champs sont vides
        if (TextUtils.isEmpty(login) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        // "FAKE AUTH" - Si les champs ne sont pas vides, on connecte l'utilisateur
        sessionManager.setLogin(login, true);

        // Message de confirmation
        Toast.makeText(this, "Connexion réussie !", Toast.LENGTH_SHORT).show();

        // Ouvrir l'écran des posts
        openPostsActivity();
    }

    private void openPostsActivity() {
         Intent intent = new Intent(LoginActivity.this, PostsActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         startActivity(intent);
         finish();

        // Pour l'instant, juste un message
        Toast.makeText(this, "Ici on ouvrira PostsActivity", Toast.LENGTH_LONG).show();
    }
}