package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ThrowOnExtraProperties;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;



import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.example.grocery.DBqueries.cartList;

public class DeliveryActivity extends AppCompatActivity {
   private RecyclerView deliveryRecyclerView;
   private Button changeOrAddnewAdressBtn;
   public static final int SELECT_ADDRESS = 0;
   private TextView totalAmount;
   private TextView fullname;
   private TextView fullAddress;
   private TextView pincode;
   private String name,mobileno;
   private Button continueBtn;
   private Dialog loadingDialog;
   private ImageButton paytm,cod;
    private Dialog paymentmethodDialog;
     private String order_id;
    private ConstraintLayout orderConfrimationLayout;
    private ImageView continueShoppingBtn;
    private TextView orderId;
    private  boolean successResponse =false;
    public static   boolean fromCart;
   public static List<CartitemModel> cartitemModelList;
    String TAG ="main";
    final int UPI_PAYMENT = 0;
    public  static  boolean codorderconfirmed = false;
    private FirebaseFirestore firebaseFirestore;
    public static  boolean allProductAvailable;
    public static boolean getQtyIds = true;
    public static CartAdapter cartAdapter;

    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

       Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        // lloading dialog
        loadingDialog = new Dialog(DeliveryActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //loadingDialog.show();

        // lloading dialog
        paymentmethodDialog = new Dialog(DeliveryActivity.this);
        paymentmethodDialog.setContentView(R.layout.payment_method);
        paymentmethodDialog.setCancelable(true);
        paymentmethodDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        paymentmethodDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paytm = paymentmethodDialog.findViewById(R.id.paytm_btn);
        cod = paymentmethodDialog.findViewById(R.id.cod_btn);
    firebaseFirestore = FirebaseFirestore.getInstance();
        //loadingDialog.show();

        deliveryRecyclerView =findViewById(R.id.delivery_recycler_view);
        changeOrAddnewAdressBtn=findViewById(R.id.change_or_add_address_btn);
       totalAmount= findViewById(R.id.total_cart_amount);
       fullname = findViewById(R.id.fullname);
       fullAddress=findViewById(R.id.address);
       pincode = findViewById(R.id.pincode);
       continueBtn = findViewById(R.id.cart_continue_btn);

       orderConfrimationLayout = findViewById(R.id.order_confirmation_layout);
       continueShoppingBtn = findViewById(R.id.continue_shopping);
       orderId = findViewById(R.id.order_id);


       ///////////
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String date = df.format(c.getTime());
        Random rand = new Random();
        int min =1000, max= 9999;
// nextInt as provided by Random is exclusive of the top value so you need to add 1
        int randomNum = rand.nextInt((max - min) + 1) + min;
        order_id =  date+String.valueOf(randomNum);

        ///////
        getQtyIds =true;
allProductAvailable = true;


       LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);

      //  List<CartitemModel> cartitemModelList = new ArrayList<>();

       // cartitemModelList.add(new CartitemModel(1,"Price (3 items)","Rs.356654/-","free","Rs550000/-","Rs.500/-"));

         cartAdapter = new CartAdapter(cartitemModelList,totalAmount,false);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddnewAdressBtn.setVisibility(View.VISIBLE);


        changeOrAddnewAdressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              getQtyIds = false;

                Intent myAddressesIntent = new Intent(DeliveryActivity.this,MyAddressesActivity.class);
                  myAddressesIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myAddressesIntent);
            }
        });
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           if (allProductAvailable) {


               paymentmethodDialog.show();

           }else {

           }




            }
        });
        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getQtyIds = false;

                paymentmethodDialog.dismiss();
                Intent otpintent = new Intent(DeliveryActivity.this,OTPverificationActivity.class);

            otpintent.putExtra("mobileNo",mobileno.substring(0,10));
                startActivity(otpintent);






            }
        });
