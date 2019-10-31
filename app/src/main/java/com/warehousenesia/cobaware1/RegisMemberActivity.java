package com.warehousenesia.cobaware1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.warehousenesia.cobaware1.Post.INodeJS;
import com.warehousenesia.cobaware1.Post.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisMemberActivity extends AppCompatActivity implements View.OnClickListener {

    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    MaterialEditText edit_username, edit_password, edit_phone, edit_email;
    MaterialButton btn_register, btn_back;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_member);

        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(INodeJS.class);

        sharedPrefManager = new SharedPrefManager(this);

        //View
        btn_back = (MaterialButton) findViewById(R.id.btn_back);
        btn_register = (MaterialButton) findViewById(R.id.member_regisbtn);

        edit_username = (MaterialEditText) findViewById(R.id.edt_username);
        edit_password = (MaterialEditText) findViewById(R.id.edt_password);
        edit_email = (MaterialEditText) findViewById(R.id.edt_email);
        edit_phone = (MaterialEditText) findViewById(R.id.edt_phone);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisMemberActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (edit_username.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Username", Toast.LENGTH_SHORT).show();
        }
        if (edit_password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Password", Toast.LENGTH_SHORT).show();
        }
        if (edit_email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Email", Toast.LENGTH_SHORT).show();
        }
        if (edit_phone.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Nomor Telphone", Toast.LENGTH_SHORT).show();
        }
        else {
//            String username = edit_username.getText().toString();
//            String password = edit_password.getText().toString();
//            String email = edit_email.getText().toString();
//            String phone = edit_phone.getText().toString();
            registerMember(edit_username.getText().toString(),
                    edit_password.getText().toString(),
                    edit_email.getText().toString(),
                    edit_phone.getText().toString());
            Intent intent = new Intent(this, Main2Activity.class);
//            intent.putExtra(Main2Activity.EXTRA_USERNAME, username);
//            intent.putExtra(Main2Activity.EXTRA_PASSWORD, password);
//            intent.putExtra(Main2Activity.EXTRA_EMAIL, email);
//            intent.putExtra(Main2Activity.EXTRA_PHONE, phone);
            startActivity(intent);
        }
    }

    private void registerMember(final String username,
                                final String phone,
                                final String password,
                                final String email) {
        compositeDisposable.add(myApi.registerMember(username, password, email, phone)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, username);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);
//                Intent intent = new Intent();
//                intent.putExtra(Main2Activity.EXTRA_USERNAME, username);
//                intent.putExtra(Main2Activity.EXTRA_EMAIL, email);
            }
        }));
    }
}
