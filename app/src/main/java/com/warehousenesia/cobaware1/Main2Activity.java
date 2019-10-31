package com.warehousenesia.cobaware1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.warehousenesia.cobaware1.Model.DataPaket;
import com.warehousenesia.cobaware1.Service.ApiService;
import com.warehousenesia.cobaware1.Service.ApiUtil;
import com.warehousenesia.cobaware1.Service.PaketClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Main2Activity extends AppCompatActivity {

//    public static String EXTRA_USERNAME = "username";
//    public static String EXTRA_EMAIL = "email";

    TextView username, email;

     private RecyclerView rvlist;
     Button btn_logout;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        username = findViewById(R.id.cetak_username);
        email = findViewById(R.id.cetak_email);

//        String c_username = getIntent().getStringExtra(EXTRA_USERNAME);
//        String c_email = getIntent().getStringExtra(EXTRA_EMAIL);

//        username.setText("Member : ");
//        email.setText("Email : ");

        rvlist = findViewById(R.id.rv_paketlist);
        getPaket(rvlist);

        sharedPrefManager = new SharedPrefManager(this);

        username.setText("Member : " + sharedPrefManager.getSPNama());
        email.setText("Email : " + sharedPrefManager.getSPEmail());

        btn_logout = findViewById(R.id.btnLogout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(Main2Activity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }

    private void getPaket(final RecyclerView recyclerView){
        Retrofit retrofit = PaketClient.getClient(ApiUtil.Base_url);
        ApiService apiService =retrofit.create(ApiService.class);

        Call<ArrayList<DataPaket>> data = apiService.getPaket();
        data.enqueue(new Callback<ArrayList<DataPaket>>() {
            @Override
            public void onResponse(Call<ArrayList<DataPaket>> call, Response<ArrayList<DataPaket>> response) {
                PaketAdapter paketAdapter = new PaketAdapter(Main2Activity.this, response.body());
                recyclerView.setLayoutManager(new LinearLayoutManager(Main2Activity.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(paketAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataPaket>> call, Throwable t) {
                Log.e("ERROR", t.getMessage() );
            }
        });
    }
}
