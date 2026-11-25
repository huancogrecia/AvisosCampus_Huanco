package com.tecsup.avisoscampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.avisoscampus.model.Aviso

@Composable
fun HistorialAvisos(avisos: List<Aviso>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Historial", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
            if (avisos.isEmpty()) {
                Text("Sin avisos todavía")
            } else {
                avisos.take(20).forEach { aviso ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(aviso.origen.texto, color = MaterialTheme.colorScheme.primary)
                            Text(aviso.titulo, style = MaterialTheme.typography.titleSmall)
                            Text(aviso.texto)
                        }
                        Text(aviso.hora, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
