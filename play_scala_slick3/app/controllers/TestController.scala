package controllers

import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.libs.json.{Json, Writes}
import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, Controller, RequestHeader}

import dto.User


@Singleton
class TestController  @Inject() extends Controller{

  def index = Action { request =>
    Ok("Got request [" + request + "]")
  }

  // リクエストを暗黙のパラメータとして受け取っとメソッドを呼び出す。
  def greet = Action { implicit request =>
    Ok(greeting("Hello"))
  }

  // リクエストを暗黙の引数を受け取る関数
  private def greeting(say: String)(implicit req: RequestHeader) = say + "," + req.remoteAddress


  /**
    * jsonをリクエストとして受け取る
    * 以下のようなリクエストを飛ばしたらjsonをパースしているのが確認できる
    * curl 'http://localhost:9000/jsonReq' -H 'Content-Type: text/json' --data-binary '{"name": "jon doh"}'
    * @return
    */
  def jsonReq = Action(parse.json) { request =>
    (request.body \ "name").asOpt[String].map { name =>
      Ok("Hello " + name)
    }.getOrElse {
      BadRequest("Missing parameter [name]")
    }
  }


  // Jsonで受け取ったリクエストをセットするためのcase class
  case class AddTodo(categoryId:Long, title:String, text:String)
  // リクエストで受け取るJsonのデータ構造
  implicit val addTodoForm:Form[AddTodo] = Form (
    mapping(
      "categoryId" -> longNumber,
      "title"-> text,
      "text"-> text
    )(AddTodo.apply)(AddTodo.unapply)
  )
  /**
    * jsonをリクエストとして受け取るcase classにセットする
    * curl 'http://localhost:9000/jsonReq2' -H 'Content-Type: text/json' --data-binary '{"categoryId": 1, "title": "play test", "text": "jon doh"}'
    * @return
    */
  def jsonReq2 = Action { implicit request =>
    try{
      addTodoForm.bindFromRequest.fold(
        errors => {BadRequest("bad request")},
        validForm => {
          Ok("categoryId: " + validForm.categoryId + " title:" + validForm.title + " text:" + validForm.text)
        }
      )
    }
  }

  // Jsonのレスポンスを返すための暗黙の型変換
  // AddTodoのcase classをJson形式に変換するときはこれが使われる
  implicit val todoWrite = new Writes[AddTodo] {
    def writes(todo: AddTodo) =
      Json.obj(
        "categoryId" -> todo.categoryId,
        "title" -> todo.title,
        "text" -> todo.text
      )
  }

  //
  /**
    * 受け取ったJsonリクエストのAddTodoに該当する部分をそのまま返す
    * 以下のリクエストで動作が確認できる
    * curl 'http://localhost:9000/jsonRes' -H 'Content-Type: text/json' --data-binary '{"categoryId": 2, "title": "json response test", "text": "this is text"}'
    * @return
    */
  def jsonRes = Action { implicit request =>
    try{
      addTodoForm.bindFromRequest.fold(
        errors => {BadRequest("bad request")},
        validForm => {
          Ok(Json.toJson(validForm))

          // こんな感じでルート要素を変更してレスポンスを返すこともできる
          //Ok(Json.obj("todo" -> validForm))
        }
      )
    }
  }

  /**
    * セッションを使ってカウントしてみる
    * playはステートレスのフレームワークになっておりセッション情報はユーザのクッキーに保持されるのには気をつけておきたい
    * 以下のURLにアクセスして動作確認
    * http://localhost:9000/sessionCount
    * @return
    */
  def sessionCount = Action { request =>
    val nextCount = request.session.get("count") match {
      case Some(x) => x.toInt + 1
      case None => 1
    }
    Ok("counta: " + nextCount).withSession(
      request.session + ("count" -> nextCount.toString)
    )
  }


  /**
    * twirl動作確認用アクション
    * http://localhost:9000/htmlTest
    * @return
    */
  def htmlTest = Action {
    val user = User("john", "doh")
    Ok(views.html.sample(user))
  }

}