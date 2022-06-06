package ua.opu.continent.useсase.logging

import android.app.Activity
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import ua.opu.continent.useсase.AuthenticationUseCase

class AuthenticationUserCaseLog(private val originalUserCase: AuthenticationUseCase, private val tag: String?) : AuthenticationUseCase {
    override suspend fun sendingCode(
        phoneNumber: String,
        activity: Activity,
        onCodeSent: (String) -> Unit
    ) {
        Log.d(tag, "Function: sendingCode, PhoneNumber: $phoneNumber")
        originalUserCase.sendingCode(phoneNumber, activity, onCodeSent)
    }

    override suspend fun verifyCode(
        verificationId: String,
        otpCode: String,
        completeListener: OnCompleteListener<AuthResult>
    ) {
        Log.d(tag, "Function: verifyCode, VerificationId: $verificationId, OtpCode: $otpCode")
        originalUserCase.verifyCode(verificationId, otpCode, completeListener)
    }

    override suspend fun isAuthenticateUser(): Boolean {
        val isSignedIn = originalUserCase.isAuthenticateUser()
        Log.d(tag, "Function: isAuthenticateUser, isSignedIn: $isSignedIn")

        return isSignedIn
    }

}