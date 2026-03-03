package com.example.teste;

import com.example.teste.model.Post;
import org.junit.Test;
import static org.junit.Assert.*;

public class PostModelTest {

    @Test
    public void testPostConstructorAndGetters() {
        // Créer un post
        Post post = new Post();

        // Tester les setters
        post.setId(1);
        post.setUserId(101);
        post.setTitle("Titre de test");
        post.setBody("Contenu de test");

        // Tester les getters
        assertEquals(1, post.getId());
        assertEquals(101, post.getUserId());
        assertEquals("Titre de test", post.getTitle());
        assertEquals("Contenu de test", post.getBody());
    }

    @Test
    public void testPostWithValues() {
        // Créer un post avec des valeurs
        Post post = new Post();
        post.setId(42);
        post.setUserId(7);
        post.setTitle("Mon titre");
        post.setBody("Mon contenu");

        // Vérifications
        assertEquals(42, post.getId());
        assertEquals(7, post.getUserId());
        assertEquals("Mon titre", post.getTitle());
        assertEquals("Mon contenu", post.getBody());
    }

    @Test
    public void testPostToString() {
        Post post = new Post();
        post.setId(5);
        post.setTitle("Test");

        // Vérifier que toString() ne retourne pas null
        assertNotNull(post.toString());
        assertTrue(post.toString().contains("Test"));
    }
}