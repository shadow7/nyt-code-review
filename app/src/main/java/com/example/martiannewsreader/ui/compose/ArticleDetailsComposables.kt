import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.martiannewsreader.R
import com.example.martiannewsreader.model.domain.Article

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArticleDetailsContent(article: Article?) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (article != null) {
            Text(
                text = article.title,
                maxLines = 2,
                fontSize = 21.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            GlideImage(
                model = article.imageUrl,
                contentDescription = article.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.body,
                fontSize = 16.sp,
            )
        } else {
            Text(text = stringResource(id = R.string.no_item))
        }
    }
}

@Preview
@Composable
fun ArticleDetailsContent_NoContent_Preview() {
    MaterialTheme {
        ArticleDetailsContent(article = null)
    }
}