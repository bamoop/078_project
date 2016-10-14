package com_t.macvision.mv_078.ui.Person;/**
 * Created by bzmoop on 2016/8/3 0003.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.macvision.mv_078.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com_t.macvision.mv_078.base.BaseToolbarFragment;
import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.core.MainActivity;
import com_t.macvision.mv_078.model.entity.UserEntity;
import com_t.macvision.mv_078.presenter.PersonHomePresenter;
import com_t.macvision.mv_078.ui.View.PersonHomeView;
import com_t.macvision.mv_078.util.CircleTransform;
import com_t.macvision.mv_078.util.ImageFromFileCache;

/**
 * 作者：LiangXiong on 2016/8/3 0003 19:33
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FragmentMenu5 extends BaseToolbarFragment<PersonHomePresenter> implements PersonHomeView {

    @Bind(R.id.image_hand)
    ImageView imageHand;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.tv_userAutograph)
    TextView tvUserAutograph;
    @Bind(R.id.btn_Focus)
    Button btnFocus;
    @Bind(R.id.item_me_head)
    RelativeLayout itemMeHead;
    @Bind(R.id.tv_vReleaseNumber)
    TextView tvVReleaseNumber;
    @Bind(R.id.tv_followNumber)
    TextView tvFollowNumber;
    @Bind(R.id.tv_fansNumber)
    TextView tvFansNumber;
    @Bind(R.id.icon_massage)
    ImageView iconMassage;
    @Bind(R.id.item_massage)
    RelativeLayout itemMassage;
    @Bind(R.id.icon_iLike)
    ImageView iconILike;
    @Bind(R.id.item_iLike)
    RelativeLayout itemILike;
    @Bind(R.id.icon_setting)
    ImageView iconSetting;
    @Bind(R.id.item_me_setting)
    RelativeLayout itemMeSetting;
    @Bind(R.id.icon_skill)
    ImageView iconSkill;
    @Bind(R.id.item_me_tips)
    RelativeLayout itemMeTips;
    @Bind(R.id.icon_version)
    ImageView iconVersion;
    @Bind(R.id.item_me_version)
    RelativeLayout itemMeVersion;
    UserEntity userEntity;
    @Override
    public int getLayout() {
        return R.layout.fragment_menu5;
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        mPresenter = new PersonHomePresenter(currentContext, this);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getData(Constant.TEST_USERID);
    }

    @Override
    public void initView(View view) {
        super.initView(view);

    }

    @Override
    protected String setTitle() {
        return "我的";
    }

    @Override
    public void fillData(UserEntity data) {
        userEntity = data;
        tvVReleaseNumber.setText(data.getVReleaseNumber());
        tvFansNumber.setText(data.getFansNumber());
        tvFollowNumber.setText(data.getFollowNumber());
        tvUserName.setText(data.getUserName());
        tvUserAutograph.setText(data.getUserAutograph());
        Glide.with(currentContext).load(ImageFromFileCache.base64ToBitmap(data.getAvatarLocation())).asBitmap().
                transform(new CircleTransform(currentContext)).into(imageHand);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void getDataFinish() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.item_me_head, R.id.item_massage, R.id.item_iLike, R.id.item_me_setting, R.id.item_me_tips, R.id.item_me_version})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_me_head:
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",userEntity);
                if (userEntity!=null)
                MainActivity.startActivity(currentContext,bundle,EditProfileActivity.class);
                break;
            case R.id.item_massage:
                break;
            case R.id.item_iLike:
                break;
            case R.id.item_me_setting:
                break;
            case R.id.item_me_tips:
                break;
            case R.id.item_me_version:
                break;
        }
    }
}
