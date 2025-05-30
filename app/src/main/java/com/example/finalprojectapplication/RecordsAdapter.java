package com.example.finalprojectapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ViewHolder> {
    private Context context;
    private List<PaymentRecord> recordList;

    public RecordsAdapter(Context context, List<PaymentRecord> recordList) {
        this.context = context;
        this.recordList = recordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_records, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PaymentRecord record = recordList.get(position);

        holder.tvAccount.setText("户号: " + record.getAccount());
        holder.tvAmount.setText("金额: ¥" + String.format("%.2f", record.getAmount()));
        holder.tvDate.setText("时间: " + record.getDate());
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAccount, tvAmount, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAccount = itemView.findViewById(R.id.tv_account);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}