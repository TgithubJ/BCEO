package com.example.chloe.bceo.Adapter;

import com.example.chloe.bceo.model.Product;

import java.util.ArrayList;

/**
 * Created by HsiangLin on 12/10/15.
 */

public class ProductAdapter {
    public static ArrayList<Product> productList;

    public ProductAdapter(){
       productList = new ArrayList<Product>();
    }

    public static ArrayList<Product> getProductList(){
        return productList;
    }

    public void addProduct(Product p){
        productList.add(p);
    }

}
