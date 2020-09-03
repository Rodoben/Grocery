package com.example.grocery;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPassword extends Fragment {
private TextView knowmypassword;
    private FrameLayout parentFramelayout;
    private EditText registeredEmail;
    private Button resetPasswordbtn;
    FirebaseAuth firebaseAuth;



    public ForgotPassword() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        registeredEmail=view.findViewById(R.id.editTextTextEmailAddress3);
        resetPasswordbtn=view.findViewById(R.id.button3);

        knowmypassword=view.findViewById(R.id.textView5);
        parentFramelayout=getActivity().findViewById(R.id.register_Framelayout);
        firebaseAuth=FirebaseAuth.getInstance();
    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
   resetPasswordbtn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           resetPasswordbtn.setEnabled(false);
           resetPasswordbtn.setTextColor(Color.argb(50,255,255,255));
           firebaseAuth.sendPasswordResetEmail(registeredEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(getActivity(),"email  sent succesfully",Toast.LENGTH_LONG).show();

                   }else{
                       String error=task.getException().getMessage();
                       Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                   }
                   resetPasswordbtn.setEnabled(true);
                   resetPasswordbtn.setTextColor(Color.rgb(255,255,255));
               }
           });
       }
   });
        knowmypassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });
        registeredEmail.addTextChangedListener(new TextWatcher() {
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
    }

    private void checkInputs() {

        if(TextUtils.isEmpty(registeredEmail.getText())){
            resetPasswordbtn.setEnabled(false);
            resetPasswordbtn.setTextColor(Color.argb(50,255,255,255));

        }else{
            resetPasswordbtn.setEnabled(true);
            resetPasswordbtn.setTextColor(Color.rgb(255,255,255));

        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFramelayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}