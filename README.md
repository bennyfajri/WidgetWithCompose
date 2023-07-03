# WidgetWithCompose
Simple android widget by using glance library, you can get to the android documentation  [here](https://developer.android.com/jetpack/androidx/releases/glance?hl=id)

### Implementation
build.gradle (module)
```
dependencies {
    implementation "androidx.glance:glance:1.0.0-alpha05"
    implementation "androidx.glance:glance-appwidget:1.0.0-alpha05"
}
```
<br>CounterWidget.kt
```
object CounterWidget :  GlanceAppWidget() {
    @Composable
    override fun Content() {
        ...
    }
}

class SimpleCounterWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = CounterWidget
}

class IncrementActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        ...
    }
}
```
<br>counter_widget_info.xml
```
<?xml version="1.0" encoding="utf-8"?>
<appwidget-provider xmlns:android="http://schemas.android.com/apk/res/android"
    android:description="@string/app_name"
    android:minWidth="200dp"
    android:minHeight="100dp"
    android:resizeMode="horizontal|vertical"
    android:widgetCategory="home_screen" />
```
<br>AndroidManifest.xml
```
<manifest>
    <application>
        <activity>...</activity>
        ...
  
        <receiver
            android:name=".SimpleCounterWidgetReceiver"
            android:exported="true">
            <intent-filter>
                <action android:name="android.appwidget.action.APPWIDGET_UPDATE" />
            </intent-filter>
            <meta-data
                android:name="android.appwidget.provider"
                android:resource="@xml/counter_widget_info" />
        </receiver>
    </application>
</manifest>
```
### preview :
<img src="https://github.com/bennyfajri/WidgetWithCompose/blob/master/preview/Android%20Emulator%20-%20Pixel_4_Android_12_5554%202023-07-03%2015-26-20.gif" alt="drawing" width="230" height="480"/>

