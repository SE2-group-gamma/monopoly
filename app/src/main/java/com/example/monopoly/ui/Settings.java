package com.example.monopoly.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.databinding.SettingsBinding;

public class Settings extends Fragment {

    private SettingsBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = SettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backButton.setOnClickListener(view1 -> NavHostFragment.findNavController(Settings.this)
                .navigate(R.id.action_Settings_to_FirstFragment));

        binding.saveButton.setOnClickListener(view12 -> {
            // TODO: Save Settings here
            boolean sound = binding.switch1.isChecked();
            boolean placeholder = binding.switch2.isChecked();

            NavHostFragment.findNavController(Settings.this)
                    .navigate(R.id.action_Settings_to_FirstFragment);
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
