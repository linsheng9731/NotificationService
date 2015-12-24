package remote

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

/**
  * Created by damon on 15/12/14.
  */
object Config {
  def remotingConfig(port:Int) = ConfigFactory.parseString(
    s"""
       |akka {
       |  loglevel = "DEBUG"
       |  actor {
       |    provider = "akka.remote.RemoteActorRefProvider"
       |   }
       |   remote {
       |   enabled-transports = ["akka.remote.netty.tcp"]
       |   netty.tcp {
       |       hostname = "127.0.0.1"
       |       port = $port
       |   }
       |   }
       |}
     """.stripMargin)

  def remoteSystem(name:String,port:Int):ActorSystem = ActorSystem(name,remotingConfig(port))

}
