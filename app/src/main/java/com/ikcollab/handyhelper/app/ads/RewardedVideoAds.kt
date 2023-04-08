package com.ilhomsoliev.askmeanything.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.ikcollab.handyhelper.app.ads.AdsConstants
import com.ikcollab.handyhelper.app.ads.findActivity

private var rewardedAd: RewardedAd? = null
private var TAG = "MainActivity"
private var isShowingRewardedAd = false

fun loadRewardedVideoAd(context: Context) {
    RewardedAd.load(
        context,
        AdsConstants.REWARDED_VIDEO_AD_UNIT_TEST,
        AdRequest.Builder().build(),
        object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("TAG", adError.toString())
                rewardedAd = null
            }

            override fun onAdLoaded(ad: RewardedAd) {
                Log.d("TAG", "Ad was loaded.")
                rewardedAd = ad
            }
        }
    )
    rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
            Log.d(TAG, "Ad was clicked.")
        }

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            // Set the ad reference to null so you don't show the ad a second time.
            Log.d(TAG, "Ad dismissed fullscreen content.")
            rewardedAd = null
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            // Called when ad fails to show.
            Log.e(TAG, "Ad failed to show fullscreen content.")
            rewardedAd = null
        }

        override fun onAdImpression() {
            // Called when an impression is recorded for an ad.
            Log.d(TAG, "Ad recorded an impression.")
        }

        override fun onAdShowedFullScreenContent() {
            // Called when ad is shown.
            Log.d(TAG, "Ad showed fullscreen content.")
        }
    }
}

fun showRewardedAd(context: Context, onDone: (Int) -> Unit, onNotReadyYet: () -> Unit) {
    if(isShowingRewardedAd) {
        onNotReadyYet()
        return
    }
    isShowingRewardedAd = true
    val activity: Activity? = context.findActivity()
    activity?.let {
        rewardedAd?.let { ad ->
            ad.show(activity) { rewardItem ->
                isShowingRewardedAd = false
                // Handle the reward.
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
                onDone(rewardAmount)
                Log.d(TAG, "User earned the reward.")
               // loadRewardedVideoAd(context)
            }
        } ?: run {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
            isShowingRewardedAd = false
            onNotReadyYet()
            //loadRewardedVideoAd(context)
        }
    }

}

fun removeRewarded() {
    rewardedAd?.fullScreenContentCallback = null
    rewardedAd = null
}