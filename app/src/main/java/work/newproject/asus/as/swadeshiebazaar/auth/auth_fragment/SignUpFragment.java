package work.newproject.asus.as.swadeshiebazaar.auth.auth_fragment;

import android.content.Intent;
import android.os.Bundle;

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

import com.basgeekball.awesomevalidation.AwesomeValidation;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.TeramsAndCondidionActivity;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.SignUpModel;


public class SignUpFragment extends Fragment {

    @BindView(R.id.edName)
    EditText edName;

    @BindView(R.id.edNumber)
    EditText edNumber;

    @BindView(R.id.edMail)
    EditText edMail;

    @BindView(R.id.edPassword)
    EditText edPassword;


    @BindView(R.id.btSinUp)
    Button btSinUp;

    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;

    @BindView(R.id.txtTerms)
    TextView txtTerms;

    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);
    private AwesomeValidation mAwesomeValidation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);


        btSinUp.setOnClickListener(v -> signUp());
        txtTerms.setOnClickListener(v -> tems());
        return view;
    }

    private void signUp() {
        String name = edName.getText().toString().trim();
        String mob = edNumber.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String mail = edMail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (mob.isEmpty()) {
            edNumber.setError("Enter mobile number");
        } else if (mob.length() < 10) {
            edNumber.setError("Enter a valid mobile number");
        } else if (password.isEmpty()) {
            edPassword.setError("Enter password");
        } else if (password.length() < 6) {
            edPassword.setError("Password length should be at least 6 character long");
        } else if (mail.isEmpty()) {
            edMail.setError("Enter email");
        } else if (!mail.matches(emailPattern)) {
            edMail.setError("Enter valid email address");
        } else {
            showProgress();
            api.userSignUp(name, mail, mob, password).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<SignUpModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }
                        @Override
                        public void onSuccess(@NonNull SignUpModel signUpModel) {
                            hideProgress();
                            if (signUpModel.getStatus().equalsIgnoreCase("success"))
                                openOtp(edNumber.getText().toString().trim(), edName.getText().toString().trim(), edMail.getText().toString().trim(), edPassword.getText().toString().trim());
                                Toast.makeText(getContext(), signUpModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(@NonNull Throwable e) {
                            hideProgress();
                            Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
          }
    }

    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
    }


    private void openOtp(String number, String name, String mail, String password) {
        Bundle args = new Bundle();
        Fragment fragmentt = new OtpFragment();
        args.putString("number", number);
        args.putString("name", name);
        args.putString("mail", mail);
        args.putString("password", password);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.addToBackStack("otp").commit();

    }

    private void tems(){
        Intent intent = new Intent(getContext(), TeramsAndCondidionActivity.class);
        startActivity(intent);
    }
}