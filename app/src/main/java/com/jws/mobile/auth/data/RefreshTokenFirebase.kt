package com.jws.mobile.auth.data

import com.google.firebase.firestore.FirebaseFirestore

object RefreshTokenFirebase {

    fun getRefreshToken(callback: (String?) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        db.collection("tokens").document("meli")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val token = document.getString("refresh_token")
                    callback(token)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun saveRefreshToken(newToken: String, callback: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        val data = hashMapOf(
            "refresh_token" to newToken
        )

        db.collection("tokens").document("meli")
            .set(data)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}