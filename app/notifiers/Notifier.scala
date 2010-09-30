package notifiers

import play.mvc._
import models._

/*
object Notifier extends Mailer {
  def emailList(list: List) = {
    setFrom(list.user)
    setSubject("Your list: %s", list.name)
    addRecipient(list.user)
    send(list)
  }
}
*/

import com.google.appengine.api.mail.{MailService, MailServiceFactory}

object Notifier {
  def emailList(list: List) = {
    val msg = new MailService.Message();
    msg.setSender(list.user)
    msg.setTo(list.user)
    msg.setSubject("Your list: %s".format(list.name))
    msg.setTextBody(list.toString)
    val mailservice = MailServiceFactory.getMailService()
    mailservice.send(msg)
  }
}
