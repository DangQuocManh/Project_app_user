package com.quocmanh.appproject.myinterface

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.quocmanh.appproject.R
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

class SSLContextHelper(private val context: Context) {

    fun getSSLContext(): SSLContext {
        val certificate: InputStream = context.resources.openRawResource(R.raw.keystore)

        val certificateFactory = CertificateFactory.getInstance("X.509")
        val stream = certificate.readBytes().inputStream()
        val x509Certificate = certificateFactory.generateCertificate(stream) as X509Certificate

        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null)
        keyStore.setCertificateEntry("ca", x509Certificate)

        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)

        val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        keyManagerFactory.init(keyStore, null)

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(keyManagerFactory.keyManagers, trustManagerFactory.trustManagers, null)

        return sslContext
    }
}