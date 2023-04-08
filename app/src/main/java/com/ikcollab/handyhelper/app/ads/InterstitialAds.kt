package com.ikcollab.handyhelper.app.ads

import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

var mInterstitialAd: InterstitialAd? = null
var adCount = 0

fun loadInterstitial(context: Context) {
    InterstitialAd.load(
        context,
        AdsConstants.INTERSTITIAL_AD_UNIT_TEST,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
                adCount--
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        }
    )
}

fun showInterstitial(context: Context, onAdDismissed: () -> Unit) {
    val activity = context.findActivity()

    if (mInterstitialAd != null && activity != null && adCount % 5 == 4) {
        adCount++
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(e: AdError) {
                mInterstitialAd = null
            }

            override fun onAdDismissedFullScreenContent() {
                mInterstitialAd = null

                loadInterstitial(context)
                onAdDismissed()
            }
        }
        mInterstitialAd?.show(activity)
    } else {
        adCount++
    }
}

fun removeInterstitial() {
    mInterstitialAd?.fullScreenContentCallback = null
    mInterstitialAd = null
}

