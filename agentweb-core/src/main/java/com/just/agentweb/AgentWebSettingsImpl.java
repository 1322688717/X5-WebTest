/*
 * Copyright (C)  Justson(https://github.com/Justson/AgentWeb)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.just.agentweb;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.view.ViewGroup;

import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebView;


/**
 * @author cenxiaozhong
 * @since 1.0.0
 */
public class AgentWebSettingsImpl extends AbsAgentWebSettings {
    private AgentWeb mAgentWeb;

    @Override
    protected void bindAgentWebSupport(AgentWeb agentWeb) {
        this.mAgentWeb = agentWeb;
    }

    @Override
    public WebListenerManager setDownloader(WebView webView, DownloadListener downloadListener) {
        // Fix Android 5.1 crashing:
        // ClassCastException: android.app.ContextImpl cannot be cast to android.app.Activity
        if (downloadListener == null) {
            Activity activity = getActivityByContext(webView.getContext());
            if (null == activity) {
                if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 23) {
                    activity = getActivityByContext(((ViewGroup) webView.getParent()).getContext());
                }
            }
            downloadListener = DefaultDownloadImpl.create(activity, webView, mAgentWeb.getPermissionInterceptor());
        }
        return super.setDownloader(webView, downloadListener);
    }

    /**
     * Copy from com.blankj.utilcode.util.ActivityUtils#getActivityByView
     */
    private Activity getActivityByContext(Context context) {
        if (context instanceof Activity) return (Activity) context;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}
