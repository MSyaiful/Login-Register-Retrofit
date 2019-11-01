package com.warehousenesia.cobaware1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.button.MaterialButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.warehousenesia.cobaware1.Post.INodeJS;
import com.warehousenesia.cobaware1.Post.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    MaterialEditText edit_username, edit_password;
    MaterialButton btn_register, btn_login;

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
        setContentView(R.layout.activity_main);

        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(INodeJS.class);

        sharedPrefManager = new SharedPrefManager(this);

        //View
        btn_login = (MaterialButton) findViewById(R.id.login_button);
        btn_register = (MaterialButton) findViewById(R.id.register_button);

        edit_username = (MaterialEditText) findViewById(R.id.edt_username);
        edit_password = (MaterialEditText) findViewById(R.id.edt_password);

        //Event
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginMember(edit_username.getText().toString(), edit_password.getText().toString());
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisMemberActivity.class);
                startActivity(intent);
//                registerMember(edit_username.getText().toString(),edit_password.getText().toString());
            }
        });


        if (sharedPrefManager.getSPSudahLogin()) {
            startActivity(new Intent(MainActivity.this, Main2Activity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }


//    private void registerMember(final String username, final String password) {
//        final View step_regis_view = LayoutInflater.from(this).inflate(R.layout.step_regis_layout, null);
//
//        new MaterialStyledDialog.Builder(this)
//                .setTitle("Register")
//                .setDescription("One More Step !")
//                .setCustomView(step_regis_view)
//                .setNegativeText("Cancel")
//                .onNegative(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        dialog.dismiss();
//                    }
//                })
//                .setPositiveText("Register")
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        MaterialEditText edt_email = (MaterialEditText) step_regis_view.findViewById(R.id.edt_email);
//                        MaterialEditText edt_phone = (MaterialEditText) step_regis_view.findViewById(R.id.edt_phone);
//
//                        compositeDisposable.add(myApi.registerMember(username, edt_email.getText().toString(), edt_phone.getText().toString(), password)
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new Consumer<String>() {
//                                    @Override
//                                    public void accept(String s) throws Exception {
//                                        Intent i = new Intent(MainActivity.this, Main2Activity.class);
//                                        startActivity(i);
//                                    }
//                                }));
//                    }
//                }).show();
//
//    }

    private void loginMember(final String username, final String password) {
        compositeDisposable.add(myApi.loginMember(username, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                if (s.contains("password")) {
                                    Intent i = new Intent(MainActivity.this, AgentRegister.class);
                                    startActivity(i);
                                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, username);
                                } else
                                    Toast.makeText(MainActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                            }
                        })
        );
    }
}
