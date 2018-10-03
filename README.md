
### Screenshot
![alt text](https://github.com/sauravrp/Places/blob/master/screenshots/places.gif)

### Comments by Saurav
* Code uses MVVM architecture, Dagger2, Retrofit, RxJava, RxBindings (for SearchView), Stetho for debugging, Fresco for image loading, butterknife and an Expandible layout.
* Code also utlizies some of the android architecture components as well as data bindings.

### Room for improvements.
* App uses SharedPreferences for persistent storage of favorite ids. This can be easily swapped out with a more feasible storage solution by implementing IStorageModel.
* LocationService currently is hardcoded to Seattle, WA and hardcoded latitude and longitude.
* On the Map Page, when toolbar is collapsed, title is missing. The back button could be nicer.
* Current test cases focuses on the view model classes. More test cases could be utilized such as Espresso tests and other instrumentation tests.
* Code could use more comments
* Search View currently fires a search on every character typed. One could limit this to at lease x no of characters and notify the user that at least x no. of characters is required to see results.
* Place holder icon on the main search resutls recycler view could use a nicer placeholder image.
* When the map view first loads, user briefly sees a map view of the world and the map view eventually zooms into interest points.
* Title text view on the Map page seems off by a bit (maybe too much margin).
* See class 'DataRepo.java' to add your own Foursquare API client id and secret. (See project description below) 
* Update AndroidManifest.xml with your own Google maps API_KEY.
* Animations can be added for material design look and feel. example would be to animate specifc items from the list view to the detail screen. 
* On the detail screen, the call icon should be hidden if there is no phone number available. Same goes for address field.



#### Project Description Below

### Mission: Seattle Place Search!

Your mission is to create a three-screen application that allows users to: search for places in Seattle, Washington using the [Foursquare API](https://developer.foursquare.com/places-api); view their search results in a list or on a map; and view the individual details of each search result.

The first screen should display an input field that allows users to search for places (for example, “coffee”, “pizza”, “Whole Foods”, etc) and, upon executing a search, should display the results in a list. Clicking an item in the list should launch a details screen. The details screen should show the place’s location on a map (for example, using [Google Maps](https://developers.google.com/maps/documentation/static-maps/)), provide details about the place, including website, and the user should be able to favorite the place on this screen.

On the main screen, when search results are visible, the screen should include a Floating Action Button. This button, when clicked, should a full-screen map with a pin marking the location of each search result. Clicking a pin should show the name of the place on the map, and clicking on the name should then open the details screen for the given place.

### Requirements

1. The main screen should display a search input, and should use industry best practices to perform a typeahead search against the Foursquare API.
2. When a search returns results, these should be displayed in a list format. Each list item should provide, at a minimum, the name of the place (e.g., Flitch Coffee), the category of the place (e.g., Coffee Shop), the icon from the response, the distance from the center of Seattle (47.6062° N, 122.3321° W) to the place, and whether the place has been favorited by the user. Clicking a list item should launch the details screen for that place.
3. When a search returns results, the main screen should include a [Floating Action Button](https://developer.android.com/reference/android/support/design/widget/FloatingActionButton.html). Clicking the Floating Action Button should launch a full-screen map with a pin for every search result. Clicking a pin should show the name of the place on the map, and clicking on the name should then open the details screen for the given place.
4. The details screen for a place should use a collapsible toolbar layout to show a map in the upper half of the screen, with two pins -- one, the location of search result, and the other, the center of Seattle. The bottom half of the details screen should provide details about the place, including whether or not the place is favorited, and should include a link to the place’s website (if it exists). Clicking this link should open an external Intent to a browser installed on the device.
5. Favorite selections should be changeable, should persist across launches of the app, and should show correctly on both the main and details screens.
6. The user should be able to navigate between screens according to Android platform conventions.
7. Include instructions for building the application and any relevant documentation in a README.md file
8. Please post your submission on github, or tar and zip the project directory for email or hosting on a file sharing service.

Feel free to use any libraries you feel are appropriate solutions to your technical needs.

### Notes

You will need to sign up for a free [Foursquare](https://developer.foursquare.com/places-api) account and get an API key to use it. Foursquare has a generous free tier, and the documentation describes integration as well as sample usage. A query to the Foursquare Places API could look something like:

```
curl -X GET -G 'https://api.foursquare.com/v2/venues/search' \
    -d client_id="CLIENT_ID"  \
    -d client_secret="CLIENT_SECRET" \
    -d near="Seattle,+WA" \
    -d query="coffee" \
    -d v="20180401" \
    -d limit=20
```

To display a map, you may use any solution you prefer. We suggest, for convenience, the [Google Static Map API](https://developers.google.com/maps/documentation/static-maps/) for the details screen and the [Google Maps Android API](https://developers.google.com/maps/android/) for the fullscreen map. Both have acceptable free tier limits and are well-documented.

For quick and easy icons, consider using the [Material icons](https://material.io/icons/).

### Considerations

We’re upping our game here and we aim to create world-class app experiences for our customers. Among other criteria, your submission will be evaluated on:

1. Implementation of the stated requirements
2. Application Architecture
3. The general quality of the code and it’s resistance to crashing
4. Your use of Android coding conventions
5. Knowledge and usage of Android libraries and sdk’s
6. Clarity of communications in comments and other documentation
7. UI and UX -- while we don’t want you to spend any time creating custom icons or other assets, the app should look clean and generally obey the Human Interface Guidelines
