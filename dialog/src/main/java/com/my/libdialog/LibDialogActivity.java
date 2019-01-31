package com.my.libdialog;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class LibDialogActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;     //编辑框

    //数据源，数组和List
    private String[] mDataArray = {"拍照", "相册"};
    private List<String> mDataList = Arrays.asList(mDataArray);

    //颜色值数组和List
    private Integer[] mColorArray = {R.color.blue,R.color.blue};
    private List<Integer> mColorsList = Arrays.asList(mColorArray);

    LinearLayout mEditLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //第 1 个按钮
        Button button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(this);

        //第 2 个按钮
        Button button2 = (Button) findViewById(R.id.button_2);
        button2.setOnClickListener(this);

        //第 3 个按钮
        Button button3 = (Button) findViewById(R.id.button_3);
        button3.setOnClickListener(this);

        //第 4 个按钮
        Button button4 = (Button) findViewById(R.id.button_4);
        button4.setOnClickListener(this);

        //第 5 个按钮
        Button button5 = (Button) findViewById(R.id.button_5);
        button5.setOnClickListener(this);

        //第 6 个按钮
        Button button6 = (Button) findViewById(R.id.button_6);
        button6.setOnClickListener(this);

        //第 7 个按钮
        Button button7 = (Button) findViewById(R.id.button_7);
        button7.setOnClickListener(this);

        //引入EditText的布局文件，找到EditText控件
        LayoutInflater editInflater = LayoutInflater.from(this);
        mEditLayout = (LinearLayout) editInflater.inflate(R.layout.edit_layout, null);
        mEditText = (EditText) mEditLayout.findViewById(R.id.editText);
    }

    /**
     * 点击事件
     *
     * @param v 按钮
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();//中间弹出，有标题，双按钮
        if (i == R.id.button_1) {
            new LibDialog(LibDialogActivity.this)
                    .setTitle("标题")
                    .setMessage("中间弹出，有标题，双按钮")
                    .setNegativeButton("取消", new View.OnClickListener() {
                        //点击事件可以设置为null
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(LibDialogActivity.this, "点击了取消",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(LibDialogActivity.this, "点击了确定",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setCancelable(false)
                    .builder()
                    .show();
            //中间弹出，无标题，单按钮
        } else if (i == R.id.button_2) {
            new LibDialog(LibDialogActivity.this)
                    .setCancelable(false)
                    .setMessage("中间弹出，无标题，单按钮")
                    .setPositiveButton("确 定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(LibDialogActivity.this, "点击了确定",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .builder()
                    .show();
            //中间弹出，有标题，蓝色List
        } else if (i == R.id.button_3) {
            new LibDialog(LibDialogActivity.this)
                    .setTitle("标题，蓝色ListView")
                    .setCancelable(false)
                    .setListView(mDataList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(LibDialogActivity.this, "点击了 " + position, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("取 消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(LibDialogActivity.this, "点击了取消",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButtonColor(R.color.blue)
                    .builder()
                    .show();
            //中间弹出，无标题，彩色List
        } else if (i == R.id.button_4) {
            new LibDialog(LibDialogActivity.this)
                    .setCancelable(false)
                    .setListView(mDataList, mColorArray, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(LibDialogActivity.this, "点击了 " + position, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("取 消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(LibDialogActivity.this, "点击了取消",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButtonColor(R.color.blue)
                    .builder()
                    .show();
            //中间弹出，有标题，内容替换为EditText
        } else if (i == R.id.button_5) {
            new LibDialog(LibDialogActivity.this)
                    .setTitle("标题，内容替换为EditText")
                    .setCancelable(false)
                    .setView(mEditLayout)
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(LibDialogActivity.this, mEditText.getText().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .builder()
                    .show();
            //底部弹出，有标题，蓝色List
        } else if (i == R.id.button_6) {
            new LibDialog(LibDialogActivity.this, "BOTTOM")
                    .setBottomTitle("有标题，底部弹窗，蓝色List")
                    .setCancelable(false)
                    .setListView(mDataList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(LibDialogActivity.this, "点击了 " + position, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setBottomNegativeButton("取 消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(LibDialogActivity.this, "点击了取消", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .builder()
                    .show();
            //底部弹出，无标题，彩色List
        } else if (i == R.id.button_7) {
            new LibDialog(LibDialogActivity.this, "BOTTOM")
                    .setCancelable(false)
                    .setListView(mDataArray, mColorsList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(LibDialogActivity.this, "点击了 " + position, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setBottomNegativeButton("取 消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(LibDialogActivity.this, "点击了取消", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .builder()
                    .show();
        }
    }
}
