package cn.ninesmart.ninenews.profile.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.data.users.models.UserModel;
import cn.ninesmart.ninenews.profile.contracts.ProfileContract;

public class ProfileFragment extends Fragment implements ProfileContract.View {
    private OnFragmentInteractionListener mListener;
    private ProfileContract.Presenter mPresenter;

    private TextView mEmailText;
    private ImageView mAvatarImage;
    private TextView mNicknameText;
    private Button mSignOutButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmailText = (TextView) view.findViewById(R.id.email_text);
        mAvatarImage = (ImageView) view.findViewById(R.id.avatar_image);
        mNicknameText = (TextView) view.findViewById(R.id.nickname_text);
        mSignOutButton = (Button) view.findViewById(R.id.sign_out_button);
        mSignOutButton.setOnClickListener(v -> mPresenter.signOut());
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.reloadProfile();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(int code) {
    }

    @Override
    public void refreshProfile(UserModel userModel) {
        mEmailText.setText(userModel.getEmail());
        Picasso.with(getContext()).load(userModel.getAvatarThumbSrc()).into(mAvatarImage);
        mNicknameText.setText(userModel.getNickname());
    }

    @Override
    public void finishSigningOut() {
        Toast.makeText(getContext(), R.string.sign_out_successfully, Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    public interface OnFragmentInteractionListener {
    }
}
