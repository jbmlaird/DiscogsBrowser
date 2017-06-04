[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=58ff64f23f33870001d2e016&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/58ff64f23f33870001d2e016/build/latest?branch=master)

<img src="/images/featuregraphic.png" height=300/>

# DiscogsBrowser
Native Android app to interact with the [Discogs' public API](https://www.discogs.com/developers). This is production-ready and tested code that is an example of a live app with modern architecture.

![app_gif](https://media.giphy.com/media/dVWMCW4rFiyZi/giphy.gif)

<img src="/images/youtube.gif" height=500/> <img src="https://cloud.githubusercontent.com/assets/16595870/26754181/6d06b75e-486d-11e7-8be0-6245fc3e54b8.png" height=500 /><img src="https://cloud.githubusercontent.com/assets/16595870/26754182/6e0cb252-486d-11e7-9a72-491579315242.png" height=500 />

## Usage

<a href="https://play.google.com/store/apps/details?id=bj.vinylbrowser"><img src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" height=80/></a>

Due to the Play Store Impersonation policy it's uploaded with the name VinylBrowser and uses a different feature graphic.

## Technical

Android development is in a great place with all of the libraries available. This app uses the usual suspects:
* [Dagger2](https://github.com/google/dagger)
* [Retrofit2](https://github.com/square/retrofit)
* [RxJava2](https://github.com/ReactiveX/RxJava)/[RxAndroid](https://github.com/ReactiveX/RxAndroid)/[RxKotlin](https://github.com/ReactiveX/RxKotlin)

Plus:
* [RxBinding2](https://github.com/JakeWharton/RxBinding) - turn your views into RxJava Observables
* [RxCache](https://github.com/VictorAlbertos/RxCache) - reactive caching
* [Airbnb's Epoxy](https://github.com/airbnb/epoxy) - never use RecyclerView.Adapter again
* [GreenDao](https://github.com/greenrobot/greenDAO) - SQLite ORM
* [Conductor](https://github.com/bluelinelabs/Conductor) - fragment functionality without Fragments

Bug/leak catching:
* [BugSnag](https://www.bugsnag.com/product/)
* [LeakCanary](https://github.com/square/leakcanary)

Of course, the usual testing frameworks plus [DaggerMock](https://github.com/fabioCollini/DaggerMock)
* JUnit4
* Mockito
* Espresso
* [DaggerMock](https://github.com/fabioCollini/DaggerMock) - will change your life

See the [build.gradle](https://github.com/jbmlaird/DiscogsBrowser/blob/master/build.gradle) for full list of libraries.

## Discogs API Limitations

Currently, in May 2017, the public API does not allow any of the following:
* Order placing/purchasing
* Marketplace actions. This includes purchasing in app, viewing purchases or searching listings.
* Seeing order comments/updates for purchases
* Recent releases
* Notifications

## Contributions

Pull requests are welcome and encouraged! Unit tests are necessary to be merged and UI tests preferable (if a UI change).

## License

This project is licensed under the terms of the MIT license.
