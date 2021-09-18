package com.li.feeling.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.li.feeling.R;

import com.li.feeling.login.api.LoginApiService;
import com.li.feeling.register.RegisterActivity;
import com.li.fragment.base_page.fragment.BaseFragment;
import com.li.framework.netwoek.retrofit_utility.BaseRetrofitConfig;
import com.li.framework.netwoek.retrofit_utility.IRetrofitConfig;
import com.li.framework.netwoek.retrofit_utility.RetrofitManager;
import com.li.framework.ui.utility.DuplicatedClickFilter;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Scheduler;


/**
 * description: 登陆页面
 */
public class LoginFragment extends BaseFragment {

  private EditText mPhoneView;
  private EditText mPasswordView;

  // 登陆按钮
  private Button mLoginButton;

  // 注册
  private View mRegisterView;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_login_layout, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView(view);
  }

  private void initView(@NonNull View rootView) {
    mPhoneView = rootView.findViewById(R.id.fragment_login_page_phone_editText);
    mPasswordView = rootView.findViewById(R.id.fragment_login_page_password_editView);
    mLoginButton = rootView.findViewById(R.id.fragment_login_pager_login_button);
    mRegisterView = rootView.findViewById(R.id.fragment_login_register_view);

    mLoginButton.setOnClickListener(new DuplicatedClickFilter() {

      @Override
      protected void handleClickEvent() {
        doLogin();
      }
    });
    mRegisterView.setOnClickListener(new DuplicatedClickFilter() {
      @Override
      protected void handleClickEvent() {
        jumpToRegisterPager();
      }
    });
  }

  private void doLogin() {
//
//    Domin{
//
//      Live("http://asd"),
//    sco..()
//
//    }
//
//    FeelingRetrofitConfig = new FeelingRetrofitConfig(Domin.live,Scheduler);
//
//    IRetrofitConfig retrofitConfig = new BaseRetrofitConfig() {
//      @NonNull
//      @Override
//      public String getBaseUrl() {
//        return "http://124/:8080";
//      }
//
//      @Override
//      public Scheduler getSubscribeScheduler() {
//        return null;
//      }
//    };
//    RetrofitManager.getInstance().create(FeelingRetrofitConfig,LoginApiService.class)
//        .login("","")
//        .enqueue(new );

  }

  private void jumpToRegisterPager() {
    Activity activity = getActivity();
    if (activity == null) {
      return;
    }
    RegisterActivity.start(activity);
  }

  public class User {

    public String mAccount;
    public String mPassword;
    public String mNickName;

  }

}
