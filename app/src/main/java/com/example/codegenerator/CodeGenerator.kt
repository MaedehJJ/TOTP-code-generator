package com.example.codegenerator

import dev.samstevens.totp.code.HashingAlgorithm
import dev.samstevens.totp.qr.QrData
import dev.samstevens.totp.qr.ZxingPngQrGenerator
import dev.samstevens.totp.secret.DefaultSecretGenerator
import dev.samstevens.totp.secret.SecretGenerator
import java.io.FileOutputStream

fun generateSecret(): String? {
    val secretGenerator: SecretGenerator = DefaultSecretGenerator()
    val secret = secretGenerator.generate()
    print(secret)
    return secret
}

fun generateQRdata(secret: String): QrData? {
    return QrData.Builder()
        .label("The domain that is shown in google authenticator")
        .secret(secret)
        .issuer("The name of the account shown in google authenticator")
        .algorithm(HashingAlgorithm.SHA256)
        .digits(6)
        .period(60)
        .build()
}

fun generateQRcode(data: QrData) {
    val generator = ZxingPngQrGenerator()
    val imageData = generator.generate(data)

    FileOutputStream("File path address").use { outputStream ->
        outputStream.write(
            imageData
        )
    }
}

fun main() {
    val secret = generateSecret() ?: return
    val data = generateQRdata(secret) ?: return
    generateQRcode(data)
}