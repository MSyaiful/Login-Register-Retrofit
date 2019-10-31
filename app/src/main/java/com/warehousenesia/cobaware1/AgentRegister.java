package com.warehousenesia.cobaware1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.warehousenesia.cobaware1.Post.INodeJS;
import com.warehousenesia.cobaware1.Post.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AgentRegister extends AppCompatActivity {

    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    MaterialEditText edit_fullname, edit_companyname, edit_address, edit_provinsi, edit_kota, edit_kodepos;
    MaterialButton btn_register;

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_register);

//        final String username = getIntent().getStringExtra("username");
//        String password = getIntent().getStringExtra("password");

        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(INodeJS.class);

        btn_register = (MaterialButton) findViewById(R.id.agent_regisbtn);

        edit_fullname = (MaterialEditText) findViewById(R.id.edt_fullname);
        edit_companyname = (MaterialEditText) findViewById(R.id.edt_companyName);
        edit_address = (MaterialEditText) findViewById(R.id.edt_address);
        edit_provinsi = (MaterialEditText) findViewById(R.id.edt_provinsi);
        edit_kota = (MaterialEditText) findViewById(R.id.edt_kota);
        edit_kodepos = (MaterialEditText) findViewById(R.id.edt_kodepos);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerAgent(
                        edit_fullname.getText().toString(),
                        edit_companyname.getText().toString(),
                        edit_address.getText().toString(),
                        edit_kota.getText().toString(),
                        edit_provinsi.getText().toString(),
                        Integer.parseInt(edit_kodepos.getText().toString()));
            }
        });
    }

    private void  registerAgent(final String fullname,
                                final String companyName,
                                final String address,
                                final String kota,
                                final String provinsi,
                                final Integer kodepos){
//        if ("id" != null){
//            loginMember().subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<String>() {
//                        @Override
//                        public void accept(String s) throws Exception {
//                            Intent i = new Intent(AgentRegister.this, Main2Activity.class);
//                            startActivity(i);
//                        }
//                    });
//        }else {
                    compositeDisposable.add(myApi.registerAgent(fullname, companyName, address,  kota, provinsi, kodepos)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    Intent i = new Intent(AgentRegister.this, Main2Activity.class);
                                    startActivity(i);
                                }
                            }));
//        }
    }

//    private Observable loginMember(){
//        return null;
//    }
}
