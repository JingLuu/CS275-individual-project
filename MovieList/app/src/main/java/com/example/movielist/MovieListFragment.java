package com.example.movielist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieListFragment extends Fragment {
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private RecyclerView mMovieRecyclerView;
    private MovieAdapter mAdapter;
    private boolean mSubtitleVisible;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mMovieRecyclerView = (RecyclerView) view.findViewById(R.id.movie_recycler_view);
        mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_movie_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_movie:
                Movie movie = new Movie();
                MovieLab.get(getActivity()).addMovie(movie);
                Intent intent = MoviePagerActivity
                        .newIntent(getActivity(), movie.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            case R.id.delete_movie:
                showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("You will delete all pet data")
                .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MovieLab.get(getContext()).deleteAllMovie();
                        updateUI();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }

    private void updateSubtitle() {
        MovieLab movieLab = MovieLab.get(getActivity());
        int movieCount = movieLab.getMovies().size();
        String subtitle = getString(R.string.subtitle_format, movieCount);
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }
    private void updateUI(){
        MovieLab movieLab= MovieLab.get(getActivity());
        List<Movie> movies = movieLab.getMovies();
        if (mAdapter ==null){
            mAdapter = new MovieAdapter(movies);
            mMovieRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setMovies(movies);
            mAdapter.notifyDataSetChanged();
        }
        updateSubtitle();

    }

    private class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Movie mMovie;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        public MovieHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_movie, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.movie_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.movie_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.movie_solved);
        }
        public void bind(Movie movie){
            mMovie = movie;
            mTitleTextView.setText(mMovie.getTitle());
            mDateTextView.setText(mMovie.getDate().toString());
            mSolvedImageView.setVisibility(movie.isSolved() ? View.VISIBLE : View.GONE);
        }
        @Override
        public void onClick(View view){
            Intent intent = MoviePagerActivity.newIntent(getActivity(), mMovie.getId());
            startActivity(intent);
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{
        private List<Movie> mMovies;
        public MovieAdapter(List<Movie> movies){
            mMovies = movies;
        }
        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new MovieHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(MovieHolder holder, int position){
            Movie movie = mMovies.get(position);
            holder.bind(movie);

        }
        @Override
        public int getItemCount(){
            return mMovies.size();
        }
        public void setMovies(List<Movie> movies) {
            mMovies = movies;
        }
    }
}
