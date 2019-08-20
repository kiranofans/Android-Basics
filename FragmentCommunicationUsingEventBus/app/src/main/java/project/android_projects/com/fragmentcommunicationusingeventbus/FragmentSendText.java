package project.android_projects.com.fragmentcommunicationusingeventbus;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSendText extends Fragment {


    public FragmentSendText() {
        // Required empty public constructor
    }

    public static synchronized FragmentSendText getInstance(){
        return new FragmentSendText();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText editText = view.findViewById(R.id.txt_input_box);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Post the event here
                EventBus.getDefault().post(charSequence.toString());

                //The text will be sending to the second fragment as typing
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
