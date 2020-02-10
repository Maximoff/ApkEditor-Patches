// Патч для удаления рекламы
// Требуется файл RemoveFuckingAds.smali
// © Maximoff, 2020

package ru.maximoff.patch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;

public class RemoveFuckingAds
{
	private final String[] checkAds = {
		"/adbuddiz/",
		"/adcolony/",
		"/addapptr/",
		"/adjust/",
		"/adincube/",
		"/adknowledge/",
		"/admarvel/",
		"/admob/",
		"/ads/",
		"/adsdk/",
		"/adserver/adview/",
		"/aerserv/",
		"/airpush/",
		"/altamob/",
		"/appAdForce/",
		"/appbrain/",
		"/appenda/",
		"/applovin/",
		"/appnext/",
		"/appnexus/",
		"/appodeal/",
		"/appia/",
		"/apprupt/",
		"/apsalar/",
		"/avocarrot/",
		"/boxdigital/sdk/ad/",
		"/branch/",
		"/chartboost/",
		"/crashlytics/",
		"/duapps/ad",
		"/fabric/",
		"/flurry/",
		"/fyber/",
		"/google/android/gms/internal/",
		"/greystripe/",
		"/heyzap/",
		"/hyprmx/",
		"/inmobi/",
		"/inneractive/",
		"/instreamatic/",
		"/integralads/",
		"/ironsource/",
		"/jirbo/",
		"/jumptap/",
		"/kochava/",
		"/Leadbolt/",
		"/localytics/",
		"/loopme/",
		"/madsdk/",
		"/mdotm/",
		"/mediabrix/",
		"/millennialmedia/",
		"/mngads/",
		"/moat/",
		"/mobclix/",
		"/mobfox/sdk/",
		"/mobvista/",
		"/mologiq/analytics/",
		"/moolah/",
		"/montexi/",
		"/mopub/",
		"/my/target/",
		"/nexage/",
		"/onelouder/adlib/",
		"/openx/",
		"psm/advertising/",
		"/pubmatic/",
		"/revmob/",
		"/shark/adsert/",
		"/smaato/SOMA/",
		"/smartadserver/",
		"/startapp/",
		"/tagmanager/",
		"/tapjoy/",
		"/unity3d/ads",
		"/vdopia/",
		"/vungle/",
		"/xtify/android/sdk/",
		"/zestadz/android/",
		"ad/AdmobInterstitial",
		"ad/AdmobNative",
		"ads/FullAdmob",
		"NativeAdViewAdmob",
		"InlineAd",
		"/NativeInterstitial",
		"ru/boxdigital/sdk/ad/",
		"ca-app-pub",
		"doubleclick.net",
		"googleadservices.com",
		"pagead/ads",
		"googleads",
		"ad.doubleclick.net",
		"//unrcv.adkmob.com/rp/",
		"//www.googleapis.com/auth/games",
		"//sb-ssl.google.com/safebrowsing/clientreport/malware",
		"//proton.flurry.com/sdk/v1/config",
		"applovin.com",
		"//ach.appodeal.com/api/v0/android/crashes",
		"//ad.mail.ru/mobile",
		"//analytics.mopub.com/i/jot/exchange_client_event",
		"//api.pubnative.net/api/partner/v2/promotions/native/video",
		"//certificate.mobile.yandex.net/api/v1/pins",
		"//code.google.com/p/android/issues/detail?id=",
		"//data.flurry.com",
		"//dwxjayoxbnyrr.cloudfront.net/amazon-ads.viewablejs",
		"//e.crashlytics.com/spi/v2/events",
		"//impact.applifier.com/mobile/campaigns",
		"//impact.staging.applifier.com/mobile/campaigns",
		"//live.chartboost.com",
		"//pagead2.googlesyndication.com/pagead/gen_204",
		"//r.my.com/mobile",
		"//rri.appodeal.com/api/stat",
		"//s3.amazonaws.com/appodeal-externallibs/android/ima3.js",
		"//settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings",
		"//www.mopub.com",
		"//startup.mobile.yandex.net/",
		"//ad.mail.ru/mobile/",
		"//r.my.com/mobile/",
		"//i.l.inmobicdn.net/sdk/sdk/500/android/mraid.js",
		"@id/adlayout",
		"@id/adslayout",
		"@id/adviewlayout",
		"@id/bannerlayout",
		"@id/ad_layout",
		"@id/ads_layout",
		"@id/adview_layout",
		"@id/banner_layout",
		"com.google.android.gms.ads.AdView"
	};
	private final String[] smaliPatterns = {
		"invoke-.*Lcom/google/android/gms/(internal|ads).*;->addView\\([^\\)]*\\)V",
		"invoke-.*(/adbuddiz/|/adcolony/|/addapptr/|/adjust/|/adincube/|/adknowledge/|/admarvel/|/admob/|/ads/|/adsdk/|/adserver/adview/|/aerserv/|/airpush/|/altamob/|/appAdForce/|/appbrain/|/appenda/|/applovin/|/appnext/|/appnexus/|/appodeal/|/appia/|/apprupt/|/apsalar/|/avocarrot/|/boxdigital/sdk/ad/|/branch/|/chartboost/|/crashlytics/|/duapps/ad|/fabric/|/flurry/|/fyber/|/google/android/gms/internal/|/greystripe/|/heyzap/|/hyprmx/|/inmobi/|/inneractive/|/instreamatic/|/integralads/|/ironsource/|/jirbo/|/jumptap/|/kochava/|/Leadbolt/|/localytics/|/loopme/|/madsdk/|/mdotm/|/mediabrix/|/millennialmedia/|/mngads/|/moat/|/mobclix/|/mobfox/sdk/|/mobvista/|/mologiq/analytics/|/moolah/|/montexi/|/mopub/|/my/target/|/nexage/|/onelouder/adlib/|/openx/|psm/advertising/|/pubmatic/|/revmob/|/shark/adsert/|/smaato/SOMA/|/smartadserver/|/startapp/|/tagmanager/|/tapjoy/|/unity3d/ads|/vdopia/|/vungle/|/xtify/android/sdk/|/zestadz/android/|ad/AdmobInterstitial|ad/AdmobNative|ads/FullAdmob|NativeAdViewAdmob|InlineAd|/NativeInterstitial|ru/boxdigital/sdk/ad/).*->(hasVideoContent|addHtmlAdView|admob|animateAdView|bannerAdmobMainActivity|beginFetchAds|canBeUsed|displayDownloadImageAlert|doBannerClick|downloadAndDisplayImage|expandAd|fetchAd|forceShowInterstitial|handleShow|incrementMetric|initBanner|initializeAds|initializeAdSDK|internalLoadAd|load|loadAd|loadAdFromBid|loadAds|loadAssetIntoView|loadBanner|loadBannerAd|loadBanners|loadBlocksInfo|loadChildAds|loadCustomEvent|loadData|loadDataWithBaseURL|loadDoAfterService|loadFromServer|loadFullscreen|loadHtml|loadHtmlResponse|loadImages|loadImageView|loadIncentivizedAd|loadInterstitial|loadInterstitialAd|loadList|loadMidPoint|loadNativeAd|loadNativeAds|loadNextAd|loadNextMediatedAd|loadNonJavascript|loadRewardedVideo|loadUrl|loadVideo|loadVideoAds|loadVideoUrl|mraidVideoAddendumInterstitialShow|nativeAdLoaded|onEvent|playVideo|preload|preloadAd|preloadHtml|preloadNativeAdImage|preloadUrl|pushAdsToPool|refreshAds|requestAd|requestBannerAd|requestInterstitial|requestInterstitialAd|setNativeAppwallBanner|setupBanner|shouldShowInterstitial|show|showAd|showAds|showAdInActivity|showAdinternal|showAndRender|showAsInterstitial|showBanner|showBannerAbsolute|showBannerInPlaceholder|showContent|showCustomEventInterstitial|showFullscreen|showIncentivizedAd|showInterstitial|showInterstitialAd|showOfferWall|showMoPubBrowserForUrl|showNativeContentAdView|showNativeAppInstallAdView|showPopup|showPopupExit|showPoststitial|showPreparedVideoFallbackAd|showSplash|showVideo|showWebPage|startAdLoadUponLayout|startMetric|submitAndResetMetrics|isLoading|uploadReports)\\(.*\\)(V|Z)",
		"\"ca-app-pub-\\d+(/|~)\\d+\"|\".*doubleclick\\.net.*\"|\".*googleadservices\\.com.*\"|\".*pagead/ads.*\"|\".*googleads.*\"|\".*ad\\.doubleclick\\.net.*\"|\"https?://unrcv\\.adkmob\\.com/rp/.*\"|\"https?://www\\.googleapis\\.com/auth/games.*\"|\"https?://sb-ssl\\.google\\.com/safebrowsing/clientreport/malware.*\"|\"https?://proton\\.flurry\\.com/sdk/v1/config.*\"|\"https?://.*applovin\\.com.*\"|\"https?://ach\\.appodeal\\.com/api/v0/android/crashes.*\"|\"https?://ad\\.mail\\.ru/mobile.*\"|\"https?://analytics\\.mopub\\.com/i/jot/exchange_client_event.*\"|\"https?://api\\.pubnative\\.net/api/partner/v2/promotions/native/video.*\"|\"https?://certificate\\.mobile\\.yandex\\.net/api/v1/pins.*\"|\"https?://code\\.google\\.com/p/android/issues/detail?id=.*\"|\"https?://data\\.flurry\\.com.*\"|\"https?://dwxjayoxbnyrr\\.cloudfront\\.net/amazon-ads\\.viewablejs.*\"|\"https?://e\\.crashlytics\\.com/spi/v2/events.*\"|\"https?://impact\\.applifier\\.com/mobile/campaigns.*\"|\"https?://impact.staging\\.applifier\\.com/mobile/campaigns.*\"|\"https?://live\\.chartboost\\.com.*\"|\"https?://pagead2\\.googlesyndication\\.com/pagead/gen_204.*\"|\"https?://r\\.my\\.com/mobile.*\"|\"https?://rri\\.appodeal\\.com/api/stat.*\"|\"https?://s3\\.amazonaws\\.com/appodeal-externallibs/android/ima3\\.js\\.*\"|\"https?://settings\\.crashlytics\\.com/spi/v2/platforms/android/apps/%s/settings.*\"|\"https?://www\\.mopub\\.com.*\"|\"https?://startup\\.mobile\\.yandex\\.net/\"|\"https?://ad\\.mail\\.ru/mobile/\"|\"https?://r\\.my\\.com/mobile/\"|\"https?://i\\.l\\.inmobicdn\\.net/sdk/sdk/500/android/mraid\\.js\""
	};
	private final String[] smaliReplacement = {
		"invoke-static {}, Lremove/fucking/ads/RemoveFuckingAds;->a()V #Remove-Fucking-Ads",
		"invoke-static {}, Lremove/fucking/ads/RemoveFuckingAds;->a()$3 #Remove-Fucking-Ads",
		"\"Remove-Fucking-Ads\" #Remove-Fucking-Ads"
	};
	private final String[] xmlPatterns = {
		"<([^>]+)(android:id=\"@id/(?i)((ads?|banner|adview)_?layout)\")([^>]+)android:layout_width=\"[^\"]+ent\"([^>]+)android:layout_height=\"[^\"]+ent\"",
		"<com\\.google\\.android\\.gms\\.ads\\.AdView([^>]+)android:layout_width=\"[^\"]+ent\"([^>]+)android:layout_height=\"[^\"]+ent\"",
		"ca-app-pub"
	};
	private final String[] xmlReplacement = {
		"<!-- #Remove-Fucking-Ads -->\n<$1$2$5android:layout_width=\"0dip\"$6android:layout_height=\"0dip\"",
		"<!-- #Remove-Fucking-Ads -->\n<com.google.android.gms.ads.AdView$1android:layout_width=\"0dip\"$2android:layout_height=\"0dip\"",
		"Remove-Fucking-Ads"
	};
	private FileFilter dirFilter;
	private FilenameFilter smaliFilter;
	private FilenameFilter xmlFilter;

