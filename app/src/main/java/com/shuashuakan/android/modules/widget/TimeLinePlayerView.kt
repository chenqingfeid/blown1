package com.shuashuakan.android.modules.widget

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.shuashuakan.android.R
import com.shuashuakan.android.modules.player.VideoPlayer
import com.shuashuakan.android.modules.timeline.AdapterToPlayerListener
import com.shuashuakan.android.utils.isWebPSupportableHost

class TimeLinePlayerView @kotlin.jvm.JvmOverloads constructor(context: Context,
                                                              attrs: AttributeSet? = null, defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr) {

  private var videoPlayer: VideoPlayer
  private var blurView: ImageView

  private var feedId: String? = null
  private var realPosition: Int = 0

  private var mGestureDetector: GestureDetector
  private var showOrHidden: AdapterToPlayerListener? = null

  var videoUrl: String? = ""

  var controllerTimeLine: com.shuashuakan.android.modules.player.TimeLinePlayerControllerImpl

  var listener: OnPlayerViewClickListener? = null

  interface OnPlayerViewClickListener {
    fun onPlayerViewClickListener()
  }

  init {
    View.inflate(context, R.layout.layout_video_view_timeline, this)
    videoPlayer = findViewById(R.id.texture_view)
    blurView = findViewById(R.id.blur_view)
    controllerTimeLine = com.shuashuakan.android.modules.player.TimeLinePlayerControllerImpl(context)
    videoPlayer.setController(controllerTimeLine)
    mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
      override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
//                if (videoPlayer.isPlaying()) {
//                    pausePlay()
//                    showOrHidden?.showLabel()
//                } else if (videoPlayer.isPaused()) {
//                    startPlay()
//                    showOrHidden?.hiddenLabel()
//                }
        listener?.onPlayerViewClickListener()
        return false
      }
    })
    videoPlayer.viewRadiusDP = 4f
  }

  fun bind(uuid: String, feedId: String, videoUrl: String, previewUrl: String, width: Int,
           height: Int, realPosition: Int, showOrHidden: AdapterToPlayerListener) {
    this.realPosition = realPosition
    this.feedId = feedId
    this.videoUrl = videoUrl
    this.showOrHidden = showOrHidden
    requestFocus()
    //显示封面图
    val buildUrl = StringBuilder(previewUrl).apply {
      if (!previewUrl.contains("?imageMogr2") && isWebPSupportableHost(previewUrl)) {
        append("?imageMogr2")
        append("/strip")
        append("/gravity/center")
        append("/thumbnail")
        append("/${width}x$height")
        append("/format/webp")
      }
    }
    controllerTimeLine.setVideoPlayerBackground(previewUrl)
    val options = RequestOptions()
        .placeholder(R.drawable.ic_block_video)
        .error(R.drawable.ic_block_video)
        .centerCrop()
    // TODO 显示封面图需要用圆角，使用SimpleDraweeView
//    Glide.with(blurView.context).load(previewUrl).apply(options).into(blurView)

    videoPlayer.setUp(videoUrl, uuid)
  }

  fun pausePlay() {
    videoPlayer.pause()
  }

  fun startPlay() {
    if (videoPlayer.isPaused() || videoPlayer.isBufferingPaused()) {
      videoPlayer.restart()
    } else {
      videoPlayer.start()
    }
  }

  fun getPlayer(): VideoPlayer {
    return videoPlayer
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    mGestureDetector.onTouchEvent(event)
    return true
  }

}