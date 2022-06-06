package ua.opu.continent.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import android.widget.Toast

class SmsReceiver(val listener: SmsReceiverListener): BroadcastReceiver() {


    interface SmsReceiverListener {
        fun onReceiverResult(code: CharSequence)
    }
    override fun onReceive(context: Context?, intent: Intent?) {
       if(intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION){
           for(msg in Telephony.Sms.Intents.getMessagesFromIntent(intent)){
               if (msg.displayOriginatingAddress == "CloudOTP"){
                   var messageText = msg.messageBody
                   val code = messageText.subSequence(0,6)
                   listener.onReceiverResult(code)
                   Toast.makeText(context, msg.messageBody, Toast.LENGTH_LONG)
                   Log.d(SmsReceiver::class.simpleName, msg.messageBody)

               }
           }
       }
    }
}