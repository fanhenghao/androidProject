package com.fhh.technology.module.update;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.fhh.technology.R;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.core.content.FileProvider;


public class DownloadService extends Service {

    public String DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + "/download/technology.apk";
    public static final String TAG = "download";
    private String url;//下载链接
    private int length;//文件长度
    private String fileName = null;//文件名
    private Notification notification;
    private RemoteViews contentView;
    private NotificationManager notificationManager;

    private static final int MSG_INIT = 0;
    private static final int URL_ERROR = 1;
    private static final int NET_ERROR = 2;
    private static final int DOWNLOAD_SUCCESS = 3;
    private static final int UPDATE = 5;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:

                    int[] data = (int[]) msg.obj;

                    int percent = data[0] * 100 / data[1];
                    notifyNotification(percent, 100);

                    if (percent == 100 && scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
                        scheduledExecutorService.shutdown();
                        scheduledExecutorService = null;

                    }


                    break;
                case DOWNLOAD_SUCCESS:
                    //下载完成
                    notifyNotification(100, 100);
                    //installApk(DownloadService.this,new File(DOWNLOAD_PATH,fileName));
                    Toast.makeText(DownloadService.this, "下载完成", Toast.LENGTH_SHORT).show();
                    break;
                case URL_ERROR:
                    Toast.makeText(DownloadService.this, "下载地址错误", Toast.LENGTH_SHORT).show();
                    break;
                case NET_ERROR:
                    Toast.makeText(DownloadService.this, "连接失败，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        }

        ;
    };


    private long refernece;

    private BroadcastReceiver receiver;

    private DownloadChangeObserver mDownloadChangeObserver;

    private ScheduledExecutorService scheduledExecutorService;
    private IntentFilter filter;

    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            url = intent.getStringExtra("url");
            if (url != null && !TextUtils.isEmpty(url)) {
                //	new InitThread(url).start();

                download(url);
            } else {
                //mHandler.sendEmptyMessage(URL_ERROR);
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }

//    @SuppressWarnings("deprecation")
    public void createNotification() {
        notification = new Notification(
                R.mipmap.ic_app_icon,//应用的图标
                "安装包正在下载...",
                System.currentTimeMillis());
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        /*** 自定义  Notification 的显示****/
        contentView = new RemoteViews(getPackageName(), R.layout.notification_item);
        contentView.setProgressBar(R.id.progress, 100, 0, false);
        contentView.setTextViewText(R.id.tv_progress, "0%");
        notification.contentView = contentView;

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(R.layout.notification_item, notification);
    }

    private void notifyNotification(long percent, long length) {

        if (percent == length) {
            contentView.setTextViewText(R.id.tv_title, "下载完成");
        } else {
            contentView.setTextViewText(R.id.tv_title, "downloading...");
        }
        contentView.setTextViewText(R.id.tv_progress, (percent * 100 / length) + "%");
        contentView.setProgressBar(R.id.progress, (int) length, (int) percent, false);
        notification.contentView = contentView;
        notificationManager.notify(R.layout.notification_item, notification);
    }


    public void download(String url) {
        if (new File(DOWNLOAD_PATH).exists()) {
            new File(DOWNLOAD_PATH).delete();
        }
        receiver();
//        Log.i("开始下载APP："+url);
        DownloadManager dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "technology.apk");
        request.setDescription("新版本下载中");
        request.setTitle("technology");

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("application/vnd.android.package-archive");
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        try {
            refernece = dManager.enqueue(request);
//            Utility.ToastShowShort("通知栏正在更新版本！");
//            SharedPreferencesUtil.getInstance().putString("updataclcik","您已下载最新版本，请在下载后安装");
        } catch (Exception e) {
//            Utility.ToastShowShort("您当前未打开存储权限，无法下载！");
//            SharedPreferencesUtil.getInstance().putString("updataclcik","您当前未打开存储权限，无法下载！");
        }
        // 把当前下载的ID保存起来
//		SharedPreferences sPreferences = getSharedPreferences("downloadcomplte", 0);
//		sPreferences.edit().putLong("refernece", refernece).commit();
    }

    public void receiver() {
        filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (refernece == myDwonloadID) {
                    DownloadManager dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    setPermission(DOWNLOAD_PATH);
                    Intent install = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
                    install.setAction(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    install.addCategory(Intent.CATEGORY_DEFAULT);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(context, getPackageName() + ".provider", new File(DOWNLOAD_PATH));
                        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        install.setDataAndType(contentUri, "application/vnd.android.package-archive");
//                        // 查询所有符合 intent 跳转目标应用类型的应用，注意此方法必须放置在 setDataAndType 方法之后
//                        List<ResolveInfo> resolveLists = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//                        // 然后全部授权
//                        for (ResolveInfo resolveInfo : resolveLists) {
//                            String packageName = resolveInfo.activityInfo.packageName;
//                            context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                        }
                        context.startActivity(install);
                    } else {
                        try {
                            Uri downloadFileUri = dManager.getUriForDownloadedFile(refernece);
                            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                            context.startActivity(install);
                        } catch (RuntimeException e) {
                            e.printStackTrace();
//                            ToastUtil.show(context,"下载失败");
                        }
                    }
                }
            }
        };
        registerReceiver(receiver, filter);
    }

    /**
     * 监听下载进度
     */
    private class DownloadChangeObserver extends ContentObserver {

        public DownloadChangeObserver() {
            super(new Handler());
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }

        /**
         * 当所监听的Uri发生改变时，就会回调此方法
         *
         * @param selfChange 此值意义不大, 一般情况下该回调值false
         */
        @Override
        public void onChange(boolean selfChange) {
            if (scheduledExecutorService != null) {
                scheduledExecutorService.scheduleAtFixedRate(progressRunnable, 0, 2, TimeUnit.SECONDS);
            }

        }
    }


    /**
     * 查询进度
     */
    private Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {

            int[] data = getBytesAndStatus(refernece);
            Message msg = mHandler.obtainMessage();
            msg.obj = data;
            msg.what = UPDATE;
            mHandler.sendMessage(msg);
        }
    };


    /**
     * 通过query查询下载状态，包括已下载数据大小，总大小，下载状态
     *
     * @param downloadId
     * @return
     */
    private int[] getBytesAndStatus(long downloadId) {
        int[] bytesAndStatus = new int[]{
                -1, -1, 0
        };
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        try {
            cursor = downloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载文件大小
                bytesAndStatus[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));

                //下载文件的总大小
                bytesAndStatus[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

//                KLog.i("下载中" + bytesAndStatus[0] + "---------------" + bytesAndStatus[1]);
                //下载状态
                bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return bytesAndStatus;
    }

    /**
     * 提升读写权限
     *
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    public static void setPermission(String filePath) {
        String command = "chmod " + "777" + " " + filePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        unregisterReceiver(receiver);
    }


}