    public RemoveFuckingAds()
	{}

    public void patch(String apkPath, String patchPath, String decodePath, String param)
	{
        File rootDir = new File(decodePath);
		dirFilter = new FileFilter()
		{
			@Override
			public boolean accept(File p1)
			{
				return p1.isDirectory();
			}
		};
		smaliFilter = new FilenameFilter()
		{
			@Override
			public boolean accept(File p1, String p2)
			{
				return p2.endsWith(".smali");
			}
		};
		xmlFilter = new FilenameFilter()
		{
			@Override
			public boolean accept(File p1, String p2)
			{
				return p2.endsWith(".xml");
			}
		};
		startReplacing(rootDir);
    }

	private void startReplacing(File rootDir)
	{
		File[] subDirs = rootDir.listFiles(dirFilter);
		if (subDirs != null)
		{
			for (File subDir : subDirs)
			{
				if (subDir.getName().equals("res"))
				{
					startReplacing(subDir);
				}
				else if (subDir.getName().startsWith("smali"))
				{
					replacer(subDir, smaliFilter);
				}
				else if (subDir.getName().startsWith("layout"))
				{
					replacer(subDir, xmlFilter);
				}
				else if (subDir.getName().startsWith("values"))
				{
					replacer(subDir, xmlFilter);
				}
			}
		}
	}

