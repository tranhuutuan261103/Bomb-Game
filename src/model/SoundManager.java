package model;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
	private String[] _relativePaths;
	
	public SoundManager(String[] relativePaths) {
		this._relativePaths = relativePaths;
	}

	public String[] getRelativePaths() {
		return _relativePaths;
	}

	public void setRelativePaths(String[] relativePaths) {
		this._relativePaths = relativePaths;
	}
	
	public void playVoiceRandomly() {
		int len = _relativePaths.length;
		Random rd = new Random();
		int i = rd.nextInt(0, len);
		
		String absolutePath = new File(_relativePaths[i]).getAbsolutePath();
	    File file = new File(absolutePath);
	    try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
