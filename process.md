# Day 1
During day 1 I worked on creating the initial proposal

# Day 2
On day 2 I was very tired and didn't accomplish very much.

# Day 3
On day 3 I finished the first version of the design document and made a small
start on working on the app itself.

# Day 4
On day 4 I started working on implementing music playback. I started following
this tutorial: https://code.tutsplus.com/tutorials/create-a-music-player-on-android-project-setup--mobile-22764

# Day 5
I continued working on following the tutorial and implementing music playback.
The tutorial is a great help but I needed to figure out how to adapt it's contents
to fragments.

# Day 6
The start of week 2. I messed around with fragments some more, I created a tabbed activity
through one of Android Studio's defaults but I'm now figuring out how to actually use it.

# Day 7
I put off fragments for later, it's more important to get music playback working first.
The first screen works and shows songs. Today I started part 2 of the tutorial: https://code.tutsplus.com/tutorials/create-a-music-player-on-android-song-playback--mobile-22778
This part focuses on music playback and writing a service.

# Day 8
I continued following the tutorial and working on music playback

# Day 9 
Unfortunately the alarm that I was expecting to wake me up was never set.
I continued working on the playback part. Pressing play shows no errors, but no sound either.
Let the debugging commence!

# Day 10
I found out why playback wasn't starting and I was able to fix it. Sort of. Now an exception is raised
so more fixing is needed. 

# Day 11
I spent all of day 10 trying to get music playback to work. It mostly works now, exceptions are only
raised sometimes now. Today I'm starting on implementing the whole database part that's going to be
part of the app.

# Day 12
I made good progress on the database. I got a helper class and got the database set up. Right now
it's not doing much yet. I also created a SmartPlaylist class which will serve as the way a list of
songs is getting put together.

# Day 13
The whole database part is a little more work than initially expected. I am making good progress however.
Filling the database from a ContentResolver now works. I made a SongAdapter to show songs in a ListView.

# Day 14
Today I worked on getting a list of songs based on a SmartPlaylist object. This now works. I can use
this as a basis for all other views. I made a PlaylistAdapter to show a list of different playlists.
I can use this to create the lists of genres/albums/artists as those are essentially just playlists too.

# Day 15
The time has come to implement the fragment navigation. This required me to move a whole bunch of functions
and logic around. Some of the functions I initially had in the SongFragment needed to move to the MainActivity
so that it is reusable for other fragments.

# Day 16
I made fragments for all the different views, however the tabbar is not handling them properly yet.
While they do show (I think) they're not loading any songs yet. I spent the whole afternoon trying
to fix this issue.

# Day 17
Time is running short and I'm behind on schedule. Today I got the tabbar to work and fragments now
show their proper lists most of the time. When clicking on a artist/genre/album nothing happens yet.

# Day 18
As I'm still behind schedule it's time to pull an all-nighter. I stayed up all night and made a lot of progress
I got the fragment navigation to work now. Including child fragments for clicking through to other lists.
I also implemented the whole adding playlists activity and the playlist fragment to go with that. The app is
now mostly finished.

# Day 19
I added some small exception handling here and there. And writing documentation. I neglected most of that
since I was in a rush the last few days so I'm playing catch-up now.