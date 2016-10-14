package com_t.macvision.mv_078.ui.Device;

import com_t.macvision.mv_078.base.IBaseView;

/**
 * 记录仪View基类
 * Created by bzmoop on 2016/9/19 0019.
 */
public interface DeviceBaseView extends IBaseView {
    void commandSucceed();
    void commandFail(String massage);
}
