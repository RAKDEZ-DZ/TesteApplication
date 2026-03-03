package com.example.teste;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.teste.model.Post;

public class DetailFragment extends Fragment {

    private static final String ARG_POST = "post";
    private Post post;

    // Factory method pour créer le fragment avec des arguments
    public static DetailFragment newInstance(Post post) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_POST, post);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Récupérer le post passé en argument
        if (getArguments() != null) {
            post = (Post) getArguments().getSerializable(ARG_POST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Initialiser les vues
        TextView tvTitle = view.findViewById(R.id.tvDetailTitle);
        TextView tvBody = view.findViewById(R.id.tvDetailBody);
        TextView tvPostId = view.findViewById(R.id.tvPostId);

        // Afficher les données du post
        if (post != null) {
            tvTitle.setText(post.getTitle());
            tvBody.setText(post.getBody());
            tvPostId.setText("Post ID: " + post.getId() + " • User ID: " + post.getUserId());
        }

        return view;
    }
}