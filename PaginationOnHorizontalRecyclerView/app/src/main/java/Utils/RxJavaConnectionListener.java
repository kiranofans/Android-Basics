package Utils;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class RxJavaConnectionListener {
    private static RxJavaConnectionListener listenerInstance;

    public static RxJavaConnectionListener getInstance() {
        if (listenerInstance == null) {
            listenerInstance = new RxJavaConnectionListener();
        }
        return listenerInstance;
    }

    private RxJavaConnectionListener() {
    }

    //Create RxBus
    private BehaviorSubject<Boolean> publisher = BehaviorSubject.create();

    public void notifyNetworkChange(Boolean isConnected) {
        publisher.onNext(isConnected);
    }

    //Listen should return an Observable
    public Observable<Boolean> listenNetworkChange() {
        return publisher;
    }
}
