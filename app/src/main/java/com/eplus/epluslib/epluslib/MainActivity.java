package com.eplus.epluslib.epluslib;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.eplus.epluslib.hxcommomlibrary.lywidget.dialog.LYcheckDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LYcheckDialog lYcheckDialog;
    private List checkList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkList = new ArrayList();
        for (int i = 0; i < 5; i++) {
            checkList.add("this."+i);
        }
        if (lYcheckDialog != null && lYcheckDialog.isShowing()){
            lYcheckDialog.dismiss();
        }else {
            lYcheckDialog = new LYcheckDialog(this,checkList);
            lYcheckDialog.show();
            lYcheckDialog.setSize(getWindowManager(),0.8,0.6);
        }



    }

}
