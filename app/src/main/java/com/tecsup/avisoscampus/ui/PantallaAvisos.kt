package com.tecsup.avisoscampus.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.avisoscampus.notificacion.Notificador
import com.tecsup.avisoscampus.viewmodel.AvisosViewModel

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current
    val permiso by vm.permiso.collectAsState()
    val faltan by vm.faltan.collectAsState()
    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        vm.permisoRespondido(concedido)
    }

    LaunchedEffect(Unit) {
        if (Notificador.tienePermiso(contexto)) {
            vm.permisoRespondido(true)
        }
    }

    // Dentro de PantallaAvisos (ya viene hecho):
    LaunchedEffect(Unit) {
        vm.eventos.collect { aviso ->
            val mostrado = Notificador.mostrar(contexto, aviso)
            if (!mostrado) {
                // TODO Paso 8: si no se mostró, avisar al ViewModel
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AvisosCampus") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaBase("Permiso de notificaciones") {
                val estado = when (permiso) {
                    com.tecsup.avisoscampus.model.PermisoUi.SinPedir -> "Sin pedir"
                    com.tecsup.avisoscampus.model.PermisoUi.Concedido -> "Permitido"
                    com.tecsup.avisoscampus.model.PermisoUi.Denegado -> "Denegado"
                }
                Text("Estado: $estado")
                Button(
                    onClick = {
                        if (Build.VERSION.SDK_INT >= 33) {
                            lanzador.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            vm.permisoRespondido(true)
                        }
                    },
                    enabled = permiso != com.tecsup.avisoscampus.model.PermisoUi.Concedido
                ) { Text("Permitir notificaciones") }
            }
            TarjetaBase("Avisos") {
                Button(onClick = vm::avisarAhora) { Text("Mostrar aviso") }
            }
            TarjetaBase("Recordatorio") {
                Button(onClick = { vm.recordarEn() }, enabled = faltan == null) {
                    Text("Recordar en 10 s")
                }
                Button(onClick = vm::cancelarRecordatorio, enabled = faltan != null) {
                    Text("Cancelar")
                }
                Text(faltan?.let { "Faltan $it s" } ?: "Sin recordatorio activo")
            }
            TarjetaBase("Receptor de anuncios") {
                Text("Escuchar el modo avión")
                Switch(checked = false, onCheckedChange = {}, enabled = false)
            }
            TarjetaBase("Historial") {
                Text("Sin avisos todavía")
            }
        }
    }
}

@Composable
private fun TarjetaBase(titulo: String, contenido: @Composable () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(titulo, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
            contenido()
        }
    }
}
