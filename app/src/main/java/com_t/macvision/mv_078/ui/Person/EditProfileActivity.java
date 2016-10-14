package com_t.macvision.mv_078.ui.Person;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.macvision.mv_078.R;

import org.videolan.vlc.VLCApplication;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import com_t.macvision.mv_078.base.BaseToolbarActivity;
import com_t.macvision.mv_078.model.entity.UserEntity;
import com_t.macvision.mv_078.util.CircleTransform;
import com_t.macvision.mv_078.util.ImageFromFileCache;

public class EditProfileActivity extends BaseToolbarActivity {
    private static final String TAG = "EditProfileActivity";

    UserEntity userEntity;
    @Bind(R.id.headPortrait)
    ImageView headPortrait;
    @Bind(R.id.head_layout)
    RelativeLayout headLayout;
    @Bind(R.id.edit_petName)
    EditText editPetName;
    @Bind(R.id.edit_personSignature)
    EditText editPersonSignature;

    @Override
    public int getLayout() {
        return R.layout.activity_edit_profile;
    }

    @Override
    public void initData() {
        Intent intent = this.getIntent();
        userEntity = (UserEntity) intent.getSerializableExtra("user");
        editPetName.setHint(userEntity.getUserName());
        editPersonSignature.setHint(userEntity.getUserAutograph());
        Glide.with(this).load(ImageFromFileCache.base64ToBitmap(userEntity.getAvatarLocation())).asBitmap().
                transform(new CircleTransform(this)).into(headPortrait);
    }

    @Override
    protected String setTitle() {
        return "编辑个人资料";
    }

    @OnClick(R.id.head_layout)
    public void onClick() {
        RxGalleryFinal
                .with(this)
                .image()
                .radio()
                .crop()
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        Log.i(TAG, "onEvent: 选择了一张照片");
                        Glide.with(VLCApplication.getAppContext()).load(imageRadioResultEvent.getResult().getCropPath()).asBitmap().
                                transform(new CircleTransform(VLCApplication.getAppContext())).into(headPortrait);
                    }
                })
                .openGallery();
    }
}
