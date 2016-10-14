package com_t.macvision.mv_078.ui.CloudDog;

import com_t.macvision.mv_078.base.IBaseView;

/**
 * User: LiangXiong(LiangXiong.sz@faxmail.com)
 * Date: 2016-09-22
 * Time: 16:34
 * QQ  : 294894105 ZH
 */

public interface CloudDogView extends IBaseView{

    void getAppTokenSuccess(String appToken);
    void getAppTokenError(String massage);

    void loginSuccess(String usetToken);
    void loginError(String massage);

    void registerSuccess();
    void registerError();

}
