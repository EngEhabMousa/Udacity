package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    TextView quantityTextView ;
    CheckBox whippedCream;
    CheckBox chocolate;
    EditText nameEditText;
    private int quantityOfCoffees=0;
    private int totalPrice=0;
    private boolean hasWippedCream;
    private boolean hasChocolate;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantityTextView = findViewById(R.id.quantity_text_view);
        whippedCream = findViewById(R.id.whipped_check);
        chocolate=findViewById(R.id.choclate_check);
        nameEditText=findViewById(R.id.name_text_edit);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        this.name = nameEditText.getText().toString();
        hasWippedCream=whippedCream.isChecked();
        hasChocolate=chocolate.isChecked();
        this.totalPrice=this.calculatePrice();
        this.sendEmail("JustJava Order for "+ this.name , this.createOrderSummary(this.totalPrice));
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    public void displayQuantity() {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(quantityOfCoffees));
    }
    private String createOrderSummary(int price ){
        return getResources().getString(R.string.name)+" "+this.name
                +"\n"+getResources().getString(R.string.add_whipped_cream) +" "+this.hasWippedCream
                +"\n"+getResources().getString(R.string.add_chocolate) +" "+this.hasChocolate
                +"\n"+getResources().getString(R.string.quantity) +" : "+this.quantityOfCoffees
                +"\n"+getResources().getString(R.string.totale)+" "+price
                +"\n"+getResources().getString(R.string.thanks);
    }
    public int calculatePrice(){
        int unitPrice = 5;
        if (this.hasWippedCream)
            unitPrice ++;
        if(this.hasChocolate)
            unitPrice +=2;
        return unitPrice * this.quantityOfCoffees;
    }
    public void increment(View view){
        if(quantityOfCoffees<100){
            quantityTextView.setText(String.valueOf(++quantityOfCoffees));
        }else{
            Context context = getApplicationContext();
            CharSequence msg = getResources().getString(R.string.increment_message);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, msg ,duration);
            toast.show();
        }
    }
    public void decrement(View view){
        if(quantityOfCoffees > 0){
            quantityTextView.setText(String.valueOf(--quantityOfCoffees));
        }
        else{
            Context context = getApplicationContext();
            String msg = getResources().getString(R.string.decrement_message);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.show();
        }

    }
    public void sendEmail( String subject ,String message){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
}