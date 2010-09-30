package controllers

import models._
import notifiers._
import play._
import play.mvc._

import play.data.validation._
import play.modules.gae._

import java.lang.{Long => JLong}

object Lists extends Controller {
    
  @Before
  def checkConnected() = {
    Option(GAE.getUser()).map{ u =>
      renderArgs.put("user", u.getEmail())
    } getOrElse { Application.login() }
  }
    
  def index() = {
    val lists = ListOp.findByUser(getUser())
    render(lists)
  }
    
  def show(id: JLong) = {
    val list = ListOp.findById(id)
    notFoundIfNull(list)
    checkOwner(list)
    val items = list.items()
    val oldItems = list.oldItems()
    render(list, items, oldItems)
  }
    
  def blank() = {
    render()
  }
    
  def create(name: String) = {
    if(Validation.hasErrors()) {
      flash.error("Oops, please give a name to your new list")
      blank()
    }
    new List(getUser(), name).insert()
    index()
  }
    
  def delete(id: JLong) = {
    val list = ListOp.findById(id)
    notFoundIfNull(list)
    checkOwner(list)
    list.delete()
    flash.success("The list %s has been deleted", list)
    index()
  }
    
  def edit(id: JLong) = {
    val list = ListOp.findById(id)
    notFoundIfNull(list)
    checkOwner(list)
    render(list)
  }
    
  def save(id: JLong, name: String, notes: String) = {
    if(Validation.hasErrors()) {
      params.flash()
      flash.error("Oops, please give a name to your list")
      edit(id)
    }
    val list = ListOp.findById(id)
    notFoundIfNull(list)
    checkOwner(list)
    list.name = name
    list.notes = notes
    list.update()
    show(list.id)
  }
    
  def addItem(id: JLong, label: String) = {
    val list = ListOp.findById(id)
    notFoundIfNull(list);
    checkOwner(list);
    new Item(list, label).insert()
    list.update()
    show(id)
  }
    
  def changeItemState(id: JLong, itemId: JLong, done: Boolean) = {
    val item = ItemOp.findById(itemId)
    notFoundIfNull(item)
    checkOwner(item)
    item.done = done
    item.list.nextPosition += 1
    item.position = item.list.nextPosition
    item.update()
    item.list.update()
    ok()
  }
    
  def deleteItem(id: JLong, itemId: JLong) = {
    val item = ItemOp.findById(itemId)
    notFoundIfNull(item)
    checkOwner(item)
    item.delete()
    ok()
  }
    
  def reorderItems(id: JLong, newOrder: String) = {
    val list = ListOp.findById(id)
    notFoundIfNull(list)
    checkOwner(list)
    list.nextPosition = 0;
    for(p <-  newOrder.split(",")) {
      val item = ItemOp.findById(JLong.parseLong(p))
      if(item.list.id.equals(id)) {
        list.nextPosition += 1
        item.position = list.nextPosition
        item.update()
      }
    }
    list.update()
    ok()
  }
    
  def email(id: JLong) = {
    val list = ListOp.findById(id)
    notFoundIfNull(list)
    checkOwner(list)
    Notifier.emailList(list)
    flash.success("This list has been emailed to %s", list.user)
    show(id)
  }
    
  private def getUser(): String = {
    renderArgs.get("user", classOf[String])
  }
    
  private def checkOwner(list: List): Unit  = {
    if(!getUser().equals(list.user)) {
      forbidden()
    }
  }
    
  private def checkOwner(item: Item): Unit = {
    item.list.get();
    checkOwner(item.list)
  }
}

