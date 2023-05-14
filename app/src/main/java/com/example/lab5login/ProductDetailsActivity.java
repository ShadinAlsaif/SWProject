package com.example.lab5login;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView imgBack;
    ImageView imgProduct;
    TextView productName;
    TextView productPrice;
    TextView productDescription;
    Product product;

    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        imgBack = findViewById(R.id.imgBack);
        imgProduct = findViewById(R.id.imgProductImage);
        productName = findViewById(R.id.txtProductName);
        productPrice = findViewById(R.id.txtProductPrice);
        productDescription = findViewById(R.id.txtProductDescription);
        product = new Product();
        db = new DBHelper(this);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (getIntent().getStringExtra("productName") != null) {
            String name = getIntent().getStringExtra("productName");
            product = db.getProductDetails(name);

            byte[] decodedString = Base64.decode(product.getProductImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgProduct.setImageBitmap(decodedByte);
            productName.setText(product.getProductName());
            productPrice.setText(String.valueOf(product.getProductPrice()));
            productDescription.setText(product.getProductDescription());


        }
    }
}