package com.example.expenseadmin.view.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expenseadmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    @BindView(R.id.user_profile_photo)
    ImageView profileImage;
    @BindView(R.id.user_profile_name)
    TextView profileName;
    @BindView(R.id.user_profile_email)
    TextView profileMail;
    @BindView(R.id.profile_phone_number)
    TextView profilePhoneNumber;
    @BindView(R.id.profile_country)
    TextView profileCountry;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        try {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            String name = null;
            String email = null;
            String gender = null;
            if (user != null) {
                name = user.getDisplayName();
                email = user.getEmail();
                gender = user.getPhoneNumber();
                profileName.setText(name);
                profileMail.setText(email);

            } else {
                profileName.setText("");
                profileMail.setText("");
            }
            if (user != null && user.getPhotoUrl() != null)
                Picasso.get().load(user.getPhotoUrl().toString()).into(profileImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        profileImage.setImageResource(R.drawable.restaurant);
    }
}
