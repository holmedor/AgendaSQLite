package com.example.agenda;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private TextView etTexto;
    private EditText etArchivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        etTexto = (TextView) findViewById(R.id.etTexto);
        etArchivo = (EditText) findViewById(R.id.etArchivo);
    }

    private void LimpiarTextos() {
        etArchivo.setText("");
        etTexto.setText("");
    }

    private boolean existeFile(String[] archivos, String archivo) {
        for (int f = 0; f < archivos.length; f++)
            if (archivo.equals(archivos[f]))
                return true;
        return false;
    }

    public void Limpiar(View view) {
        this.LimpiarTextos();
    }

    public void Buscar(View view) {
        String[] archivos = fileList(); //Sólo para MEMORIA INTERNA
        String archivo = etArchivo.getText().toString();
        archivo = archivo.replace('/', '-');
        /* Código para recuperar el archivo de la memoria MicroSD
        try{
            File tarjeta = Environment.getExternalStorageDirectory();
            File file = new File(tarjeta.getAbsolutePath(),archivo);
            if(file.exists()){
                FileInputStream fin = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fin);
                BufferedReader br = new BufferedReader(isr);
                String linea=br.readLine();
                String texto="";
                while (linea != null) {
                    texto=texto+linea + "\n";
                    linea=br.readLine();
                }
                br.close();
                isr.close();
                etTexto.setText(texto);
                } else {
                Toast.makeText(this, "El archivo no existe", Toast.LENGTH_SHORT).show();
                }
                } catch(IOException e){
                Log.i("Agenda", e.toString());
        }
         */
        if (existeFile(archivos, archivo)) {
            try {
                InputStreamReader file = new InputStreamReader(openFileInput(archivo));
                BufferedReader br = new BufferedReader(file);
                String linea = br.readLine();
                String texto = "";
                while (linea != null) {
                    texto += linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                file.close();
                etTexto.setText(texto);
            } catch (IOException e) {
                Log.i("Agenda", e.toString());
            }
        } else {
            Toast.makeText(this, "El archivo no existe", Toast.LENGTH_SHORT).show();
        }
    }

    public void Grabar(View view) {
        String archivo = etArchivo.getText().toString();
        archivo = archivo.replace('/', '-')+".txt";
        try {
/*   MICROSD
            File tarjeta= Environment.getExternalStorageDirectory();
            File file=new File(tarjeta.getAbsolutePath(),archivo);
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
            osw.write(etTexto.getText().toString());
            osw.flush();
            osw.close();
    MEMO INTERNA
*/
            OutputStreamWriter file = new OutputStreamWriter(openFileOutput(archivo, MODE_PRIVATE));
            file.write(etTexto.getText().toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.i("Agenda", e.toString());
        }
        Toast.makeText(this, "Datos grabados", Toast.LENGTH_SHORT).show();
        this.LimpiarTextos();
    }
/*        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        })
 */
}
