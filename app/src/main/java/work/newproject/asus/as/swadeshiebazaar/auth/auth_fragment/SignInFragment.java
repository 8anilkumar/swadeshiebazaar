package work.newproject.asus.as.swadeshiebazaar.auth.auth_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.MainActivity;
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.user_login;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;


public class SignInFragment extends Fragment {

    @BindView(R.id.txtForgtPassword)
    TextView txtForgtPassword;

    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);

    @BindView(R.id.edNumber)
    EditText edNumber;

    @BindView(R.id.edPassword)
    EditText edPassword;

    @BindView(R.id.btSignIn)
    Button btSignIn;


    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;


    @BindView(R.id.btLoginAsGuest)
    Button btLoginAsGuest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, view);
        btLoginAsGuest.setOnClickListener(v -> goDashBoard());
        txtForgtPassword.setOnClickListener(v -> forgotPassword());
        return view;
    }

    private void forgotPassword() {
        Bundle args = new Bundle();
        Fragment fragmentt = new ForgetPasswordFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.addToBackStack("reg").commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation(edNumber.getText().toString(), edPassword.getText().toString());
            }
        });
    }

    private void checkValidation(String mobile, String password) {
        if (mobile.isEmpty()) {
            edNumber.setError("Enter mobile number");
        } else if (mobile.length() < 10) {
            edNumber.setError("Enter a valid mobile number");
        } else if (password.isEmpty()) {
            edPassword.setError("Enter password");
        } else if (edPassword.length() < 6) {
            edPassword.setError("Password length should be at least 6 character long");
        } else {
            //  startActivity(new Intent(getContext(), MainActivity.class));
            showProgress();
            api.userLogin(mobile, password).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<user_login>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }
                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull user_login user_login) {
                            hideProgress();
                            if (user_login.getStatus().equalsIgnoreCase("success")) {
                                MySharedpreferences.getInstance().save(getContext(), AppStrings.userID, user_login.getData().get(0).getUserId());
                                goDashBoard();
                            } else {
                                Toast.makeText(getContext(), "login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            hideProgress();
                            Toast.makeText(getContext(), "Wrong Password", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }

    private void goDashBoard() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
    }


}