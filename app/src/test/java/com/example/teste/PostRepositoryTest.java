package com.example.teste;

import com.example.teste.model.Post;
import com.example.teste.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;
import static org.junit.Assert.*;

public class PostRepositoryTest {

    private PostRepository repository;

    @Before
    public void setUp() {
        repository = new PostRepository();
    }

    @Test
    public void testGetPosts() throws IOException {
        // Exécuter l'appel API de façon synchrone (pour les tests)
        Response<List<Post>> response = repository.getPosts().execute();

        // Vérifier que l'appel a réussi
        assertTrue("La requête devrait réussir", response.isSuccessful());

        // Vérifier que le corps n'est pas null
        assertNotNull("Le corps de la réponse ne devrait pas être null",
                response.body());

        // Vérifier qu'on a des posts
        List<Post> posts = response.body();
        assertTrue("On devrait avoir au moins un post", posts.size() > 0);

        // Vérifier la structure du premier post
        Post firstPost = posts.get(0);
        assertNotNull("Le post devrait avoir un ID", firstPost.getId());
        assertNotNull("Le post devrait avoir un titre", firstPost.getTitle());
        assertNotNull("Le post devrait avoir un contenu", firstPost.getBody());

        // Afficher les résultats (optionnel)
        System.out.println("Nombre de posts reçus: " + posts.size());
        System.out.println("Premier titre: " + firstPost.getTitle());
    }

    @Test
    public void testPostDataStructure() throws IOException {
        Response<List<Post>> response = repository.getPosts().execute();

        if (response.isSuccessful() && response.body() != null) {
            Post post = response.body().get(0);

            // Vérifier que tous les champs sont bien remplis
            assertTrue("L'ID devrait être > 0", post.getId() > 0);
            assertTrue("Le userId devrait être > 0", post.getUserId() > 0);
            assertNotNull("Le titre ne devrait pas être null", post.getTitle());
            assertNotNull("Le body ne devrait pas être null", post.getBody());

            // Vérifier que le titre n'est pas vide
            assertFalse("Le titre ne devrait pas être vide",
                    post.getTitle().isEmpty());
        }
    }
}