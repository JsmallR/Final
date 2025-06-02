package com.example.finalprojectapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomePageActivity extends AppCompatActivity {
    private Button btnPayment,btnQuery,btnRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnPayment = findViewById(R.id.btn_payment);
        btnQuery = findViewById(R.id.btn_query);
        btnRecords = findViewById(R.id.btn_records);

        //点击支付按钮跳转到支付界面
        btnPayment.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(HomePageActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });

        //点击查询按钮跳转到查询界面
        btnQuery.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(HomePageActivity.this,QueryActivity.class);
                startActivity(intent);
            }
        });

        //点击缴费记录按钮跳转到记录界面
        btnRecords.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(HomePageActivity.this,RecordsActivity.class);
                startActivity(intent);
            }
        });

    }
}