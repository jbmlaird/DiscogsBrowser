# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in G:\sdk1/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-verbose
-dontpreverify
-allowaccessmodification
-mergeinterfacesaggressively
-useuniqueclassmembernames

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class bj.rxjavaexperimentation.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keepclassmembers enum * { *; }

##---------------Begin: proguard configuration for ButterKnife  ----------
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.mView.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

##---------------Begin: proguard configuration for Retrofit2 ----------

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

##---------------Begin: proguard configuration for LeakCanary ----------
-dontwarn com.squareup.haha.guava.**
-dontwarn com.squareup.haha.perflib.**
-dontwarn com.squareup.haha.trove.**
-dontwarn com.squareup.leakcanary.**
-keep class com.squareup.haha.** { *; }
-keep class com.squareup.leakcanary.** { *; }

# Marshmallow removed Notification.setLatestEventInfo()
-dontwarn android.app.Notification

##---------------Begin: proguard configuration for Glide ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

##---------------Begin: proguard configuration for Iconify ----------
-keep class com.joanzapata.iconify.** { *; }


##---------------Begin: proguard configuration for RxSocial ----------
-dontwarn javax.xml.bind.DatatypeConverter
-dontwarn org.apache.commons.codec.**
-dontwarn com.ning.http.client.**

##---------------Begin: proguard configuration for Jsoup ----------
-keeppackagenames org.jsoup.nodes

##---------------Begin: proguard configuration for Bugsnag ----------
-keepattributes LineNumberTable,SourceFile

##---------------Begin: proguard configuration for RxCache ----------
-dontwarn io.rx_cache.internal.**

##---------------Begin: proguard configuration for Okio/Okhttp3 ----------
-dontwarn okhttp3.**
-dontwarn okio.**

##---------------Begin: proguard configuration for RxSocialConnect ----------
-dontwarn javax.xml.bind.DatatypeConverter
-dontwarn org.apache.commons.codec.**
-dontwarn com.ning.http.client.**
-keep class org.fuckboilerplate.rx_social_connect.internal.persistence.OAuth1AccessToken {
    <fields>;
}
