package org.d3if0069.miniprojek2.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0069.miniprojek2.R
import org.d3if0069.miniprojek2.database.TaekwondoDb
import org.d3if0069.miniprojek2.ui.theme.Miniprojek2Theme
import org.d3if0069.miniprojek2.util.ViewModelFactory

const val KEY_ID_Taekwondo = "idTaekwondo"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null){
    val contenx = LocalContext.current
    val db = TaekwondoDb.getInstance(contenx)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama by remember { mutableStateOf("") }
    var sabuk by remember { mutableStateOf("") }
    var selectedClass by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getTaekwondo(id) ?: return@LaunchedEffect
        nama = data.nama
        sabuk = data.sabuk
        selectedClass = data.fakultas
        selectedGender = data.gender
    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_Atlet))
                    else
                        Text(text = stringResource(id = R.string.edit_atlet))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (nama == "" || sabuk == "" || selectedClass == "") {
                            Toast.makeText(contenx, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(nama, sabuk, selectedClass, selectedGender)
                        } else {
                            viewModel.update(id, nama, sabuk, selectedClass, selectedGender)
                        }
                        navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if ( id != null ) {
                        DeleteAction {
                            showDialog = true
                        }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDissmissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()

                        }
                    }
                }
            )
        }
    ) { padding ->
        FormTaekwondo(
            name = nama,
            onNameChange = { nama = it },
            sabuk = sabuk,
            onSabukChange = { sabuk = it },
            selectedClass = selectedClass,
            onClassChange = { selectedClass = it },
            selectedGender = selectedGender,
            onGenderChange = { selectedGender = it },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var  expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormTaekwondo(
    name: String, onNameChange: (String) -> Unit,
    sabuk: String, onSabukChange: (String) -> Unit,
    selectedClass: String, onClassChange: (String) -> Unit,
    selectedGender: String, onGenderChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { onNameChange(it) },
            label = { Text(text = stringResource(R.string.nama_atlet)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = sabuk,
            onValueChange = { onSabukChange(it) },
            label = { Text(text = stringResource(R.string.Sabuk)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedCard(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ){
            Column {
                listOf(
                    "FIT",
                    "FEB",
                    "FKB",
                    "FIK",
                    "FTI",
                    "FTE",
                    "FRI"
                ).forEach { classOption ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onClassChange(classOption) }
                    ) {
                        RadioButton(
                            selected = classOption == selectedClass,
                            onClick = { onClassChange(classOption) }
                        )
                        Text(text = classOption, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
        OutlinedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp)) {
            Row {
                listOf("Laki-laki", "Perempuan").forEach { genderOption ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onGenderChange(genderOption) }) {
                        RadioButton(selected = genderOption == selectedGender, onClick = { onGenderChange(genderOption) })
                        Text(text = genderOption, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Miniprojek2Theme {
        DetailScreen(rememberNavController())
    }
}