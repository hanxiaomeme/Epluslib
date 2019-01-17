package com.eplus.epluslib.epluslib;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eplus.epluslib.hxcommomlibrary.lywidget.dialog.LYcheckDialog;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LYcheckDialog lYcheckDialog;
    private Button checkdialog_bt;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView(){
        mContext = this;
        checkdialog_bt = findViewById(R.id.checkdialog_bt);
        checkdialog_bt.setOnClickListener(this);

    }

    private void fun1(){
        List checkList = new ArrayList();
        for (int i = 0; i < 25; i++) {
            checkList.add("this."+i);
        }
        lYcheckDialog = new LYcheckDialog(this,checkList);
        lYcheckDialog.show();
        lYcheckDialog.setSize(getWindowManager(),0.8,0.6);
        lYcheckDialog.setClicklistener(new LYcheckDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(List user, String text) {
                Toast.makeText(mContext,"勾选的审核人："+user.toString()+",审核意见:"+text,Toast.LENGTH_LONG).show();
            }

            @Override
            public void doCancel() {
                lYcheckDialog.dismiss();
            }

            @Override
            public void doReject(String text) {
                Toast.makeText(mContext,"驳回："+text,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.checkdialog_bt:
                fun1();
                break;
        }
    }
}
