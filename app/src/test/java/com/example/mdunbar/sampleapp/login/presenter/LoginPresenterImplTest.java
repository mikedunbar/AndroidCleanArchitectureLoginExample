package com.example.mdunbar.sampleapp.login.presenter;

import com.example.mdunbar.sampleapp.login.view.LoginView;
import com.example.mdunbar.sampleapp.model.LoginUseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterImplTest {
    @Mock private LoginView loginView;
    @Mock private LoginUseCase loginUseCase;
    private LoginPresenterImpl loginPresenter;
    private String INVALID_EMAIL = "tt_gmail.com"; // No @
    private String INVALID_PASSWORD = "abcd"; // < 5 characters
    private String VALID_EMAIL = "tt@gmail.com";
    private String VALID_PASSWORD = "abcde";

    @Before
    public void setUp() {
        loginPresenter = new LoginPresenterImpl(loginUseCase);
        loginPresenter.attachView(loginView);

        // Discard interactions with loginView during setup, so they don't throw off the counts for other methods under test
        // The test code is way more readable that way - simply verify(loginView), compared to compensating with verify(loginView, times(n))
        // Test methods for attachView just re-call it explicitly and we count from THAT point on
        reset(loginView);
    }

    @Test
    public void testAttachViewSetsViewEmailAndPassword() {
        loginPresenter.doLogin(VALID_EMAIL, VALID_PASSWORD);
        loginPresenter.attachView(loginView);
        verify(loginView).setEmail(VALID_EMAIL);
        verify(loginView).setPassword(VALID_PASSWORD);
    }

    @Test
    public void testAttachViewWithLoginNotStartedHidesProgressOnView() {
        loginPresenter.attachView(loginView);
        verify(loginView).hideProgress();
    }

    @Test
    public void testAttachViewWithLoginStartedAndNotCompletedShowsProgressOnView() {
        loginPresenter.loginStarted = true;
        loginPresenter.attachView(loginView);
        verify(loginView).showProgress();
    }

    @Test
    public void testAttachViewWithLoginStartedAndCompletedSuccessfullyNavigatesToLandingPage() {
        loginPresenter.loginStarted = true;
        loginPresenter.loginResult = LoginUseCase.Result.SUCCESS;
        loginPresenter.attachView(loginView);
        verify(loginView).navigateToLandingPage();
    }

    @Test
    public void testAttachViewWithLoginStartedAndCompletedWithNetworkErrorHidesProgressViewAndShowsNetworkErrorOnView() {
        loginPresenter.loginStarted = true;
        loginPresenter.loginResult = LoginUseCase.Result.NETWORK_ERROR;
        loginPresenter.attachView(loginView);
        verify(loginView).hideProgress();
        verify(loginView).showNetworkError();
    }

    @Test
    public void testAttachViewWithLoginStartedAndCompletedWithValidationErrorHidesProgressViewAndShowsValidationErrorOnView() {
        loginPresenter.loginStarted = true;
        loginPresenter.loginResult = LoginUseCase.Result.VALIDATION_ERROR;
        loginPresenter.attachView(loginView);
        verify(loginView).hideProgress();
        verify(loginView).showValidationError();
    }

    @Test
    public void testDoLoginWithNullEmailShowsEmailRequiredErrorOnView() {
        loginPresenter.doLogin(null, "password");
        verify(loginView).showEmailRequiredError();
    }

    @Test
    public void testDoLoginWithNullPasswordShowsPasswordRequiredErrorOnView() {
        loginPresenter.doLogin("email", null);
        verify(loginView).showPasswordRequiredError();
    }

    @Test
    public void testDoLoginWithInvalidEmailShowsInvalidEmailErrorOnView() {
        loginPresenter.doLogin(INVALID_EMAIL, VALID_PASSWORD);
        verify(loginView).showEmailInvalidError();
    }

    @Test
    public void testDoLoginWithInvalidPasswordShowsInvalidPasswordErrorOnView() {
        loginPresenter.doLogin(VALID_EMAIL, INVALID_PASSWORD);
        verify(loginView).showPasswordInvalidError();
    }

    @Test
    public void testDoLoginWithValidEmailAndPasswordDelegatesToUseCaseAndShowsProgressOnView() {
        loginPresenter.doLogin(VALID_EMAIL, VALID_PASSWORD);
        verify(loginUseCase).doLogin(VALID_EMAIL, VALID_PASSWORD, loginPresenter);
        verify(loginView).showProgress();
    }

    @Test
    public void testOnLoginSuccessNavigatesToLandingPageOnSuccess() {
        loginPresenter.onLoginSuccess();
        verify(loginView).navigateToLandingPage();
    }

    @Test
    public void testOnNetworkErrorHidesProgressAndShowsNetworkErrorOnView() {
        loginPresenter.onNetworkError();
        verify(loginView).hideProgress();
        verify(loginView).showNetworkError();
    }

    @Test
    public void testOnValidationErrorHidesProgressAndShowsNetworkErrorOnView() {
        loginPresenter.onValidationError();
        verify(loginView).hideProgress(); //
        verify(loginView).showValidationError();
    }
}
