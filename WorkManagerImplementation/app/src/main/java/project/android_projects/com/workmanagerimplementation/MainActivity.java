package project.android_projects.com.workmanagerimplementation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private WorkManager workMgr;
    private OneTimeWorkRequest workRequest;

    private Button sendBtn;
    private TextView stateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initContent();
    }

    private void initContent(){
        workMgr = WorkManager.getInstance();

        sendBtn = findViewById(R.id.button_main);
        stateTV = findViewById(R.id.tv_main);

        //Step 3: Create OneTimeWorkRequest for a task that will be executed only ONCE
        workRequest = new OneTimeWorkRequest.Builder(WorkerBaseClass.class).build();

        setSendBtn();
        fetchTaskStatus();//Fetch task or work states
    }

    private void setSendBtn(){
        //Step 4: Enqueue the request with WorkManager
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workMgr.enqueue(workRequest);
            }
        });
    }

    private void fetchTaskStatus(){
        //Step 4
        workMgr.getWorkInfoByIdLiveData(workRequest.getId()).observe
                (this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if(workInfo != null){
                    WorkInfo.State workState = workInfo.getState();
                    stateTV.append(workState.toString()+"\n");
                }
            }
        });
    }
}
