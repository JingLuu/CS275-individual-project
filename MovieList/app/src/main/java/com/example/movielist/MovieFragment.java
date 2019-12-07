package com.example.movielist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class MovieFragment extends Fragment {
    private Movie mMovie;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private static final String ARG_MOVIE_ID= "movie_id";

    public static MovieFragment newInstance(UUID movieId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_ID, movieId);

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID movieId = (UUID) getArguments().getSerializable(ARG_MOVIE_ID);
        mMovie = MovieLab.get(getActivity()).getMovie(movieId);
    }
    @Override
    public void onPause() {
        super.onPause();
        MovieLab.get(getActivity())
                .updateMovie(mMovie);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_movie, container, false);
        mTitleField = (EditText) v.findViewById(R.id.movie_title);
        mTitleField.setText(mMovie.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDateButton=(Button) v.findViewById(R.id.movie_date);
        mDateButton.setText(mMovie.getDate().toString());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.movie_solved);
        mSolvedCheckBox.setChecked(mMovie.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                mMovie.setSolved(isChecked);
            }
        });
        return v;
    }
}
