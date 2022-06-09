package ua.opu.continent.useсase.impl

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import ua.opu.continent.useсase.AuthenticationUseCase
import java.util.concurrent.TimeUnit

class AuthenticationUseCaseFirebase(private val auth: FirebaseAuth) : AuthenticationUseCase {


    @WorkerThread
    override suspend fun isAuthenticateUser(): Boolean {
        return auth.currentUser != null
    }

    @WorkerThread
    override suspend fun sendingCode(
        phoneNumber: String,
        activity: Activity,
        onCodeSent: (String) -> Unit
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Toast.makeText(activity, "Verification Completed", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(activity, "Verification Failed", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onCodeSent(
                    verifyId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verifyId, forceResendingToken)
                    val imm =
                        activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                    verifyId.let(onCodeSent)
                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    @WorkerThread
    override suspend fun verifyCode(
        verificationId: String,
        otpCode: String,
        completeListener: OnCompleteListener<AuthResult>
    ) {
        print(verificationId)
        val credential = PhoneAuthProvider.getCredential(verificationId, otpCode)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(completeListener)
    }

}
