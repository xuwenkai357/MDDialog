package com.example.view.mddialog;


import android.app.TimePickerDialog;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.folderselector.FileChooserDialog;
import com.jaredrummler.materialspinner.MaterialSpinner;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


//开源dialog使用
public class MainActivity extends AppCompatActivity implements ColorChooserDialog.ColorCallback,FileChooserDialog.FileCallback{


    @Bind(R.id.listview)
    ListView listView;

    MaterialSpinner spinner;
    Spinner spinner2;

    EditText editText;


    private List<String> mData;
    private String dataChoose, dataChooseMul;//list选中数据


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getData()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                dataChoose = "";
                switch (i) {
                    case 0:
                        new MaterialDialog.Builder(MainActivity.this)
                                .title("basic dialog")
                                .content("一个简单的dialog,高度会随着内容改变，同时还可以嵌套RecyleView")
                                .iconRes(R.drawable.icon)
                                .positiveText("同意")
                                .negativeText("不同意")
                                .neutralText("更多信息")

                                .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色
                                //CheckBox
                                .checkBoxPrompt("不再提醒", false, new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                        if (b) {
                                            Toast.makeText(MainActivity.this, "不再提醒", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "会再次提醒", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                //嵌套recycleview，这个的点击事件可以先获取此Recycleview对象然后自己处理
                                .adapter(new RecycleviewAdapter(getData(), MainActivity.this), new LinearLayoutManager(MainActivity.this))


                                //点击事件添加 方式1
                                .onAny(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (which == DialogAction.NEUTRAL) {
                                            Toast.makeText(MainActivity.this, "更多信息", Toast.LENGTH_LONG).show();
                                        } else if (which == DialogAction.POSITIVE) {
                                            Toast.makeText(MainActivity.this, "同意" + dataChoose, Toast.LENGTH_LONG).show();
                                        } else if (which == DialogAction.NEGATIVE) {
                                            Toast.makeText(MainActivity.this, "不同意", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                })

                                //点击事件添加 方式2
                             /*   .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Toast.makeText(MainActivity.this,"同意",Toast.LENGTH_LONG).show();
                                    }
                                })*/

                                //点击事件添加 方式3
                               /* .callback(new MaterialDialog.ButtonCallback() {//添加按钮点击监听
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        super.onPositive(dialog);
                                        Toast.makeText(MainActivity.this,"同意",Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onNegative(MaterialDialog dialog) {
                                        super.onNegative(dialog);
                                        Toast.makeText(MainActivity.this,"不同意",Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onNeutral(MaterialDialog dialog) {
                                        super.onNeutral(dialog);
                                        Toast.makeText(MainActivity.this,"更多信息",Toast.LENGTH_LONG).show();
                                    }
                                })*/
//                                .btnSelector(R.color.colorPrimary)//按钮的背景颜色
                                //分开设置2个按钮的背景颜色
//                                .btnSelector(R.color.colorPrimary, DialogAction.NEGATIVE)
//                                .btnSelector(R.color.colorPrimaryDark, DialogAction.POSITIVE)
//                                .btnSelector(R.color.colorPrimary,DialogAction.NEUTRAL)
//                                .backgroundColor(Color.parseColor("#FF9988"))//dialog的背景颜色
//                                .contentColor(Color.WHITE)//内容字体的颜色
                                .show();
                        break;
                    case 1:

                        new MaterialDialog.Builder(MainActivity.this)
                                .title("List Dialog")
                                .iconRes(R.drawable.ic_logo)
                                .content("List Dialog,显示数组信息，高度会随着内容扩大")
//                                .items(new String[]{"AAAA","BBBBB","CCCCC","DDDDDDDD","EEEEE","FFFFFF","GGGGGG","HHHHHHH"})
                                .items(R.array.item)
//                                .listSelector(R.color.green)//列表的背景颜色
                                .autoDismiss(false)//不自动消失
                                //可以多选
                                .itemsCallback(new MaterialDialog.ListCallback() {//选中监听，同时dialog消失
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                        dataChoose += "下标：" + position + " and 数据：" + text;

                                        Toast.makeText(MainActivity.this, dataChoose, Toast.LENGTH_LONG).show();
                                    }
                                })
                                //单选
                               /* .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {//0 表示第一个选中 -1 不选
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                        dataChoose="此时选中的下标"+which;
                                        Toast.makeText(MainActivity.this,dataChoose,Toast.LENGTH_LONG).show();
                                        return true;
                                    }
                                })*/


                                .show();
                        break;


                    case 2:
                        dataChooseMul = "";
                        new MaterialDialog.Builder(MainActivity.this)
                                .title("Multi Choice List Dialogs")
                                .iconRes(R.drawable.ic_logo)
                                .content("Multi Choice List Dialogs,显示数组信息，高度会随着内容扩大.可以多选")
                                .items(R.array.item)
                                .positiveText("确定")
                                .widgetColor(Color.RED)//改变checkbox的颜色
                                //多选框添加
                                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {

                                        return true;//false 的时候没有选中样式
                                    }
                                })
                                //点击确定后获取选中的下标数组
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                        Toast.makeText(MainActivity.this, "选中" + dialog.getSelectedIndices().length + "个", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .show();
                        break;
                    case 3:


                       /* new MaterialDialog.Builder(MainActivity.this)

                                .customView(R.layout.custome_view,false)
                                .show();*/
                        //比如我们要点击这个按钮关闭dialog可以这样操作
                        final MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                                .customView(R.layout.custome_view, false)
                                .positiveText("添加")
                                .negativeText("取消")
                                .neutralText("更多信息")
                                .show();
                        View customeView = dialog.getCustomView();

                        Button button = (Button) customeView.findViewById(R.id.btn_closeCustome);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        editText = (EditText) customeView.findViewById(R.id.edt);

                        editText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, TimeListener,1,0,true);
                                timePickerDialog.show();
                            }
                        });



                        spinner = (MaterialSpinner) customeView.findViewById(R.id.spinner_11);
                        spinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");

                        spinner2 = (Spinner) customeView.findViewById(R.id.spinner_22);







                        break;
                    case 4:
                        new MaterialDialog.Builder(MainActivity.this)
                                .title("输入窗")
                                .iconRes(R.drawable.ic_logo)
                                .content("包含输入框的diaolog")
