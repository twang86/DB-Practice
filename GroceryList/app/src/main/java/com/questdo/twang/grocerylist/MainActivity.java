package com.questdo.twang.grocerylist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Grocery> mGroceries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView shoppingRecyclerView = (RecyclerView) findViewById(R.id.grocery_recycler);

        mGroceries = ShoppingSQLiteHelper.getInstance(this).getShoppingList();

        final GroceryRecyclerAdapter groceryRecyclerAdapter = new GroceryRecyclerAdapter(mGroceries);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        shoppingRecyclerView.setLayoutManager(linearLayoutManager);
        shoppingRecyclerView.setAdapter(groceryRecyclerAdapter);

        findViewById(R.id.add_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = ShoppingSQLiteHelper.getInstance(v.getContext()).addItem("New Item", "Edit Later", "99", "Just Testing");
                Grocery newItem = new Grocery(id, "New Item", "Edit Later", "10", "Just Testing");
                mGroceries.add(0, newItem);
                groceryRecyclerAdapter.notifyItemInserted(0);
                shoppingRecyclerView.scrollToPosition(0);
            }
        });
    }
}
