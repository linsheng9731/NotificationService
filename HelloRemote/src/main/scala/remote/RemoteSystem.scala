package remote
import Config._
import akka.actor.Props
import Actors._

object RemoteSystem extends App  {
    val system = remoteSystem("remote_system",1234)
    val route = system.actorOf(Props[RouteActor],"ruote")
    route ! "The remote system is alive !"

}

