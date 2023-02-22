/*
 * Copyright 2023 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.webrtc.sample.compose.ui.components

import android.content.Context
import android.content.res.Resources
import android.graphics.SurfaceTexture
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.TextureView
import android.view.TextureView.SurfaceTextureListener
import org.webrtc.EglBase
import org.webrtc.EglRenderer
import org.webrtc.GlRectDrawer
import org.webrtc.RendererCommon.RendererEvents
import org.webrtc.ThreadUtils
import org.webrtc.VideoFrame
import org.webrtc.VideoSink
import java.util.concurrent.CountDownLatch

/**
 * Custom [TextureView] used to render local/incoming videos on the screen.
 */
open class VideoTextureViewRenderer @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : TextureView(context, attrs), VideoSink, SurfaceTextureListener {

  /**
   * Cached resource name.
   */
  private val resourceName: String = getResourceName()

  /**
   * Renderer used to render the video.
   */
  private val eglRenderer: EglRenderer = EglRenderer(resourceName)

  /**
   * Callback used for reporting render events.
   */
  private var rendererEvents: RendererEvents? = null

  /**
   * Handler to access the UI thread.
   */
  private val uiThreadHandler = Handler(Looper.getMainLooper())

  /**
   * Whether the first frame has been rendered or not.
   */
  private var isFirstFrameRendered = false

  /**
   * The rotated [VideoFrame] width.
   */
  private var rotatedFrameWidth = 0

  /**
   * The rotated [VideoFrame] height.
   */
  private var rotatedFrameHeight = 0

  /**
   * The rotated [VideoFrame] rotation.
   */
  private var frameRotation = 0

  init {
    surfaceTextureListener = this
  }

  /**
   * Called when a new frame is received. Sends the frame to be rendered.
   *
   * @param videoFrame The [VideoFrame] received from WebRTC connection to draw on the screen.
   */
  override fun onFrame(videoFrame: VideoFrame) {
    eglRenderer.onFrame(videoFrame)
    updateFrameData(videoFrame)
  }

  /**
   * Updates the frame data and notifies [rendererEvents] about the changes.
   */
  private fun updateFrameData(videoFrame: VideoFrame) {
    if (isFirstFrameRendered) {
      rendererEvents?.onFirstFrameRendered()
      isFirstFrameRendered = true
    }

    if (videoFrame.rotatedWidth != rotatedFrameWidth ||
      videoFrame.rotatedHeight != rotatedFrameHeight ||
      videoFrame.rotation != frameRotation
    ) {
      rotatedFrameWidth = videoFrame.rotatedWidth
      rotatedFrameHeight = videoFrame.rotatedHeight
      frameRotation = videoFrame.rotation

      uiThreadHandler.post {
        rendererEvents?.onFrameResolutionChanged(
          rotatedFrameWidth,
          rotatedFrameHeight,
          frameRotation
        )
      }
    }
  }

  /**
   * After the view is laid out we need to set the correct layout aspect ratio to the renderer so that the image
   * is scaled correctly.
   */
  override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    eglRenderer.setLayoutAspectRatio((right - left) / (bottom.toFloat() - top))
  }

  /**
   * Initialise the renderer. Should be called from the main thread.
   *
   * @param sharedContext [EglBase.Context]
   * @param rendererEvents Sets the render event listener.
   */
  fun init(
    sharedContext: EglBase.Context,
    rendererEvents: RendererEvents
  ) {
    ThreadUtils.checkIsOnMainThread()
    this.rendererEvents = rendererEvents
    eglRenderer.init(sharedContext, EglBase.CONFIG_PLAIN, GlRectDrawer())
  }

  /**
   * [SurfaceTextureListener] callback that lets us know when a surface texture is ready and we can draw on it.
   */
  override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
    eglRenderer.createEglSurface(surfaceTexture)
  }

  /**
   * [SurfaceTextureListener] callback that lets us know when a surface texture is destroyed we need to stop drawing
   * on it.
   */
  override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
    val completionLatch = CountDownLatch(1)
    eglRenderer.releaseEglSurface { completionLatch.countDown() }
    ThreadUtils.awaitUninterruptibly(completionLatch)
    return true
  }

  override fun onSurfaceTextureSizeChanged(
    surfaceTexture: SurfaceTexture,
    width: Int,
    height: Int
  ) {
  }

  override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture) {}

  override fun onDetachedFromWindow() {
    eglRenderer.release()
    super.onDetachedFromWindow()
  }

  private fun getResourceName(): String {
    return try {
      resources.getResourceEntryName(id) + ": "
    } catch (e: Resources.NotFoundException) {
      ""
    }
  }
}
