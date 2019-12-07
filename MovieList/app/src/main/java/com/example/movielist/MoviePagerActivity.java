package com.example.movielist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.UUID;

public class MoviePagerActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE_ID = "com.example.movielist.movie_id";
    //private ViewPager mViewPager;
    private List<Movie> mMovies;
    public static Intent newIntent(Context packageContext, UUID movieId){
        Intent intent = new Intent(packageContext, MoviePagerActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_pager);
        UUID movieId = (UUID) getIntent().getSerializableExtra(EXTRA_MOVIE_ID);

        ViewPager mViewPager= findViewById(R.id.movie_view_pager);

        mMovies = MovieLab.get(this).getMovies();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Movie movie = mMovies.get(position);
                return MovieFragment.newInstance(movie.getId());
            }

            @Override
            public int getCount() {
                return mMovies.size();
            }
        });
        for (int i=0; i<mMovies.size(); i++){
            if (mMovies.get(i).getId().equals(movieId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
    public void deletedPet() {
        finish();
    }
}
