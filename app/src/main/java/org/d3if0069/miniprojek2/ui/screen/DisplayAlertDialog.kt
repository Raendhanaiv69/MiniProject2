package org.d3if0069.miniprojek2.ui.screen

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.d3if0069.miniprojek2.R
import org.d3if0069.miniprojek2.ui.theme.Miniprojek2Theme

@Composable
fun DisplayAlertDialog(
    openDialog: Boolean,
    onDissmissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            text = { Text(text = stringResource(R.string.pesan_hapus)) },
            confirmButton = {
                TextButton(
                    onClick = { onConfirmation() },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color(0xFF800000) )){
                            Text(
                                text = stringResource(R.string.tombol_hapus),
                                color = Color(0xFFFFFFFF)
                            )

                }
            },
            dismissButton = {
                TextButton(onClick = { onDissmissRequest() }) {
                    Text(text = stringResource(R.string.tombol_batal),
                        color = Color(0xFF000000)
                    )

                }
            },
            onDismissRequest = {onDissmissRequest()}

        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogPreview(){
    Miniprojek2Theme {
        DisplayAlertDialog(openDialog = true,
            onDissmissRequest = {},
            onConfirmation = {}
        )
    }
}
