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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AgentRegister extends AppCompatActivity implements View.OnClickListener{

    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    MaterialEditText edit_fullname, edit_companyname, edit_address, edit_provinsi, edit_kota, edit_kodepos;
    MaterialButton btn_register;

    SharedPrefManager sharedPrefManager;

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

        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(INodeJS.class);

        sharedPrefManager = new SharedPrefManager(this);

        btn_register = (MaterialButton) findViewById(R.id.agent_regisbtn);

        edit_fullname = (MaterialEditText) findViewById(R.id.edt_fullname);
        edit_companyname = (MaterialEditText) findViewById(R.id.edt_companyName);
        edit_address = (MaterialEditText) findViewById(R.id.edt_address);
        edit_provinsi = (MaterialEditText) findViewById(R.id.edt_provinsi);
        edit_kota = (MaterialEditText) findViewById(R.id.edt_kota);
        edit_kodepos = (MaterialEditText) findViewById(R.id.edt_kodepos);

        edit_fullname.setText(sharedPrefManager.getSPNama());

        btn_register.setOnClickListener(this);

        if (sharedPrefManager.getSPSudahLogin()) {
            startActivity(new Intent(AgentRegister.this, Main2Activity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    @Override
    public void onClick(View view){
        if (edit_fullname.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Fullname", Toast.LENGTH_SHORT).show();
        }
        else if (edit_companyname.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Companyname", Toast.LENGTH_SHORT).show();
        }
        else if (edit_address.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Address", Toast.LENGTH_SHORT).show();
        }
        else if (edit_provinsi.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Provinsi", Toast.LENGTH_SHORT).show();
        }
        else if (edit_kota.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Kota", Toast.LENGTH_SHORT).show();
        }
        else if (edit_kodepos.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Kodepos", Toast.LENGTH_SHORT).show();
        }
        else {
            registerAgent(
                    edit_fullname.getText().toString(),
                    edit_companyname.getText().toString(),
                    edit_address.getText().toString(),
                    edit_provinsi.getText().toString(),
                    edit_kota.getText().toString(),
                    Integer.parseInt(edit_kodepos.getText().toString()));
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        }
    }

    private void registerAgent(String fullname,
                               String companyname,
                               String address,
                               String provinsi,
                               String kota,
                               Integer kodepos) {
        compositeDisposable.add(myApi.registerAgent(fullname, companyname, address, kota, provinsi, kodepos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(AgentRegister.this, "Registrasi Success", Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}

//Integer.parseInt(edit_idagent.getText().toString()),
