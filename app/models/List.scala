package models

import java.util._
import siena._
import java.lang.{Long => JLong}
/*
class List(var user: String, var name: String, 
           var notes: String = "",
           var nextPosition: Int = 0) extends Model {

  @Id
  var id: JLong = 0
    
  @Filter("list")
  var _items: Query[Item] = _ 
    
    
  def items() = _items.filter("done", false).order("position").fetch()
    
  def oldItems() = _items.filter("done", true).order("-position").fetch()
    
  override def toString() = name
    
}
*/

object ListOp {
  def all = Model.all(classOf[List])
    
  def findById(id: JLong) = all.filter("id", id).get()
    
  def findByUser(user: String) = all.filter("user", user).fetch()
}
