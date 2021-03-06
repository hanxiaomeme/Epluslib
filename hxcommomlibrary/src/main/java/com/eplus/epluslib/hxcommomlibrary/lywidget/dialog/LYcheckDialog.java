package com.eplus.epluslib.hxcommomlibrary.lywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eplus.epluslib.hxcommomlibrary.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 选择下级审核人dialog
 */
public class LYcheckDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private EditText note_et;
    private Button all_bt,sure_bt,bh_bt,cancel_bt,bt_unall;
    private ClickListenerInterface clickListenerInterface;
    //供选择的人员
    private List user;
    //选择的人员列表
    private List choseList;
    private LinearLayout user_lin;
    private List<CheckBox> mCheckBoxs;
    //是否必须勾选人员
    private boolean isChoseUser = true;
    public interface ClickListenerInterface {

        public void doConfirm(List user, String text);

        public void doCancel();

        public void doReject(String text);
    }
    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public LYcheckDialog(@NonNull Context mContext, List choseList,boolean isChoseUser) {
        super(mContext);
        this.mContext = mContext;
        this.choseList = choseList;
        this.isChoseUser = isChoseUser;
    }
    public LYcheckDialog(@NonNull Context mContext, List choseList) {
        super(mContext);
        this.mContext = mContext;
        this.choseList = choseList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    public void setSize(WindowManager windowManager,double widthPresent,double heightPresent){
        // 将对话框的大小按屏幕大小的百分比设置
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.height = (int)(display.getHeight() * heightPresent); //设置宽度
        lp.width = (int)(display.getWidth() * widthPresent); //设置宽度
        this.getWindow().setAttributes(lp);
    }
    public void init(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.check_dialog_item, null);
        setContentView(view);

        mCheckBoxs = new ArrayList<>();

        user = new ArrayList();
        note_et  = (EditText) view.findViewById(R.id.checkNote_et);
        all_bt = (Button) view.findViewById(R.id.bt_all);
        sure_bt = (Button) view.findViewById(R.id.bt_sure);
        bh_bt = (Button) view.findViewById(R.id.bt_bohui);
        cancel_bt = (Button) view.findViewById(R.id.bt_cancel);
        bt_unall = (Button) view.findViewById(R.id.bt_unall);
        user_lin = (LinearLayout)view.findViewById(R.id.user_lin);

        all_bt.setOnClickListener(this);
        sure_bt.setOnClickListener(this);
        bh_bt.setOnClickListener(this);
        cancel_bt.setOnClickListener(this);
        bt_unall.setOnClickListener(this);
        for (int i = 0; i < choseList.size(); i++) {
            final CheckBox checkBox = new CheckBox(mContext);
            checkBox.setText(choseList.get(i).toString());
            checkBox.setTextSize(15.0f);
            checkBox.setTextColor(Color.parseColor("#88000000"));

            checkBox.setTag(i);
            mCheckBoxs.add(checkBox);
            LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            checkBoxParams.gravity = Gravity.CENTER_VERTICAL;
            checkBoxParams.weight = 1.0f;
            user_lin.addView(checkBox, checkBoxParams);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        user.add(checkBox.getText());
                    }
                    else {
                        user.remove(checkBox.getText());
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.bt_all) {
            allChose();

        } else if (i == R.id.bt_sure) {
            if (user.size() <= 0 && isChoseUser) {
                Toast.makeText(mContext, mContext.getString(R.string.error_dialog_chose), Toast.LENGTH_SHORT).show();
                return;
            }
            if (clickListenerInterface != null)
                clickListenerInterface.doConfirm(user, note_et.getText().toString());

        } else if (i == R.id.bt_bohui) {
            if (clickListenerInterface != null)
                clickListenerInterface.doReject(note_et.getText().toString());

        } else if (i == R.id.bt_cancel) {
            if (clickListenerInterface != null)
                clickListenerInterface.doCancel();
        } else if (i == R.id.bt_unall) {
            unChose();

        }
        else {
        }
    }

    /**
     * 全部选中
     */
    public void allChose(){
        for (int i = 0; i < mCheckBoxs.size(); i++) {
            mCheckBoxs.get(i).setChecked(true);
        }
    }

    /**
     * 全部反选
     */
    public void unChose(){
        for (int i = 0; i < mCheckBoxs.size(); i++) {
            mCheckBoxs.get(i).setChecked(false);
        }
    }


}
