package com.tecsup.avisoscampus.notificacion

import android.content.Context
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.Manifest
import androidx.core.content.ContextCompat
import com.tecsup.avisoscampus.model.Aviso

object Notificador {
    const val CANAL = "avisos"

    fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                CANAL,
                "Avisos",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val administrador = context.getSystemService(NotificationManager::class.java)
            administrador.createNotificationChannel(canal)
        }
    }

    fun tienePermiso(context: Context): Boolean {
        return Build.VERSION.SDK_INT < 33 ||
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
    }

    fun mostrar(context: Context, aviso: Aviso): Boolean {
        // TODO 1: si no tienePermiso(context) -> return false
        // TODO 2: construir la notificación con NotificationCompat.Builder
        // TODO 3: NotificationManagerCompat.from(context).notify(aviso.id, ...)
        return false
    }
}
