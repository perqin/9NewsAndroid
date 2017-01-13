package cn.ninesmart.ninenews.authpage.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.authpage.contracts.LoginRegisterContract;

public class LoginRegisterFragment extends Fragment implements LoginRegisterContract.View {
    private LoginRegisterContract.Presenter mPresenter;
    private OnFragmentInteractionListener mListener;

    public LoginRegisterFragment() {
        // Required empty public constructor
    }

    public static LoginRegisterFragment newInstance() {
        return new LoginRegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_register, container, false);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setPresenter(LoginRegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(int code) {
    }

    public interface OnFragmentInteractionListener {
    }
}
