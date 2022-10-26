package com.example.m.ismayilov.timetracker.unkonown

import android.annotation.SuppressLint
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import org.json.JSONException
import org.json.JSONObject


class FireBaseSendMessage {
//    public val BASE_URL = "https://fcm.googleapis.com/fcm/send"
//    public var SERVER_KEY: String? = null
//
//    fun SetServerKey(serverKey: String) {
//        SERVER_KEY = "key=$serverKey"
//    }
//
//    protected var title: String? =
//        null, protected  var body:kotlin.String? = null, protected  var to:kotlin.String? = null, protected  var image:kotlin.String? = null
//    protected var datas: HashMap<String, String>? = null
//    protected var topic = false
//    protected var result: String? = null
//
//    fun Result(): String? {
//        return result
//    }
//
//    class Builder {
//        private var mFcm: FCMSend
//
//        constructor(to: String) {
//            mFcm = FCMSend()
//            mFcm.to = to
//        }
//
//        constructor(to: String, topic: Boolean) {
//            mFcm = FCMSend()
//            mFcm.to = to
//            mFcm.topic = topic
//        }
//
//        fun setTitle(title: String): Builder {
//            mFcm.title = title
//            return this
//        }
//
//        fun setBody(body: String): Builder {
//            mFcm.body = body
//            return this
//        }
//
//        fun setImage(image: String): Builder {
//            mFcm.image = image
//            return this
//        }
//
//        fun setData(datas: HashMap<String?, String?>): Builder {
//            mFcm.datas = datas
//            return this
//        }
//
//        @SuppressLint("NewApi")
//        fun send(): FCMSend {
//            if (SERVER_KEY == null) mFcm.result = "No Server Key" else {
//                val policy = ThreadPolicy.Builder().permitAll().build()
//                StrictMode.setThreadPolicy(policy)
//                try {
//                    val json = JSONObject()
//                    if (mFcm.topic) json.put("to", "/topics/" + mFcm.to) else json.put(
//                        "to",
//                        mFcm.to
//                    )
//                    val notification = JSONObject()
//                    notification.put("title", mFcm.title)
//                    notification.put("body", mFcm.body)
//                    if (mFcm.image != null) notification.put("image", mFcm.image)
//                    notification.put("click_action", "com.deeplabstudio.fcm_NOTIFICATION")
//                    json.put("notification", notification)
//                    if (mFcm.datas != null) {
//                        val data = JSONObject()
//                        mFcm.datas.forEach { key, value ->
//                            try {
//                                data.put(key, value)
//                            } catch (e: JSONException) {
//                                e.printStackTrace()
//                            }
//                        }
//                        json.put("data", data)
//                    }
//                    val conn: HttpURLConnection =
//                        URL(BASE_URL).openConnection() as HttpURLConnection
//                    conn.setConnectTimeout(5000)
//                    conn.setRequestProperty("Content-Type", "application/json;")
//                    conn.setRequestProperty("Authorization", SERVER_KEY)
//                    conn.setDoOutput(true)
//                    conn.setDoInput(true)
//                    conn.setRequestMethod("POST")
//                    val os: OutputStream = conn.getOutputStream()
//                    os.write(json.toString().toByteArray(charset("UTF-8")))
//                    os.close()
//                    val `in`: InputStream = BufferedInputStream(conn.getInputStream())
//                    val result: String = IOUtils.toString(`in`, "UTF-8")
//                    `in`.close()
//                    conn.disconnect()
//                    mFcm.result = result
//                } catch (e: JSONException) {
//                    mFcm.result = e.getMessage()
//                } catch (e: IOException) {
//                    mFcm.result = e.getMessage()
//                }
//            }
//            return mFcm
//        }
//    }
}