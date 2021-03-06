package com.shuashuakan.android.modules.widget.loadmoreview;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.shuashuakan.android.R;

/**
 * Author:  Chenglong.Lu
 * Email:   1053998178@qq.com | w490576578@gmail.com
 * Date:    2018/11/30
 * Description:
 */
public class SskLoadMoreView extends LoadMoreView {

  public static final String TOPIC = "topic";
  public static final String IMAGE = "image";

  public static final String LOAD_MORE_CHAINS_END = "load_more_chains_end";
  public static final String LOAD_MORE_LOAD_END = "load_more_load_end";
  public static final String LOAD_MORE_NONE_END = "load_more_none_end";

  private boolean isChains = false;
  private String type = "";

  public SskLoadMoreView() {
    type = LOAD_MORE_LOAD_END; // 默认
  }

  public SskLoadMoreView(boolean isChains) {
    this.isChains = isChains;
    type = LOAD_MORE_CHAINS_END;
  }

  public SskLoadMoreView(String type) {
    this.type = type;
  }

  @Override
  public int getLayoutId() {
    return R.layout.view_more;
  }

  @Override
  protected int getLoadingViewId() {
    return R.id.load_more_loading_view;
  }

  @Override
  protected int getLoadFailViewId() {
    return R.id.load_more_load_fail_view;
  }

  @Override
  protected int getLoadEndViewId() {
    if (type.equals(TOPIC)) {
      return R.id.load_more_topic_end_view;
    } else if (type.equals(IMAGE)) {
      return R.id.load_more_bird_end_view;
    } else if (type.equals(LOAD_MORE_LOAD_END)) {
      return R.id.load_more_load_end_view;
    } else if (type.equals(LOAD_MORE_CHAINS_END)) {
      return R.id.load_more_chains_end_view;
    } else if (type.equals(LOAD_MORE_NONE_END)) {
      return 0;
    }

//    if(isChains){
//      return R.id.load_more_chains_end_view;
//    }else {
//      return R.id.load_more_load_end_view;
//    }

    return R.id.load_more_load_end_view;
  }

}
