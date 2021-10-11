package com.li.feeling.feellist;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.li.feeling.feellist.adapter.HomeFeelListRecyclerAdapter;
import com.li.feeling.feellist.service.HomeFeelListResponse;
import com.li.feeling.feellist.service.IHomeFeelListApiService;
import com.li.feeling.feellist.viewdata.HomeFeelingListFeelItemViewData;
import com.li.feeling.feellist.viewdata.HomeFeelingListFooterItemViewData;
import com.li.feeling.home.R;
import com.li.feeling.model.Feel;
import com.li.feeling.model.FeelPublishSuccessEvent;
import com.li.feeling.publish.PublishFeelActivity;
import com.li.fragment.base_page.fragment.BaseFragment;
import com.li.framework.common_util.RxUtil;
import com.li.framework.common_util.ToastUtil;
import com.li.framework.network.FeelingException;
import com.li.framework.network.FeelingResponseTransformer;
import com.li.framework.scheduler_utility.SchedulerManager;
import com.li.framework.ui.utility.DuplicatedClickFilter;
import com.li.library.recycler.LiRecyclerItemViewData;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * home页面的feel列表Fragment
 */
public class HomeFeelListFragment extends BaseFragment {

  private SwipeRefreshLayout mRefreshLayout;
  private RecyclerView mRecyclerView;
  private HomeFeelListRecyclerAdapter mFeelListAdapter;
  private Button mAddFeelView;

  @Nullable
  private Disposable mFeelListDisposable;

  @NonNull
  private SwipeRefreshLayout.OnRefreshListener mRefreshListener =
      new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
          refreshFeelList();
        }
      };

  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_home_feel_list_layout;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.getDefault().register(this);
  }

  @Override
  public void onViewCreated(
      @NonNull View view,
      @Nullable Bundle savedInstanceState,
      boolean isFirstCall) {
    // 刷新列表数据
    if (isFirstCall) {
      initView(view);
      refreshFeelList();
    }
  }

  private void initView(@NonNull View view) {
    mRefreshLayout = view.findViewById(R.id.home_feel_list_refresh_list_view);
    mRefreshLayout.setOnRefreshListener(mRefreshListener);

    mRecyclerView = view.findViewById(R.id.home_feel_list_recycler_view);
    mAddFeelView = view.findViewById(R.id.home_feel_list_add_feel_view);

    mAddFeelView.setOnClickListener(new DuplicatedClickFilter() {
      @Override
      protected void handleClickEvent() {
        Activity activity = getActivity();
        if (activity != null) {
          PublishFeelActivity.start(activity);
        }
      }
    });

    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mFeelListAdapter = new HomeFeelListRecyclerAdapter(getContext());
    mRecyclerView.setAdapter(mFeelListAdapter);
  }

  // 刷新feel列表:网络请求
  @NonNull
  private void refreshFeelList() {
    mFeelListDisposable = IHomeFeelListApiService.get()
        .getFeelListData()
        .observeOn(SchedulerManager.MAIN)
        .map(FeelingResponseTransformer.transform())
        .doFinally(new Action() {
          @Override
          public void run() throws Exception {
            // 请求结束的时候，恢复
            mRefreshLayout.setRefreshing(false);
          }
        })
        .subscribe(
            new Consumer<HomeFeelListResponse>() {
              @Override
              public void accept(HomeFeelListResponse response) {
                onFeelListDataChanged(response.mFeelList, response.mFooterTip);
              }
            },
            throwable -> {
              if (throwable instanceof FeelingException) {
                ToastUtil.showToast(((FeelingException) throwable).mErrorMessage);
              }
            });
  }

  /**
   * 列表数据变化时
   *
   * @param feelList  列表数据
   * @param footerTip 滑动到底部的提示
   */
  private void onFeelListDataChanged(@NonNull List<Feel> feelList, String footerTip) {
    //列表UI数据
    List<LiRecyclerItemViewData> itemViewDataList = new ArrayList<>();

    // 把原始的业务数据转化为列表需要的UI数据
    for (Feel feel : feelList) {
      HomeFeelingListFeelItemViewData feelItemViewData = new HomeFeelingListFeelItemViewData();
      feelItemViewData.mAvatarResId = R.drawable.mine_head_my_photo;
      feelItemViewData.mName = feel.mUser.mNickName;
      feelItemViewData.mTime = feel.mPublishTime + "";
      feelItemViewData.mContentText = feel.mContentText;
      feelItemViewData.mLikeNum = feel.mLikeNum;

      itemViewDataList.add(feelItemViewData);
    }

    // footerItem需要的数据
    itemViewDataList.add(new HomeFeelingListFooterItemViewData(footerTip));

    mFeelListAdapter.setList(itemViewDataList);
  }

  // 发布feel成功时，会回到home页面，因此需要刷新下列表
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onFeelPublishSuccessEvent(FeelPublishSuccessEvent event) {
    refreshFeelList();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
    RxUtil.dispose(mFeelListDisposable);
  }
}