package com.example.teste;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.teste.adapter.PostsAdapter;
import com.example.teste.model.Post;
import com.example.teste.utile.SessionManager;
import com.example.teste.viewmodel.PostViewModel;

public class PostsActivity extends BaseActivity implements PostsAdapter.OnItemClickListener {

    private RecyclerView rvPosts;
    private ProgressBar progressBar;
    private View errorView;
    private TextView errorText;
    private Button btnRetry;
    private PostsAdapter adapter;
    private SessionManager sessionManager;
    private PostViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        // Initialisation des vues
        rvPosts = findViewById(R.id.rvPosts);
        progressBar = findViewById(R.id.progressBar);
        errorView = findViewById(R.id.errorView);
        errorText = findViewById(R.id.errorText);
        btnRetry = findViewById(R.id.btnRetry);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Initialisation des managers
        sessionManager = new SessionManager(this);

        // Configuration du RecyclerView
        setupRecyclerView();

        // Initialisation du ViewModel
        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        // Observer les données
        observeViewModel();

        btnRetry.setOnClickListener(v -> onRetry());
    }

    private void observeViewModel() {
        // Observer la liste des posts
        viewModel.getPosts().observe(this, posts -> {
            if (posts != null) {
                adapter.setPosts(posts);
                hideError();
            }
        });

        // Observer le chargement
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                showLoading();
            } else {
                hideLoading();
            }
        });
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                if (error.contains("Failed to connect") || error.contains("timeout") ||
                        error.contains("network") || error.contains("internet")) {
                    showError("Pas de connexion internet. Vérifiez votre réseau.");
                } else {
                    showError("Erreur serveur . Réessayez plus tard.");
                }
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new PostsAdapter(this);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Post post) {
        DetailFragment detailFragment = DetailFragment.newInstance(post);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right,  // entrée
                        R.anim.slide_out_left,   // sortie
                        R.anim.slide_in_right,   // retour entrée
                        R.anim.slide_out_left    // retour sortie
                )
                .replace(android.R.id.content, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.posts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        sessionManager.logout();
        Intent intent = new Intent(PostsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onRetry() {
        viewModel.loadPosts();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            finishAffinity();
        }
    }
}