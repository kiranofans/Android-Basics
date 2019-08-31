package project.android_projects.com.rxjava_rxandroid_example;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * RxJava (React Extension) is a combination of
 * the best ideas from the Observer pattern, Iterator pattern,
 * and functional programming.
 */
public class MainActivity extends AppCompatActivity {

    private TextView obsv_titleTV, obsv_detailTV, flowable_titleTV, flowable_detailTV;
    private TextView single_titleTV, single_detailTV, maybe_titleTV, maybe_detailTV;
    private TextView completable_titleTV, completable_detailTV;

    private View observable, flowable, single, maybe, completable;

    private List<String> resultList;
    /**
     * RxJava is a library for composing asynchronous and event-based
     * programming by using observable sequence
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initContent();

        displayData();

        createObservableType();
        maybeType();
        flowableType();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayData();
    }

    private void initContent() {

        observable = findViewById(R.id.observable);
        obsv_titleTV = observable.findViewById(R.id.title_tv);
        obsv_detailTV = observable.findViewById(R.id.detail_tv);

        single = findViewById(R.id.single);
        single_titleTV = single.findViewById(R.id.title_tv);
        single_detailTV = single.findViewById(R.id.detail_tv);

        maybe = findViewById(R.id.maybe);
        maybe_titleTV = maybe.findViewById(R.id.title_tv);
        maybe_detailTV = maybe.findViewById(R.id.detail_tv);

        completable = findViewById(R.id.completable);
        completable_titleTV = completable.findViewById(R.id.title_tv);
        completable_detailTV = completable.findViewById(R.id.detail_tv);

        flowable = findViewById(R.id.flowable);
        flowable_titleTV = flowable.findViewById(R.id.title_tv);
        flowable_detailTV = flowable.findViewById(R.id.detail_tv);
    }

    private void displayData() {
        obsv_titleTV.setText("Observable type"); //Set title text
        single_titleTV.setText("Single  Observable Type");
        maybe_titleTV.setText("Maybe Observable Type");
        completable_titleTV.setText("Completable Observable Type");
        flowable_titleTV.setText("Flowable Observable Type");

    }

    private void createObservableType() {
        /** Emits 0 or n items and terminates with an success or an error event. */
        Observable<String> observableList = Observable
                .just("Maroon", "Red", "Orange", "Yellow",
                        "Green", "White", "Black", "Blue", "Navy");

        resultList = new ArrayList<>();

        observableList.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                resultList.add(s);
                for (String strs : resultList) {//user foreach to iterate
                    obsv_detailTV.append(strs + ", ");
                }

                /*Toast.makeText(MainActivity.this, "OnNext, New data received: "+ s,
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onError(Throwable e) {
                obsv_detailTV.setText("Error received: " + e.getMessage());
                Toast.makeText(MainActivity.this, "Error received: " +
                        e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

                obsv_detailTV.append("\nAll data emitted.");
               /* Toast.makeText(MainActivity.this, "All Observable data emitted.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    private void maybeType() {
        /** Succeeds with an item, or no item, or errors.
         * The reactive version of an Optional. */
        resultList = new ArrayList<>();
        Disposable disposable = Maybe.just("Maroon").delay(10, TimeUnit.SECONDS,
                Schedulers.io()).subscribeWith(new DisposableMaybeObserver<String>() {
            @Override
            protected void onStart() {
                super.onStart();
                Toast.makeText(MainActivity.this,"Maybe Started",
                        Toast.LENGTH_SHORT).show();
                // maybe_detailTV.append("OnStart");
            }

            @Override
            public void onSuccess(String s) {
                resultList.add(s);
                for(String str:resultList){
                    maybe_detailTV.append("Maybe Success: "+ str);
                }
                maybe_detailTV.append("Maybe Success: "+ s);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this,
                        "All Maybe data emitted!",Toast.LENGTH_SHORT).show();

            }
        });
        //Thread.sleep(5000);
        disposable.dispose();
    }

    private void flowableType(){
        /** Emits 0 or n items and terminates with an success or an error event.
         * Supports backpressure, which allows to control how fast a source emits items. */
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                //Signal an item
                emitter.onNext("Hello");

                //could be some blocking operation
                Thread.sleep(1000);

                //The consumer (observer) might have canceled the flow
                if(emitter.isCancelled()){
                    return;
                }

                emitter.onNext("Flowable");
                Thread.sleep(1000);

                emitter.onNext("World!");
                Thread.sleep(1000);

                // the end-of-sequence has to be signaled, otherwise the
                // consumers may never finish
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        flowable_detailTV.append("Subscribe!\n");
        flowable_detailTV.append("Flowable type has been "+flowable.subscribe().toString());
        flowable_detailTV.append("\nDone!");
    }
}

