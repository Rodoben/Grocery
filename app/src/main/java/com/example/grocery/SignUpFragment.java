package com.example.grocery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SignUpFragment extends Fragment {

    private TextView alreadyhaveanaccount;
    private FrameLayout parentFrameLayout;

    private EditText name;
    private EditText email;
    private EditText pass;
    private EditText cnfpass;

    private ImageButton closeBtn;
    private Button signUpBtn;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    public static boolean disableCloseBtn = false;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
      alreadyhaveanaccount = view.findViewById(R.id.textView3);
      parentFrameLayout=getActivity().findViewById(R.id.register_Framelayout);
      name=view.findViewById(R.id.editTextTextPersonName);
      email=view.findViewById(R.id.editTextTextEmailAddress2);
      pass=view.findViewById(R.id.editTextTextPassword2);
      cnfpass=view.findViewById(R.id.editTextTextPassword3);
      signUpBtn=view.findViewById(R.id.button2);
      closeBtn=view.findViewById(R.id.imageButton2);
      progressBar=view.findViewById(R.id.progressBar);
      firebaseAuth=FirebaseAuth.getInstance();
      firebaseFirestore=FirebaseFirestore.getInstance();
        if (disableCloseBtn){
            closeBtn.setVisibility(View.GONE);
        }else {
            closeBtn.setVisibility(View.VISIBLE);
        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity(),MainActivity.class);
                startActivity(mainIntent);
                getActivity().finish();
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

name.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
checkInputs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
});
pass.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
       checkInputs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
});
cnfpass.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
   checkInputs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
});
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to send data to firebase

                checkEmailAndPassword();
            }
        });
    }

    private void checkEmailAndPassword() {
     if(email.getText().toString().matches(emailPattern)){
         if(pass.getText().toString().equals(cnfpass.getText().toString())){
              progressBar.setVisibility(View.VISIBLE);
             signUpBtn.setEnabled(false);
             signUpBtn.setTextColor(Color.argb(50,255,255,255));
             firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                  if(task.isSuccessful()){
                      Map<String,Object> userdata= new HashMap<>();
                      userdata.put("name",name.getText().toString());



                      firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).set(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              if (task.isSuccessful()){
                                  CollectionReference userDataReference=firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");



                                  Map<String,Object> wishListMap= new HashMap<>();
                                  wishListMap.put("list_size", (long) 0);

                                  Map<String,Object> ratingsMap= new HashMap<>();
                                  ratingsMap.put("list_size", (long) 0);

                                  Map<String,Object> cartMap= new HashMap<>();
                                  cartMap.put("list_size", (long) 0);

                                  final List<String> documentNames = new ArrayList<>();
                                  documentNames.add("MY_WISHLIST");
                                  documentNames.add("MY_RATINGS");
                                  documentNames.add("MY_CART");

                                  List<Map<String,Object>> documentFields = new ArrayList<>();
                                  documentFields.add(wishListMap);
                                  documentFields.add(ratingsMap);
                                  documentFields.add(cartMap);



                                   for (int x=0;x<documentNames.size();x++){

                                       final int finalX = x;
                                       userDataReference.document(documentNames.get(x)).set(documentFields.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {

                                               if (task.isSuccessful())
                                               {
                                                   if (finalX == documentNames.size()-1){
                                                       mainIntent();
                                                   }


                                               }else {
                                                   progressBar.setVisibility(View.INVISIBLE);
                                                   signUpBtn.setEnabled(true);
                                                   signUpBtn.setTextColor(Color.argb(255,255,255,255));
                                                   String error=task.getException().getMessage();
                                                   Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();

                                               }
                                           }
                                       });
                                   }






                              }else{

                                  String error=task.getException().getMessage();
                                  Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                              }
                          }
                      });



                  }else {
                      progressBar.setVisibility(View.INVISIBLE);
                      signUpBtn.setEnabled(true);
                      signUpBtn.setTextColor(Color.argb(255,255,255,255));
                      String error=task.getException().getMessage();
                      Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                  }
                 }
             });
         }else {
                 cnfpass.setError("Password doesn't match");
         }
     }else{
              email.setError("Invalid Email!");
     }
    }

    private void mainIntent() {
        if (disableCloseBtn) {
            disableCloseBtn = false;

        } else {
            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainIntent);


        }
        getActivity().finish();
    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(name.getText())){
                if(!TextUtils.isEmpty(pass.getText()) && pass.length()>=8){
                    if (!TextUtils.isEmpty(cnfpass.getText())) {
                        signUpBtn.setEnabled(true);
                        signUpBtn.setTextColor(getResources().getColor(R.color.colorAccent));
                    }else{
                        signUpBtn.setEnabled(false);
                        signUpBtn.setTextColor(Color.argb(50,255,255,255));

                    }
                }else {
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.argb(50,255,255,255));

                }
            }else{
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50,255,255,255));

            }
        }else{
           signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(Color.argb(50,255,255,255));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
      fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}