// Copyright 2022 The Chromium Authors & Kiwi Browser Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.chrome.browser.settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceFragmentCompat;

import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.ui.widget.Toast;

/**
 * Settings fragment for configuring Kiwi Browser toolbar customization.
 */
public class ToolbarSettings extends PreferenceFragmentCompat {
    private static final String TAG = "ToolbarSettings";

    public static final String PREF_TOOLBAR_POSITION = "toolbar_position";
    public static final String PREF_BOTTOM_TOOLBAR = "bottom_toolbar";
    public static final String PREF_SWIPE_TOOLBAR = "swipe_toolbar";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        if (getActivity() != null) {
            getActivity().setTitle("Toolbar");
        }
    }

    /**
     * Helper to prompt the user to restart the browser after changing core UI/rendering preferences.
     */
    public static void AskForRelaunch(Activity activity) {
        if (activity == null || activity.isFinishing()) return;
        try {
            new AlertDialog.Builder(activity)
                    .setTitle("Relaunch Required")
                    .setMessage("Please restart Kiwi Browser for settings to take effect.")
                    .setPositiveButton("Relaunch Now", (dialog, which) -> {
                        android.content.Intent intent = activity.getPackageManager()
                                .getLaunchIntentForPackage(activity.getPackageName());
                        if (intent != null) {
                            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
                        }
                        System.exit(0);
                    })
                    .setNegativeButton("Later", null)
                    .show();
        } catch (Throwable t) {
            Log.w(TAG, "Failed to display relaunch dialog, falling back to toast", t);
            try {
                Toast.makeText(activity, "Restart Kiwi Browser to apply changes", Toast.LENGTH_SHORT).show();
            } catch (Throwable ignored) {
            }
        }
    }
}
