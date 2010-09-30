What Is This?
=============
This sample app is just a translation of play-gae[1]'s lists-with-gae[2] in Scala.


How To Try it?
==============
At preset time(Sep 30, 2010), the latest play 1.1 and play-gae module
are required to run this app.

  $ pwd
  /tmp
  $ wget http://download.playframework.org/1.1-nightly/play-1.1-unstable-r1081.zip
  $ unzip play-1.1-unstable-r1081.zip
  $ export PLAY_PATH=/tmp/play-1.1-unstable-r1081
  $ export PATH=$PLAY_PATH:$PATH
  $ play install scala

  $ wget wget http://googleappengine.googlecode.com/files/appengine-java-sdk-1.3.7.zip
  $ unzip appengine-java-sdk-1.3.7.zip
  $ export GAE_PATH=/tmp/appengine-java-sdk-1.3.7

  $ git clone git://github.com/guillaumebort/play-gae.git
  $ (cd play-gae; ant -Dplay.path=$PLAY_PATH)
  $ mv play-gae $PLAY_PATH/modules/gae-trunk

  $ play install siena
  $ git clone git://github.com/ymnk/lists-with-play-scala-gae.git
  $ cd lists-with-play-scala-gae
  $ vi war/WEB-INF/appengine-web.xml // set your GAE app-id into application tag.
  $ play gae:deploy

[1] http://www.playframework.org/modules/gae
[2] http://github.com/guillaumebort/play-gae/tree/master/samples-and-tests/lists-with-gae/
