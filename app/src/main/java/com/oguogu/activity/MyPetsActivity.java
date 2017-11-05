package com.oguogu.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.oguogu.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 김민정 on 2017-10-17.
 */

public class MyPetsActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.sv_pets) HorizontalScrollView sv_pets;
    @Bind(R.id.ll_pets) LinearLayout ll_pets;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypets);

        ButterKnife.bind(this);

        getMyPets();

    }

    private void getMyPets() {

    }


}
