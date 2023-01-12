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

package io.getstream.webrtc.sample.compose.webrtc.utils

import org.webrtc.SdpObserver
import org.webrtc.SessionDescription
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend inline fun createValue(
  crossinline call: (SdpObserver) -> Unit
): Result<SessionDescription> = suspendCoroutine {
  val observer = object : SdpObserver {

    /**
     * Handling of create values.
     */
    override fun onCreateSuccess(description: SessionDescription?) {
      if (description != null) {
        it.resume(Result.success(description))
      } else {
        it.resume(Result.failure(RuntimeException("SessionDescription is null!")))
      }
    }

    override fun onCreateFailure(message: String?) =
      it.resume(Result.failure(RuntimeException(message)))

    /**
     * We ignore set results.
     */
    override fun onSetSuccess() = Unit
    override fun onSetFailure(p0: String?) = Unit
  }

  call(observer)
}

suspend inline fun setValue(
  crossinline call: (SdpObserver) -> Unit
): Result<Unit> = suspendCoroutine {
  val observer = object : SdpObserver {
    /**
     * We ignore create results.
     */
    override fun onCreateFailure(p0: String?) = Unit
    override fun onCreateSuccess(p0: SessionDescription?) = Unit

    /**
     * Handling of set values.
     */
    override fun onSetSuccess() = it.resume(Result.success(Unit))
    override fun onSetFailure(message: String?) =
      it.resume(Result.failure(RuntimeException(message)))
  }

  call(observer)
}
