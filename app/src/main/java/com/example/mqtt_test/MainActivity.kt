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
            "tcp://kermareg.be:1883",
            UUID.randomUUID().toString()
        )

        val opt = MqttConnectOptions()
        with(opt) {
            userName = "helha"
            password = "helha".toCharArray()
        }

        fun mqttConnect() {
            client.connect(opt, this, object: IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.i("mqtt2", "Connection success")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.w("mqtt2", "Erreur de connexion", exception)
                }
            })
        }

        client.setCallback(object: MqttCallbackExtended {
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
                client.subscribe("/#",0)
            }
        })

        mqttConnect()
    }
}