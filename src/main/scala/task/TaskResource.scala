package task

import com.twitter.finatra.http.Controller
import com.twitter.finagle.http.Request
import scala.collection.mutable.SortedMap
import com.google.inject.Inject

class TaskResource @Inject() (repo: SortedMapTaskRepo) extends Controller {

  val todoList: SortedMap[Int, Todo] = SortedMap.empty[Int, Todo]
  val doingList: SortedMap[Int, Doing] = SortedMap.empty[Int, Doing]

  get("/ping") { _: Request => repo.getPong() }

  post("/todo") { todo: Todo =>
    val id = repo.createNewTodo(todo)
    response.created(s"created $id")
  }

  get("/todo") { _: Request => repo.getAllItemInTodo }

  post("/todo/next") { todo: Todo =>
    val result = repo.moveToDoing(todo)
    result match {
      case Some(value) => response.created(s"Item was moved to doing")
      case None        => response.notFound(s"Item not found")
    }
  }

  get("/doing", { _: Request => repo.getAllItemInDoing })

}
