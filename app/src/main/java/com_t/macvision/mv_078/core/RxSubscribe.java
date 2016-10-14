package com_t.macvision.mv_078.core;/**
 * Created by bzmoop on 2016/8/12 0012.
 */

import android.content.Context;
import android.content.DialogInterface;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/**
 * 作者：LiangXiong on 2016/8/12 0012 11:26
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public abstract class RxSubscribe<T> extends Subscriber<T> {
    private Context mContext;
    private SweetAlertDialog dialog;

    private String msg;
    private boolean showDialog = true;

    protected boolean showDialog() {
        return true;
    }

    /**
     * @param context context
     * @param msg     dialog message
     */
    public RxSubscribe(Context context, String msg) {
        this.mContext = context;
        if (msg.equals("")) {
            showDialog = false;
        }
        this.msg = msg;

    }

    /**
     * @param context context
     */
    public RxSubscribe(Context context) {
        this(context, "请稍后...");
    }

    @Override
    public void onCompleted() {
        if (showDialog())
            dialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog()) {
            dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText(msg);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            //点击取消的时候取消订阅
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (!isUnsubscribed()) {
                        unsubscribe();
                    }
                }
            });
//            if (showDialog)
//                dialog.show();
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (false) { //这里自行替换判断网络的代码
            _onError("网络不可用");
        } else if (e instanceof ServerException) {
            _onError(e.getMessage());
        } else {
            _onError("请求失败，请稍后再试..." + e);
        }
        if (showDialog())
            dialog.dismiss();
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}