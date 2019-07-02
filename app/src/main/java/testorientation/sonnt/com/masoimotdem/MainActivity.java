package testorientation.sonnt.com.masoimotdem;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {

    private TextView mTextMessage;
    private EditText mETextMessage;
    private TextToSpeech textToSpeech;

    Button speakBtn, stopBtn;
    String stringToSpeak;
    CheckBox fox, eyes, thief, drunk, silent, soundEffect;

    MediaPlayer player, playerHeart;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        textToSpeech = new TextToSpeech(this, this);
        textToSpeech.setOnUtteranceCompletedListener(this);

        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(soundEffect.isChecked()){

                    speak();
                    playMusic();

                }else{
                    speak();
                }



            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.stop();

                stopMusic();
            }
        });
    }

    private void speak(){
        getStringToSpeak();
        textToSpeech.speak(stringToSpeak,TextToSpeech.QUEUE_FLUSH, null);
    }

    private void playMusic(){
        player.start();
        playerHeart.start();
    }

    private void stopMusic(){
        player.stop();
        playerHeart.stop();
    }

    private void init(){
        speakBtn = (Button) findViewById(R.id.speak_btn);
        stopBtn = (Button) findViewById(R.id.stop_btn);

        fox       = (CheckBox) findViewById(R.id.ob_fox);
        eyes      = (CheckBox) findViewById(R.id.ob_eyes);
        thief     = (CheckBox) findViewById(R.id.ob_thief);
        drunk     = (CheckBox) findViewById(R.id.ob_drunk);
        silent    = (CheckBox) findViewById(R.id.ob_silent);
        soundEffect    = (CheckBox) findViewById(R.id.ob_sound);

        prepareBgMusic();
        prepareSoundHeart();

    }

    private void playSoundWolf(){
        try{
            AssetFileDescriptor afd = getAssets().openFd("wolf.mp3");
            MediaPlayer player = new MediaPlayer();
//            player.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
            player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            player.prepare();
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    speak();
                    playMusic();
                }
            });
        }catch (Exception e){

        }
    }

    private void prepareBgMusic(){
        try{

            AssetFileDescriptor afd = getAssets().openFd("background_sound.mp3");
            player = new MediaPlayer();
//            player.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
            player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            player.prepare();
            player.setLooping(true);
            player.setVolume(0.2f, 0.2f);

        }catch (Exception e){
            Toast.makeText(this, "sound err", Toast.LENGTH_LONG);
        }
    }

    private void prepareSoundHeart(){
        try{
            AssetFileDescriptor afd = getAssets().openFd("heartbeat.mp3");
            playerHeart = new MediaPlayer();
//            player.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
            playerHeart.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            playerHeart.prepare();
            playerHeart.setLooping(true);
        }catch (Exception e){

        }
    }

    //test commit 2
    private void getStringToSpeak(){

        stringToSpeak = getString(R.string.sleep);
        if(fox.isChecked()){
            stringToSpeak += getString(R.string.fox);
        }

        if(eyes.isChecked()){
            stringToSpeak += getString(R.string.eyes);
        }

        if(thief.isChecked()){
            stringToSpeak += getString(R.string.thief);
        }

        if(drunk.isChecked()){
            stringToSpeak += getString(R.string.drunk);
        }

        if(silent.isChecked()){
            stringToSpeak += getString(R.string.silent);
        }

        stringToSpeak += getString(R.string.wake_up);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
        }


    }

    @Override
    public void onUtteranceCompleted(String s) {
        textToSpeech.stop();

        player.stop();
    }
}
