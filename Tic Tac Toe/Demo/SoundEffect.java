import java.net.URL;
import javax.sound.sampled.*;

public enum SoundEffect {
    EAT_FOOD("audio/eatfood.wav"),
    EXPLODE("audio/explode.wav"),
    DIE("audio/die.wav"),
    MUSIC("audio/music.wav"),
    CLICKING("audio/click.wav");

    public static float sfxVolume = 100f;
    public static float musicVolume = 100f;

    private Clip clip;
    private FloatControl gainControl;

    private static boolean isBGMusicPlaying = false;

    SoundEffect(String soundFileName) {
        try {
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (this != MUSIC && sfxVolume > 0) {
            if (clip.isRunning())
                clip.stop();
            clip.setFramePosition(0);
            setVolume(sfxVolume / 100f);
            clip.start();
        }
    }

    public void play(float volume) {
        if (this != MUSIC && sfxVolume > 0) {
            if (clip.isRunning())
                clip.stop();
            clip.setFramePosition(0);
            setVolume(sfxVolume / volume);
            clip.start();
        }
    }

    private void setVolume(float volume) {
        if (gainControl == null)
            return;

        float min = gainControl.getMinimum();
        float max = gainControl.getMaximum();

        if (volume <= 0f) {
            gainControl.setValue(min);
        } else {
            float dB = (float) (Math.log10(volume) * 20.0);
            dB = Math.max(min, Math.min(dB, max));
            gainControl.setValue(dB);
        }
    }

    public static void playBGMusic() {
        if (MUSIC.clip == null)
            return;

        if (!isBGMusicPlaying) {
            MUSIC.clip.loop(Clip.LOOP_CONTINUOUSLY);
            MUSIC.clip.start();
            isBGMusicPlaying = true;
        }

        MUSIC.setVolume(musicVolume / 100f);
    }

    public static void updateAllSFXVolume(int newVolume) {
        sfxVolume = newVolume;
        for (SoundEffect sfx : SoundEffect.values()) {
            if (sfx != MUSIC) {
                sfx.setVolume(sfxVolume / 100f);
            }
        }
    }

    public static void updateBGMusicVolume(int newVolume) {
        musicVolume = newVolume;
        MUSIC.setVolume(musicVolume / 100f);
    }

    public static void initGame() {
        values(); // preload all clips
    }
}
