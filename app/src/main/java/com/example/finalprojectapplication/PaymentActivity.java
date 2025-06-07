package com.example.finalprojectapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class PaymentActivity extends AppCompatActivity {

    private EditText etAccount;
    private Button btn50, btn100, btn200, btn500, btnPay;
    private double selectedAmount = 0;
    private PaymentHelper paymentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etAccount = findViewById(R.id.et_account);
        btn50 = findViewById(R.id.btn_50);
        btn100 = findViewById(R.id.btn_100);
        btn200 = findViewById(R.id.btn_200);
        btn500 = findViewById(R.id.btn_500);
        btnPay = findViewById(R.id.btn_pay);
        paymentHelper = new PaymentHelper(this);

        //设置金额按钮点击事件
        btn50.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                selectAmount(50);
            }
        });

        btn100.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                selectAmount(100);
            }
        });

        btn200.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                selectAmount(200);
            }
        });

        btn500.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                selectAmount(500);
            }
        });

        //设置支付按钮点击事件
        btnPay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                processPayment();
            }
        });
    }

    private void selectAmount(double amount) {
        selectedAmount = amount;
    }

    private void processPayment(){
        String account = etAccount.getText().toString().trim();
        if(account.isEmpty()){
            Toast.makeText(this,"请输入户号",Toast.LENGTH_SHORT).show();
            return;
        }
        if(selectedAmount<=0){
            Toast.makeText(this,"请选择缴费金额",Toast.LENGTH_SHORT).show();
            return;
        }

        //显示确认对话框
        new AlertDialog.Builder(this).setTitle("确认缴费")
                .setMessage("户号："+account+"\n金额："+selectedAmount+"元")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        simulatePayment(account,selectedAmount);
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }

    //模拟支付操作
    private void simulatePayment(String account,double amount){
        //显示加载对话框
        android.app.ProgressDialog dialog = new android.app.ProgressDialog(this);
        dialog.setMessage("支付处理中...");
        dialog.setCancelable(false);
        dialog.show();

        //模拟支付延迟
        new android.os.Handler().postDelayed(new Runnable(){
            public void run(){
                dialog.dismiss();
                //保存缴费记录
                long result = paymentHelper.addRecord(account,amount);
                if(result != -1){
                    double currentBill = PaymentRecord.getElectricityBill();
                    PaymentRecord.setElectricityBill(currentBill - amount);
                    Toast.makeText(PaymentActivity.this, "缴费成功！", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
                else{
                    Toast.makeText(PaymentActivity.this, "缴费失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        },2000);
    }
}