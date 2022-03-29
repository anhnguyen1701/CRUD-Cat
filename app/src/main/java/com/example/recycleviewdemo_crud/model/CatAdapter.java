package com.example.recycleviewdemo_crud.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewdemo_crud.R;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {
    private Context context;
    private List<Cat> mList;
    private CatItemListener mCatItem;

    public CatAdapter(Context context) {
        this.context = context;
        this.mList = new ArrayList<>();
    }

    public void setClickListener(CatItemListener mCatItem) {
        this.mCatItem = mCatItem;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        Cat cat = mList.get(position);
        if (cat == null) {
            return;
        }
        holder.img.setImageResource(cat.getImg());
        holder.tvName.setText(cat.getName());
        holder.tvDescribe.setText(cat.getDesc());
        holder.tvPrice.setText(cat.getPrice() + "");

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Ban co chac chan muon xoa " + cat.getName() + " nay khong");
                builder.setIcon(R.drawable.ic_launcher_background);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mList.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });


                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        } else
            return 0;
    }

    public Cat getItemAt(int position) {
        return mList.get(position);
    }

    public void add(Cat c) {
        mList.add(c);
        notifyDataSetChanged();
    }

    public void update(int position, Cat cat) {
        mList.set(position, cat);
        notifyDataSetChanged();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img;
        private TextView tvName, tvDescribe, tvPrice;
        private Button btnRemove;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.txtName);
            tvDescribe = itemView.findViewById(R.id.txtDescribe);
            tvPrice = itemView.findViewById(R.id.txtPrice);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mCatItem != null) {
                mCatItem.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface CatItemListener {
        void onItemClick(View view, int position);
    }
}
