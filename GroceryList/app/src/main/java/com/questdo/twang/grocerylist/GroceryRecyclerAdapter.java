package com.questdo.twang.grocerylist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

/**
 * Created by twang on 3/29/17.
 */

public class GroceryRecyclerAdapter extends RecyclerView.Adapter<GroceryViewHolder> {

    private List<Grocery> mGroceries;

    public GroceryRecyclerAdapter(List<Grocery> groceries){
        mGroceries = groceries;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grocery_view, parent, false);
        return new GroceryViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(final GroceryViewHolder holder, final int position) {
        final Grocery currentItem = mGroceries.get(position);
        holder.mItem.setText(mGroceries.get(position).getName());
        holder.mType.setText(mGroceries.get(position).getType());
        holder.mDescription.setText(mGroceries.get(position).getDescription());
        holder.mPrice.setText(mGroceries.get(position).getPrice());
        holder.mRoot.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mGroceries.remove(currentItem);
                notifyItemRemoved(holder.getAdapterPosition());

                ShoppingSQLiteHelper.getInstance(v.getContext()).deleteItem(currentItem);

                return true;
            }
        });

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            //Man I really hate alert dialogs. So much extra work for just a tiny little pop up window.
            //LOOK AT THIS RIDICULOUSNESS!!! I can't believe this actually WORKS...
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final View editView = LayoutInflater.from(v.getContext())
                        .inflate(R.layout.grocery_edit_fields, null);
                builder.setMessage("Edit Grocery Item")
                        .setView(editView);
                final EditText nameEdit = (EditText) editView.findViewById(R.id.edit_item_name);
                nameEdit.setHint(currentItem.getName());
                final EditText descriptionEdit = (EditText) editView.findViewById(R.id.edit_description);
                descriptionEdit.setHint(currentItem.getDescription());
                final EditText priceEdit = (EditText) editView.findViewById(R.id.edit_price);
                priceEdit.setHint(currentItem.getPrice());
                final EditText typeEdit = (EditText) editView.findViewById(R.id.edit_type);
                typeEdit.setHint(currentItem.getType());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if (nameEdit.getText().toString().isEmpty()){
//                            currentItem.setName(nameEdit.getHint().toString());
//                        }else{
//                            currentItem.setName(nameEdit.getText().toString());
//                        }
                        //this replaced the above if else statement
                        currentItem.setName((nameEdit.getText().toString().isEmpty()?nameEdit.getHint():nameEdit.getText()).toString());
                        //dat tertiary tho
                        currentItem.setDescription((descriptionEdit.getText().toString().isEmpty()?descriptionEdit.getHint():descriptionEdit.getText()).toString());
                        currentItem.setPrice((priceEdit.getText().toString().isEmpty()?priceEdit.getHint():priceEdit.getText()).toString());
                        currentItem.setType((typeEdit.getText().toString().isEmpty()?typeEdit.getHint():typeEdit.getText()).toString());

                        notifyItemChanged(holder.getAdapterPosition());
                        ShoppingSQLiteHelper.getInstance(v.getContext()).editItem(currentItem);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mGroceries.size();
    }
}