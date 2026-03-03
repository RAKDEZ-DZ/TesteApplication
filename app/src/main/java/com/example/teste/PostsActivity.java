//package com.example.teste;
//
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import com.example.teste.R;
//import com.example.teste.adapter.PostsAdapter;
//import com.example.teste.model.Post;
//import com.example.teste.repository.PostRepository;
//import com.example.teste.utile.SessionManager;
//import java.util.List;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import androidx.recyclerview.widget.RecyclerView;
//import android.widget.ProgressBar;
//import androidx.appcompat.widget.Toolbar;
//public class PostsActivity extends AppCompatActivity implements PostsAdapter.OnItemClickListener {
//
//    private RecyclerView rvPosts;
//    private ProgressBar progressBar;
//    private PostsAdapter adapter;
//    private SessionManager sessionManager;
//    private PostRepository repository;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_posts);
//
//        // Initialisation des vues
//        rvPosts = findViewById(R.id.rvPosts);
//        progressBar = findViewById(R.id.progressBar);
//
//        // Toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        // Initialisation des managers
//        sessionManager = new SessionManager(this);
//        repository = new PostRepository();
//
//        // Configuration du RecyclerView
//        setupRecyclerView();
//
//        // Charger les posts
//        loadPosts();
//    }
//
//    private void setupRecyclerView() {
//        adapter = new PostsAdapter(this);
//        rvPosts.setLayoutManager(new LinearLayoutManager(this));
//        rvPosts.setAdapter(adapter);
//    }
//
//    private void loadPosts() {
//        showLoading(true);
//
//        repository.getPosts().enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
//                showLoading(false);
//
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Post> posts = response.body();
//
//                    // AJOUTE CES LOGS POUR DEBUG
//                    System.out.println("====== NOMBRE DE POSTS RECUS ======");
//                    System.out.println("Taille de la liste: " + posts.size());
//                    if (posts.size() > 0) {
//                        System.out.println("Premier titre: " + posts.get(0).getTitle());
//                    }
//
//                    adapter.setPosts(posts);
//
//                    // Afficher un Toast avec le nombre de posts
//                    Toast.makeText(PostsActivity.this,
//                            posts.size() + " posts chargés",
//                            Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(PostsActivity.this,
//                            "Erreur de chargement: " + response.code(),
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
//                showLoading(false);
//                Toast.makeText(PostsActivity.this,
//                        "Erreur réseau: " + t.getMessage(),
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//    @Override
//    public void onItemClick(Post post) {
//        // Remplacer le Toast par l'ouverture du fragment
//        DetailFragment detailFragment = DetailFragment.newInstance(post);
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(android.R.id.content, detailFragment)  // Remplace tout le contenu
//                .addToBackStack(null)  // Ajoute à la pile pour pouvoir revenir en arrière
//                .commit();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.posts_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.action_logout) {
//            logout();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void logout() {
//        sessionManager.logout();
//        finish(); // Retour à LoginActivity
//    }
//
//    private void showLoading(boolean show) {
//        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//        rvPosts.setVisibility(show ? View.GONE : View.VISIBLE);
//    }
//
//    @Override
//    public void onBackPressed() {
//        // Si on est dans le fragment détail, revenir à la liste
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            // Si on est sur la liste, fermer l'application
//            super.onBackPressed();
//            finishAffinity();
//        }
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        // Gérer le bouton de retour dans la toolbar
//        onBackPressed();
//        return true;
//    }
//}


package com.example.teste;

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

        // Bouton réessayer
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

        // Observer les erreurs
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                showError(error);
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