package com.netcircle.imageloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.netcircle.imageloader.app.MyApplication;
import com.netcircle.imageloader.dao.User;
import com.netcircle.imageloader.gen.UserDao;
import com.netcircle.imageloader.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText input_name;
    private EditText input_password;
    private Button btn_login;
    private TextView tv_sign_up;

    private UserDao mUserDao ;

    private String username;
    private String password;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();
        initEvent();
        getData();
        //注册事件
        EventBus.getDefault().register(this);
    }

    private void initView(){
        input_name = (EditText)findViewById(R.id.input_name);
        input_password = (EditText)findViewById(R.id.input_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        tv_sign_up = (TextView)findViewById(R.id.tv_signup);
    }



    private void initEvent(){
        btn_login.setOnClickListener(this);
        tv_sign_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                getData();
                if (isNotEmpty(username)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this,"你未注册！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_signup:
                if (isNotEmpty(username)){
                    Toast.makeText(LoginActivity.this,"你已注册！",Toast.LENGTH_SHORT).show();
                }else {
                    startAction();
                }
                break;
        }
    }

    public void getData(){
        List<User> users = mUserDao.loadAll();
        String userName = "";
        for (int i = 0; i < users.size(); i++) {
            userName += users.get(i).getName()+",";
            username = users.get(i).getName();
            password = users.get(i).getPassword();
        }
        if (userName.equals(null)){
            Toast.makeText(LoginActivity.this,"请先注册！",Toast.LENGTH_SHORT).show();
        }
        else {
            input_name.setText(username);
            input_password.setText(password);
        }
    }

    private boolean isNotEmpty(String s) {
        if (s != null && !s.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void startAction(){
        Intent intent = new Intent(LoginActivity.this, RegisteredActivity.class);
        startActivity(intent);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent){
        id =messageEvent.getMessage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

}
