package com.example.teste.repository;

import com.example.teste.api.ApiService;
import com.example.teste.api.RetrofitClient;
import com.example.teste.model.Post;
import java.util.List;
import retrofit2.Call;

public class PostRepository {

    private ApiService apiService;

    public PostRepository() {
        apiService = RetrofitClient.getInstance().getApiService();
    }

    public Call<List<Post>> getPosts() {
        return apiService.getPosts();
    }
}