package task

import scala.collection.mutable.SortedMap

class SortedMapTaskRepo {

  // data
  val todoList: SortedMap[Int, Todo] = SortedMap.empty[Int, Todo]
  val doingList: SortedMap[Int, Doing] = SortedMap.empty[Int, Doing]

  def getPong(): String = "pong"

  def createNewTodo(todo: Todo): Int = {
    val item = todoList.lastOption
    val id = item match {
      case Some(value) => value._1 + 1
      case None        => 0
    }
    todoList.addOne(id -> Todo(id, todo.detail))
    id
  }

  def moveToDoing(todo: Todo): Option[Doing] = {
    val item = todoList.get(todo.id)
    val result = item match {
      case Some(value) =>
        todoList.remove(value.id)
        val doing = Doing(value.id, value.detail)
        doingList.addOne(doing.id -> doing)
        Some(doingList.last._2)
      case None => None
    }
    result
  }

  def getAllItemInTodo(): List[Todo] = todoList.values.toList

  def getAllItemInDoing(): List[Todo] = doingList.values.toList

}
