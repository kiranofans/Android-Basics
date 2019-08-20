package project.android_projects.com.fragmentcommunicationusingeventbus;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentReceiveText extends Fragment {
    //Subscriber
    private View v;
    private TextView resultTV;

    private Context context;
    private static FragmentReceiveText instance;

    public FragmentReceiveText() {
        // Required empty public constructor
        //this.context = context;
    }

    public static FragmentReceiveText getInstance(){
        return new FragmentReceiveText();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_receive_layout, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultTV = view.findViewById(R.id.result_text_view);
       // EventBus.getDefault().register(this);

    }

    //Declare and annotate subscribing method
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onResultReceived(String resultTxt) {
        /** Can put some code to update text of TextView
         present in the receiveFragment;
         can handle the data received from the SendingFragment*/
        resultTV.setText(resultTxt);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Register event
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        //Stop processing event
        EventBus.getDefault().unregister(this);
    }
}
