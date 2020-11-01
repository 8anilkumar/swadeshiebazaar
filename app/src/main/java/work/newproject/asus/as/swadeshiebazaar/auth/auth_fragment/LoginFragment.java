package work.newproject.asus.as.swadeshiebazaar.auth.auth_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import work.newproject.asus.as.swadeshiebazaar.R;

public class LoginFragment extends Fragment {


    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.imgLogo)
    ImageView imgLogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        Glide.with(getActivity()).load(R.drawable.s_logo).into(imgLogo);

        openSignINFragment();


        tabs.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    openSignINFragment();
                } else if (tab.getPosition() == 1) {
                    openSignUpFragment();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //        tabs.setBackground(getResources().getC);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return view;
    }


    private void openSignINFragment() {
        Bundle args = new Bundle();
        Fragment fragmentt = new SignInFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragmentDAta, fragmentt);
        transaction.commit();
    }

    private void openSignUpFragment() {
        Bundle args = new Bundle();
        Fragment fragmentt = new SignUpFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragmentDAta, fragmentt);
        transaction.commit();
    }
}