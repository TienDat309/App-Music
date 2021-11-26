package com.example.mp3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


@SuppressLint("SimpleDateFormat") 
public class MainActivity extends Activity {

	TextView txtTitle, txtTimeSong, txtTimeTotal;
	SeekBar skSong;
	ImageView imgHinh;
	ImageButton btnPrev, btnPlay, btnStop, btnNext;
	
	ArrayList<Song> arraySong;
	int position = 0;
	MediaPlayer mediaPlayer;
	Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Mp3();
        AddSong();
        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        KhoiTaoMediaPlayer();
        
        
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
		
		txtTitle.setText(arraySong.get(position).getTitle());
		
		btnNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				position++;
				if(position > arraySong.size() - 1){
					position = 0;
				}
				if(mediaPlayer.isPlaying()){
					mediaPlayer.stop();
				}
				KhoiTaoMediaPlayer();
				mediaPlayer.start();
				btnPlay.setImageResource(R.drawable.pause);
				SetTimeTotal();
				UpdateTimeSong();
			}
		});
		
	btnPrev.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					position--;
					if(position < 0){
						position = arraySong.size() - 1;
					}
					if(mediaPlayer.isPlaying()){
						mediaPlayer.stop();
					}
					KhoiTaoMediaPlayer();
					mediaPlayer.start();
					btnPlay.setImageResource(R.drawable.pause);
					SetTimeTotal();
					UpdateTimeSong();
				}
			});
		
		btnStop.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View view){
					mediaPlayer.stop();
					mediaPlayer.release();
					btnPlay.setImageResource(R.drawable.play);
					KhoiTaoMediaPlayer();
				}
		});        
        btnPlay.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View view) {
				if(mediaPlayer.isPlaying()){
					//nếu đang phát -> pause -> đổi hình play
					mediaPlayer.pause();
					btnPlay.setImageResource(R.drawable.play);
				}else{
					//đang ngừng -> phát ->đổi hình pause
					mediaPlayer.start();
					btnPlay.setImageResource(R.drawable.pause);
				}
				SetTimeTotal();
				UpdateTimeSong();
				imgHinh.startAnimation(animation);
				
			}
		});
        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
        	
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				mediaPlayer.seekTo(skSong.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
        });
                  
    }
    private void UpdateTimeSong(){
    	final Handler handler = new Handler();
    	handler.postDelayed(new Runnable() {
			
			@SuppressLint("SimpleDateFormat") @Override
			public void run() {
				// TODO Auto-generated method stub
				SimpleDateFormat dinhDinhGio = new SimpleDateFormat("mm:ss");
				txtTimeSong.setText(dinhDinhGio.format(mediaPlayer.getCurrentPosition()));
				//update progress sksong
				skSong.setProgress(mediaPlayer.getCurrentPosition());
				//kiểm tra thời gian bài hát -> nếu kết thúc -> next
				mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						position++;
						if(position > arraySong.size() - 1){
							position = 0;
						}
						if(mediaPlayer.isPlaying()){
							mediaPlayer.stop();
						}
						KhoiTaoMediaPlayer();
						mediaPlayer.start();
						btnPlay.setImageResource(R.drawable.pause);
						SetTimeTotal();
						UpdateTimeSong();
					}
				});
			    handler.postDelayed(this,500);
			}
		},100);
    }
    private void SetTimeTotal(){
    	SimpleDateFormat dinhDanhGio = new SimpleDateFormat("mm:ss");
    	txtTimeTotal.setText(dinhDanhGio.format(mediaPlayer.getDuration()));
    	//gán max của sksong = mediaPlayer.getDuration()
    	skSong.setMax(mediaPlayer.getDuration());
    }
    
    private void KhoiTaoMediaPlayer(){
    	mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
    	txtTitle.setText(arraySong.get(position).getTitle());
    }
    
    private void AddSong() {
		// TODO Auto-generated method stub
		arraySong = new ArrayList<Song>();
		arraySong.add(new Song("Đã đến lúc", R.raw.da_den_luc));
		arraySong.add(new Song("Một lần thương cả đời", R.raw.mot_lan_thuong_ca_doi));
		arraySong.add(new Song("Trên tình bạn dưới tình yêu", R.raw.tren_tinh_ban_duoi_tinh_yeu));
		arraySong.add(new Song("Viên đá nhỏ", R.raw.vien_da_nho));
		arraySong.add(new Song("Yêu là cưới", R.raw.yeu_la_cuoi));
	}


	private void Mp3() {
		// TODO Auto-generated method stub
		txtTimeSong = (TextView) findViewById(R.id.textViewTimeSong);
		txtTimeTotal = (TextView) findViewById(R.id.TextViewTimeTotal);
		txtTitle = (TextView) findViewById(R.id.textviewTitle);
		skSong = (SeekBar) findViewById(R.id.seekBarSong);
		btnNext = (ImageButton) findViewById(R.id.imageButtonNext);
		btnPlay = (ImageButton) findViewById(R.id.imageButtonPlay);
		btnPrev = (ImageButton) findViewById(R.id.imageButtonPrev);
		btnStop = (ImageButton) findViewById(R.id.imageButtonStop);
		imgHinh = (ImageView) findViewById(R.id.imageViewDisc);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
