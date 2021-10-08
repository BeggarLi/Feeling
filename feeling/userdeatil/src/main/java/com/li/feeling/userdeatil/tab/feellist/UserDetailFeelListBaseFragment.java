package com.li.feeling.userdeatil.tab.feellist;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.li.feeling.userdeatil.R;
import com.li.feeling.userdeatil.tab.adapter.UserDetailFeelListBaseRecyclerAdapter;
import com.li.fragment.base_page.fragment.BaseFragment;

/**
 * description: 用户详情页面的feel列表，目前有两种列表
 * 1. 用户自己发布的feel列表
 * 2. 用户点赞的feel列表
 */
public abstract class UserDetailFeelListBaseFragment extends BaseFragment {

  protected RecyclerView mRecyclerView;
  protected UserDetailFeelListBaseRecyclerAdapter mRecyclerAdapter;

  @Override
  public void onViewCreated(
      @NonNull View view,
      @Nullable Bundle savedInstanceState,
      boolean isFirstCall) {
    if (isFirstCall) {
      initView(view);
      refreshFeelList();
    }
  }

  @CallSuper
  protected void initView(@NonNull View rootView){
    mRecyclerView = rootView.findViewById(R.id.recycler_view);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerAdapter = createRecyclerAdapter();
    mRecyclerView.setAdapter(mRecyclerAdapter);
  }

  // 子类实现自己的adapter
  @NonNull
  protected abstract UserDetailFeelListBaseRecyclerAdapter createRecyclerAdapter();

  protected abstract void refreshFeelList();

}
