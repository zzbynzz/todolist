package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Task
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi

import views._
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport{

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  val taskForm = Form (
  	"label" -> nonEmptyText
  )

  def index = Action {
    Redirect(routes.HomeController.tasks)
  }

  def tasks = Action {
  	Ok(html.index(Task.all(),taskForm))
  }

  def newTask = Action { implicit request =>
  	taskForm.bindFromRequest.fold(
  		errors => BadRequest(views.html.index(Task.all(), errors)),
  		label => {
  			Task.create(label)
  			Redirect(routes.HomeController.tasks)
  		}
  	)
  }

  def deleteTask(id: Long) = Action {
  	Task.delete(id)
  	Redirect(routes.HomeController.tasks)
  }

}
