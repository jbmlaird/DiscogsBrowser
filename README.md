[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=58ff64f23f33870001d2e016&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/58ff64f23f33870001d2e016/build/latest?branch=master)

![app_logo](app/src/main/res/drawable-xxxhdpi/ic_app.png)

![app_gif](https://media.giphy.com/media/UHn06Zh8EBCGA/giphy.gif)

# DiscogsBrowser
Android app to interact with Discogs' API

# Usage

This build is login only.

It is not ready for consumers at this stage.

# Features

* Reactive search
* Search history
* Displays artist, master, release and label information.
* Displays marketplace information on release page NB: marketplace searches only return 12"
* Collection/wantlist editing
* Request caching

# Forthcoming

* Carousels for images
* Browsing history
* Recent releases
* Browsing without being logged in
* Recommendation system
* Adding notes to items in collection
* Seeing order comments/updates

# Desirable but no plans to implement

* Listen to releases in-app. This would require an architecture change and would have to use a library like [DraggablePanel](https://github.com/pedrovgs/DraggablePanel). Effectively, the whole app has to be one activity to keep the window displaying in the bottom-right.

# Limitations

Currently, in April 2017, the public API does not allow any of the following:
* Order placing
* Marketplace actions. This includes purchasing in app, viewing purchases or searching listings.
* Notifications

# License

This project is licensed under the terms of the MIT license.
