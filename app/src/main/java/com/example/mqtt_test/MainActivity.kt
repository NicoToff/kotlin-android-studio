package com.example.mqtt_test

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Switch
import android.widget.TextView
import com.example.mqtt_test.databinding.ActivityMainBinding
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.*
import com.ekn.gruzer.gaugelibrary.Range

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        context = this

        val range = Range()
        range.color = Color.parseColor("#009790")
        range.from = 0.0
        range.to = 700.0

        val range2 = Range()
        range2.color = Color.parseColor("#E3E500")
        range2.from = 700.0
        range2.to = 1200.0

        val range3 = Range()
        range3.color = Color.parseColor("#ce0000")
        range3.from = 1200.0
        range3.to = 1800.0

        //add color ranges to gauge
        bind.co2Gauge.addRange(range)
        bind.co2Gauge.addRange(range2)
        bind.co2Gauge.addRange(range3)

        //set min max and current value
        bind.co2Gauge.minValue = 0.0
        bind.co2Gauge.maxValue = 1800.0
        bind.co2Gauge.value = 0.0

        val topics: Hashtable<String, Message> = Hashtable() // Thread-safe version of a Map

        bind.txtTopicDisplay.movementMethod = ScrollingMovementMethod() // Enables the scrollbar

        val client = MqttAndroidClient(
            this,
            "tcp://kermareg.be:1883",
            UUID.randomUUID().toString()
        )

        val opt = MqttConnectOptions()
        with(opt) {
            userName = "helha"
            password = "helha".toCharArray()
            isAutomaticReconnect = true
        }

        fun mqttConnect() {
            client.connect(opt, this, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.i("mqtt2", "Connection success")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.w("mqtt2", "Erreur de connexion", exception)
                }
            })
        }

        client.setCallback(object : MqttCallbackExtended {
            override fun connectionLost(cause: Throwable?) {
                Log.w("mqtt2", "Connexion perdue")
                toggleSwitch(bind.swiConnectedIndicator, false)
                mqttConnect()
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                if (topic != null && message != null) {
                    topics[topic] = Message(topic, message.toString())
                    bind.lblTopicCounter.text = topics.size.toString()
                    bind.txtTopicDisplay.text = topics.keys.joinToString("\n")
                    bind.rcView.adapter = MessageAdapter(context, topics.values.toList())
                    autoScroll(bind.txtTopicDisplay)

                    if (topic == "/node/knode/KNode_98/air/eCO2") {
                        val co2: Double = message.toString().toDouble()
                        bind.co2Gauge.value = co2
                    }

                }
                Log.i("mqtt2", "New message: ($topic) $message")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.i("mqtt2", "Delivery complete")
            }

            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                Log.i("mqtt2", if (reconnect) "Reconnected ~~" else "Connection complete !")
                toggleSwitch(bind.swiConnectedIndicator, true)
                client.subscribe("/#", 0)
            }
        })

        mqttConnect()
    }
}

fun toggleSwitch(swi: Switch, state: Boolean) {
    swi.text = if (state) "Connecté" else "Déconnecté"
    swi.isChecked = state
}

private fun autoScroll(textView: TextView) {
    // Get the total height of the TextView
    val layout = textView.layout
    val scrollAmount =
        layout.getLineTop(textView.lineCount) - textView.height

    // Scroll to the bottom of the TextView
    if (scrollAmount > 0)
        textView.scrollTo(0, scrollAmount)
    else
        textView.scrollTo(0, 0)
}