package com.li.feeling.feellist.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.li.feeling.feellist.viewdata.HomeFeelingListFeelItemViewData;
import com.li.feeling.home.R;
import com.li.frameowrk.recycler.LiRecycleViewHolder;

/**
 * description: feelItem的vh
 */
public class HomeFeelListFeelItemViewHolder extends
    LiRecycleViewHolder<HomeFeelingListFeelItemViewData> {

  private ImageView mAvatarView;
  private TextView mNameView;
  private TextView mTimeVIew;
  private TextView mContentTextView;
  private TextView mLikeNumView;

  public HomeFeelListFeelItemViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  @Override
  protected void doBindView(@NonNull View itemView) {
    mAvatarView = itemView.findViewById(R.id.feel_item_avatar_view);
    mNameView = itemView.findViewById(R.id.feel_item_name_view);
    mTimeVIew = itemView.findViewById(R.id.feel_item_time_view);
    mContentTextView = itemView.findViewById(R.id.feel_item_content_text_view);
    mLikeNumView = itemView.findViewById(R.id.feel_item_like_num_view);
  }

  @Override
  protected void onBind(@NonNull HomeFeelingListFeelItemViewData data, int position) {
    mAvatarView.setImageResource(data.mAvatarResId);
    mNameView.setText(data.mName);
    mTimeVIew.setText(data.mTime);
    mContentTextView.setText(data.mContentText);
    mLikeNumView.setText(data.mLikeNum + "");
  }

  @Override
  protected void onUnBind() {

  }

}
