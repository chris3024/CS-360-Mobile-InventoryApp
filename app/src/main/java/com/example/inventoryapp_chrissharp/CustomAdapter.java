package com.example.inventoryapp_chrissharp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Adapter that takes the CardView and adapts it to the RecyclerView to display the data
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private ArrayList itemName, itemQuantity;



    CustomAdapter(Context context, ArrayList<String> itemName, ArrayList<String> itemQuantity){
        this.context = context;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;


    }

    // Inflating the layout view
    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {

        holder.itemName.setText(String.valueOf(itemName.get(position)));
        holder.itemQuantity.setText(String.valueOf(itemQuantity.get(position)));
        holder.editText.setOnClickListener(v -> {
            Intent intent = new Intent(context, ActivityUpdate.class);
            intent.putExtra("Item Name", String.valueOf(itemName.get(holder.getAdapterPosition())));
            intent.putExtra("Item Quantity", String.valueOf(itemQuantity.get(holder.getAdapterPosition())));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemName, itemQuantity;
        Button editText;

        public ViewHolder(@NonNull View view) {
            super(view);

            itemName = view.findViewById(R.id.item_name);
            itemQuantity = view.findViewById(R.id.item_quantity);
            editText = view.findViewById(R.id.buttonEdit);
        }
    }
}
