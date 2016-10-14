package com_t.macvision.mv_078.ui.Device;

import com_t.macvision.mv_078.base.IBaseView;

/**
 * Created by bzmoop on 2016/9/19 0019.
 */
public interface DeviceConnectView extends IBaseView {
    void getDateError(String massage);

    void getDataSuccess(String playUrl);

}
