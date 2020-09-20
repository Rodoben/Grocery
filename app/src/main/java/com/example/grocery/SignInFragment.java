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

import static com.example.grocery.RegisterActivity.onResetPasswordFragment;


public class SignInFragment extends Fragment {

   private TextView donthaveanaccount;
   private FrameLayout parentFramelayout;
   private ImageButton closebtn;
   private Button signInbtn;
private ProgressBar progressBar;
   private EditText email;
   private EditText pass;
   private FirebaseAuth firebaseAuth;
   private TextView forgotPassword;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public static boolean disableCloseBtn = false;

    public SignInFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        donthaveanaccount=view.findViewById(R.id.textView2);
        email=view.findViewById(R.id.editTextTextEmailAddress);
        pass=view.findViewById(R.id.editTextTextPassword);
        signInbtn=view.findViewById(R.id.button);
        closebtn=view.findViewById(R.id.imageButton);
        forgotPassword=view.findViewById(R.id.textView);
        progressBar=view.findViewById(R.id.progressBar3);
        firebaseAuth=FirebaseAuth.getInstance();
        parentFramelayout=getActivity().findViewById(R.id.register_Framelayout);
     if (disableCloseBtn){
         closebtn.setVisibility(View.GONE);
     }else {
         closebtn.setVisibility(View.VISIBLE);
     }
         return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         donthaveanaccount.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 setFragment(new SignUpFragment());
             }
         });
         forgotPassword.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 onResetPasswordFragment = true;
                 setFragment(new ForgotPassword());
             }
         });

         closebtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 mainIntent();



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

    signInbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkEmailandPassword();
        }
    });
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

    private void checkEmailandPassword() {
    if(email.getText().toString().matches(emailPattern)){
        if(pass.length()>=8){
            progressBar.setVisibility(View.VISIBLE);
            signInbtn.setEnabled(false);
            signInbtn.setTextColor(Color.argb(50,255,255,255));
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Intent mainIntent= new Intent(getActivity(),MainActivity.class);
                       startActivity(mainIntent);
                      getActivity().finish();

                   }else{
                       progressBar.setVisibility(View.INVISIBLE);
                       signInbtn.setEnabled(true);
                       signInbtn.setTextColor(Color.rgb(255,255,255));

                       String error=task.getException().getMessage();
                       Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                   }
                }
            });

        }else {
            Toast.makeText(getActivity(),"Incorrect Email or Password",Toast.LENGTH_SHORT).show();
        }
    }else{
        Toast.makeText(getActivity(),"Incorrect Email or Password",Toast.LENGTH_SHORT).show();
    }
    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(pass.getText()))
            {
               signInbtn.setEnabled(true);
               signInbtn.setTextColor(Color.rgb(255,255,255));
            }else {
                signInbtn.setEnabled(false);
                signInbtn.setTextColor(Color.argb(50,255,255,255));
            }
        }else{
            signInbtn.setEnabled(false);
            signInbtn.setTextColor(Color.argb(50,255,255,255));

        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
       fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFramelayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}