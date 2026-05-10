package com.example.widgetutilslib.utils

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.iflytek.cloud.*
import com.iflytek.cloud.util.ResourceUtil
import java.io.File

/**
 * Created by qlw on 2020/8/25
 *
 * @author qinliwen@yoonuu.com
 **/
class XfTtsWrapper private constructor(context: Context) {

    private var mContext: Context? = context
    private var mSpeech: SpeechSynthesizer? = null
    var callback: XfTTSCallback? = null

    init {
        setLocalParam()
    }

    fun setLocalParam(): XfTtsWrapper {
        mSpeech = SpeechSynthesizer.createSynthesizer(mContext, mListener)
        Log.d("7777777", "setLocalParam " + (mSpeech == null))
        mSpeech?.let { speech ->
            // 清空参数
            speech.setParameter(SpeechConstant.PARAMS, null)
            //设置使用本地引擎
            speech.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL)
            //设置发音人资源路�?
            speech.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath(SpeechConstant.TYPE_LOCAL))

            // 设置在线合成发音�?
            speech.setParameter(SpeechConstant.VOICE_NAME, "xiaofeng")

            speech.setParameter(SpeechConstant.SPEED, "50")
            //设置合成音调
            speech.setParameter(SpeechConstant.PITCH, "50")
            //设置合成音量
            speech.setParameter(SpeechConstant.VOLUME, "100")
            //设置播放器音频流类型
            speech.setParameter(SpeechConstant.STREAM_TYPE, "3")
            // 设置播放合成音频打断音乐播放，默认为true
            speech.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false")

            // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
            speech.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
            speech.setParameter(
                SpeechConstant.TTS_AUDIO_PATH,
                Environment.getExternalStorageDirectory().toString() + "/msc/tts.wav"
            )
        }
        return this
    }

    fun setXfParam(et: String): XfTtsWrapper {
        mSpeech = SpeechSynthesizer.createSynthesizer(mContext, mListener)
        mSpeech?.let { speech ->
            // 清空参数
            speech.setParameter(SpeechConstant.PARAMS, null)

            if (et == SpeechConstant.TYPE_LOCAL) {
                //设置使用本地引擎
                speech.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL)
                //设置发音人资源路�?
                speech.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath(et))
            } else {
                speech.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD)
                //支持实时音频返回，仅在synthesizeToUri条件下支�?
                speech.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1")
            }
            // 设置在线合成发音�?
            speech.setParameter(SpeechConstant.VOICE_NAME, "xiaofeng")

            speech.setParameter(SpeechConstant.SPEED, "50")
            //设置合成音调
            speech.setParameter(SpeechConstant.PITCH, "50")
            //设置合成音量
            speech.setParameter(SpeechConstant.VOLUME, "50")
            //设置播放器音频流类型
            speech.setParameter(SpeechConstant.STREAM_TYPE, "3")
            // 设置播放合成音频打断音乐播放，默认为true
            speech.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false")

            // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
            speech.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
            speech.setParameter(
                SpeechConstant.TTS_AUDIO_PATH,
                Environment.getExternalStorageDirectory().toString() + "/msc/tts.wav"
            )
        }
        return this
    }

    //获取发音人资源路�?
    private fun getResourcePath(et: String): String {
        val tempBuffer = StringBuilder()
        var type = "tts" // 资源已迁移至 commonMain/composeResources/files/tts
        if (et == SpeechConstant.TYPE_XTTS) {
            type = "xtts"
        }
        //合成通用资源
        tempBuffer.append(
            ResourceUtil.generateResourcePath(
                mContext,
                ResourceUtil.RESOURCE_TYPE.assets,
                "$type/common.jet"
            )
        )
        tempBuffer.append(";")
        //发音人资�?
        tempBuffer.append(
            ResourceUtil.generateResourcePath(
                mContext,
                ResourceUtil.RESOURCE_TYPE.assets,
                "$type/xiaofeng.jet"
            )
        )

        return tempBuffer.toString()
    }

    fun speak(text: String) {
        mSpeech?.startSpeaking(text, mTtsListener)
    }

    fun stopSpeaking() {
        mSpeech?.stopSpeaking()
    }

    fun release() {
        mContext?.let { deleteFile(it) }
        mContext = null
        mSpeech?.let {
            it.stopSpeaking()
            it.destroy()
        }
        mSpeech = null
        callback = null
    }

    private val mListener = InitListener { code ->
        callback?.ttsInitialize(code)
        Log.d("77777777", "InitListener $code")
    }

    private val mTtsListener: SynthesizerListener = object : SynthesizerListener {
        override fun onSpeakBegin() {}
        override fun onSpeakPaused() {}
        override fun onSpeakResumed() {}
        override fun onBufferProgress(percent: Int, beginPos: Int, endPos: Int, info: String) {
            Log.d("TTS", "=============xfSpeech========onBufferProgress========")
        }

        override fun onSpeakProgress(percent: Int, beginPos: Int, endPos: Int) {}
        override fun onCompleted(error: SpeechError?) {
            Log.d("TTS", "=============xfSpeech========onCompleted========")
            callback?.ttsCompleted()
        }

        override fun onEvent(eventType: Int, arg1: Int, arg2: Int, obj: Bundle?) {}
    }

    fun setXfTTSCallback(callback: XfTTSCallback): XfTtsWrapper {
        this.callback = callback
        return this
    }

    interface XfTTSCallback {
        fun ttsInitialize(code: Int)
        fun ttsCompleted()
    }

    companion object {
        @JvmStatic
        fun initXf(ctx: Context) {
            Log.d("7777777", "initXf ")
            SpeechUtility.createUtility(ctx, "${SpeechConstant.APPID}=b2b19968")
        }

        @Volatile
        private var instance: XfTtsWrapper? = null

        @JvmStatic
        fun getInstance(ctx: Context): XfTtsWrapper {
            return instance ?: synchronized(this) {
                instance ?: XfTtsWrapper(ctx).also { instance = it }
            }
        }

        @JvmStatic
        fun deleteFile(ctx: Context): Boolean {
            val storageDir = File(Environment.getExternalStorageDirectory().toString() + "/msc")
            if (!storageDir.exists() || !storageDir.isDirectory) {
                return false
            }
            val files = storageDir.listFiles() ?: return true
            var flag = true
            for (file in files) {
                if (file.isFile) {
                    flag = deleteFile(file.absolutePath)
                    if (!flag) break
                }
            }
            return flag
        }

        private fun deleteFile(filePath: String): Boolean {
            val file = File(filePath)
            return file.isFile && file.exists() && file.delete()
        }
    }
}
