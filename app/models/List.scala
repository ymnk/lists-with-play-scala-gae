package models

import java.util._
import siena._
import java.lang.{Long => JLong}

class List extends Model {

  @Id
  var id: JLong = 0L

  var user: String = _
  var name: String = _
  var notes: String = ""
  var nextPosition: Int = 0
    
  def this(user: String, name: String) = {
    this()
    this.user = user
    this.name = name
  }

  /*
   * @Filter annotation will cause the following exception,
   *   SienaException occured : java.lang.IllegalAccessException: 
   *     Class siena.Model can not access a member of class models.List 
   *     with modifiers "private"
   */
  //@Filter("list")
  //var _items: Query[Item] = _
  //def items() = _items.filter("done", false).order("position").fetch()
  //def oldItems() = _items.filter("done", true).order("-position").fetch()

  def items() = ItemOp.all.filter("list", this).filter("done", false).order("position").fetch()
    
  def oldItems() = ItemOp.all.filter("list", this).filter("done", true).order("-position").fetch()
    
  override def toString() = name
    
}


object ListOp {
  def all = Model.all(classOf[List])
    
  def findById(id: JLong) = all.filter("id", id).get()
    
  def findByUser(user: String) = all.filter("user", user).fetch()
}
