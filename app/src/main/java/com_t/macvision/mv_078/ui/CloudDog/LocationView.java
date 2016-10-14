package com_t.macvision.mv_078.ui.CloudDog;

import com_t.macvision.mv_078.base.IBaseView;
import com_t.macvision.mv_078.model.CloudModel.CloudBaseModel;
import com_t.macvision.mv_078.model.CloudModel.LocationModel;

/**
 * Created by bzmoop on 2016/9/24 0024.
 */

public interface LocationView<T extends CloudBaseModel> extends IBaseView {
    void getLocationSuccess(LocationModel locationModel);

    void getLocationError(String massage);
}
