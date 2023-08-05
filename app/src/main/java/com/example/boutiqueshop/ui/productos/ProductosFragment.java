package com.example.boutiqueshop.ui.productos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.boutiqueshop.R;
import com.example.boutiqueshop.databinding.FragmentGalleryBinding;
import com.example.boutiqueshop.databinding.FragmentProductosBinding;
import com.example.boutiqueshop.ui.gallery.GalleryViewModel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductosFragment extends Fragment {

    private EditText editTextId, editTextName, editTextAddress;
    private TextView textViewResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_productos, container, false);

        editTextId = rootView.findViewById(R.id.edittextid);
        editTextName = rootView.findViewById(R.id.edittextname);
        editTextAddress = rootView.findViewById(R.id.edittextaddress);
        textViewResult = rootView.findViewById(R.id.textViewResult);

        Button buttonAdd = rootView.findViewById(R.id.btnAdd);
        Button buttonUpdate = rootView.findViewById(R.id.btnupdate);
        Button buttonDelete = rootView.findViewById(R.id.btndelete);
        Button buttonGet = rootView.findViewById(R.id.btnget);

        LottieAnimationView lottieAnimationView = rootView.findViewById(R.id.lottieAnimationViewclientes);


        lottieAnimationView.setAnimation(R.raw.cliente2);
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView.playAnimation();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextId.getText().toString();
                String name = editTextName.getText().toString();
                String address = editTextAddress.getText().toString();
                addData(id, name, address);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextId.getText().toString();
                String name = editTextName.getText().toString();
                String address = editTextAddress.getText().toString();
                updateData(id, name, address);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextId.getText().toString();
                deleteData(id);
            }
        });

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextId.getText().toString();
                getData(id);
            }
        });

        return rootView;
    }

    private void addData(String id, String name, String address) {
        class AddData extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://192.168.1.70/clientesboutique/insert.php"); // Reemplaza la URL_DEL_PHP por la URL correspondiente
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    OutputStream outputStream = connection.getOutputStream();

                    // Construir el objeto JSON con los datos a enviar
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", id);
                    jsonObject.put("name", name);
                    jsonObject.put("address", address);
                    String data = jsonObject.toString();

                    outputStream.write(data.getBytes());
                    outputStream.flush();
                    outputStream.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            response.append(line);
                        }
                        bufferedReader.close();
                        return response.toString();
                    } else {
                        return "Error: " + responseCode;
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return "Error: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                textViewResult.setText(result);
            }
        }

        AddData addData = new AddData();
        addData.execute();
    }

    private void updateData(String id, String name, String address) {
        class UpdateData extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://192.168.1.70/clientesboutique/update.php"); // Reemplaza la URL_DEL_PHP por la URL correspondiente
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    OutputStream outputStream = connection.getOutputStream();

                    // Construir el objeto JSON con los datos a enviar
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", id);
                    jsonObject.put("name", name);
                    jsonObject.put("address", address);
                    String data = jsonObject.toString();

                    outputStream.write(data.getBytes());
                    outputStream.flush();
                    outputStream.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            response.append(line);
                        }
                        bufferedReader.close();
                        return response.toString();
                    } else {
                        return "Error: " + responseCode;
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return "Error: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                textViewResult.setText(result);
            }
        }

        UpdateData updateData = new UpdateData();
        updateData.execute();
    }

    private void deleteData(String id) {
        class DeleteData extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://192.168.1.70/clientesboutique/delete.php"); // Reemplaza la URL_DEL_PHP por la URL correspondiente
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    OutputStream outputStream = connection.getOutputStream();

                    // Construir el objeto JSON con el ID a enviar
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", id);
                    String data = jsonObject.toString();

                    outputStream.write(data.getBytes());
                    outputStream.flush();
                    outputStream.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            response.append(line);
                        }
                        bufferedReader.close();
                        return response.toString();
                    } else {
                        return "Error: " + responseCode;
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return "Error: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                textViewResult.setText(result);
            }
        }

        DeleteData deleteData = new DeleteData();
        deleteData.execute();
    }

    private void getData(String id) {
        class GetData extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://192.168.1.70/clientesboutique/get.php?id=" + id); // Reemplaza la URL_DEL_PHP por la URL correspondiente
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            response.append(line);
                        }
                        bufferedReader.close();
                        return response.toString();
                    } else {
                        return "Error: " + responseCode;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Error: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (textViewResult != null) {
                    textViewResult.setText(result);
                } else {
                    Log.e("ProductosFragment", "textViewResult es nulo");
                }
            }
        }

        GetData getData = new GetData();
        getData.execute();
    }
}

