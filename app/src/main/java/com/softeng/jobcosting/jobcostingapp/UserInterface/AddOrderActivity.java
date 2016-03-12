package com.softeng.jobcosting.jobcostingapp.UserInterface;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.softeng.jobcosting.jobcostingapp.BusinessLogic.Calculations;
import com.softeng.jobcosting.jobcostingapp.R;

import java.util.ArrayList;

public class AddOrderActivity extends AppCompatActivity {
    private ArrayList<View> items;
    private Calculations calc;
    private int orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        items = new ArrayList<View>();

        //add the first item
        //LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linearLayout);
        //LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //View mView = inflater.inflate(R.layout.input_item, null);
        //items.add(mView);
        //mainLayout.addView(mView);

        //create a new order
        calc = new Calculations();
        String newOrder = calc.newOrder();

        final int ORDER_ID = 0;
        final int DATE = 1;

        String[] values = newOrder.split(",");

        //display the orderID
        orderID = Integer.parseInt(values[ORDER_ID]);
        TextView orderNum = (TextView)findViewById(R.id.orderNumber);
        String orderNumView = orderNum.getText().toString();
        orderNumView += " " + Integer.toString(orderID);
        orderNum.setText(orderNumView);

        //display the date
        TextView date = (TextView)findViewById(R.id.date);
        String dateView = date.getText().toString();
        dateView += " " + values[DATE];
        date.setText(dateView);
    }

    //Called when ADD ITEM button is clicked
    public void addItem(View view) {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linearLayout);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.input_item, null);
        items.add(mView);
        mainLayout.addView(mView);
    }

    //Called when DONE button is clicked
    public void done(View view) {
        for(View v : items) {
            EditText storeInput = (EditText) v.findViewById(R.id.storeEditText);
            String store = storeInput.getText().toString();

            Spinner types = (Spinner) v.findViewById(R.id.typeSpinner);
            String type = (types.getSelectedItem()).toString();

            EditText descriptionInput = (EditText) v.findViewById(R.id.descEditText);
            String description = descriptionInput.getText().toString();

            EditText amountInput = (EditText) v.findViewById(R.id.amtEditText);
            String stringAmount = amountInput.getText().toString();
            float amount = Float.parseFloat(stringAmount);

            calc.newItem(orderID, store, type, description, amount);
        }

        Intent returnIntent = new Intent(this, MainActivity.class);
        startActivity(returnIntent);
    }
}
