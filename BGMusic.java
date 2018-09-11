

package Sudoku;
import java.net.URL;
import javax.sound.sampled.*;
 
/** Test playing sound file */
@SuppressWarnings("serial")
public class BGMusic{
   static Clip clip;  
  // static Clip soundClipbgmusic;// Java Internal Sound Clip

   
   /** Constructor to setup the GUI components and sound clips */
   public BGMusic() {
	   
	   // Prepare the Sound Clip
      try {
         // Generate an URL from filename
         URL url = this.getClass().getClassLoader().getResource("BGMUSIC.wav");
         if (url == null) {
            System.err.println("Couldn't find file: " + "BGMUSIC.wav");
         } else {
            // Get an audio input stream to read the audio data
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Allocate a sound clip, used by Java internal
            clip = AudioSystem.getClip();
            // Read the audio data into sound clip
            clip.open(audioIn);
         }
      } catch (UnsupportedAudioFileException e) {
         System.err.println("Audio Format not supported: " + "BGMUSIC.wav");
      } catch (Exception e) {
         e.printStackTrace();
      }      
            // Play sound clip
      if (clip.isRunning()) clip.stop();
      	  clip.setFramePosition(0); // rewind to the beginning
          clip.start();             // start playing
      
	}
   public static void stop(){
	   clip.stop();
   }
}


