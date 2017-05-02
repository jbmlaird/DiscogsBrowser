[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=58ff64f23f33870001d2e016&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/58ff64f23f33870001d2e016/build/latest?branch=master)

![app_logo](app/src/main/res/drawable-xxxhdpi/ic_app.png)

![app_gif](https://media.giphy.com/media/UHn06Zh8EBCGA/giphy.gif) ![another_app_gif](https://media.giphy.com/media/3IH00o747keju/giphy.gif)

# DiscogsBrowser
Android app to interact with Discogs' API

# Usage

This build is login only.

# Features

* Reactive search
* Search history
* Displays artist, master, release and label information.
* Displays marketplace information on release page NB: marketplace searches only return 12"
* Collection/wantlist editing
* Request caching

# Forthcoming

* Browsing history
* Recent releases
* Browsing without being logged in
* Recommendation system
* Adding notes to items in collection

# Desirable but no plans to implement

* Listen to releases in-app. This would require an architecture change. The whole app has to be one activity to keep a YouTube window displaying in the bottom-right.
* Carousels for images. Images are different sizes so I think showing extra images is unnecessary.
* Seeing/posting order comments/updates. The API restricts this to only being able to see notes for your own orders where you're the seller.

# Limitations

Currently, in April 2017, the public API does not allow any of the following:
* Order placing
* Marketplace actions. This includes purchasing in app, viewing purchases or searching listings.
* Seeing order comments/updates for purchases
* Notifications

# License

This project is licensed under the terms of the MIT license.
