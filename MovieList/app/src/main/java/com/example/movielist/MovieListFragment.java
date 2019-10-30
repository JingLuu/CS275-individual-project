package com.example.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieListFragment extends Fragment {
    private RecyclerView mMovieRecyclerView;
    private MovieAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mMovieRecyclerView = (RecyclerView) view.findViewById(R.id.movie_recycler_view);
        mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        MovieLab movieLab= MovieLab.get(getActivity());
        List<Movie> movies = movieLab.getMovies();
        if (mAdapter ==null){
            mAdapter = new MovieAdapter(movies);
            mMovieRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }

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
            Intent intent = MainActivity.newIntent(getActivity(), mMovie.getId());
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
    }
}
