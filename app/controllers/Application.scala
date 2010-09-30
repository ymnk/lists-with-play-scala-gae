package controllers

import play.mvc._
import play.modules.gae._

import com.google.appengine.api.users._

object Application extends Controller {

  def index() = {
    if(GAE.isLoggedIn()) {
      Lists.index()
    }
    render()
  }
    
  def login() = {
    GAE.login("Application.index")
  }
    
  def logout() = {
    GAE.logout("Application.index");
  }
}
