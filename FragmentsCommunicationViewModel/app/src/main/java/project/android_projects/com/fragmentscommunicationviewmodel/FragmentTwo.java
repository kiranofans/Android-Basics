package project.android_projects.com.fragmentscommunicationviewmodel;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ViewModel.PageViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
//ViewModel does keep track of the associations between ViewModels and UI Controllers
// (Activities or fragments)
public class FragmentTwo extends Fragment {
    private TextView displayTV;
    private PageViewModel pageViewModel;

    public FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize ViewModel here as well
        pageViewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel.class);
        //this init will return the pre-existing ViewModel associated with the specific Fragment
        //This is what preserves the data
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        displayTV = view.findViewById(R.id.result_text_view);
        displayText();
    }

    private void displayText() {
        pageViewModel.getLiveDataStr().observe(requireActivity(), new Observer<String>() {
            //The Observer<String> used here is from androidx.lifecycle
            @Override
            public void onChanged(String s) {
                displayTV.setText(s);
            }
        });
    }
}
