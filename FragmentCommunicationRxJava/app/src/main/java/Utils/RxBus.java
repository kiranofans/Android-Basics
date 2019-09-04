package Utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBus {

    private static RxBus rxBus;

    public static RxBus getInstance() {
        //Need to code the instance in lazy init form
        return rxBus == null ? rxBus = new RxBus() : rxBus;
    }

    private RxBus() {
    }

    //Create publishSubject variable (instance)
    private PublishSubject<String> publishSubject = PublishSubject.create();

    public void publish(String event) {
        publishSubject.onNext(event);
    }

    //Listen should return an Observable
    public Observable<String> listen() {
        return publishSubject;
    }
}
