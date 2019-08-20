package Utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBus {

    private static RxBus rxBus;

    public static RxBus getInstance() {
        if(rxBus == null){
            rxBus = new RxBus();
        }
        return rxBus;
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
