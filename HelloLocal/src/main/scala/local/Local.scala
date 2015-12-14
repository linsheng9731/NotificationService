package local

import akka.actor._
import remote.Messages._
object Local extends App {

  implicit val system = ActorSystem("LocalSystem")
  val localActor = system.actorOf(Props[LocalActor], name = "LocalActor")  // the local actor
  localActor ! "START"                                                     // start the action

}

class LocalActor extends Actor {

  // create the remote actor
  val remote = context.actorSelection("akka.tcp://remote_system@127.0.0.1:1234/user/ruote")
  var counter = 0

  def receive = {
    case "START" =>
        remote ! new sendMail("welcome regist growingio ! enjoy it !","linshengsheng@growingio.com","regist email")
    case msg: String =>
        println(s"LocalActor received message: '$msg'")
        if (counter < 5) {
            sender ! "Hello back to you"
            counter += 1
        }
  }
}
