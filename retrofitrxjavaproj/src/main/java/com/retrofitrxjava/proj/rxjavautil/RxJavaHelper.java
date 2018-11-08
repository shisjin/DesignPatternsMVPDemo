package com.retrofitrxjava.proj.rxjavautil;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


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


    public static void concat(){
        Observable.concat(Observable.just(1,2,3,5,6),Observable.just("A","B","C")).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object Object) throws Exception {
                Log.i(LOGTAG, "concat ---accept-->"+Object.toString());
            }
        });
    }

    public static void flatMap(){
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
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                Log.i(LOGTAG,"Observable-flatMap-apply->"+integer);
                ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < 4; i++) {
                    list.add("value="+integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(LOGTAG,"flatMap.subscribe-accept->"+s);
                    }
                });
    }
    public static void concatMap(){
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
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                Log.i(LOGTAG,"Observable-flatMap-apply->"+integer);
                ArrayList<String> list = new ArrayList();
                for (int i = 0; i <4 ; i++) {
                    list.add("value="+integer);
                }
                int delayTime= (int) (1+Math.random()*10);
                return Observable.fromIterable(list).delay(delayTime,TimeUnit.MILLISECONDS);
            }
        }).observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(LOGTAG,"concatMap.subscribe-accept->"+s);
                    }
                });

    }

    public static void distinct(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.i(LOGTAG,"Observable-onNext->"+1);
                e.onNext(1);
                Log.i(LOGTAG,"Observable-onNext->"+2);
                e.onNext(2);
                Log.i(LOGTAG,"Observable-onNext->"+1);
                e.onNext(1);
                Log.i(LOGTAG,"Observable-onNext->"+3);
                e.onNext(3);
                Log.i(LOGTAG,"Observable-onNext->"+2);
                e.onNext(2);
                Log.i(LOGTAG,"Observable-onNext->"+5);
                e.onNext(5);
                Log.i(LOGTAG,"Observable-onNext->"+3);
                e.onNext(3);
            }
        }).distinct().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(LOGTAG,"subscribe-accept-------------->"+integer);
            }
        });


    }


    public static void filter(){
        Observable.just(1,0,-8,100,10,9,20,6).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer>-8&&integer<=9;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(LOGTAG,"subscribe-accept->"+integer);
            }
        });

    }
    public static void buffer(){
        Observable.just(1,2,2,3,4,5,5,1,8,7).buffer(5,2).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                Log.i(LOGTAG,"subscribe-accept-list->"+integers);
            }
        });
    }

    public static void timer(){
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.i(LOGTAG,"start--> at:"+sdf.format(new Date()));
        Observable.timer(3,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())// timer 默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(LOGTAG,"subscribe-accept->"+aLong+" at:"+sdf.format(new Date()));
                    }
                });
    }
    public static void interval (){
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.i(LOGTAG,"start--> at:"+sdf.format(new Date()));
        Observable.interval(2,3,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())// timer 默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(LOGTAG,"subscribe-accept->"+aLong+" at:"+sdf.format(new Date()));
                    }
                });
    }

    public static void doOnNext(){
        Observable.just(1,2,3,4,5).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(LOGTAG,"doOnNext->value:"+integer+" thisHadSave");
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(LOGTAG,"subscribe-accept->"+integer);
            }
        });
    }

    public static void skip(){
        Observable.just(1,2,1,3,4,5,5).skip(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(LOGTAG,"subscribe-accept->"+integer);
            }
        });
    }

    public static void take(){
        Observable.just(1,2,1,3,4,5,5).take(3).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(LOGTAG,"subscribe-accept->"+integer);
            }
        });
    }

    public static void just(){
        Observable.just(1,2,1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(LOGTAG,"subscribe-accept->"+integer);
            }
        });
    }

    public static void single(){
        Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        Log.i(LOGTAG,"subscribe-onSuccess->"+integer);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(LOGTAG,"subscribe-onError->"+e);
                    }
                });
    }
    public static  void debounce(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1); // skip 先收到一个1
                Thread.sleep(400);
                emitter.onNext(2); // deliver 过了400ms收到一个2，小于设定时间500ms，把前一个丢掉,现在只有一个2
                Thread.sleep(505);
                emitter.onNext(3); // skip 过了505ms收到一个3，符合设定时间，保存，现在是2、3
                Thread.sleep(100);
                emitter.onNext(4); // deliver 过了100ms收到一个4，小于设定时间，把前一个丢掉，丢掉3，保存4，现在是2、4
                Thread.sleep(605);
                emitter.onNext(5); // deliver 过了605ms收到一个5，符合设定时间，保存，现在是2、4、5
                Thread.sleep(510);
                emitter.onComplete();
            }
        }).debounce(500,TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(LOGTAG,"subscribe-accept->"+integer);
                    }
                });
    }

    public static void defat(){
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(1,2,3,4);
            }
        });
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(LOGTAG,"subscribe onSubscribe->"+d);
            }
            @Override
            public void onNext(@NonNull Integer integer) {
                Log.i(LOGTAG,"subscribe onNext->"+integer);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(LOGTAG,"subscribe onError->"+e);
            }
            @Override
            public void onComplete() {
                Log.i(LOGTAG,"subscribe onComplete->");
            }
        });
    }

    public static void last(){
        Observable.just(1,2,3,4).last(4).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(LOGTAG,"subscribe accept->"+integer);
            }
        });

    }

    public static void merge (){

        Observable.merge(Observable.just(1,3,8,9),Observable.just(2,4,7,10),Observable.just(11,12,14,15))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(LOGTAG,"subscribe accept->"+integer);
                    }
                });

    }
    public static void reduce(){

        Observable.just(1,2,3,5,6,7).reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                Log.i(LOGTAG,"Observable apply->integer="+integer+" integer2="+integer2);
                return integer+integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(LOGTAG,"subscribe accept->"+integer);
            }
        });

    }

    public static void scan(){
        Observable.just(1,2,3,4,5).scan(new BiFunction<Integer,Integer,Integer>() {

            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                Log.i(LOGTAG,"Observable apply->integer="+integer+" integer2="+integer2);
                return integer+integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(LOGTAG,"subscribe accept->"+integer);
            }
        });
    }


    public static void window(){
        Log.i(LOGTAG,"Observable statr");
        Observable.interval(1,TimeUnit.SECONDS)// 间隔一秒发一次
                .take(15)// 最多接收15个
                .window(2,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                 public void accept(Observable<Long> longObservable) throws Exception {
                        Log.i(LOGTAG,"-->>--subscribe accept->window begain----<<--");
                        longObservable.subscribeOn(Schedulers.io())
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        Log.i(LOGTAG,"subscribe accept->aLong:"+aLong);
                                    }
                                });
                    }
                });

    }








}
