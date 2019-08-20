package project.android_projects.com.fragmentcommunicationusingeventbus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentMgr;
    private FragmentTransaction fragmentTrans;

    private FragmentSendText fragmentSend;
    private FragmentReceiveText fragmentReceive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentMgr = getSupportFragmentManager();
        fragmentTrans = fragmentMgr.beginTransaction();

        if(savedInstanceState == null){
            initSendFragment();
            initReceiveFragment();
            fragmentTrans.commit();
        }
    }

    private void initSendFragment() {
        fragmentSend = new FragmentSendText();
        fragmentTrans.add(R.id.send_fragment, fragmentSend);

    }

    private void initReceiveFragment() {
        fragmentReceive = new FragmentReceiveText();
        fragmentTrans.add(R.id.receive_fragment, fragmentReceive);
    }
}
