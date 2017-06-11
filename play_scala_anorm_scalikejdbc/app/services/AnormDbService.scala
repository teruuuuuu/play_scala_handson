package services

import javax.inject.Inject

import dto.RequestText
import play.api.db.Database
import anorm.SqlParser.get
import anorm.{SQL, ~}




@javax.inject.Singleton
class AnormDbService  @Inject()() {
  val textParser = {
    get[Long]("id") ~
      get[String]("input_text") map {
      case id ~ input_text => RequestText(id, input_text)
    }
  }

  def addText(requestText: String)(implicit db: Database): Option[Long] = {
    insertText(requestText)
  }

  def insertText(requestText: String)(implicit db: Database) = {
    db.withConnection { implicit connection =>
      SQL(
        """
          insert into request_text (id, input_text) values
          ( null,
            {request_text}
           )
        """
      ).on(
        'request_text -> requestText
      ).executeInsert()
    }
  }

  def getText()(implicit db: Database): Seq[RequestText] = {
    db.withConnection { implicit connection =>
      SQL("select * from request_text").as(textParser.*)
    }
  }
}