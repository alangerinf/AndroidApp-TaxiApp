-keep public class com.google.android.gms.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

-dontwarn android.support.v7.**
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.google.android.gms.**
-dontwarn java.nio.file.Files
-dontwarn java.nio.file.Path
-dontwarn java.nio.file.OpenOption
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement


-dontwarn rx.Observale.**
-dontwarn rx.**
-dontwarn com.squareup.okhttp.internal.huc.**
-dontwarn com.google.appengine.api.urlfetch.**