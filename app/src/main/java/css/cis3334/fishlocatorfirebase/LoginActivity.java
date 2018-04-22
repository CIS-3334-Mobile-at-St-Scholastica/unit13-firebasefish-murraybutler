package css.cis3334.fishlocatorfirebase;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ArrayList<String> ret;
    EditText userID;
    EditText passwd;
    Intent retInt = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("CSS3334","onCreate for Login");

        mAuth = FirebaseAuth.getInstance();

        userID = (EditText) findViewById(R.id.userID);
        passwd = (EditText) findViewById(R.id.passwd);

        setupCreateButton();
        Log.d("CSS3334","CreateButton setup");
        setupLoginButton();
        Log.d("CSS3334","LoginButton setup");
    }

    private void setupCreateButton() {
        Button createBtn = (Button) findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CSS3334","call CreateAcct");
                createAccount(userID.toString(), passwd.toString());
            }
        });
    }

    private void setupLoginButton() {
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CSS3334","call SignIn");
                signIn(userID.toString(),passwd.toString());
            }
        });
    }


    private void signIn(String email, String password){
        Log.d("CSS3334","Sign In");
        //sign in the recurrent user with email and password previously created.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { //add to listener
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) { //when failed
                    Toast.makeText(LoginActivity.this, "SignIn--Authentication failed.",Toast.LENGTH_LONG).show();
                } else {
                    Log.d("CSS3334","onClick for Login");
                    // signIn worked, return to MainActivity
                    setResult(Activity.RESULT_OK,retInt);
                    finish();
                }
            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d("CSS3334","Create");
        //create account for new users
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {  //update listener.
                if (!task.isSuccessful()) { //when failed
                    Toast.makeText(LoginActivity.this, "createAccount--Authentication failed.",Toast.LENGTH_LONG).show();
                } else {
                    //return to MainActivity is login works
                    setResult(Activity.RESULT_OK,retInt);
                    finish();
                }
            }
        });
    }
}
