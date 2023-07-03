package com.example.module_3_lesson_4_hw_1_composee

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.module_3_lesson_4_hw_1_composee.ui.theme.Module_3_Lesson_4_hw_1_ComposeeTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var retrofit = RetrofitClient.getClient("https://catfact.ninja/")
            .create(API::class.java)

        setContent {
            Module_3_Lesson_4_hw_1_ComposeeTheme {
                MyApp(retrofit = retrofit)
            }
        }
    }
}

@Composable
fun MyApp(retrofit: API) {
    val textFromQuery = remember { mutableStateOf("") }

    Image(
        painter = painterResource(id = R.drawable.cute_cat_cartoon_cat_couple),
        contentDescription = "Image background",
        contentScale = ContentScale.FillBounds
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = stringResource(id = R.string.text_intro),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        )

        Text(
            text = textFromQuery.value,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .defaultMinSize(minHeight = dimensionResource(id = R.dimen.text_height))
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.text_height)))
        Button(
            onClick = {
                retrofit.getRandomFact().enqueue(object : Callback<ResponseMain> {
                    override fun onResponse(
                        call: Call<ResponseMain>,
                        response: Response<ResponseMain>
                    ) {
                        val result = response.body()?.fact.toString()
                        Log.d("MYLOG", result)
                        textFromQuery.value = result
                    }

                    override fun onFailure(call: Call<ResponseMain>, t: Throwable) {
                        Log.d("MYLOG", "Some error in getRandomIdea()")
                    }

                })
            }
        ) {
            Text(text = "GET A FACT")
        }

    }
}