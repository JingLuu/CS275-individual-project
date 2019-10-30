package com.example.movielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class MainActivity extends SingleFragmentActivity {
    private static final String EXTRA_MOVIE_ID = "com.example.movielist.movie_id";
    public static Intent newIntent(Context packageContext, UUID movieId) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }
    @Override
    protected Fragment createFragment(){
        UUID movieId = (UUID) getIntent().getSerializableExtra(EXTRA_MOVIE_ID);
        return MovieFragment.newInstance(movieId);
    }
}
