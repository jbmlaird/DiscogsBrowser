[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=58ff64f23f33870001d2e016&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/58ff64f23f33870001d2e016/build/latest?branch=master)

![app_logo](app/src/main/res/drawable-xxxhdpi/ic_app.png)

# DiscogsBrowser
Android app to interact with Discogs' API

![app_gif](https://media.giphy.com/media/UHn06Zh8EBCGA/giphy.gif) ![another_app_gif](https://media.giphy.com/media/3IH00o747keju/giphy.gif)

# Usage

Get it on the Play Store: [link to come]

# Features

* Displays artist, master, release and label information.
* Displays marketplace information on release page NB: marketplace searches only return 12"
* Collection/wantlist editing

# Forthcoming

* Currency localisation
* Browsing without being logged in
* Intelligent recommender system 

# Desirable but no plans to implement

* Listen to releases in-app. This would require an architecture change and have to work like the YouTube app (windowed videos)
* Carousels for images
* Order comments/updates. The API restricts this to orders where you're the seller
* Adding notes to releases in collection

# Limitations

Currently, in April 2017, the public API does not allow any of the following:
* Order placing/purchasing
* Marketplace actions. This includes purchasing in app, viewing purchases or searching listings.
* Seeing order comments/updates for purchases
* Recent releases
* Notifications

# License

This project is licensed under the terms of the MIT license.
