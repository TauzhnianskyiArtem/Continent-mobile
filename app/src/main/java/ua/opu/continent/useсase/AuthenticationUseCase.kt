package ua.opu.continent.useсase

import android.app.Activity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult

interface AuthenticationUseCase {

    suspend fun sendingCode(phoneNumber: String, activity: Activity, onCodeSent: (String) -> Unit)

    suspend fun verifyCode(
        verificationId: String,
        otpCode: String,
        completeListener: OnCompleteListener<AuthResult>
    )

    suspend fun isAuthenticateUser(): Boolean
}