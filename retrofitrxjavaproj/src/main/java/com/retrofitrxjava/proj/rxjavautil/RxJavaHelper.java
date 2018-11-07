package com.retrofitrxjava.proj.rxjavautil;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Created by Administrator on 2018/11/7.
 */

public class RxJavaHelper {
private final static String LOGTAG="RXJAVALog";

    public  static  void create(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.i(LOGTAG,"Observable-->"+1);
                e.onNext(1);
                Log.i(LOGTAG,"Observable-->"+2);
                e.onNext(2);
                Log.i(LOGTAG,"Observable-->"+3);
                e.onNext(3);
                Log.i(LOGTAG,"Observable-->"+4);
                e.onNext(4);
                Log.i(LOGTAG,"Observable-->e.onComplete");
                e.onComplete();
                Log.i(LOGTAG,"Observable-->"+5);
                e.onNext(5);
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable disposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(LOGTAG,"subscribe-Disposable-->"+d+" d.isdisp"+d.isDisposed());
                this.disposable=d;
            }
            @Override
            public void onNext(@NonNull Integer integer) {
                Log.i(LOGTAG,"subscribe-onNext-->"+integer);
                if (integer==3){
                    disposable.dispose();
                    Log.i(LOGTAG,"subscribe-onNext-disposable.isDisposed()->"+disposable.isDisposed());
                }
            }
            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(LOGTAG,"subscribe-onError->>>>");
            }
            @Override
            public void onComplete() {
                Log.i(LOGTAG,"subscribe-onError->>>>");
            }
        });

    }

    public  static  void  map(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.i(LOGTAG,"Observable-->1");
                e.onNext(1);
                Log.i(LOGTAG,"Observable-->2");
                e.onNext(2);
                Log.i(LOGTAG,"Observable-->3");
                e.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                Log.i(LOGTAG,"map-apply-->"+integer);
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(LOGTAG,"subscribe-accept-->"+s);
            }
        });
    }

    public static  void zip(){
      Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer, String>() {
          @Override
          public String apply(@NonNull String s, @NonNull Integer integer) throws Exception {
              Log.i(LOGTAG, "Observables ---apply-->"+s);
              return s+integer;
          }
      }).subscribe(new Consumer<String>() {
          @Override
          public void accept(String s) throws Exception {
              Log.i(LOGTAG, "subscribe ---accept-->"+s);
          }
      });


    }

    private static Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    Log.i(LOGTAG, "Observables+String : A");
                    e.onNext("A");
                    Log.i(LOGTAG, "Observables+String : B");
                    e.onNext("B");
                    Log.i(LOGTAG, "Observables+String : C");
                    e.onNext("C");

                }
            }
        });
    }

    private static Observable<Integer> getIntegerObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {
                    Log.i(LOGTAG, "Observables+String : 1");
                    e.onNext(1);
                    Log.i(LOGTAG, "Observables+String : 2");
                    e.onNext(2);
                    Log.i(LOGTAG, "Observables+String : 3");
                    e.onNext(3);
                    Log.i(LOGTAG, "Observables+String : 4");
                    e.onNext(4);
                    Log.i(LOGTAG, "Observables+String : 5");
                    e.onNext(5);
                }
            }
        });
    }
    



}
