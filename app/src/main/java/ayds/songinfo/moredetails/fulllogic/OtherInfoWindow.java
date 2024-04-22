package ayds.songinfo.moredetails.fulllogic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import ayds.songinfo.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;


import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;



public class OtherInfoWindow extends Activity {

  private final static String ARTIST_NAME_EXTRA = "artistName";
  private TextView articleTextView;
  private ArticleDatabase articleDatabase = null;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_other_info);
    initProperties();
    initializeDatabase();
    getArtistInfoAsync();
  }

  private void initProperties() {
    articleTextView = findViewById(R.id.textPane1);
  }

  private void getArtistInfoAsync() {

    getArtistInfo();

  }

  public static String textToHtml(String text, String term) {

    StringBuilder builder = new StringBuilder();

    builder.append("<html><div width=400>");
    builder.append("<font face=\"arial\">");

    String textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replaceAll("(?i)" + term, "<b>" + term.toUpperCase() + "</b>");

    builder.append(textWithBold);

    builder.append("</font></div></html>");

    return builder.toString();
  }



  //------------------------------------------------------------------------------------------------
  @NonNull
  private static LastFMAPI createLastFMAPI(String artistName) {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    LastFMAPI result = retrofit.create(LastFMAPI.class);

    Log.e("TAG","artistName " + artistName);
    return result;
  }
  
  private void getArtistInfo() {
    new Thread(new Runnable() {
      String artistName = getIntent().getStringExtra(ARTIST_NAME_EXTRA);
      LastFMAPI lastFMAPI = createLastFMAPI(artistName);
      @Override
      public void run() {

        ArticleEntity article = articleDatabase.ArticleDao().getArticleByArtistName(artistName);

        String description;

        if (existsInDatabase(article)) {
          description = getFromDataBase(article);
        } else {
          description = getFromService(artistName, lastFMAPI);
        }

        mountArticleDescription(description);

      }
    }).start();
  }

  private boolean existsInDatabase(ArticleEntity article) {
    return article != null;
  }

  private void setListener(String articleUrl) {
    findViewById(R.id.openUrlButton1).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(articleUrl));
        startActivity(intent);
      }
    });
  }
  @NonNull
  private String getFromDataBase(ArticleEntity article) {
    String description = "[*]" + article.getBiography();

    final String articleUrl = article.getArticleUrl();
    setListener(articleUrl);

    return description;
  }

  @NonNull
  private String getFromService(String artistName, LastFMAPI lastFMAPI) {
    String description = "";
    Response<String> callResponse;

    try {
      callResponse = lastFMAPI.getArtistInfo(artistName).execute();

      Log.e("TAG","JSON " + callResponse.body());

      Gson gson = new Gson();
      JsonObject jobj = gson.fromJson(callResponse.body(), JsonObject.class);
      JsonObject artist = jobj.get("artist").getAsJsonObject();
      JsonObject bio = artist.get("bio").getAsJsonObject();
      JsonElement bioContent = bio.get("content");
      JsonElement url = artist.get("url");


      if (bioContent == null) {
        description = "No Results";
      } else {
        String formattedDescription = bioContent.getAsString().replace("\\n", "\n");
        description = textToHtml(formattedDescription, artistName);

        saveToDataBase(artistName, description, url);

      }


      final String artistUrl = url.getAsString();
      setListener(artistUrl);

    } catch (IOException e1) {
      Log.e("TAG", "Error " + e1);
      e1.printStackTrace();
    }
    return description;
  }

  private void saveToDataBase(String artistName, String description, JsonElement url) {
    final String finalDescription = description;
    new Thread(new Runnable() {
      @Override
      public void run() {
        articleDatabase.ArticleDao().insertArticle(new ArticleEntity(artistName, finalDescription, url.getAsString()));
      }
    }).start();
  }

  private void mountArticleDescription(String description) {
    String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png";
    Log.e("TAG","Get Image from " + imageUrl);

    final String finalDescription = description;

    runOnUiThread( () -> {
      Picasso.get().load(imageUrl).into((ImageView) findViewById(R.id.imageView1));

      articleTextView.setText(Html.fromHtml(finalDescription));

    });
  }

  private void initializeDatabase() {

    articleDatabase = Room.databaseBuilder(this, ArticleDatabase.class, "database-name-thename").build();
    new Thread(new Runnable() {
      @Override
      public void run() {
        articleDatabase.ArticleDao().insertArticle(new ArticleEntity( "test", "sarasa", "")  );
        Log.e("TAG", ""+ articleDatabase.ArticleDao().getArticleByArtistName("test"));
        Log.e("TAG", ""+ articleDatabase.ArticleDao().getArticleByArtistName("nada"));

      }
    }).start();
  }
}







