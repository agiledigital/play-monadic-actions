package controllers

import controllers.ActionDSL.MonadicActions
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future
import scala.util.Try

/**
 * @author Valentin Kasas
 */
object ExampleController extends Controller with MonadicActions {

  def service(id: Long) = Future.successful(Some(id * 2))

  def action(idStr: String) = Action.async {
    request =>
      for {
        id <- Try(idStr.toLong) ?| BadRequest
        number <- service(id) ?| NotFound
      } yield Ok(number.toString)
  }

}