cod.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
   getQtyIds = false;
        paymentmethodDialog.dismiss();
        payUsingUpi("Ronald Benjamin", "9986398896@ybl",
               "payment", totalAmount.getText().toString().substring(3,totalAmount.getText().length()-2),order_id);





       // orderConfrimationLayout.setVisibility(View.VISIBLE);

    }
});



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //accessing quantity.
        if (getQtyIds) {

            for (int x = 0; x < cartitemModelList.size() - 1; x++) {

                for (int y = 0;y < cartitemModelList.get(x).getProductQuantity();y++){

                    final String quantityDocumentName = UUID.randomUUID().toString().substring(0,20);
                    Map<String,Object> timeStamp = new HashMap<>();
                    timeStamp.put("time", FieldValue.serverTimestamp());
                    final int finalX = x;
                    final int finalY = y;
                    firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(x).getProductID()).collection("QUANTITY").document(quantityDocumentName).set(timeStamp)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    cartitemModelList.get(finalX).getQtyIDs().add(quantityDocumentName);
                                    if (finalY +1 == cartitemModelList.get(finalX).getProductQuantity()){

                                        firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(finalX).getProductID()).collection("QUANTITY").orderBy("time",Query.Direction.ASCENDING).limit(cartitemModelList.get(finalX).getStockQuantity()).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()){

                                                            List<String> serverQuantity = new ArrayList<>();
                                                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){

                                                                serverQuantity.add(queryDocumentSnapshot.getId());

                                                            }

                                                            long availableQty=0;
                                                            boolean noLongerAvaialble = true;
                                                             for (String qtyId : cartitemModelList.get(finalX).getQtyIDs()){

                                                                 if (!serverQuantity.contains(qtyId)) {

                                                                     if (noLongerAvaialble) {
                                                                         cartitemModelList.get(finalX).setInStock(false);
                                                                     }else {
                                                                         cartitemModelList.get(finalX).setQtyError(true);
                                                                         cartitemModelList.get(finalX).setMaxQuantity(availableQty);
                                                                         Toast.makeText(DeliveryActivity.this, "All Product may not be available in required quantity....Sorry!", Toast.LENGTH_LONG).show();
                                                                     }


                                                                         allProductAvailable = false;
                                                                     }


                                                                 else {
                                                                     availableQty++;
                                                                     noLongerAvaialble = false;
                                                                 }



                                                             }

                                                                 cartAdapter.notifyDataSetChanged();
                                                        }else {

                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(DeliveryActivity.this,error,Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });

                                    }
                                }
                            });

                }
            }
        }else {
            getQtyIds=true;
        }

        ////accesing quantity.

        name = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getFullname();
        mobileno = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getMobileno();
        fullname.setText(name+" - "+mobileno);
        fullAddress.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAddess());
        pincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getPincode());
        if (codorderconfirmed){
            showConfirmationLayout();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        loadingDialog.dismiss();

       if (getQtyIds) {

           for (int x = 0; x < cartitemModelList.size() - 1; x++) {

               if (!successResponse) {

                   for (final String qtyID : cartitemModelList.get(x).getQtyIDs()) {
                       final int finalX = x;
                       firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(x).getProductID()).collection("QUANTITY").document(qtyID).delete()
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {

                                       if (qtyID.equals(cartitemModelList.get(finalX).getQtyIDs().get(cartitemModelList.get(finalX).getQtyIDs().size() - 1))){
                                           cartitemModelList.get(finalX).getQtyIDs().clear();




                                       }


                                   }
                               });


                   }
               }else {
                   cartitemModelList.get(x).getQtyIDs().clear();
               }
               }

       }

    }
    void payUsingUpi(  String name,String upiId, String note, String amount,String orderid) {
        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                //.appendQueryParameter("mc", "")
                .appendQueryParameter("tr", orderid)
                //.appendQueryParameter("tr", "25584584")
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                //.appendQueryParameter("refUrl", "blueapp")
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
      //  intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
     //   startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(intent, "Pay with");
        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(DeliveryActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response "+resultCode );

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }
    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(DeliveryActivity.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {

                 showConfirmationLayout();
                //Code to handle successful transaction here.
                Toast.makeText(DeliveryActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(DeliveryActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
            }
            else {
                Toast.makeText(DeliveryActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(DeliveryActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        if (successResponse)
        {
            finish();
            return;
        }

        super.onBackPressed();
    }


    private void showConfirmationLayout(){
        successResponse =true;
        codorderconfirmed=false;
        getQtyIds = false;

        for (int x = 0;x<cartitemModelList.size()-1;x++){

            for (String qtyID : cartitemModelList.get(x).getQtyIDs()){
                firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(x).getProductID()).collection("QUANTITY").document(qtyID).update("user_ID",FirebaseAuth.getInstance().getUid());
            }
        }



        if (MainActivity.mainActivity!=null){
            MainActivity.mainActivity.finish();
            MainActivity.mainActivity = null;
            MainActivity.showCart =false;
        }else {
    MainActivity.resetMainActivity = true;
        }
        if (ProductDetailsActivity.productDetailsActivity!=null){
            ProductDetailsActivity.productDetailsActivity.finish();
            ProductDetailsActivity.productDetailsActivity = null;
        }

        // orderConfrimationLayout.setVisibility(View.VISIBLE);
        String SMS_API ="https://www.fast2sms.com/dev/bulk";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SMS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers  = new HashMap<>();

                headers.put("authorization","T7cDgA3jyfzG4WLYKpHh5M1dN0Pust29mCwQOoJvxIlXVFaqESqZI0rUjWckEP29SMg5pTwQGuBJ4D7e");

                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> body  = new HashMap<>();

                body.put("sender_id","FSTSMS");
                body.put("language","english");
                body.put("route","qt");
                body.put("numbers",mobileno);
                body.put("message","38344");//generate message and set id
                body.put("variables","{#BB#}");
                body.put("variables_values",order_id);

                return body;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryActivity.this);
        requestQueue.add(stringRequest);




        if (fromCart){

            loadingDialog.show();
            Map<String,Object> updateCartList = new HashMap<>();
            long cartListSize = 0;
            final List<Integer> indexList = new ArrayList<>();

            for (int x = 0; x<cartList.size(); x++){

                if(!cartitemModelList.get(x).isInStock()){

                    updateCartList.put("product_id_"+cartListSize,cartitemModelList.get(x).getProductID());
                    cartListSize++;
                }else {
                    indexList.add(x);
                }


            }
            updateCartList.put("list_size",cartListSize);

            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                    .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        orderConfrimationLayout.setVisibility(View.VISIBLE);
                        for (int x=0;x<indexList.size();x++){
                            cartList.remove(indexList.get(x).intValue());
                            cartitemModelList.remove(indexList.get(x).intValue());
                            cartitemModelList.remove(cartitemModelList.size()-1);

                        }

                    }else {
                        String error = task.getException().getMessage();
                        Toast.makeText(DeliveryActivity.this,error,Toast.LENGTH_LONG).show();

                    }
                    loadingDialog.dismiss();

                }
            });
        }




        continueBtn.setEnabled(false);
        changeOrAddnewAdressBtn.setEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        orderId.setText(order_id);
        orderConfrimationLayout.setVisibility(View.VISIBLE);

        continueShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}