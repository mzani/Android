package br.com.fiap.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import br.com.fiap.githubapp.api.GitHubService
import br.com.fiap.githubapp.model.Usuario
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btPesquisar.setOnClickListener {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(GitHubService::class.java)

            service.pesquisar(imputUsuario.text.toString()).enqueue(object : Callback<Usuario>{

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful)
                    {
                        val usuario = response.body()
                        tvNomeUsuario.text = usuario?.nome

                        Picasso.get().load(usuario?.avatarUrl).into(ivUsuario)
                }
                    else{
                    Toast.makeText(this@MainActivity, "Deu erro", Toast.LENGTH_LONG).show()
                    }
                }
            }
            )


        }
    }
}
