# SpotifySample

### You need a premium Spotify account to log in

## Description

This application allows to log in to the Sptotify service using the Android Spotify sdk:
https://developer.spotify.com/documentation/android-sdk/

#### Login view
Simple view with a unique button to launch the Spotify sdk, connect the user and get the token for other calls


#### Main view 
It is composed of 3 sections and the player control 

1. Home
    * On the top is a button to launch the default song: All I Want by Tania Bowra
    * Then are the last released albums 
    * Finally there are the "Featured Playlists"


2. Discover
    * Display the list of Categories

3. Search
    * Containt an EditText to execute a search, return 3 Albums and 3 Artists matching the request

4. Player Control
    * Is available in Main Page. It is slidable followinf a vertical orientation
    * Display the name of the song, artist name, album name, and album cover.
    * Contains Play/Pause button (working)
    * Previous and Next buttons (not working)
    * Seekbar and timers (not working)


#### Details View

When selecting an album, a playlist or a category you land on the DetailsActivity

Display the choosen content

If it is an album or playlist, display the corresponding image and the list of the tracks.

Tracks are selectable and are played with the Spotify Player. When going back on the Main page, the player control is updated

If the content is a category, display the playlists of this category.

## Ways to improve

Here are the some ideas I wanted to add with more time available

* Update the UI of the Login page with an image and/or an onboarding screen
* Add more recommendations on the Home page
* Display the player control view evrywhere in the application, not only on the main screen
* Replace the track saved in the PlayerHelper by a List to manage next/preivous tracks
* Set the search results clickable 
* A 4th tab with the user profile and his/her libraries, favorites tracks
