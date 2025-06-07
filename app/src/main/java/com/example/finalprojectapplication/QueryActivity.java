package com.example.finalprojectapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QueryActivity extends AppCompatActivity {
    private EditText etAccount;
    private Button btnQuery;
    private TextView tvResult;
    private static Map<String, String> billDataMap = new HashMap<>();
    private PaymentHelper paymentHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_query);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etAccount = findViewById(R.id.et_account);
        btnQuery = findViewById(R.id.btn_query);
        tvResult = findViewById(R.id.tv_result);
        paymentHelper = new PaymentHelper(this);

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryElectricityBill();
            }
        });
    }

    //查询电费
    private void queryElectricityBill() {
        String account = etAccount.getText().toString().trim();
        //检查是否输入户号
        if (account.isEmpty()) {
            Toast.makeText(this, "请输入户号", Toast.LENGTH_SHORT).show();
            return;
        }

        tvResult.setText("查询中. . .");
        btnQuery.setEnabled(false); //禁用按钮防止重复点击

        //检查该户号是否已经存储过电费数据了
        if (billDataMap.containsKey(account)) {
            tvResult.setText(billDataMap.get(account));
        }
        else {
            // 生成随机电费数据
            String billData = generateAndShowBillData(account);
            billDataMap.put(account, billData);
            tvResult.setText(billData);
        }
        btnQuery.setEnabled(true); //重新启用按钮
    }

    // 生成并显示电费数据
    private String generateAndShowBillData(String account) {
        double currentBill = 50 + Math.random() * 500;  // 50~550元
        int usage = 100 + new Random().nextInt(300); // 100~400度
        String dueDate = "2025-06-30"; // 随机固定日期
        String result = "户号：" + account + "\n" +
                "当前电费：" + String.format("%.2f", currentBill) + "元\n" +
                "本月用电量：" + usage + "度\n" +
                "缴费截止日期：" + dueDate;
        return result;
    }

    // 更新电费数据的静态方法
    public static void updateBillData(String account, double paidAmount) {
        if (billDataMap.containsKey(account)) {
            String oldData = billDataMap.get(account);
            String[] lines = oldData.split("\n");
            String billLine = lines[1];
            double oldBill = Double.parseDouble(billLine.split("：")[1].replace("元", ""));
            double newBill = oldBill - paidAmount;
            if (newBill < 0) {
                newBill = 0;
            }
            lines[1] = "当前电费：" + String.format("%.2f", newBill) + "元";
            String newData = String.join("\n", lines);
            billDataMap.put(account, newData);
        }
    }
}