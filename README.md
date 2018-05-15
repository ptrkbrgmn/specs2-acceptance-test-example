# specs2 acceptance test example

### Resources
specs2  
https://etorreborre.github.io/specs2/guide/SPECS2-2.4.17/org.specs2.guide.UserGuide.html#User+Guide

Scala HTTP client  
http://sttp.readthedocs.io/en/latest/index.html

## Prerequisites ##
Install Scala and sbt

## Test case ##
Acceptance test of for fetching a single video from Klaras REST API:   
 
<b>Given</b> there is a video added to ElasticSearch with id 'dn.screen9.1uwHxJLDuuBKBHGHQcissw'  
<b>When</b> fetching a video with id 'dn.screen9.1uwHxJLDuuBKBHGHQcissw'  
<b>Then</b> response should contain the right json body  

```json
{
  "id" : "dn.screen9.1uwHxJLDuuBKBHGHQcissw",
  "fileName" : "20171107-arbogany-1053_NormalHires.mp4",
  "title" : "Grafikfilm: Arbogamorden  (uppdaterad för hovrätten)",
  "description" : "Huvudpersonerna och händelserna som ledde fram till rättegången och dom mot den 42:åriga kvinnan och hennes pojkvän. Nu prövas målet i Svea Hovrätt",
  "streamUrl" : "https://video-cdn.dn.se/M/V/1/u/1uwHxJLDuuBKBHGHQcissw_360p_h264h.mp4?v=1&token=0ed558211ccafe3db4784",
  "thumbnails" : {
    "small" : "https://csp.screen9.com/img/1/u/w/H/thumb_1uwHxJLDuuBKBHGHQcissw/8.jpg",
    "large" : "https://csp.screen9.com/img/1/u/w/H/image_1uwHxJLDuuBKBHGHQcissw/8.jpg"
  },
  "duration" : "PT1M36.28S",
  "publishedAt" : null,
  "createdAt" : "2017-11-07T14:36:46.000+01:00",
  "createdBy" : null,
  "transcodeStatus" : "done",
  "sentToTranscodeAt" : "2017-11-07T14:38:12.000+01:00",
  "status" : "published"
}
```

## Setup test fixture ##
In nav project comment out existing Groovy tests mvn execution in script `.run.sh`, and then it to start containers with ElasticSearch, Redis and nav-klara-dx up and running:
```sh
./integration-test/nav-klara-it/run.sh
```

## Run test with sbt ##
```sh
sbt> testOnly example.NavaKlaraVideo 
```

## Run test with IntelliJ ##
Right click on test an choose run

## Test Success Output
```sh
[info] Given there is a video added to elasticsearch with id dn.screen9.1uwHxJLDuuBKBHGHQcissw
[info] When fetching a video with id dn.screen9.1uwHxJLDuuBKBHGHQcissw
[info] + Then response should contain the right data
[info] Fetch a single video
[info] Total for specification NavaKlaraVideo
[info] Finished in 499 ms
[info] 1 example, 4 expectations, 0 failure, 0 error
[info] Passed: Total 1, Failed 0, Errors 0, Passed 1
[success] Total time: 8 s, completed May 15, 2018 10:02:55 AM
```

## Test Failure Output
```sh
[info] Given there is a video added to elasticsearch with id dn.screen9.1uwHxJLDuuBKBHGHQcissw
[info] When fetching a video with id dn.screen9.1uwHxJLDuuBKBHGHQcissw
[error] x Then response should contain the right data
..
[error] ...ished[xx]"
[error] }
[info] Fetch a single video
[info] Total for specification NavaKlaraVideo
[info] Finished in 457 ms
[info] 1 example, 4 expectations, 1 failure, 0 error
[error] Failed: Total 1, Failed 1, Errors 0, Passed 0
[error] Failed tests:
[error] 	example.NavaKlaraVideo
[error] (Test / test) sbt.TestsFailedException: Tests unsuccessful
[error] Total time: 2 s, completed May 15, 2018 10:10:50 AM
```

### Pros
+ Can be run by IntelliJ
+ \<Click to se difference> in IntelliJ works
+ Seems pretty fast
 
### Cons
- For some types of test conditions are specified twice, both in text and in method, i.e you can have the right condition in the text but the wrong ine int the method doing the actual check.  
  This might be fixed using StandardDelimitedStepParsers 
- Error messages could be more informative
- Not very intuitive (at least not for a Scala newbie). A lot of magic.
- Not as nice output as ScalaTest
- How pass result from Given to THen? Variables in Given is not in scope in Then