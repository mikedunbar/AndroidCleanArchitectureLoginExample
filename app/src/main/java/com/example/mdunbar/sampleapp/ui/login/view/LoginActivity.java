package com.example.mdunbar.sampleapp.ui.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mdunbar.sampleapp.ui.landing.LandingPageActivity;
import com.example.mdunbar.sampleapp.SampleApplication;
import com.example.mdunbar.sampleapp.R;
import com.example.mdunbar.sampleapp.ui.login.presenter.LoginPresenter;

import javax.inject.Inject;

/**
 * An email/password login screen.
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    @Inject
    LoginPresenter loginPresenter;

    private EditText emailView;
    private EditText passwordView;
    private View progressView;
    private View formView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((SampleApplication)getApplication()).getLoginComponent().inject(this);

        setContentView(R.layout.activity_login);

        emailView = (EditText) findViewById(R.id.email);

        passwordView = (EditText) findViewById(R.id.password);
        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button signInButton = (Button) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button bypassButton = (Button) findViewById(R.id.bypass_login_button);
        bypassButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bypassLogin();
            }
        });

        formView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);
    }

    @Override
    public void onStart() {
        super.onStart();
        loginPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        loginPresenter.detachView();
    }

    private void attemptLogin() {
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        loginPresenter.doLogin(email, password);
    }

    private void bypassLogin() {
        loginPresenter.bypassLogin();
    }

   /******** LoginView Methods ***/
   @Override
   public void setEmail(String email) {
       emailView.setText(email);
   }

    @Override
    public void setPassword(String password) {
        passwordView.setText(password);
    }

    @Override
    public void showEmailRequiredError() {
        emailView.setError(getString(R.string.error_field_required));
        emailView.requestFocus();
    }

    @Override
    public void showPasswordRequiredError() {
        passwordView.setError(getString(R.string.error_field_required));
        passwordView.requestFocus();
    }

    @Override
    public void showPasswordInvalidError() {
        passwordView.setError(getString(R.string.error_invalid_password));
        passwordView.requestFocus();
    }

    @Override
    public void showEmailInvalidError() {
        emailView.setError(getString(R.string.error_invalid_email));
        emailView.requestFocus();
    }

    @Override
    public void navigateToLandingPage() {
        formView.setVisibility(View.GONE);
        progressView.setVisibility(View.GONE);

        Intent landingPageIntent = new Intent(this, LandingPageActivity.class);
        startActivity(landingPageIntent);
    }

    @Override
    public void showNetworkError() {
        passwordView.setError(getString(R.string.error_network));
        passwordView.requestFocus();
    }

    @Override
    public void showValidationError() {
        passwordView.setError(getString(R.string.error_incorrect_password));
        passwordView.requestFocus();
    }

    @Override
    public void showProgress() {
        progressView.setVisibility(View.VISIBLE);
        formView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressView.setVisibility(View.GONE);
        formView.setVisibility(View.VISIBLE);
    }

}

