package com.example.sabihachowdhury.eatinghouse.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.sabihachowdhury.eatinghouse.Cart;
import com.example.sabihachowdhury.eatinghouse.Database.Database;
import com.example.sabihachowdhury.eatinghouse.Interface.ItemClickListener;
import com.example.sabihachowdhury.eatinghouse.Model.Order;
import com.example.sabihachowdhury.eatinghouse.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData = new ArrayList<>();
    private Cart cart;

    public CartAdapter(List<Order> listData, Cart cart) {
        this.listData = listData;
        this.cart = cart;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(cart);
        View itemView = layoutInflater.inflate(R.layout.cart_layout,viewGroup,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, final int i) {
       /* TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(""+listData.get(i).getQuantity(), Color.RED);
        cartViewHolder.btn_number_cart.setImageDrawable(textDrawable);*/

       cartViewHolder.btn_number_cart.setNumber(listData.get(i).getQuantity());
       cartViewHolder.btn_number_cart.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
           @Override
           public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
               Order order = listData.get(i);
               order.setQuantity(String.valueOf(newValue));
               new Database(cart).updateCart(order);

               int total = 0;
               List<Order> orders = new Database(cart).getCarts();
               for(Order item:orders)
                   total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(item.getQuantity()));
               Locale locale = new Locale("en","BD");
               NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

               cart.txtTotalPrice.setText(fmt.format(total));

           }
       });

        Locale locale = new Locale("en","BD");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(i).getPrice()))*(Integer.parseInt(listData.get(i).getQuantity()));
        cartViewHolder.txt_price.setText(fmt.format(price));
        cartViewHolder.txt_cart_name.setText(listData.get(i).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void removeItem(int position)
    {
        listData.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Order item,int position)
    {
        listData.add(position,item);
        notifyItemInserted(position);
    }
}
