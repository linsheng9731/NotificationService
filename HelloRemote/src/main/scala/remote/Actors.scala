package remote

import akka.actor._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import concurrent.duration._
import remote.Messages._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection._
import remote.CommonUtils._
import com.redis._

object Actors{

  class RouteActor extends Actor {

    var i = 0
    val mailSenders = for (_<- 0 until 4) yield context.actorOf(Props[MailSender])
    val snsSenders = for (_<- 0 until 4) yield  context.actorOf(Props[SNSsender])
    val reload = context.actorOf(Props[ReloadActor],"reload")
    def receive = {
      case  msg:sendMail =>{
          mailSenders(i) forward msg
          i = (i+1)%4 //robin
      }
      case msg: sendSNS=>{
          snsSenders(i) forward msg
          i = (i+1)%4
      }
      case msg =>{
        println("recieve message : "+msg)
      }

    }
  }

  class ReloadActor extends Actor{

    var failedTasks = mutable.Queue[String]()
    val redisClient = new RedisClient("127.0.0.1",6379)

    val routeActorRef = context.actorSelection("/user/ruote")
    val reloadTask = context.system.scheduler.schedule(10.seconds,20.seconds,self,"reload")


    def receive = {
      case "reload" => {
        println("reload task begin ....")
          redisClient.rpop("failedTasks") match{
            case Some(message) =>{
              val msgProfiles = message.split(";")
              val msg = jacksonMapper.readValue(msgProfiles(1),Class.forName(msgProfiles(0)))
              //send to route
              routeActorRef !msg
              println("reload sendMailTask")
            }
            case None => println("no failed tasks ")
          }
      }
      case msg:String =>{
        println("one  task failed !")
        redisClient.lpush("failedTasks",msg)

      }
    }

  }

  class MailSender extends Actor {

    val reloadActorRef = context.actorSelection("/user/ruote/reload")

    def sendMail(content:String,to:String,title:String):Boolean ={
      println("begin send the mail")
      println("content:"+content+" to "+to)
      println("send mail end ")
      false
    }

    def receive = {

      case msg: sendMail => {
        println(s"Remote work actor received message '$msg'")
        if (!sendMail(msg.content, msg.to, msg.title))
          reloadActorRef ! msg.getClass.getName+";"+jacksonMapper.writeValueAsString(msg)//serialize
      }

      case msg =>{
        println("MailSender recieve unhandle message : ")+msg.toString
      }
    }

  }


  class SNSsender extends Actor {

    val reloadActorRef = context.actorSelection("/user/ruote/reload")

    def sendSNS(content:String,to:String):Boolean ={
      println("begin send the SNS")
      println("send SNS end ")
      false
    }

    def receive = {
      case msg: sendSNS =>{
        println(s"Remote work actor received message '$msg'")
        if (!sendSNS(msg.content,msg.to)) //task failed
          reloadActorRef ! msg.getClass.getName+";"+jacksonMapper.writeValueAsString(msg)//serialize
      }
      case msg =>{
        println("MailSender recieve unhandle message : ")+msg.toString
      }
    }
  }

}


