package com.luv2code.android.muvizis.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luv2code.android.muvizis.R;
import com.luv2code.android.muvizis.db.models.SimpleDataObject;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private List<SimpleDataObject> dataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvData;

        private MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvData = view.findViewById(R.id.tvData);
        }
    }

    public DataAdapter(List<SimpleDataObject> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_data_object, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SimpleDataObject dataObject = dataList.get(position);
        holder.tvTitle.setText(dataObject.getTitle());
        holder.tvData.setText(dataObject.getData());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
