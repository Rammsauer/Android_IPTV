package com.rammsauer.tv

val M3U_REGEX = """(#EXTINF:-\d+) (.*)(tvg-name=")(.*)" (tvg-logo=")(https*:.*)" (group-title=")(.*)",(.*)\n(https*:.*)""".toRegex()
val HTTP_REGEX = """(https*)""".toRegex()

const val gistUrl = "https://raw.githubusercontent.com/Free-TV/IPTV/master/playlist.m3u8"