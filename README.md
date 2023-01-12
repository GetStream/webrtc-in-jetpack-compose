![WebRTC in Jetpack Compose-1200x630](https://user-images.githubusercontent.com/24237865/211961074-8e01056c-a820-468b-bdca-3ab2570f783a.jpg)

<h1 align="center">WebRTC in Jetpack Compose</h1></br>

This project demonstrates [WebRTC protocol](https://getstream.io/glossary/webrtc-protocol/) to facilitate real-time video communications with Jetpack Compose.

The purpose of this repository is to demonstrate below:
- Implementing entire UI elements for real-time video communication with Jetpack Compose.
- Performing real-time communication in background with Kotlin Coroutines.
- Understanding the peer connection based on WebRTC.
- Communicating with a signaling server to exchange peer connection information between clients.

## ✍️ Technical Content

We're preparing to publish a blog post about **WebRTC in Jetpack Compose**. If you need to understand WebRTC and communication protocols, check out the [HTTP, WebSocket, gRPC or WebRTC: Which Communication Protocol is Best For Your App?](https://getstream.io/blog/communication-protocols/).

If you'd like to get notified as we release future posts, join the **[watchers](https://github.com/skydoves/android-developer-roadmap/watchers)** on GitHub or follow **[Stream](https://twitter.com/getstream_io)** on Twitter. You can also follow the __[author](https://github.com/skydoves)__ of this repository on GitHub.


<a href="https://getstream.io/chat/sdk/compose">
<img src="https://user-images.githubusercontent.com/24237865/138428440-b92e5fb7-89f8-41aa-96b1-71a5486c5849.png" align="right" width="12%"/>
</a>

## 🛥 Stream Chat and Voice & Video calling SDK
If you’re interested in adding powerful In-App Messaging to your app, check out the __[Compose Chat SDK for Messaging](https://getstream.io/chat/sdk/compose/)__! We're also planning to release voice & video calling SDK very soon! If you want to be an early access to our SDK, check out the **[Video & Voice Calling API on Stream's Global Edge Network](https://getstream.io/video/)**.

- [Stream Chat SDK for Android on GitHub](https://github.com/getStream/stream-chat-android)
- [Android Samples for Stream Chat SDK on GitHub](https://github.com/getStream/android-samples)
- [Stream Chat Compose UI Componenets Guidelines](https://getstream.io/chat/docs/sdk/android/compose/overview/)

## 📷 Previews

<p align="center">
<img src="previews/preview0.png" alt="drawing" width="273" />
<img src="previews/preview1.png" alt="drawing" width="273" />
</p>

## 🛠 Tech Sacks & Open Source Libraries
- Minimum SDK level 21.
- 100% [Jetpack Compose](https://developer.android.com/jetpack/compose) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [WebRTC](https://webrtc.org/): To build real-time communication capabilities to your application that works on top of an open standard.
- [WebRTC-android](https://github.com/webrtc-sdk/android): WebRTC pre-compiled library for android.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and paging network data.
- [StreamLog](https://github.com/GetStream/stream-log): A lightweight and extensible logger library for Kotlin and Android.
- [Ktor](https://github.com/ktorio/ktor): Building a signaling client websocket server.

## 💻 How to build the project?

To build this project properly, you should follow the instructions below:

1. Run the [WebRTC backend server](https://github.com/advocacies/webrtc-in-jetpack-compose/tree/main/webrtc-backend).
2. Add the local ip address of your pc on the `local.properties` file on the project (Android Studio) like the below:

```
SIGNALING_SERVER_IP_ADDRESS=ws://192.168.1.123:8080/rtc # You should change 192.168.1.123 to your local ip address, which is running the WebRTC backend server
```
3. Lastly, run the [WebRTC android](https://github.com/advocacies/webrtc-in-jetpack-compose/tree/main/webrtc-android) project on your multiple devices to test peer communication.

## 🤝 Contribution

Most features are not completed except the chat feature, so that anyone can contribute and improve this project following the [Contributing Guideline](https://github.com/GetStream/webrtc-in-jetpack-compose/blob/main/CONTRIBUTING.md).

## Find this repository useful? 💙
Support it by joining __[stargazers](https://github.com/GetStream/webrtc-in-jetpack-compose/stargazers)__ for this repository. :star: <br>
Also, follow __[maintainers](https://github.com/skydoves)__ on GitHub for our next creations! 🤩

# License
```xml
Copyright 2023 Stream.IO, Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
