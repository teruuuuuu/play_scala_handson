package controllers

import javax.inject._

import model.RequestText
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.libs.json.{JsError, Json, Writes}
import play.api.mvc._
import scalikejdbc.{DB, DBSession}
import views.html.helper.select

@Singleton
class ScalikeController @Inject() extends Controller {


  // jsonの書き込み
  implicit val requestWrite = new Writes[RequestText] {
    def writes(request: RequestText) =
      Json.obj(
        "id" -> request.id,
        "input_text" -> request.input_text
      )
  }

  /**
    * リストを全件取得して返す
    *　curl http://localhost:9000/scalikeList
    * @return
    */
  def list = Action { implicit request =>
    DB.readOnly { implicit session =>
      Ok(Json.obj("root" -> RequestText.findAll()))
    }
  }

  /**
    * ID指定で検索する
    * curl http://localhost:9000/scalikeList/1
    * @param id
    * @return
    */
  def findById(id: Long) = Action { implicit request =>
    DB.readOnly { implicit session =>
      Ok(Json.obj("root" -> RequestText.findById(id)))
    }
  }


  /**
    * ID指定で検索する2
    * curl http://localhost:9000/scalikeList/2
    * @param id
    * @return
    */
  def findByIdWithQuery(id: Long) = Action { implicit request =>
    DB.readOnly { implicit session =>
      Ok(Json.obj("root" -> RequestText.findByIdWithQuery(id)))
    }
  }


  case class Reuqest(input_text:String)
  // jsonの読み込み
  implicit val addRequestForm:Form[Reuqest] = Form (
    mapping(
      "input_text"-> text
    )(Reuqest.apply)(Reuqest.unapply)
  )

  /**
    * インサートを実行する
    * curl 'http://localhost:9000/scalike' -H 'Content-Type: text/json' --data-binary '{"input_text": "input A"}'
    * @return
    */
  def add = Action { implicit request =>
    addRequestForm.bindFromRequest.fold(
      e => {
        BadRequest(Json.obj("result" ->"failure"))
      },
      form => {
        DB.localTx { implicit session =>
          RequestText.create(form.input_text)
          Ok(Json.obj("root" -> RequestText.findAll()))
        }
      }
    )
  }

  /**
    * 削除
    * curl http://localhost:9000/scalikeDell/1
    * @param id
    * @return
    */
  def delete(id: Long) = Action { implicit request =>
    DB.localTx { implicit session =>
      RequestText.findById(id) match {
        case Some(requestText ) =>
          RequestText.destroy(requestText)
          Ok("success")
        case _ => Ok("fail")
      }
    }
  }
}