# Amadeus by Jos Vlaar
Amadeus is an app for music lovers and collectors. Many still keep extensive libraries of music with them on their phones.
With a properly tagged and organized library the possibilities for a nice listening experience become endless! Unfortunately most current android music apps only support basic filtering on tags like artist, album or genre.

Amadeaus brings smart playlists to android, allowing users to combine all id-3 tags in their own way to create perfect playlists!

# Solution

## Visual sketch
In its core Amadeus aims to remain simple and easy to use. The app is all about giving users easy acces to their favourite music, so that's what it's designed to do.
![Visual sketch](/doc/sketch.png "Visual sketch")

## Features
* Select and play music from playlists ^1
* View default playlists based album, artist or genre ^1
* Search through music
* Create smart playlists based on filter criteria ^1
* Adjust the rating of songs
* Have a widget of the music player on the home screen.

_^1 minimum viable product_

## Prequisites
At the core of the app is an index of all music files using *SQLite* to allow for smart indexing and searching.

A *service* needs to be used in order to let the app play music while the user is doing other things.

A *widget* needs to be made, the widget needs to show the current playing song and playback controls.

## Similar apps
A very similar app is RocketPlayer. This app functions like a normal music player. The app is robust and works decent but lacks functionality for smart playlists.

## Risks and potential roadblocks
A large risk in the building of this app is the amount of unknown things. A lot of the functionality that this app will implement many things that have never been touched upon in the minor. For example handling music and media and more complex user interfaces.
Implementing a service could be challenging but this too is a unknown unknown.
