# Creating a custom  List adaptor Android

 Customized ListView with Image and Text gives you a good overview of customizing a list view which contains a thumbnail image and few text fields. All the list data will be downloaded by making a network calls

# Android Studio App 

> Material Design Specifications[Navigation Drawer](http://blog.teamtreehouse.com/add-navigation-drawer-android) 
> Creating Apps with Material Design[Material Design](http://developer.android.com/training/material/index.html) 

## Running Locally
Make sure you have [Android studio/Eclipse ADB](http://developer.android.com/tools/studio/index.html) 

```sh
https://github.com/tkssharma/worldLibrary.git
import project in app studio
run gradle build 
```

## Create a Drawer Layout

Custom Library are used to show data on UI View , LoopJ is to make Async calls and Picasso is best way of image caching and loading on UI 
```sh
    compile 'com.loopj.android:android-async-http:1.4.6'
    compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'org.apache.httpcomponents:httpclient:4.0-alpha4'
```


```sh
AsyncHttpClient client = new AsyncHttpClient();
client.get("https://www.google.com", new AsyncHttpResponseHandler() {

    @Override
    public void onStart() {
        // called before request is started
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
        // called when response HTTP status is "200 OK"
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
    }

    @Override
    public void onRetry(int retryNo) {
        // called when request is retried
	}
});
```