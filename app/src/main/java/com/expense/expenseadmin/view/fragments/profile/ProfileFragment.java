package com.expense.expenseadmin.view.fragments.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.expense.expenseadmin.R;
import com.expense.expenseadmin.view.activities.signInUp.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.expense.expenseadmin.Utilities.PlaceColumns.phoneNumber;

public class ProfileFragment extends Fragment{
    private static final String TAG = "ProfileFragment";

    @BindView(R.id.user_profile_photo)
    ImageView userProfileImage;
    @BindView(R.id.account_name)
    TextView userProfileName;
    @BindView(R.id.mail)
    TextView userEmail;
    @BindView(R.id.phone_number)
    TextView userPhoneNumber;
    @BindView(R.id.country_text)
    TextView userCountry;
    @BindView(R.id.change_photo)
    CircleImageView changePhoto;
    @BindView(R.id.profile_content_layout)
    ConstraintLayout profileContentLayout;

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

            if (user != null) {
                String name = user.getDisplayName();
                if(name != null) userProfileName.setText(name);
                else userProfileName.setText("");

                String email = user.getEmail();
                if(email != null) userEmail.setText(email);
                else userEmail.setText("");

                String phoneNumber = user.getPhoneNumber();
                if(phoneNumber != null) userPhoneNumber.setText(phoneNumber);
                else userPhoneNumber.setText("");

                Uri photoUrl = user.getPhotoUrl();
                if(photoUrl != null) Picasso.get().load(photoUrl.toString()).into(userProfileImage);
                else Picasso.get().load(R.drawable.empty_profile_image).into(userProfileImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}