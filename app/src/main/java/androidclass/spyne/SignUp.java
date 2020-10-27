package androidclass.spyne;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hp on 10/25/2020.
 */


public class SignUp extends AppCompatActivity{

    private Button loginBtn , signUpBtn;

    private SignInButton signInButton;

    private GoogleSignInOptions gso;

    public int RC_SIGN_IN =100 ;

    private GoogleApiClient mGoogleApiClient;

    EditText emailtext, passtext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        getSupportActionBar().hide();   //hide the Title bar


        loginBtn = (Button) findViewById(R.id.loginBtnSignUpPage);

        emailtext = (EditText) findViewById(R.id.signUp_email);
        passtext = (EditText) findViewById(R.id.signUp_pass);

        loginBtn.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
        //On pressing login btn, go to login page
        Intent i = new Intent(SignUp.this, Login.class);
        startActivity(i);
        }
        }
        );

        signUpBtn = (Button) findViewById(R.id.sign_Up_btn_SignUpPage);

        signUpBtn.setOnClickListener(new View.OnClickListener() {

                                         @Override
                                         public void onClick(View v) {
                                             int flag = 0;

                                             String email = emailtext.getText().toString();
                                             String pass = passtext.getText().toString();


                                             if (!isValidEmail(email)) {
                                                 emailtext.setError("Invalid Email");
                                                 flag = 1;
                                             }


                                             if (!isValidPassword(pass)) {
                                                 passtext.setError("Invalid password");
                                                 flag = 1;
                                             }

                                             //If that email already exists then set error
                                             DatabaseHandler db1 = new DatabaseHandler(SignUp.this);
                                             if (!((db1.searchPass(email)).equals("NOT FOUND IN DATABASE")) && isValidEmail(email)) {
                                                 flag = 1;
                                                 emailtext.setError("Emailid already exists");
                                             }

                                                 if (flag == 0) {
                                                     Toast.makeText(getApplicationContext(), "Signed up using" + email, Toast.LENGTH_SHORT).show();
                                                     //wait for 1 seconds
                                                     long futuretime = System.currentTimeMillis() + 500;
                                                     while (System.currentTimeMillis() < futuretime) {
                                                         synchronized (this) {
                                                             try {
                                                                 wait(futuretime - System.currentTimeMillis());
                                                                 finish();
                                                             } catch (Exception e) {
                                                             }
                                                         }
                                                     }


                                                     savesignupInfo();
                                                     DatabaseHandler db = new DatabaseHandler(SignUp.this);
                                                     Log.d("Insert: ", "Inserting ..");
                                                     db.addContact(new Contact(email, pass));


                                                     Intent i = new Intent(SignUp.this, MainActivity.class);
                                                     startActivity(i);


                                                 }
                                             }
                                         }

        );

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d("connectionResult",connectionResult.getErrorMessage());
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    //Validating email id
    private boolean isValidEmail(String email) {
        String empattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com";
        Pattern pattern = Pattern.compile(empattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }

    //Save the usersignup info
    public void savesignupInfo() {
        // SharedPreferences sharedPref= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences sharedPref1 = getSharedPreferences("signupInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref1.edit();
        editor.putString("username", emailtext.getText().toString());
        editor.putString("password", passtext.getText().toString());
        editor.apply();

      //  Toast.makeText(this, "Saved yuhu!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                //Getting google account
                GoogleSignInAccount acct = result.getSignInAccount();

                //Displaying name and email
                //textViewName.setText(acct.getDisplayName());
                //textViewEmail.setText(acct.getEmail());
                /*Picasso.with(MainActivity.this)
                        .load(acct.getPhotoUrl())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(android.R.drawable.ic_dialog_alert)
                        .into(im);*/

                Toast.makeText(this, "Signed up using " + acct.getEmail(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SignUp.this, MainActivity.class);
                startActivity(i);

            } else {
                //If login fails
                Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
    }




