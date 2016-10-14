package com_t.macvision.mv_078.core;/**
 * Created by bzmoop on 2016/8/12 0012.
 */

import com_t.macvision.mv_078.base.BaseModel;

import com.orhanobut.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com_t.macvision.mv_078.model.CloudModel.CloudBaseModel;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Rx处理服务器返回
 * 作者：LiangXiong on 2016/8/12 0012 11:21
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class RxResultHelper {
    /**
     * 对结果进行预处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<BaseModel<T>, T> handleServerResult() {
        return new Observable.Transformer<BaseModel<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseModel<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseModel<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseModel<T> result) {
                        Logger.i("result from network : " + result);
                        if (result.success()) {
                            return createData(result.data);
                        } else {
                            return Observable.error(new Throwable(result.msg));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 对云狗后台请求结果进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<CloudBaseModel<T>, T> handleCloudResult() {
        return new Observable.Transformer<CloudBaseModel<T>, T>() {
            @Override
            public Observable<T> call(Observable<CloudBaseModel<T>> tObservable) {
                return tObservable.flatMap(new Func1<CloudBaseModel<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(CloudBaseModel<T> result) {
                        Logger.i("result cloud network : " + result);
                        if (result.success()) {
                            return createData(result.data);
                        } else {
                            return Observable.error(new Throwable(result.errmsg));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一处理记录仪相关请求
     **/
    public static <T> Observable.Transformer<String, T> handleDeviceResult() {
        return new Observable.Transformer<String, T>() {
            @Override
            public Observable<T> call(Observable<String> tObservable) {
                return tObservable.flatMap(new Func1<String, Observable<T>>() {
                    @Override
                    public Observable<T> call(String result) {
                        Logger.i("result from network : " + result);
                        String pat = "0OK";
                        if (result != null) {
                            result = replaceBlank(result);
                            if (result.contains(pat)) {
                                result = result.replaceAll(pat, "");
                                return createData((T) result);
                            } else {
                                return Observable.error(new Throwable("请求异常"));
                            }
                        } else {
                            return Observable.error(new Throwable("请求异常"));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 创建成功的数据
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}