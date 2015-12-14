package remote
import com.fasterxml.jackson.databind.ObjectMapper
import akka.actor._
import com.fasterxml.jackson.module.scala.DefaultScalaModule

/**
  * Created by damon on 15/12/14.
  */
object CommonUtils {
 // val reloadActorRef = context.actorSelection("/user/ruote/reload")

 var jacksonMapper = new ObjectMapper().registerModule(DefaultScalaModule)

}
