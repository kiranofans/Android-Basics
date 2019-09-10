package project.android_projects.com.autoscrollviewpager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


/**
 * A simple {@link Fragment} subclass.
 */
public class SlideFragment extends Fragment {
    private static volatile SlideFragment fragmentInstance;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String[] PAGE_TITLES = new String[]{"Code Well", "Eat Well", "Sleep Well"};
    private static final int[] PAGE_IMAGES = new int[]
            {R.drawable.ic_code, R.drawable.ic_eat, R.drawable.ic_sleep};

    private SliderViewModel sliderViewModel;

    public SlideFragment() {
        // Required empty public constructor
    }

    public static synchronized SlideFragment getInstance(int index) {
        fragmentInstance = new SlideFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragmentInstance.setArguments(bundle);

        return fragmentInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_slide, container, false);
        final TextView sectionLableTV = rootView.findViewById(R.id.section_label);
        final ImageView imgView = rootView.findViewById(R.id.imageView);

        //Observing live data change that observing the position of the current fragment
        sliderViewModel.getText().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer index) {
                sectionLableTV.setText(PAGE_TITLES[index]);
                imgView.setImageResource(PAGE_IMAGES[index]);
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init viewModel
        sliderViewModel = ViewModelProviders.of(this).get(SliderViewModel.class);

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        sliderViewModel.setIndex(index);
    }


}
