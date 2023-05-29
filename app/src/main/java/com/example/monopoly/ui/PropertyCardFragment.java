package com.example.monopoly.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentPropertyCardBinding;
import com.example.monopoly.ui.propertycards.PropertyCardsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PropertyCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PropertyCardFragment extends Fragment {

    private FragmentPropertyCardBinding binding;

    public PropertyCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PropertyCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PropertyCardFragment newInstance(String param1, String param2) {
        PropertyCardFragment fragment = new PropertyCardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_property_card, container, false);
        PropertyCardsAdapter pca = new PropertyCardsAdapter();
        RecyclerView recyclerView = rootView.findViewById(R.id.propertyCardsListView);
        recyclerView.setAdapter(pca);
        return rootView;
    }
}