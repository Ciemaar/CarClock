package com.infiniti.clock

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infiniti.clock.ui.theme.InfinitiGreen

@Composable
fun SettingsOverlay(
    isAnalog: Boolean,
    onAnalogChanged: (Boolean) -> Unit,
    showDate: Boolean,
    onShowDateChanged: (Boolean) -> Unit,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable { onClose() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(400.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .clickable { /* consume clicks inside panel */ }
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Clock Settings",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            SettingRow(
                title = "Clock Type",
                option1 = "Digital",
                option2 = "Analog",
                isChecked = isAnalog,
                onCheckedChange = onAnalogChanged
            )

            Spacer(modifier = Modifier.height(24.dp))

            SettingRow(
                title = "Show Date",
                option1 = "Off",
                option2 = "On",
                isChecked = showDate,
                onCheckedChange = onShowDateChanged
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(
                    containerColor = InfinitiGreen,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Close", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun SettingRow(
    title: String,
    option1: String,
    option2: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = option1, color = MaterialTheme.colorScheme.primary, fontSize = 16.sp)
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = InfinitiGreen,
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.DarkGray
                )
            )
            Text(text = option2, color = MaterialTheme.colorScheme.primary, fontSize = 16.sp)
        }
    }
}
