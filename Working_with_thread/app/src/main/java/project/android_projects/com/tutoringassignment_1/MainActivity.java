package project.android_projects.com.tutoringassignment_1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import project.android_projects.com.tutoringassignment_1.databinding.ActivityMainBindingImpl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBindingImpl mBinding;

    private static int currentIndexNext = 0;
    private static int currentIndexPre = 0;
    private Handler handler = new Handler();

    private int[] imgIds = new int[]{R.drawable.cute_cat, R.drawable.burger,
            R.drawable.chouder_soup, R.drawable.earth, R.drawable.ship};

    private String stopStr;
    private String startStr;

    private MyAsyncImageTask1 imgAsyncTask1;

    private boolean isRunning = true;
    private Thread thread;

    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initContentView();

    }

    private void initContentView() {
        mBinding.btnPlay.setOnClickListener(this);
        mBinding.btnNext.setOnClickListener(this);
        mBinding.btnPrevious.setOnClickListener(this);
    }

    private void startOrStopImageLooping() {
        //Start
        imgAsyncTask1 = new MyAsyncImageTask1();
        imgAsyncTask1.execute();

        if (mBinding.btnPlay.getText().equals(startStr)) {

            mBinding.btnPlay.setText(stopStr);
            mBinding.btnPrevious.setEnabled(false);
            mBinding.btnNext.setEnabled(false);
            Log.d(TAG, "Async Started");
        } else {
            handler.removeCallbacks(runnable);

            mBinding.btnPlay.setText(startStr);
            mBinding.btnPrevious.setEnabled(true);
            mBinding.btnNext.setEnabled(true);
            Log.d(TAG, "Async Stopped");
        }

    }


    @Override
    public void onClick(View v) {
        stopStr = getResources().getString(R.string.button_stop);
        startStr = getResources().getString(R.string.button_play);
        switch (v.getId()) {
            case R.id.btn_play:
                //run a thread to loop through the images after a delay of 2 secs
                startOrStopImageLooping();

                break;

            case R.id.btn_previous:
                mBinding.btnPrevious.setEnabled(true);
                mBinding.btnNext.setEnabled(true);
                //show previous image
                showPreviousImage();

                break;
            case R.id.btn_next:
                //show next image
                mBinding.btnPrevious.setEnabled(true);
                mBinding.btnNext.setEnabled(true);
                showNextImage();

                break;
            default:
                //show default image

                break;
        }

    }

    private void showPreviousImage() {
        mBinding.imgPlaceholder.setImageResource(imgIds[currentIndexPre]);
        currentIndexPre -= currentIndexNext;
        if (currentIndexNext == imgIds.length - 1) {
            currentIndexPre = currentIndexNext;
            currentIndexPre--;

            if (currentIndexPre < 0 || currentIndexNext == 0) {
                currentIndexPre = 0;
                currentIndexNext = 0;
                Toast.makeText(this, "no more image", Toast.LENGTH_SHORT).show();
            }
            /*if(currentIndexNext == imgIds.length-1){

            }*/
        }
        Log.d(TAG, currentIndexPre + " previous");
    }

    private void showNextImage() {
        mBinding.imgPlaceholder.setImageResource(imgIds[currentIndexNext]);
        currentIndexNext++;//increase the currentIndexNext by 1

        if (currentIndexNext > imgIds.length - 1) {
            //If currentIndexNext exceed the last one, return to the first one
            currentIndexNext = 0;
        }
        Log.d(TAG, currentIndexNext + " next");

    }

    class MyThread extends Thread {
        @Override
        public void run() {
            //currentIndexNext = (currentIndexNext++) % imgIds.length;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showNextImage();

                }
            });

        }

    }


    class MyAsyncImageTask1 extends AsyncTask<Void, Void, Void> {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future taskFuture;

        @Override
        protected Void doInBackground(Void... voids) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    //currentIndexNext = (currentIndexNext++) % imgIds.length;
                    //mBinding.btnPlay.setText(stopStr);
                    if (isRunning) {
                        showNextImage();
                        handler.postDelayed(this, 2000);
                    }
                }

                public void kill() {
                    isRunning = false;
                }
            };
            handler.postDelayed(runnable, 2000);

            taskFuture = executorService.submit(runnable);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //mBinding.imgPlaceholder.setImageResource(R.drawable.burger);
            taskFuture.cancel(true);
            super.onPostExecute(aVoid);
        }
    }
}
