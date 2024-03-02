package com.example.inventoryapp_chrissharp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Adapter that takes the CardView and adapts it to the RecyclerView to display the data
// Code is a slightly modified version from
// "https://www.youtube.com/playlist?list=PLSrm9z4zp4mGK0g_0_jxYGgg3os9tqRUQ"
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private final Context context;
    private final Activity activity;
    private final ArrayList<String> itemId;
    private final ArrayList<String> itemName;
    private final ArrayList<String> itemQuantity;


    // Adapter constructor
    CustomAdapter(Activity activity, Context context, ArrayList<String> itemId, ArrayList<String> itemName,
                  ArrayList<String> itemQuantity){
        this.activity = activity;
        this.context = context;
        this.itemId = itemId;
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
            intent.putExtra("id", String.valueOf(itemId.get(holder.getAdapterPosition())));
            intent.putExtra("Item Name", String.valueOf(itemName.get(holder.getAdapterPosition())));
            intent.putExtra("Item Quantity", String.valueOf(itemQuantity.get(holder.getAdapterPosition())));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return itemId.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemName, itemQuantity;
        ImageButton editText;

        public ViewHolder(@NonNull View view) {
            super(view);

            itemName = view.findViewById(R.id.item_name);
            itemQuantity = view.findViewById(R.id.item_quantity);
            editText = view.findViewById(R.id.imageButtonEdit);
        }
    }
}
