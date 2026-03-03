package com.example.teste;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressBar progressBar;
    protected View errorView;
    protected TextView errorText;
    protected View btnRetry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showLoading() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
    }

    protected void hideLoading() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    protected void showError(String message) {
        hideLoading();
        if (errorView != null) {
            errorView.setVisibility(View.VISIBLE);
            if (errorText != null) {
                errorText.setText(message);
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        }
    }

    protected void hideError() {
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
    }

    protected abstract void onRetry();
}