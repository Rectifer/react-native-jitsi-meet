package com.reactnativejitsimeet;

import android.util.Log;
import java.net.URL;
import java.net.MalformedURLException;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ReadableMap;

@ReactModule(name = RNJitsiMeetModule.MODULE_NAME)
public class RNJitsiMeetModule extends ReactContextBaseJavaModule {
    public static final String MODULE_NAME = "RNJitsiMeetModule";
    private IRNJitsiMeetViewReference mJitsiMeetViewReference;

    public RNJitsiMeetModule(ReactApplicationContext reactContext, IRNJitsiMeetViewReference jitsiMeetViewReference) {
        super(reactContext);
        mJitsiMeetViewReference = jitsiMeetViewReference;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void initialize() {
        Log.d("JitsiMeet", "Initialize is deprecated in v2");
    }

    @ReactMethod
    public void call(String url, ReadableMap userInfo, ReadableMap meetOptions) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // }
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
                    if (userInfo != null) {
                          if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }

                    RNJitsiMeetConferenceOptions options = new RNJitsiMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setSubject(meetOptions.getString("subject"))
                            .setToken(meetOptions.getString("token"))
                            .setAudioMuted(meetOptions.getBoolean("setAudioMuted"))
                            .setVideoMuted(meetOptions.getBoolean("setVideoMuted"))
                            .setUserInfo(_userInfo)


                            .setFeatureFlag("pip.enabled", true)
                            .setFeatureFlag("add-people.enabled", false)
                            .setFeatureFlag("invite.enabled", false)
                            .setFeatureFlag("calendar.enabled", false)
                            .setFeatureFlag("close-captions.enabled", false)
                            .setFeatureFlag("kick-out.enabled", false)
                            .setFeatureFlag("meeting-name.enabled", false)
                            .setFeatureFlag("meeting-password.enabled", false)
                            .setFeatureFlag("recording.enabled", false)
                            .setFeatureFlag("live-streaming.enabled", false)
                            .setFeatureFlag("call-integration.enabled", meetOptions.getBoolean("call"))
                            .setFeatureFlag("conference-timer.enabled", meetOptions.getBoolean("timer"))
                            .setFeatureFlag("raise-hand.enabled", meetOptions.getBoolean("raiseHand"))
                            .setFeatureFlag("toolbox.alwaysVisible", meetOptions.getBoolean("toolbox"))
                            .setFeatureFlag("video-share.enabled",false)
                            .setFeatureFlag("mute-everyone.enabled", meetOptions.getBoolean("mute-everyone"))
                            .setFeatureFlag("lobby.enabled", false)
                            .build();
                    mJitsiMeetViewReference.getJitsiMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void audioCall(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNJitsiMeetConferenceOptions options = new RNJitsiMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(true)
                            .setUserInfo(_userInfo)
                            .build();
                    mJitsiMeetViewReference.getJitsiMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void endCall() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    mJitsiMeetViewReference.getJitsiMeetView().leave();
                }
            }
        });
    }

    // @ReactMethod
    // public void dispose() {
    //     UiThreadUtil.runOnUiThread(new Runnable() {
    //         @Override
    //         public void dispose() {
    //             if(mJitsiMeetViewReference.getJitsiMeetView() != null) {
    //                 mJitsiMeetViewReference.getJitsiMeetView().dispose();
    //             }
    //         }
    //     });
    // }
}
