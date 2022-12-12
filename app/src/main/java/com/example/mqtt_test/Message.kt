package com.example.mqtt_test

class Message(val topic: String, val message: String) {

    override fun toString(): String {
        return "($topic) \t $message"
    }
}