package project.android_projects.com.rxjava_rxandroid_example;

import org.junit.Assert;
import org.junit.Test;

import io.reactivex.Observable;

import static org.junit.Assert.assertTrue;

public class RxJavaUnitTest {
    String result = "";

    @Test
    public void returnAValue(){
        Observable<String> observable = io.reactivex.Observable.just("Hello");//Provides

        observable.subscribe(s -> result = s); //Callable as subscriber
        Assert.assertTrue(result.equals("Hello"));
    }
}
