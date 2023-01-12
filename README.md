# Overview
An Android app to play public .m3u8 streams from TV channels around the world on your cell phone. 
The app was written in Kotlin and the UI was implemented using [Jetpack Compose](https://developer.android.com/jetpack/compose). 
In addition, the app has a database based on [Room](https://developer.android.com/training/data-storage/room) to store channel information such as "Logo", "Country" or "Channel name". 
<br>
<br>
The initial channel when starting the app is always arte, other channels can be played by simply clicking on the elements in the list.


## Videopool
The pool of m3u8 streams comes from the GitHub project [IPTV](https://github.com/Free-TV/IPTV). 

In the app, the [raw file](https://raw.githubusercontent.com/Free-TV/IPTV/master/playlist.m3u8) is parsed using a regex and transferred to the app.

## Currently not possible may be added soon or later
 - [ ] Set the app to fullscreen in landscape mode
 - [ ] Manually add your own m3u8 streams
 - [ ] Program information about the selected channel

---
![AndreasRoller](AndreasRoller.jpg)
