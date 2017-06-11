package controllers

import javax.inject.{Inject, Singleton}

import db.AnormDb
import dto.RequestText
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.Forms.mapping
import play.api.i18n.MessagesApi
import play.api.mvc.{Action, Controller, RequestHeader}

import services.AnormDbService


@Singleton
class AnormController  @Inject()(val messagesApi: MessagesApi,anormDb: AnormDb,anormDbService: AnormDbService) extends Controller{
  val db = anormDb.db

  def anormPage = Action { implicit request =>
    db.withTransaction { tr =>
      val texts:Seq[RequestText] = anormDbService.getText()(db)
      Ok(views.html.anorm(texts))
    }
  }


  case class AddText(input_text:String)
  implicit val addTextForm:Form[AddText] = Form (
    mapping(
      "input_text"-> text
    )(AddText.apply)(AddText.unapply)
  )

  /**
    * curl 'http://localhost:9000/anorm' -H "Content-type: application/json charset=utf-8" -d '{"input_text":"aaaa"}'
    * @return
    */
  def anormAdd = Action { implicit request =>
    addTextForm.bindFromRequest.fold(
      errors => {BadRequest("bad request")},
      validForm => {
        db.withTransaction{ tr =>
          anormDbService.insertText(validForm.input_text)(db)
        }
      }
    )
    Redirect("/anorm")
  }

}