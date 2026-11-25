package com.tecsup.avisoscampus.notificacion

import android.content.Context
import android.content.Intent
import android.app.PendingIntent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.Manifest
import androidx.core.content.ContextCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tecsup.avisoscampus.model.Aviso
import com.tecsup.avisoscampus.R
import com.tecsup.avisoscampus.MainActivity

object Notificador {
    const val CANAL_AVISOS = "avisos"
    const val CANAL_URGENTES = "urgentes"

    fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val administrador = context.getSystemService(NotificationManager::class.java)
            val canalAvisos = NotificationChannel(
                CANAL_AVISOS,
                "Avisos",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val canalUrgentes = NotificationChannel(
                CANAL_URGENTES,
                "Urgentes",
                NotificationManager.IMPORTANCE_HIGH
            )
            administrador?.createNotificationChannel(canalAvisos)
            administrador?.createNotificationChannel(canalUrgentes)
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
        if (!tienePermiso(context)) return false

        val intent = Intent(context, MainActivity::class.java)
        val pendiente = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val esUrgente = aviso.origen == com.tecsup.avisoscampus.model.Origen.RECORDATORIO
        val canalId = if (esUrgente) CANAL_URGENTES else CANAL_AVISOS
        val prioridad = if (esUrgente) NotificationCompat.PRIORITY_HIGH else NotificationCompat.PRIORITY_DEFAULT

        val notificacion = NotificationCompat.Builder(context, canalId)
            .setSmallIcon(R.drawable.ic_notificacion)
            .setContentTitle(aviso.titulo)
            .setContentText(aviso.texto)
            .setStyle(NotificationCompat.BigTextStyle().bigText(aviso.texto))
            .setPriority(prioridad)
            .setAutoCancel(true)
            .setContentIntent(pendiente)
            .build()

        NotificationManagerCompat.from(context).notify(aviso.id, notificacion)
        return true
    }
}
