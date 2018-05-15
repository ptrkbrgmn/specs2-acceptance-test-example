package example

import com.softwaremill.sttp.HttpURLConnectionBackend
import com.softwaremill.sttp._
import org.specs2.mutable.Specification
import org.specs2.specification.AfterAll
import org.specs2.specification.dsl.mutable.GWT
import org.specs2.specification.script.StandardDelimitedStepParsers
import scala.collection.immutable.Seq

import scala.io.Source


class NavaKlaraVideo extends Specification with AfterAll with GWT with StandardDelimitedStepParsers {

  implicit val backend = HttpURLConnectionBackend()

  "Fetch a single video".p

  "Given there is a video added to elasticsearch with id {dn.screen9.1uwHxJLDuuBKBHGHQcissw}".step(aString) { videoId =>
    val videoElasticJson = Source.fromURL(getClass.getResource("/es-dn.screen9.1uwHxJLDuuBKBHGHQcissw.json")).mkString
    val uri = Uri.apply("es-content.dev.bonnier.news", 9200, Seq("klara1", "klara", videoId))
    val addVideoElasticRequest =
      sttp.header("content-type", "application/json; charset=UTF-8")
          .body(videoElasticJson)
          .post(uri)
    val addVideoResponse = addVideoElasticRequest.send()
    addVideoResponse.code must be equalTo(201)
  }

  var getVideoKlaraJson: String = _

  "When fetching a video with id {dn.screen9.1uwHxJLDuuBKBHGHQcissw}".step(aString) { videoId =>
    val getVideoKlaraRequest =
      sttp.header("content-type", "application/json; charset=UTF-8")
          .get(uri"http://nav-klara-dn.dev.internal.bonnier.news/videos/dn.screen9.1uwHxJLDuuBKBHGHQcissw")
    val getVideoKlaraResponse = getVideoKlaraRequest.send()
    getVideoKlaraResponse.code must be equalTo(200)
    getVideoKlaraJson = getVideoKlaraResponse.unsafeBody
  }

  "Then response should contain the right data".example(aString) { s: String =>
    val videoKlaraExpectedJson = Source.fromURL(getClass.getResource("/nav-dn.screen9.1uwHxJLDuuBKBHGHQcissw.json")).mkString
    getVideoKlaraJson must be equalTo(videoKlaraExpectedJson)
  }

  def afterAll = {
    val getVideoElasticRequest =
      sttp.header("content-type", "application/json; charset=UTF-8")
        .get(uri"http://es-content.dev.bonnier.news:9200/klara1/klara/dn.screen9.1uwHxJLDuuBKBHGHQcissw/")
    val getVideoElasticResponse = getVideoElasticRequest.send()
    getVideoElasticResponse.code match {
      case 200 => sttp.header("content-type", "application/json; charset=UTF-8")
        .delete(uri"http://es-content.dev.bonnier.news:9200/klara1/klara/dn.screen9.1uwHxJLDuuBKBHGHQcissw/")
        .send()
    }
  }
}
