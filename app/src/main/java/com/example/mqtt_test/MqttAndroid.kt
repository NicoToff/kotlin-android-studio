package com.example.mqtt_test

import android.content.Context
import android.util.Log
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.*

class MqttAndroid(
    context: Context,
    serverURI: String,
    userName: String,
    password: String,
    clientId: String = UUID.randomUUID().toString()
) {
    private var client: MqttAndroidClient;
    private lateinit var opt: MqttConnectOptions;
    private lateinit var context: Context;

    init {
        opt.userName = userName
        opt.password = password.toCharArray()
        client = MqttAndroidClient(context, serverURI, clientId)
        client.setCallback( object : MqttCallbackExtended {
            override fun connectionLost(cause: Throwable?) {
                Log.w("mqtt2", "Connexion perdue")
                mqttConnect()
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.i("mqtt2", "Nouveau message $topic - $message")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.i("mqtt2", "Délivré ok")
            }

            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                Log.i("mqtt2", if(reconnect) "Reconnected ~~" else "Connection complete !")
            }
        })
        mqttConnect()
    }

    private fun mqttConnect() {
        client.connect(opt, context, object: IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.i("mqtt2", "Connection success")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.w("mqtt2", "Erreur de connexion", exception)
            }
        })
    }

    fun subscribe(topic: String, qos: Int = 0) {
        client.subscribe(topic, qos)
    }

    fun publish(topic: String, message: String, qos: Int = 0) {
        client.publish(topic, message.toByteArray(), qos, false)
    }

    fun disconnect() {
        client.disconnect()
    }

    fun setCallback(callback: MqttCallbackExtended) {
        client.setCallback(callback)
    }
}