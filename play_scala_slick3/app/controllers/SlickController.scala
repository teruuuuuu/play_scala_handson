package controllers

import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.db.slick._
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import slick.driver.JdbcProfile
import models.Tables._
import javax.inject.Inject
import javax.inject.Singleton

import slick.driver.H2Driver.api._
import play.api.libs.json._

import scala.concurrent.Future

@Singleton
class SlickController @Inject()(val dbConfigProvider: DatabaseConfigProvider) extends Controller
  with HasDatabaseConfigProvider[JdbcProfile] {

  // defaultとは別のDBに対してリクエストを投げることもできる
  val db2 = Database.forConfig("db2")

  // jsonの書き込み
  implicit val requestWrite = new Writes[RequestTextRow] {
    def writes(request: RequestTextRow) =
      Json.obj(
        "id" -> request.id,
        "input_text" -> request.inputText
      )
  }

  /**
    * list表示する
    * curl http://localhost:9000/slick
    * @return
    */
  def list = Action.async { implicit rs =>
    db.run(RequestText.sortBy(t => t.id).result).map{ texts =>
      Ok(Json.obj("root" -> texts))
    }
  }

  /**
    * list表示する
    * curl http://localhost:9000/slickDb2
    * @return
    */
  def db2List = Action.async { implicit rs =>
    db2.run(RequestText.sortBy(t => t.id).result).map{ texts =>
      Ok(Json.obj("root" -> texts))
    }
  }

  /**
    * id指定で検索する
    * curl http://localhost:9000/find/1
    * @param id
    * @return
    */
  def findById(id: Long) = Action.async {implicit rs =>
    db.run(RequestText.filter(t => t.id === id).result).map{ text =>
      Ok(Json.obj("root" -> text))
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
    * insert
    * curl 'http://localhost:9000/create' -H 'Content-Type: text/json' -XPOST --data-binary '{"input_text": "input A"}'
    * @return
    */
  def create = Action.async { implicit rs =>
    addRequestForm.bindFromRequest.fold(
      error => {
        Future {
          BadRequest(Json.obj("result" ->"failure"))
        }
      },
      form => {
        val requestTextRow = RequestTextRow(0, Option(form.input_text))
        db.run(RequestText += requestTextRow).map { _ =>
          Ok(Json.obj("result" -> "success"))
        }
      }
    )
  }

  /**
    * update
    *
    * curl 'http://localhost:9000/update/1' -H 'Content-Type: text/json' -XPOST --data-binary '{"input_text": "update text"}'
    * @param id
    * @return
    */
  def update (id: Long)= Action.async { implicit rs =>
    addRequestForm.bindFromRequest.fold(
      error => {
        Future {
          BadRequest(Json.obj("result" ->"failure"))
        }
      },
      form => {
        val requestTextRow = RequestTextRow(id, Option(form.input_text))
        db.run(RequestText.filter(t => t.id === requestTextRow.id.bind).update(requestTextRow)).map { _ =>
          Ok(Json.obj("result" -> "success"))
        }
      }
    )
  }

  /**
    * delete
    * curl 'http://localhost:9000/delete/1' -H 'Content-Type: text/json' -XPOST --data-binary '{}'
    * @param id
    * @return
    */
  def delete(id: Long) = Action.async {implicit rs =>
    db.run(RequestText.filter(t => t.id === id).delete).map { _=>
      Ok(Json.obj("result" -> "success"))
    }
  }
}
