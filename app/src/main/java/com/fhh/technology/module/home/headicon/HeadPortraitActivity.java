package com.fhh.technology.module.home.headicon;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.base.TechnologyApplication;
import com.fhh.technology.utils.ImageUtils;
import com.fhh.technology.utils.RoundHeadUtils;
import com.fhh.technology.utils.SharedPreferenceUtils;
import com.fhh.technology.utils.ToolBarOptions;
import com.fhh.technology.utils.ZipUtils;
import com.jaeger.library.StatusBarUtil;
import com.my.libdialog.LibDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class HeadPortraitActivity extends BaseActivity implements HeadPortraitContract.View {

    private static final int PERMISSION_ALBUM = 1;
    private static final int PERMISSION_PHOTOGRAPH = 2;
    private static final int REQUEST_ALBUM_CODE = 3;
    private static final int REQUEST_PHOTOGRAPH_CODE = 4;

    @BindView(R.id.iv_head_portrait)
    ImageView mIvHeadPortrait;

    private String[] mDataArray = {"拍照", "相册"};
    private String[] mPermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private int maxWidth = 612;
    private int maxHeight = 816;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private int quality = 80;
    //颜色值数组和List
    private Integer[] mColorArray = {com.my.libdialog.R.color.blue, com.my.libdialog.R.color.blue};
    private List<Integer> mColorsList = Arrays.asList(mColorArray);
    private HeadPortraitPresenter mPresenter;
    private String mPath;
    private Uri mImaegeUri;


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, HeadPortraitActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_head_portrait;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.theme_color), Constant.STATUS_BAR_ALPHA);
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = true;
        options.backgroundColor = R.color.theme_color;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {
        mPresenter = new HeadPortraitPresenter(this, this);
    }

    @Override
    public void initData() {
        String path = SharedPreferenceUtils.getInstance(this).getString(Constant.PICTURE_URL_KEY);
        loadPath(path);
        mIvHeadPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LibDialog(mActivity, "BOTTOM")
                        .setCancelable(false)
                        .setListView(mDataArray, mColorsList, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {
                                    ActivityCompat.requestPermissions(mActivity, mPermission, PERMISSION_PHOTOGRAPH);
                                } else {
                                    ActivityCompat.requestPermissions(mActivity, mPermission, PERMISSION_ALBUM);
                                }
                            }
                        })
                        .setBottomNegativeButton("取 消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setCancelable(true)
                        .builder()
                        .show();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean readPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        boolean writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
        switch (requestCode) {
            case PERMISSION_PHOTOGRAPH:
                //权限
                if (readPermission && writePermission) {
                    openCamera();
                } else {
                    showToast("当前没有得到权限，请给予权限");
                }
                break;
            case PERMISSION_ALBUM:
                //权限
                if (readPermission && writePermission) {
                    openAlbum();
                } else {
                    showToast("当前没有得到权限，请给予权限");
                }
                break;
        }
    }


    private void openCamera() {
        Intent intoPhotograph = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +
                File.separator + System.currentTimeMillis() + ".jpeg";

        mImaegeUri = FileProvider.getUriForFile(mActivity,
                TechnologyApplication.getInstance().getPackageName(),
                new File(mPath));
        intoPhotograph.putExtra(MediaStore.EXTRA_OUTPUT, mImaegeUri);
        // 授予目录临时共享权限
        intoPhotograph.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intoPhotograph, REQUEST_PHOTOGRAPH_CODE);
    }

    private void openAlbum() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), REQUEST_ALBUM_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_ALBUM_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ALBUM_CODE:
                if (data != null) {
                    String realPathFromUri = ImageUtils.getRealPathFromUri(this, data.getData());
                    File file = new File(realPathFromUri);
                    try {
                        File file1 = ZipUtils.compressImage(file, maxWidth, maxHeight, compressFormat, quality,
                                file.getParent() + File.pathSeparator + file.getName());
                        Log.e("test", "得相册到的路径为1：" + file1.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    SharedPreferenceUtils.getInstance(this).put(Constant.PICTURE_URL_KEY, realPathFromUri);
                    loadPath(realPathFromUri);
                    Log.e("test", "得相册到的路径为2：" + realPathFromUri);
                } else {
                    showToast("获取图片失败！！！");
                }
                break;
            case REQUEST_PHOTOGRAPH_CODE:
                if (resultCode == 0) {//未得到拍照的图片

                } else if (resultCode == -1) {//得到了拍照的图片
                    SharedPreferenceUtils.getInstance(this).put(Constant.PICTURE_URL_KEY, mPath);
                    loadPath(mPath);
                    Log.e("test", "得到拍照的路径为：" + mPath);
                }
                break;
        }
    }

    @Override
    public void onDestroyActivity() {
        mPresenter = null;
    }

    @Override
    public void loadPath(String path) {
        Picasso.with(this).load(new File(path)).transform(new RoundHeadUtils()).error(R.mipmap.ic_head_icon).into(mIvHeadPortrait);
    }
}
