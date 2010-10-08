package models

import siena._
import java.lang.{Long => JLong}

class Item extends Model {

  @Id
  var id: JLong = _
    
  var label: String = _
  var done: Boolean = _
  var position: Int = _
    
  @Index( Array("list_index") )
  var list: List = _
    
  def this(list: List, label: String) = {
    this()
    this.label = label
    this.list = list
    this.done = done
    list.nextPosition += 1
    this.position = list.nextPosition
  }

  override def toString() = label
}

object ItemOp {
  def all = Model.all(classOf[Item])
    
  def findById(id: JLong) = all.filter("id", id).get
}
