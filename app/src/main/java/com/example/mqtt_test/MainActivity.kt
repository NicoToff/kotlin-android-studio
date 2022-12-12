package com.example.mqtt_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = MqttAndroidClient(
            this,
            "tcp://test.mosquitto.org:1883",
            UUID.randomUUID().toString()
        )// .apply { setForegroundService(foregroundNotidication, 3) }

        val opt = MqttConnectOptions()

        client.setCallback(object: MqttCallbackExtended {
            override fun connectionLost(cause: Throwable?) {
                Log.w("mqtt2", "Connexion perdue")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.i("mqtt2", "Nouveau message $topic - $message")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.i("mqtt2", "Délivré ok")
            }

            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                Log.i("mqtt2", "Connecté. reconnect : $reconnect")
                client.subscribe("/helha/nicotoff/rfid",0)
            }
        })

        client.connect(opt, this, object: IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.i("mqtt2", "Connecté")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.w("mqtt2", "Erreur de connexion", exception)
            }
        })
    }
}