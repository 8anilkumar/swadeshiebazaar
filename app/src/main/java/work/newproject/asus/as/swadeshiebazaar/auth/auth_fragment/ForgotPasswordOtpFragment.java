package work.newproject.asus.as.swadeshiebazaar.auth.auth_fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.MainActivity;
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ForgortPasswordModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.OtpVerifyModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.UpdatePasswordModel;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class ForgotPasswordOtpFragment extends Fragment {


    @BindView(R.id.imgLogo)
    ImageView imgLogo;

    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);
    @BindView(R.id.etOtp)
    EditText etOtp;

    @BindView(R.id.fabNext)
    FloatingActionButton fabNext;

    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;

    @BindView(R.id.tvResendCode)
    TextView tvResendCode;

    private AlertDialog alertDialog;
    String number;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forgot_password_otp, container, false);
        ButterKnife.bind(this,view);
        Glide.with(getActivity()).load(R.drawable.s_logo).into(imgLogo);
        number = getArguments().getString("number");


        fabNext.setOnClickListener(v -> sendOtp());

        tvResendCode.setOnClickListener(v -> forgotPassword());
        return view;
    }
    private void forgotPassword() {
        showProgress();
        api.forhroPassword(number).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ForgortPasswordModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ForgortPasswordModel forgortPasswordModel) {
                        Toast.makeText(getContext(), forgortPasswordModel.getMessage(), Toast.LENGTH_SHORT).show();
                        hideProgress();
                        if (forgortPasswordModel.getStatus().equals("success")) {
                        }


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideProgress();
                    }
                });



    }
    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
    }

    private void goDashBoad() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void sendOtp() {
        if (etOtp.getText().toString().trim().isEmpty()) {
            etOtp.setError("Empty");
        } else {

            showProgress();
            api.otpVerfy(number, etOtp.getText().toString().trim())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<OtpVerifyModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull OtpVerifyModel otpVerifyModel) {
                            Toast.makeText(getContext(), otpVerifyModel.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                            if (otpVerifyModel.getStatus().equals("success")) {
                                chengPassword(otpVerifyModel.getData().get(0).getUserId(),otpVerifyModel.getData().get(0).getMobile());

                            }


                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            hideProgress();
                            Toast.makeText(getContext(), "Wrong OTP", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }

    private void chengPassword(String id ,String number) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View vieww = getLayoutInflater().inflate(R.layout.comfrim_password, null);
        EditText edOldPasswordd = vieww.findViewById(R.id.edOldPassword);
        EditText edNewPassword = vieww.findViewById(R.id.edNewPassword);
        Button btChange = vieww.findViewById(R.id.btChange);

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edOldPasswordd.getText().toString().trim().isEmpty()) {
                    edOldPasswordd.setError("Empty");
                }else if (edOldPasswordd.getText().toString().trim().length() < 6) {
                        edOldPasswordd.setError("Password length should be at least 6 character long");
                } else if (edNewPassword.getText().toString().trim().isEmpty()) {
                    edNewPassword.setError("Empty");
                }else if (edNewPassword.getText().toString().trim().length() < 6) {
                    edNewPassword.setError("Password length should be at least 6 character long");
                } else if (!edOldPasswordd.getText().toString().trim().equals(edNewPassword.getText().toString().trim())){
                    edNewPassword.setError("Password Not Match");
                }else {
                    chengPasswordApi(edNewPassword.getText().toString().trim(),id,number);
                }

            }
        });

        builder.setView(vieww);
        alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    private void chengPasswordApi(String trim, String id, String number) {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please Wait");
        pd.show();
        api.updatePassword(number,trim).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<UpdatePasswordModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull UpdatePasswordModel updatePasswordModel) {
                        pd.dismiss();
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), updatePasswordModel.getMessage(), Toast.LENGTH_SHORT).show();
                        if (updatePasswordModel.getStatus().equals("success")){
                            MySharedpreferences.getInstance().save(getContext(), AppStrings.userID, id);
                            goDashBoad();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        pd.dismiss();
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}