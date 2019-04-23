package com.btradesm.storemanager.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.franmontiel.localechanger.LocaleChanger;
import com.btradesm.storemanager.MainActivity;
import com.btradesm.storemanager.R;

public class AssignSuccess extends AppCompatActivity {

    RelativeLayout btn_go_back;
    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_success);
        btn_go_back=(RelativeLayout)findViewById(R.id.btn_go_back);
        btn_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AssignSuccess.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }
}
