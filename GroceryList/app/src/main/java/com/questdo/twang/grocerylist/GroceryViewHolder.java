package com.questdo.twang.grocerylist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by twang on 3/29/17.
 */

public class GroceryViewHolder extends RecyclerView.ViewHolder {

    public TextView mItem, mDescription, mPrice, mType;
    public View mRoot;

    public GroceryViewHolder(View itemView) {
        super(itemView);
        mRoot = itemView;
        mItem = (TextView) itemView.findViewById(R.id.item_name);
        mDescription = (TextView) itemView.findViewById(R.id.description);
        mPrice = (TextView) itemView.findViewById(R.id.price);
        mType = (TextView) itemView.findViewById(R.id.type);
    }
}