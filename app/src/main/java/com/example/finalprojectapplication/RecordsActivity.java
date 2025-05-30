package com.example.finalprojectapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordsActivity extends AppCompatActivity {
    private RecyclerView rvRecords;
    private RecordsAdapter adapter;
    private List<PaymentRecord> recordList;
    private PaymentHelper paymentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_records);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvRecords = findViewById(R.id.rv_records);
        paymentHelper = new PaymentHelper(this);

        //获取缴费记录
        loadRecords();

        //设置RecycleView
        rvRecords.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecordsAdapter(this,recordList);
        rvRecords.setAdapter(adapter);
    }

    private void loadRecords(){
        recordList = paymentHelper.getAllRecords();
    }

    protected void onResume(){
        super.onResume();
        loadRecords();  //刷新记录
        adapter.notifyDataSetChanged();
    }
}