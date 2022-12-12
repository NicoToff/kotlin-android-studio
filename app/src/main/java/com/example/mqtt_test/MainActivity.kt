package com.example.mqtt_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import android.widget.TextView
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topics : ArrayList<String> = arrayListOf()

        val swiConnectedIndicator = findViewById<Switch>(R.id.swiConnectedIndicator)
        toggleSwitch(swiConnectedIndicator, false)

        val lblTopicCounter = findViewById<TextView>(R.id.lblTopicCounter)
        lblTopicCounter.text = "0"

        val txtTopicDisplay = findViewById<TextView>(R.id.txtTopicDisplay)

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
                toggleSwitch(swiConnectedIndicator, false)
                mqttConnect()
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                if(topic != null && !topics.contains(topic)) {
                    topics.add(topic)
                    lblTopicCounter.text = topics.size.toString()
                    topics.sort()
                    txtTopicDisplay.text = topics.joinToString("\n")
                }
                Log.i("mqtt2", "New message: ($topic) $message")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.i("mqtt2", "Delivery complete")
            }

            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                Log.i("mqtt2", if(reconnect) "Reconnected ~~" else "Connection complete !")
                toggleSwitch(swiConnectedIndicator, true)
                client.subscribe("/#",0)
            }
        })

        mqttConnect()
    }
}

fun toggleSwitch(swi: Switch, state: Boolean) {
    swi.text = if(state) "Connecté" else "Déconnecté"
    swi.isChecked = state
}