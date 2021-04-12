package com.example.rankingu.ui.Suggestions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.rankingu.R;

public class SuggestionsFragment extends Fragment {

    private Suggestions suggestions;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        suggestions =
                ViewModelProviders.of(this).get(Suggestions.class);
        View root = inflater.inflate(R.layout.fragment_suggestions, container, false);
        return root;
    }
}