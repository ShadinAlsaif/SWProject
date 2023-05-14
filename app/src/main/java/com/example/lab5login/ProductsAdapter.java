package com.example.lab5login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

// تعديل اسم الكلاس "Task Adapter " واستبداله بالاسم الجديد في كل مرة موجود فيها
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    // "Task" تعديل المحتوى الموجود داخل الليست
    private ArrayList<Product> list;
    private Activity activity;


    public ProductsAdapter(ArrayList list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // نعديل اسم ملف التصميم المراد تكراه
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //  استبدال ال Course بالاسم الجديد الموجود داخل array list
        DBHelper db;
        db = new DBHelper(activity);

        Product product = list.get(position);

        holder.name.setText(product.getProductName());

        if (product.getProductImage() != null) {
            byte[] decodedString = Base64.decode(product.getProductImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.productImage.setImageBitmap(decodedByte);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ProductDetailsActivity.class);
                intent.putExtra("productName",product.getProductName());
                activity.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if (db.deleteProduct(product.getProductName())) {
                    Log.d("TAG", "onClick: in");
                    Toast.makeText(activity, product.getProductName() + " deleted successfully", Toast.LENGTH_SHORT).show();
                }
                db.close();
                Log.d("TAG", "onClick: " + position);
                list.remove(product);
                notifyItemRemoved(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        // تتغير العناصر بناء على التصميم والعناصر الموجودة
        TextView name;
        ImageView productImage;
        ImageView delete;

        MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.cardProductName);
            productImage = view.findViewById(R.id.cardProductImage);
            delete = view.findViewById(R.id.delete);
        }

    }
}