	private void replacer(File dir, FilenameFilter filter)
	{
		File[] files = dir.listFiles(filter);
		File[] subDirs = dir.listFiles(dirFilter);
		if (files != null)
		{
			for (File file : files)
			{
				String content = readToString(file);
				if (content == null)
					continue;
				if (filter == smaliFilter)
				{
					for (int i = 0; i < smaliPatterns.length; i++)
					{
						content = content.replaceAll(smaliPatterns[i], smaliReplacement[i]);
					}
				}
				else
				{
					for (int i = 0; i < xmlPatterns.length; i++)
					{
						content = content.replaceAll(xmlPatterns[i], xmlReplacement[i]);
					}
				}
				writeToFile(file, content);
			}
		}
		if (subDirs != null)
		{
			for (File subDir : subDirs)
			{
				replacer(subDir, filter);
			}
		}
	}

    private String readToString(File file)
	{
		try
		{
			String  line;
			boolean contains = false;
			StringBuilder response = new StringBuilder();
			FileInputStream fis = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			while ((line = reader.readLine()) != null)
			{
				response.append(line).append("\n");
				if (!contains)
				{
					String lineToLow = line.toLowerCase();
					for (String check : checkAds)
					{
						if (lineToLow.indexOf(check.toLowerCase()) >= 0)
						{
							contains = true;
							break;
						}
					}
				}
			}
			return (contains ? response.toString() : null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private void writeToFile(File file, String string)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(string.getBytes("UTF-8"));
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

