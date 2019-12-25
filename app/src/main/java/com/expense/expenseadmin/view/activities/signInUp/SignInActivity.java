package com.expense.expenseadmin.view.activities.signInUp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.expense.expenseadmin.R;
import com.expense.expenseadmin.Utilities.AppUtils;
import com.expense.expenseadmin.view.activities.Home.HomeActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    CallbackManager mCallbackManager;

    @BindView(R.id.login_layout)
    LinearLayout loginLayout;

    @BindView(R.id.signup_layout)
    LinearLayout signUpLayout;

    @BindView(R.id.log_in_button)
    Button logInButton;

    @BindView(R.id.txt_sign_up)
    TextView signUpTxt;

    @BindView(R.id.logo)
    ImageView logo;

    @BindView(R.id.fb_login_button)
    Button fbLogIn;

    @BindView(R.id.tv_back)
    TextView backTV;

    @BindView(R.id.sign_google_button)
    Button mGoogleButton;

    @BindView(R.id.login_email)
    EditText mEmailLoginET;

    @BindView(R.id.login_password)
    EditText mPasswordLoginET;

    @BindView(R.id.first_last_name_et)
    EditText mFirstNameLastNameEditText;

    @BindView(R.id.email_et)
    EditText mEmailEditText;

    @BindView(R.id.password_et)
    EditText mPasswordEditText;

    @BindView(R.id.confirm_password_et)
    EditText mConfirmPasswordEditText;

    @BindView(R.id.normal_sign_button)
    Button mNormalSignUp;

    Handler handler = new Handler();
    Runnable runnableLogIn = new Runnable() {
        @Override
        public void run() {
            loginLayout.setVisibility(View.GONE);
            signUpLayout.setVisibility(View.VISIBLE);
        }
    };
    Runnable runnableSignUp = new Runnable() {
        @Override
        public void run() {
            loginLayout.setVisibility(View.VISIBLE);
            signUpLayout.setVisibility(View.GONE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_sign_in);
            ButterKnife.bind(this);
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            mAuth = FirebaseAuth.getInstance();
            logInButton.setOnClickListener(this);
            signUpTxt.setOnClickListener(this);
            backTV.setOnClickListener(this);
            mGoogleButton.setOnClickListener(this);
            fbLogIn.setOnClickListener(this);
            mNormalSignUp.setOnClickListener(this);

            mConfirmPasswordEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        if (mPasswordEditText.getText() == null || mPasswordEditText.getText().toString().isEmpty()) {
                            mConfirmPasswordEditText.setText("");
                            mPasswordEditText.setError(getString(R.string.required));
                        } else {
                            String password = mPasswordEditText.getText().toString().trim();
                            String confirmPassword = s.toString();

                            if (password.matches(confirmPassword)) {
                                mConfirmPasswordEditText.setTextColor(getResources().getColor(R.color.progress_start));
                                mPasswordEditText.setTextColor(getResources().getColor(R.color.progress_start));
                            } else if (password.length() > confirmPassword.length()) {
                                mPasswordEditText.setTextColor(getResources().getColor(R.color.text_color));
                                mConfirmPasswordEditText.setTextColor(getResources().getColor(R.color.text_color));
                            } else if (password.length() < confirmPassword.length()) {
                                mConfirmPasswordEditText.setTextColor(getResources().getColor(R.color.red));
                            } else if (password.length() == confirmPassword.length() && !password.matches(confirmPassword)) {
                                mConfirmPasswordEditText.setTextColor(getResources().getColor(R.color.red));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSignWithGoogle() {

        try {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.web_client_id))
                    .requestEmail()
                    .build();
            Log.i(TAG, "startSignWithGoogle: got gso " + gso);
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            Log.i(TAG, "startSignWithGoogle: got google sign in client" + mGoogleSignInClient);
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            Log.i(TAG, "startSignWithGoogle: start make an intent");
            startActivityForResult(signInIntent, RC_SIGN_IN);
            Log.i(TAG, "startSignWithGoogle: now start activity for result");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSignInWithFB() {
        try {
            mCallbackManager = CallbackManager.Factory.create();
            Log.i(TAG, "startSignInWithFB: init call back manager");
            LoginManager.getInstance().logInWithReadPermissions(SignInActivity.this, Arrays.asList("email", "public_profile"));
            Log.i(TAG, "startSignInWithFB: setting permissions to LoginManager  like email and profile");
            LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    try {
                        Log.i(TAG, "register callback  facebook:onSuccess:" + loginResult);
                        handleAccessTokenFB(loginResult.getAccessToken());
                        Log.i(TAG, "register callback onSuccess: calling method handleAccessTokenFB with a null GoogleSignInAccount");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancel() {
                    Log.d(TAG, "facebook:onCancel");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.i(TAG, "facebook:onError", error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSignInWithTwitter() {
        try {
            OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
            Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
            if (pendingResultTask != null) {
                // There's something already here! Finish the sign-in for your user.
                pendingResultTask.addOnSuccessListener(
                        authResult -> {
                            // User is signed in.
                            // IdP data available in
                            // authResult.getAdditionalUserInfo().getProfile().
                            // The OAuth access token can also be retrieved:
                            // authResult.getCredential().getAccessToken().
                            // The OAuth secret can be retrieved by calling:
                            // authResult.getCredential().getSecret().
                        })
                        .addOnFailureListener(
                                e -> {
                                    // Handle failure.
                                    Toast.makeText(SignInActivity.this, "Twitter Authentication has failed", Toast.LENGTH_SHORT).show();
                                });
            } else {
                // There's no pending result so you need to start the sign-in flow.
                // See below.
                mAuth.startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                        .addOnSuccessListener(
                                new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        // User is signed in.
                                        // IdP data available in
                                        // authResult.getAdditionalUserInfo().getProfile().
                                        // The OAuth access token can also be retrieved:
                                        // authResult.getCredential().getAccessToken().
                                        // The OAuth secret can be retrieved by calling:
                                        // authResult.getCredential().getSecret().
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle failure.
                                        Toast.makeText(SignInActivity.this, "Twitter Authentication has failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAccessTokenFB(AccessToken token) {
        try {
            Log.i(TAG, "handleAccessTokenFB: acct = " + token);
            AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
            Log.i(TAG, "handleAccessTokenFB: getting credential for Facebook login" + credential);
            getCredentialWithFB(credential);
            Log.i(TAG, "handleAccessTokenFB: calling getCredential ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAccessTokenGoogle(GoogleSignInAccount acct) {
        try {
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            Log.i(TAG, "handleAccessTokenFB: getting credential for google login" + credential);
            getCredentialWithGoogle(credential);
            Log.i(TAG, "handleAccessTokenFB: calling getCredential ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCredentialWithFB(AuthCredential credential) {
        try {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, task -> {
                        try {
                            if (task.isSuccessful()) {
                                Log.i(TAG, "signInWithCredential:success");
                                startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                Log.i(TAG, "signInWithCredential:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCredentialWithGoogle(AuthCredential credential) {
        try {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, task -> {
                        try {
                            if (task.isSuccessful()) {
                                Log.i(TAG, "signInWithCredential:success");
                                String m = "message";
                                Intent i = new  Intent(SignInActivity.this, HomeActivity.class);
                                i.putExtra("message", m);
                                startActivity(i);
                                finish();
                            } else {
                                Log.i(TAG, "signInWithCredential:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RC_SIGN_IN) {
                GoogleSignIn.getSignedInAccountFromIntent(data).addOnSuccessListener(googleSignInAccount ->
                        Log.i(TAG, "GoogleSignIn onSuccess(): is called"))
                        .addOnCompleteListener(task1 -> {
                            try {
                                Log.i(TAG, "GoogleSignIn onComplete(): is called");
                                if (task1.isComplete()) {
                                    Log.i(TAG, "GoogleSignIn onComplete(): task is completed");

                                } else if (task1.isSuccessful()) {
                                    Log.i(TAG, "GoogleSignIn onComplete(): task is successful");

                                } else if (task1.isCanceled()) {
                                    Log.i(TAG, "GoogleSignIn onComplete(): task is canceled");
                                }
                                GoogleSignInAccount account = task1.getResult(ApiException.class);
                                if (account != null) {
                                    handleAccessTokenGoogle(account);
                                    Log.i(TAG, "onActivityResult: calling handleAccessTokenFB for google sign in " + account);
                                } else {
                                    Toast.makeText(this, "account is null", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).
                        addOnFailureListener(e -> {
                            Log.i(TAG, "GoogleSignIn onFailure(): is called");
                            e.printStackTrace();
                        });

            }
        } catch (Exception e) {
            // Google Sign In failed, update UI appropriately
            e.printStackTrace();
            Log.w(TAG, "Google sign in failed", e);
            Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
        }
        if (mCallbackManager != null) {
            boolean b = mCallbackManager.onActivityResult(requestCode, resultCode, data);
            Log.i(TAG, "onActivityResult: calling mCallBackManager " + b);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        try {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.log_in_button:
                startNormalLogin();
                break;
            case R.id.txt_sign_up:
                handler.postDelayed(runnableLogIn, 0);
                break;
            case R.id.tv_back:
                handler.postDelayed(runnableSignUp, 0);
                break;
            case R.id.sign_google_button:
                startSignWithGoogle();
                break;
            case R.id.fb_login_button:
                startSignInWithFB();
                break;
            case R.id.normal_sign_button:
                startNormalSignUp();
                break;
            case R.id.twitter_login_button:
                startSignInWithTwitter();
                break;
        }
    }

    private void startNormalLogin() {
        try {
            if (mEmailLoginET.getText() == null || mEmailLoginET.getText().toString().isEmpty()) {
                mEmailLoginET.setError(getString(R.string.required));
            } else if (mPasswordLoginET.getText() == null || mPasswordLoginET.getText().toString().isEmpty()) {
                mPasswordLoginET.setError(getString(R.string.required));
            } else {
                MaterialDialog mProgressDlg = AppUtils.showProgressDialog(this, getString(R.string.loading));

                String email = mEmailLoginET.getText().toString().trim();
                String password = mPasswordLoginET.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (mProgressDlg != null)
                                    mProgressDlg.dismiss();

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startNormalSignUp() {
        try {

            if (mFirstNameLastNameEditText.getText() == null || mFirstNameLastNameEditText.getText().toString().isEmpty()) {
                mFirstNameLastNameEditText.setError(getString(R.string.required));
            } else if (mEmailEditText.getText() == null || mEmailEditText.getText().toString().isEmpty()) {
                mEmailEditText.setError(getString(R.string.required));
            } else if (mPasswordEditText.getText() == null || mPasswordEditText.getText().toString().isEmpty()) {
                mPasswordEditText.setError(getString(R.string.required));
            } else if (mConfirmPasswordEditText.getText() == null || mConfirmPasswordEditText.getText().toString().isEmpty()) {
                mConfirmPasswordEditText.setError(getString(R.string.required));
            } else {
                MaterialDialog mProgressDlg = AppUtils.showProgressDialog(this, getString(R.string.loading));

                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (mProgressDlg != null)
                                    mProgressDlg.dismiss();

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI(FirebaseUser user) {

        try {
            if (user != null) {
                //TODO save user data into shared preferences
                String m = "message";
                Intent i = new  Intent(SignInActivity.this, HomeActivity.class);
                i.putExtra("message", m);
                startActivity(i);

                // Start home activity
                startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
