package work.newproject.asus.as.swadeshiebazaar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import work.newproject.asus.as.swadeshiebazaar.R;

public class WalletHistoryFragment extends Fragment {

     @BindView(R.id.imgBack)
     ImageView imgBack;

     @BindView(R.id.textView2)
     TextView textView;

     String txtWallet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wallet_history, container, false);
        ButterKnife.bind(this,view);
        txtWallet = getArguments().getString("userMoney");
        textView.setText(txtWallet);
        imgBack.setOnClickListener(v -> getActivity().onBackPressed());
        return view;
    }
}