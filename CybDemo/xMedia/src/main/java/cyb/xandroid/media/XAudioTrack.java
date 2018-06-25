package cyb.xandroid.media;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import java.util.Date;

/**
 * Created by asus on 2018/3/27.
 */
public class XAudioTrack {
    //Test case 1: setStereoVolume() with max volume returns SUCCESS

    public static final int STATE_AUDIO_PLAYING = 1;

    private int mState;

    //采样率，每秒16k 个点
    final int SAMPLE_RATE_IN_HZ = 16000;
    //声道:单声道后双声道
    final int CHANNEL_CONFIG = AudioFormat.CHANNEL_OUT_STEREO;
    //采样率精度（深度，位宽）
    final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    //播放模式：MODE_STREAM/MODE_STREAM
    final int AUDIO_MODE = AudioTrack.MODE_STREAM;
    //播放的音频类型
    final int STREAM_TYPE = AudioManager.STREAM_MUSIC;

    public void play(byte[] datas) throws Exception {
        //采样率，每秒16k 个点
        final int SAMPLE_RATE_IN_HZ = 16000;
        //声道:单声道后双声道
        final int CHANNEL_CONFIG = AudioFormat.CHANNEL_OUT_STEREO;
        //采样率精度（深度，位宽）
        final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
        //播放模式：MODE_STREAM/MODE_STREAM
        final int AUDIO_MODE = AudioTrack.MODE_STREAM;
        //播放的音频类型
        final int STREAM_TYPE = AudioManager.STREAM_MUSIC;

        // 稍后详细分析 getMinBufferSize
        int minBuffSize = AudioTrack.getMinBufferSize(SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT);
        // 创建一个 AudioTrack 实例
        AudioTrack audioTrack = new AudioTrack(
                //
                STREAM_TYPE,
                //
                SAMPLE_RATE_IN_HZ,
                //
                CHANNEL_CONFIG,
                //
                AUDIO_FORMAT,
                //
                minBuffSize,
                //
                AUDIO_MODE);
        // 调用 play() 开始播放
        audioTrack.play();
        byte[] data = new byte[minBuffSize];
        while (mState == STATE_AUDIO_PLAYING) {
            // 调用 write() 写入回放数据
            audioTrack.write(data, 0, data.length);
        }
        // 播放完成后，调用 release() 释放 AudioTrack 实例
        audioTrack.stop();
        audioTrack.release();
    }

//    public void play(byte[] datas) throws Exception {
//        if (mState < STATE_AUDIO_PLAYING) {
//            synchronized (XAudioTrack.class) {
//                if (mState < STATE_AUDIO_PLAYING) {
//                    mState = STATE_AUDIO_PLAYING;
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    }).start();
//                }
//            }
//        }
//    }
//
//    private void play() {
//        if(){
//
//        }
//    }

}
