// Патч для удаления рекламы
// Требуется файлы RemoveFuckingAds.smali и rules.txt
// © Maximoff, 2020

package ru.maximoff.patch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.concurrent.TimeUnit;

public class RemoveFuckingAds implements Runnable
{

	private FilenameFilter mFilter;

	private File mFile;

	private ExecutorService mThreadService;

	@Override
	public void run()
	{
		String content = readToString(mFile);
		if (content == null)
			return;
		String str = content;

		if (mFilter == smaliFilter)
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

		if (str != content)
			writeToFile(mFile, content);
	}



	private static String[] checkAds = {
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
	private static String[] smaliPatterns = {
		"invoke-.*Lcom/google/android/gms/(internal|ads).*;->addView\\([^\\)]*\\)V",
		"invoke-.*(/adbuddiz/|/adcolony/|/addapptr/|/adjust/|/adincube/|/adknowledge/|/admarvel/|/admob/|/ads/|/adsdk/|/adserver/adview/|/aerserv/|/airpush/|/altamob/|/appAdForce/|/appbrain/|/appenda/|/applovin/|/appnext/|/appnexus/|/appodeal/|/appia/|/apprupt/|/apsalar/|/avocarrot/|/boxdigital/sdk/ad/|/branch/|/chartboost/|/crashlytics/|/duapps/ad|/fabric/|/flurry/|/fyber/|/google/android/gms/internal/|/greystripe/|/heyzap/|/hyprmx/|/inmobi/|/inneractive/|/instreamatic/|/integralads/|/ironsource/|/jirbo/|/jumptap/|/kochava/|/Leadbolt/|/localytics/|/loopme/|/madsdk/|/mdotm/|/mediabrix/|/millennialmedia/|/mngads/|/moat/|/mobclix/|/mobfox/sdk/|/mobvista/|/mologiq/analytics/|/moolah/|/montexi/|/mopub/|/my/target/|/nexage/|/onelouder/adlib/|/openx/|psm/advertising/|/pubmatic/|/revmob/|/shark/adsert/|/smaato/SOMA/|/smartadserver/|/startapp/|/tagmanager/|/tapjoy/|/unity3d/ads|/vdopia/|/vungle/|/xtify/android/sdk/|/zestadz/android/|ad/AdmobInterstitial|ad/AdmobNative|ads/FullAdmob|NativeAdViewAdmob|InlineAd|/NativeInterstitial|ru/boxdigital/sdk/ad/).*->(hasVideoContent|addHtmlAdView|admob|animateAdView|bannerAdmobMainActivity|beginFetchAds|canBeUsed|displayDownloadImageAlert|doBannerClick|downloadAndDisplayImage|expandAd|fetchAd|forceShowInterstitial|handleShow|incrementMetric|initBanner|initializeAds|initializeAdSDK|internalLoadAd|load|loadAd|loadAdFromBid|loadAds|loadAssetIntoView|loadBanner|loadBannerAd|loadBanners|loadBlocksInfo|loadChildAds|loadCustomEvent|loadData|loadDataWithBaseURL|loadDoAfterService|loadFromServer|loadFullscreen|loadHtml|loadHtmlResponse|loadImages|loadImageView|loadIncentivizedAd|loadInterstitial|loadInterstitialAd|loadList|loadMidPoint|loadNativeAd|loadNativeAds|loadNextAd|loadNextMediatedAd|loadNonJavascript|loadRewardedVideo|loadUrl|loadVideo|loadVideoAds|loadVideoUrl|mraidVideoAddendumInterstitialShow|nativeAdLoaded|onEvent|playVideo|preload|preloadAd|preloadHtml|preloadNativeAdImage|preloadUrl|pushAdsToPool|refreshAds|requestAd|requestBannerAd|requestInterstitial|requestInterstitialAd|setNativeAppwallBanner|setupBanner|shouldShowInterstitial|show|showAd|showAds|showAdInActivity|showAdinternal|showAndRender|showAsInterstitial|showBanner|showBannerAbsolute|showBannerInPlaceholder|showContent|showCustomEventInterstitial|showFullscreen|showIncentivizedAd|showInterstitial|showInterstitialAd|showOfferWall|showMoPubBrowserForUrl|showNativeContentAdView|showNativeAppInstallAdView|showPopup|showPopupExit|showPoststitial|showPreparedVideoFallbackAd|showSplash|showVideo|showWebPage|startAdLoadUponLayout|startMetric|submitAndResetMetrics|isLoading|uploadReports)\\(.*\\)(V|Z)"
	};
	private static String[] smaliReplacement = {
		"invoke-static {}, Lremove/fucking/ads/RemoveFuckingAds;->a()V #Remove-Fucking-Ads; Original: $0",
		"invoke-static {}, Lremove/fucking/ads/RemoveFuckingAds;->a()$3 #Remove-Fucking-Ads; Original: $0"
	};
	private static final String[] xmlPatterns = {
		"<([^>]+)(android:id=\"@id/(?i)((ads?|banner|adview)_?layout)\")([^>]+)android:layout_width=\"[^\"]+ent\"([^>]+)android:layout_height=\"[^\"]+ent\"",
		"<com\\.google\\.android\\.gms\\.ads\\.AdView([^>]+)android:layout_width=\"[^\"]+ent\"([^>]+)android:layout_height=\"[^\"]+ent\"",
		"ca-app-pub"
	};
	private static final String[] xmlReplacement = {
		"<!-- #Remove-Fucking-Ads -->\n<$1$2$5android:layout_width=\"0dip\"$6android:layout_height=\"0dip\"",
		"<!-- #Remove-Fucking-Ads -->\n<com.google.android.gms.ads.AdView$1android:layout_width=\"0dip\"$2android:layout_height=\"0dip\"",
		"Remove-Fucking-Ads"
	};
	private static FileFilter dirFilter;
	private static FilenameFilter smaliFilter;
	private static FilenameFilter xmlFilter;

    public RemoveFuckingAds()
	{}

	public RemoveFuckingAds(File f, FilenameFilter filter)
	{
		this.mFile = f;
		this.mFilter = filter;
	}

    public void patch(String apkPath, String patchPath, String decodePath, String param)
	{
		mThreadService = Executors.newFixedThreadPool(8);
        File rootDir = new File(decodePath);
		try
		{
			getRules(new File(patchPath));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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

		mThreadService.shutdown();

		try
		{
			while (!mThreadService.awaitTermination(1, TimeUnit.SECONDS))
			{}
		}
		catch (InterruptedException e)
		{}
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
				mThreadService.submit(new RemoveFuckingAds(file, filter));
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


    private synchronized String readToString(File file)
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

	private synchronized void writeToFile(File file, String string)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(string.getBytes("UTF-8"));
			fos.flush();
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void getRules(File patchFile) throws Exception
	{
        ZipFile zip = new ZipFile(patchFile);
        Enumeration e = zip.entries();
        while (e.hasMoreElements())
		{
            ZipEntry entry = (ZipEntry) e.nextElement();
			if (entry.getName().equals("rules.txt"))
			{
				InputStream is = zip.getInputStream(entry);
				try
				{
					List<String> rules = parseRules(zip.getInputStream(entry));
					if (!rules.isEmpty())
					{
						mergeRules(rules);
					}
				}
				finally
				{
					is.close();
				}
			}
        }
	}

	private List<String> parseRules(InputStream input)
	{
		List<String> result = new ArrayList<String>();
		try
		{
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			while ((line = reader.readLine()) != null)
			{
				line = line.trim();
				if (!line.startsWith("#") && !line.equals(""))
					result.add(line);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private void mergeRules(List<String> rules)
	{
		String[] newCheckAds = new String[checkAds.length + rules.size()];
		for (int i = 0; i < checkAds.length; i++)
		{
			newCheckAds[i] = checkAds[i];
		}
		String pattern = "\"[^\"]*(";
		for (int i = 0; i < rules.size(); i++)
		{
			if (i > 0)
				pattern += "|";
			pattern += Pattern.quote(rules.get(i));
			newCheckAds[i + checkAds.length] = rules.get(i);
		}
		pattern += ")[^\"]*\"";
		String[] newSmaliPatterns = new String[smaliPatterns.length + 1];
		String[] newSmaliReplacement = new String[smaliPatterns.length + 1];
		for (int i = 0; i < smaliPatterns.length; i++)
		{
			newSmaliPatterns[i] = smaliPatterns[i];
			newSmaliReplacement[i] = smaliReplacement[i];
		}
		newSmaliPatterns[smaliPatterns.length] = pattern;
		newSmaliReplacement[smaliPatterns.length] = "\"Remove-Fucking-Ads\" #Remove-Fucking-Ads; Original: $0";
		checkAds = newCheckAds;
		smaliPatterns = newSmaliPatterns;
		smaliReplacement = newSmaliReplacement;
	}
}