//                                .widgetColor(Color.BLUE)//输入框光标的颜色
                                .inputType(InputType.TYPE_CLASS_PHONE)//可以输入的类型-电话号码
//                                .inputRange(11, 41, R.color.colorAccent)//限制输入的长度,只有在这个长度内ok按钮才是可用的
                                //前2个一个是hint一个是预输入的文字
                                .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {

                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
//                                        .inputRange(11, 41, R.color.colorAccent) 也可以按照下面这个方式来
                                        if (dialog.getInputEditText().length() < 10) {

                                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                                        }else{
                                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                                        }
                                        Log.i("yqy", "输入的是：" + input);
                                    }
                                })
                                .alwaysCallInputCallback()
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (dialog.getInputEditText().length() <=10) {

                                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                                        }else {
                                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                                        }
                                    }
                                })
                                .show();


                        break;
                    case 5:
                        //progress(boolean indeterminate, int max, boolean showMinMax)
                        //   false 的话是水平进度条，true是等待圆环  最大值  是否显示数值，false的时候是不可以用progress这类的属性的灰报错：Cannot use setProgress() on this dialog.
                        Toast.makeText(MainActivity.this,"正在加载",Toast.LENGTH_LONG).show();
                        MaterialDialog dialogPro =  new MaterialDialog.Builder(MainActivity.this)
                                .title("Progress Dialog")
                                //水平进度条
                              /*  .progress(true, 100)
                                .progressIndeterminateStyle(true)*/
                                //加载完成后弹
//                                .progress(false, 100, true)

                                .progress(false, 100, true)
                                  .progressNumberFormat("%1d/%2d")
//                            .progressPercentFormat(NumberFormat.getPercentageInstance())
                                .show();

                        //加载
                        while (dialogPro.getCurrentProgress()!=dialogPro.getMaxProgress()){
                            if (dialogPro.isCancelled()) break;

                            try {
                                Thread.sleep(50);//模拟加载时间
                            } catch (InterruptedException e) {
                                break;
                            }

                            dialogPro.incrementProgress(1);
                        }
                        dialogPro.setContent("加载完成");
                        break;
                    case 6:
                        //一个可以选择颜色的view
                       /* new ColorChooserDialog.Builder(MainActivity.this,R.string.app_name)
                                .titleSub(R.string.input_hint)  // title of dialog when viewing shades of a color
                                .accentMode(false)  // when true, will display accent palette instead of primary palette
                                .doneButton(R.string.md_done_label)  // changes label of the done button
                                .cancelButton(R.string.md_cancel_label)  // changes label of the cancel button
                                .backButton(R.string.md_back_label)  // changes label of the back button
                                .preselect(Color.RED)  // 开始的时候的默认颜色
                                .dynamicButtonColor(true)  // defaults to true, false will disable changing action buttons' color to currently selected color
                                .show();*/


                        //可以自己定义要显示的颜色
                    /*    int[] primary = new int[] {
                                Color.parseColor("#F44336")
                        };
                        int[][] secondary = new int[][] {
                                new int[] { Color.parseColor("#EF5350"), Color.parseColor("#F44336"), Color.parseColor("#E53935") }
                        };

                        new ColorChooserDialog.Builder(MainActivity.this, R.string.app_name)
//                                .titleSub(R.string.app_name)
                                .customColors(primary, secondary)
                                .doneButton(R.string.done)
                                .cancelButton(R.string.cancel)
                                .titleSub(R.string.done)//设置二级选择的标题
//                                .presetsButton(R.string.input_hint)//从RRGB切换到CUstome的文字提示
                                .show();*/

                        new ColorChooserDialog.Builder(MainActivity.this, R.string.app_name)
                                .allowUserColorInput(false)
                                .customButton(R.string.md_custom_label)
                                .presetsButton(R.string.md_presets_label)
                                .show();


                        break;
                    case 7:
                        new FileChooserDialog.Builder(MainActivity.this)
                                .initialPath("/sdcard/Download")  // changes initial path, defaults to external storage directory
                                .mimeType("image/*") // Optional MIME type filter
                                .extensionsFilter(".png", ".jpg") // Optional extension filter, will override mimeType()
                                .tag("optional-identifier")
                                .goUpLabel("Up") // custom go up label, default label is "..."
                                .show();
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                }
            }
        });

    }

    private TimePickerDialog.OnTimeSetListener TimeListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            editText.setText(i+":"+i1);
        }

    };

    private List<String> getData() {


        mData = new ArrayList<String>();

        mData.add("Basic Dialog");
        mData.add("List Dialogs");
        mData.add("Multi Choice List Dialogs");
        mData.add("Custom Views");
        mData.add("Input Dialogs ");
        mData.add("Progress Dialogs ");
        mData.add("ColorDialog");
        mData.add("File Selector Dialogs");

        return mData;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        //此时选择的颜色

    }

    @Override
    public void onFileSelection(@NonNull FileChooserDialog dialog, @NonNull File file) {

    }
}
