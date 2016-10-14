package com_t.macvision.mv_078.ui.CloudDog;

import java.util.ArrayList;

import com_t.macvision.mv_078.base.IBaseView;
import com_t.macvision.mv_078.model.CloudModel.CloudBaseModel;
import com_t.macvision.mv_078.model.CloudModel.LocationModel;

/**
 * Created by bzmoop on 2016/9/24 0024.
 */

public interface TreckView<T extends CloudBaseModel> extends IBaseView {

    void getTrackSuccess(ArrayList<T> locationModel);

    void getTrackError(String massage);
    void getTrackEmpty();


}
