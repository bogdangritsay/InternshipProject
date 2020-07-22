package com.hritsay.internsiphproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hritsay.internsiphproject.R;
import com.hritsay.internsiphproject.databinding.FragmentDescriptionBinding;
import com.hritsay.internsiphproject.databinding.FragmentDetailsBinding;


public class DescriptionFragment extends Fragment {
    private FragmentDescriptionBinding fragmentDescriptionBinding;
    private String description;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        description = getArguments().getString("filmDescription");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDescriptionBinding = FragmentDescriptionBinding.inflate(inflater, container, false);
        fragmentDescriptionBinding.descriptionText.setText(description);
        return fragmentDescriptionBinding.getRoot();
    }
}