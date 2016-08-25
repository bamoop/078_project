package com_t.macvision.mv_078.presenter;/**
 * Created by bzmoop on 2016/8/25 0025.
 */

import android.text.TextUtils;

import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.model.impl.BusinessTask;
import com_t.macvision.mv_078.ui.person_main.DynamicContract;
import com_t.macvision.mv_078.util.GsonUtil;
import rx.Subscriber;

/**
 * 作者：LiangXiong on 2016/8/25 0025 15:44
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class DynamicPresenter implements DynamicContract.Presenter {
    private DynamicContract.View mView;
    private BusinessTask mTask;

    /**
     * 设置每页的长度
     */
    private static final int PAGE_SIZE = 5;

    /**
     * 是否加载更多
     **/
    private static boolean ifgetDataMore = false;

    public DynamicPresenter(DynamicContract.View mView) {
        this.mView = mView;
        mTask = new BusinessTask();
    }

    @Override
    public void getData(int userId, int page, boolean isgetDataMore) {
        this.ifgetDataMore = isgetDataMore;
        getDynamicList(userId,page,PAGE_SIZE);
    }

    public void getDynamicList(int userId, int page, int pageSize) {
        mTask.getDynamicList(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                if (!TextUtils.isEmpty(s)) {
                    if (ifgetDataMore)
                        mView.appendMoreDataToView(GsonUtil.changeGsonToBean(s, VideoEntity.class));
                    else
                        mView.fillData(GsonUtil.changeGsonToBean(s, VideoEntity.class));
                    if (GsonUtil.changeGsonToBean(s, VideoEntity.class).getNewslist().size() < PAGE_SIZE) {
                        mView.hasNoMoreData();
                    }
                }
                mView.getDataFinish();
            }
        }, userId, page, pageSize);
    }


}
