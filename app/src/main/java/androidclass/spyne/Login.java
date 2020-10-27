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

import static android.content.ContentValues.TAG;

/**
 * Created by hp on 10/25/2020.
 */

public class Login extends AppCompatActivity {


    private Button signUpBtn , loginBtn;

    private SignInButton signInButton;

    private GoogleSignInOptions gso;

    public int RC_SIGN_IN =100 ;

    private GoogleApiClient mGoogleApiClient;
    public static Boolean KEY_IS_LOGGED_IN=false;

    EditText emailtxt,passtxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();//hide the Title bar

        signUpBtn = (Button) findViewById(R.id.sign_Up_btn_LoginPage);

        loginBtn = (Button) findViewById(R.id.login_btn_LoginPage);

        emailtxt = (EditText) findViewById(R.id.login_email);
        passtxt = (EditText) findViewById(R.id.login_pass);

        if(isLogin())
        {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        signUpBtn.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            //On pressing signup btn, go to signup page
                                            Intent i = new Intent(Login.this, SignUp.class);
                                            startActivity(i);
                                        }
                                    }
        );


    loginBtn.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            int flag = 0;
            final String email = emailtxt.getText().toString();
            final String pass = passtxt.getText().toString();


            //validation of email and password of login page
            if (!isValidEmail(email)) {
                emailtxt.setError("Invalid Email");
                flag = 1;
            }


            if (!isValidPassword(pass)) {
                passtxt.setError("Minimum 6 letter/digit password required");
                flag = 1;
            }

            //Checking that Login id and password exist in database or not
            DatabaseHandler db=new DatabaseHandler(Login.this);
            String passwrd=db.searchPass(email);
            //Not Signed up yet
            if((passwrd.equals("NOT FOUND IN DATABASE")) && isValidEmail(email)){
                flag=1;
                emailtxt.setError("First Signup please");
                passtxt.setText(null);
                passtxt.setError(null);
            }
            //Signed up but incorrect password
            if(!(passwrd.equals("NOT FOUND IN DATABASE")) && !(pass.equals(passwrd)) && isValidEmail(email)){
                flag=1;
                passtxt.setError("Password not matched");
            }


            if (flag == 0) {
                Toast.makeText(getApplicationContext(), "Logged in using " + email, Toast.LENGTH_SHORT).show();
                saveInfo();
                setLogin(true);
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                finish();
                emailtxt.setText(null);
                passtxt.setText(null);
            }
        }

        }
    );

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        //signInButton.setSize(SignInButton.SIZE_WIDE);
        // im= (ImageView) findViewById(R.id.profileImage);



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

                Toast.makeText(this, "Logged in using " + acct.getEmail(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                setLogin(true);

            } else {
                //If login fails
                Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
            }
        }
    }


    //Validating email id
    private boolean isValidEmail(String email)
    {String empattern="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com";
        Pattern pattern=Pattern.compile(empattern);
        Matcher matcher=pattern.matcher(email);
        return matcher.matches();
    }
    private boolean isValidPassword(String pass){
        if(pass!=null && pass.length()>=6){
            return true;
        }
        return false;
    }
    //Displaying or setting signupinfo into logininfo so that user don't have to type it again using shared preference
    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sharedPref1 = getSharedPreferences("signupInfo", Context.MODE_PRIVATE);
        //SharedPreferences sharedPref= PreferenceManager.getDefaultSharedPreferences(Second.this);
        String eml = sharedPref1.getString("username", "");
        String pw = sharedPref1.getString("password", "");
        emailtxt.setText(eml);
        passtxt.setText(pw);
    }

    @Override
    protected void onResume() {
        super.onResume();
        emailtxt.setError(null);
        passtxt.setError(null);
        //Already logged in then on back press not reach again to login screen
    }

    //Save the user login info
    public void saveInfo(){
        // SharedPreferences sharedPref= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences sharedPref=getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putString("username",emailtxt.getText().toString());
        editor.putString("password",passtxt.getText().toString());
        editor.apply();

        Toast.makeText(this,"Logged in using " + emailtxt.getText().toString(),Toast.LENGTH_SHORT).show();
    }
    //Session Manager
    public void setLogin(boolean isLoggedIn)
    {
        SharedPreferences sharedPref=getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();;
        editor.putBoolean(String.valueOf(KEY_IS_LOGGED_IN),isLoggedIn);
        editor.apply();
        Log.d(TAG,"User Login Session Modified");
    }

    public boolean isLogin() {
        SharedPreferences sharedPref=getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        return sharedPref.getBoolean(String.valueOf(KEY_IS_LOGGED_IN), false);
    }
}
