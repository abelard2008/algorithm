package com.effectiveJava;

class Song{}

class AudioClip{}

abstract class Singer {
	AudioClip sing(Song s){return null;};
}

abstract class SongWriter{
	Song compose(boolean hit){return null;};
}
public class SingerSongwriter extends Singer{

}

 