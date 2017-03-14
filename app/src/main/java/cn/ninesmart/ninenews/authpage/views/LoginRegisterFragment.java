package cn.ninesmart.ninenews.authpage.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.authpage.contracts.LoginRegisterContract;

public class LoginRegisterFragment extends Fragment implements LoginRegisterContract.View, View.OnClickListener {
    private LoginRegisterContract.Presenter mPresenter;
    private OnFragmentInteractionListener mListener;
    private boolean mIsLogin;

    private TextInputEditText mEmailEdit;
    private TextInputEditText mPasswordEdit;
    private TextInputLayout mConfirmPasswordEditLayout;
    private TextInputEditText mConfirmPasswordEdit;
    private TextInputLayout mNicknameEditLayout;
    private TextInputEditText mNicknameEdit;
    private Button mPrimaryButton;
    private Button mSecondaryButton;

    public LoginRegisterFragment() {
        // Required empty public constructor
    }

    public static LoginRegisterFragment newInstance() {
        return new LoginRegisterFragment();
    }

    private void switchLoginRegister(boolean isLogin) {
        mIsLogin = isLogin;
        mConfirmPasswordEditLayout.setVisibility(mIsLogin ? View.GONE : View.VISIBLE);
        mNicknameEditLayout.setVisibility(mIsLogin ? View.GONE : View.VISIBLE);
        mPrimaryButton.setText(mIsLogin ? R.string.login : R.string.register);
        mSecondaryButton.setText(mIsLogin ? R.string.register : R.string.login);
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
        return inflater.inflate(R.layout.fragment_login_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmailEdit = (TextInputEditText) view.findViewById(R.id.email_edit);
        mPasswordEdit = (TextInputEditText) view.findViewById(R.id.password_edit);
        mConfirmPasswordEditLayout = (TextInputLayout) view.findViewById(R.id.confirm_password_edit_layout);
        mConfirmPasswordEdit = (TextInputEditText) view.findViewById(R.id.confirm_password_edit);
        mNicknameEditLayout = (TextInputLayout) view.findViewById(R.id.nickname_edit_layout);
        mNicknameEdit = (TextInputEditText) view.findViewById(R.id.nickname_edit);
        mPrimaryButton = (Button) view.findViewById(R.id.primary_button);
        mPrimaryButton.setOnClickListener(this);
        mSecondaryButton = (Button) view.findViewById(R.id.secondary_button);
        mSecondaryButton.setOnClickListener(this);

        switchLoginRegister(true);
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
    public void setPresenter(LoginRegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(int code) {
    }

    @Override
    public void showLoading(boolean isLoading, int target) {
    }

    @Override
    public void finishLogin() {
        Toast.makeText(getContext(), R.string.login_successfully, Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void finishRegister() {
        Toast.makeText(getContext(), R.string.register_successfully, Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.primary_button:
                if (mIsLogin) {
                    mPresenter.login(mEmailEdit.getText().toString(),
                            mPasswordEdit.getText().toString());
                } else {
                    mPresenter.register(mEmailEdit.getText().toString(),
                            mPasswordEdit.getText().toString(),
                            mConfirmPasswordEdit.getText().toString(),
                            mNicknameEdit.getText().toString());
                }
                break;
            case R.id.secondary_button:
                switchLoginRegister(!mIsLogin);
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
