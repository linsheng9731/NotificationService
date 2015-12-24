package remote

/**
  * Created by damon on 15/12/14.
  */
object Messages {

  case class sendMail(content:String,to:String,title:String)
  case class sendSNS(content:String,to:String)
  case class reload(message:String)

}
