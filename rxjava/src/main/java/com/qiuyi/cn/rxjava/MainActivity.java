package com.qiuyi.cn.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.qiuyi.cn.rxjava.bean.Transaction;
import com.qiuyi.cn.rxjava.bean.Transaction2;
import com.qiuyi.cn.rxjava.request.GetRequest;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RxJava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.rxjava的简单使用
        //rxJavaDemo1();

        //2.rxjava+retrofit循环请求
        //rxjavaDemo2();

        //3.变换操作符
        //rxjavaDemo3();

        //4.实现网络请求嵌套回调
        //rxjavaDemo4();

        //5.合并操作符（具体看介绍）->这里是实现一个例子
        // 获取缓存数据，缓存->磁盘->网络
        //rxjavaDemo5();

        //6 合并事件的操作
    }

    //获取数据
    private void rxjavaDemo5() {
        final String memoryCache = null;

        final String diskCache = "从磁盘缓存中获取数据";

        /*
         * 设置第1个Observable：检查内存缓存是否有该数据的缓存
         **/
        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //首先判断内存缓存中是否有数据
                if(memoryCache!=null){
                    //若有数据则直接发送
                    e.onNext(memoryCache);
                }else{
                    e.onComplete();
                }
            }
        });

        /*
         * 设置第2个Observable：检查磁盘缓存是否有该数据的缓存
         **/
        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //首先判断内存缓存中是否有数据
                if(diskCache!=null){
                    //若有数据则直接发送
                    e.onNext(diskCache);
                }else{
                    e.onComplete();
                }
            }
        });

        /*
         * 设置第3个Observable：通过网络获取数据
         **/
        Observable<String> network = Observable.just("从网络中获取数据");
        // 此处仅作网络请求的模拟

        /*
         * 通过concat（） 和 firstElement（）操作符实现缓存功能
         **/
        // 1. 通过concat（）合并memory、disk、network 3个被观察者的事件（即检查内存缓存、磁盘缓存 & 发送网络请求）
        //    并将它们按顺序串联成队列
        Observable.concat(memory,disk,network)
                // 2. 通过firstElement()，从串联队列中取出并发送第1个有效事件（Next事件），即依次判断检查memory、disk、network
                .firstElement()
                // 即本例的逻辑为：
                // a. firstElement()取出第1个事件 = memory，即先判断内存缓存中有无数据缓存；由于memoryCache = null，即内存缓存中无数据，所以发送结束事件（视为无效事件）
                // b. firstElement()继续取出第2个事件 = disk，即判断磁盘缓存中有无数据缓存：由于diskCache ≠ null，即磁盘缓存中有数据，所以发送Next事件（有效事件）
                // c. 即firstElement()已发出第1个有效事件（disk事件），所以停止判断。

                // 3. 观察者订阅
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept( String s) throws Exception {
                        Log.d(TAG,"最终获取的数据来源 =  "+ s);
                    }
                });

    }

    //实现网络请求嵌套回调
    private void rxjavaDemo4() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        GetRequest request = retrofit.create(GetRequest.class);

        //这个是注册
        Observable<Transaction> observable1 = request.getCall_Register();
        //这个是登陆
        final Observable<Transaction2> observable2 = request.getCall_Login();

        //1 先调用注册
        observable1.subscribeOn(Schedulers.io())// （初始被观察者）切换到IO线程进行网络请求1
                .observeOn(AndroidSchedulers.mainThread())//（新观察者）切换到主线程 处理网络请求1的结果
                .doOnNext(new Consumer<Transaction>() {
                    @Override
                    public void accept(Transaction transaction) throws Exception {
                        Log.e(TAG, "第1次网络请求成功");
                        transaction.show();
                    }
                })

                //切换到IO线程去发起登陆请求
                .observeOn(Schedulers.io())

                .flatMap(new Function<Transaction, ObservableSource<Transaction2>>() {
                    @Override
                    public ObservableSource<Transaction2> apply(Transaction transaction) throws Exception {
                        return observable2;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Consumer<Transaction2>() {
                    @Override
                    public void accept(Transaction2 transaction2) throws Exception {
                        Log.e(TAG, "第2次网络请求成功");
                        transaction2.show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "登陆失败");
                    }
                });
    }

    //变换操作符
    private void rxjavaDemo3() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 被观察者发送事件 = 参数为整型 = 1、2、3
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).map(new Function<Integer, String>() {
            // 2. 使用Map变换操作符中的Function函数对被观察者发送的事件进行统一变换：整型变换成字符串类型
            @Override
            public String apply(Integer integer) throws Exception {
                return "使用 Map变换操作符 将事件" + integer +"的参数从 整型"+integer + " 变换成 字符串类型" + integer ;
            }
        }).subscribe(new Consumer<String>() {
            //3.消耗事件
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, s);
            }
        });
        /*.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.e(TAG, value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });*/
    }

    //rxJava+retrifit循环请求网络数据
    private void rxjavaDemo2() {

        /*
         * 步骤1：采用interval（）延迟发送
         * 注：此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
         **/
        // 参数说明：
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）
        Observable.interval(2,1, TimeUnit.SECONDS)
                /*
                  * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                  * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                  **/
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "第"+aLong+"次循环" );

                     /*
                      * 步骤3：通过Retrofit发送网络请求
                      **/
                        // a. 创建Retrofit对象
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://fy.iciba.com/")
                                .addConverterFactory(GsonConverterFactory.create())//解析器
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava
                                .build();
                        // b. 创建 网络请求接口 的实例
                        GetRequest request = retrofit.create(GetRequest.class);

                        // c. 采用Observable<...>形式 对 网络请求 进行封装
                        Observable<Transaction> observable = request.getCall_Register();

                        // d. 通过线程切换发送网络请求
                        observable.subscribeOn(Schedulers.io())// 切换到IO线程进行网络请求
                                .observeOn(AndroidSchedulers.mainThread())// 切换回到主线程 处理请求结果
                                .subscribe(new Observer<Transaction>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(Transaction value) {
                                        //e.接受服务器返回数据
                                        value.show();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(TAG, "请求失败");
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });

                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "对Complete事件作出响应");
            }
        });

    }

    //rxJava的简单使用
    private void rxJavaDemo1() {
        //1 创建被观察者对象
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            //2 通过订阅（subscribe连接观察者和被观察者对象）
            //3 创建观察者 & 定义响应事件的行为
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG, "对Next事件"+value+"做出响应" );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "对Error事件做出响应");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "对Complete事件做出响应");
            }
        });

    }
}
