package com.netcircle.imageloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.netcircle.imageloader.app.MyApplication;
import com.netcircle.imageloader.dao.User;
import com.netcircle.imageloader.gen.UserDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;

public class RegisteredActivity extends AppCompatActivity {

    private EditText et_input_id;
    private EditText input_name;
    private EditText input_password;
    private Button btn_registered;

    private String id ;
    private String username;
    private String password;

    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        initView();
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();

        btn_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = et_input_id.getText().toString();
                username = input_name.getText().toString();
                password = input_password.getText().toString();

                if (isNotEmpty(id) && isNotEmpty(username) && isNotEmpty(password)) {
                    QueryBuilder qb = mUserDao.queryBuilder();
                    ArrayList<User> list = (ArrayList<User>) qb.where(UserDao.Properties.Id.eq(id)).list();
                    if (list.size() > 0) {
                        Toast.makeText(RegisteredActivity.this, "主键重复", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mUserDao.insert(new User(Long.valueOf(id), username,password));
                        Toast.makeText(RegisteredActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post("你好啊？");
                        startAction();
                    }
                }
                else {
                    if (isEmpty(id) && isNotEmpty(username) && isNotEmpty(password)) {
                        Toast.makeText(RegisteredActivity.this, "id为空", Toast.LENGTH_SHORT).show();
                    }
                    if (isEmpty(username) && isNotEmpty(id) && isNotEmpty(password)) {
                        Toast.makeText(RegisteredActivity.this, "姓名为空", Toast.LENGTH_SHORT).show();
                    }
                    if (isEmpty(password) && isNotEmpty(id) && isNotEmpty(username)) {
                        Toast.makeText(RegisteredActivity.this, "密码为空", Toast.LENGTH_SHORT).show();
                    }
                    if (isEmpty(id) && isEmpty(username) && isEmpty(password)) {
                        Toast.makeText(RegisteredActivity.this, "请填写信息", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    public void getData(View view){
        input_name = (EditText)findViewById(R.id.et_input_name);
        input_password = (EditText)findViewById(R.id.et_input_password);
        btn_registered = (Button)findViewById(R.id.btn_registered);
        et_input_id = (EditText)findViewById(R.id.et_input_id);
        id = et_input_id.getText().toString();
        username = input_name.getText().toString();
        password = input_password.getText().toString();
        btn_registered.setText("hello"+id);
    }

    private void initView(){
        input_name = (EditText)findViewById(R.id.et_input_name);
        input_password = (EditText)findViewById(R.id.et_input_password);
        btn_registered = (Button)findViewById(R.id.btn_registered);
        et_input_id = (EditText)findViewById(R.id.et_input_id);

    }


    private void startAction(){
        Intent intent = new Intent(RegisteredActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     *  is not null
     * @param s
     * @return
     */
    private boolean isNotEmpty(String s) {
        if (s != null && !s.equals("") || s.length() > 0) {
            return true;
        }
        else {
            return false;
        }
    }


    private boolean isEmpty(String s) {
        if (isNotEmpty(s)) {
            return false;
        }
        else {
            return true;
        }
    }

}
