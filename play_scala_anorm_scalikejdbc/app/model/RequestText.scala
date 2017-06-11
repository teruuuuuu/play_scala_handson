package model

import scalikejdbc._

//Class.forName("org.h2.Driver")
//ConnectionPool.singleton("jdbc:h2:mem:scalikeDb", "sa", "")


case class RequestText(id: Long,
                        input_text: String) {

  def save()(implicit session: DBSession = RequestText.autoSession): RequestText = RequestText.save(this)(session)
  def destroy()(implicit session: DBSession = RequestText.autoSession): Int = RequestText.destroy(this)(session)

}


object RequestText extends SQLSyntaxSupport[RequestText] {

  override val autoSession = AutoSession
  override val schemaName = Some("PUBLIC")
  override val tableName = "request_text"
  override val columns = Seq("id", "input_text")
  val r = RequestText.syntax("r")

  def apply(r: SyntaxProvider[RequestText])(rs: WrappedResultSet): RequestText = apply(r.resultName)(rs)
  def apply(r: ResultName[RequestText])(rs: WrappedResultSet): RequestText = new RequestText(
    id = rs.get(r.id),
    input_text = rs.get(r.input_text)
  )

  def save(entity: RequestText)(implicit session: DBSession = autoSession): RequestText = {
    withSQL {
      update(RequestText).set(
        column.id -> entity.id,
        column.input_text -> entity.input_text
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: RequestText)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(RequestText).where.eq(column.id, entity.id) }.update.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[RequestText] = {
    withSQL(select.from(RequestText as r)).map(RequestText(r.resultName)).list.apply()
  }

  def findById(id: Long)(implicit session: DBSession = autoSession): Option[RequestText] = {
    withSQL {select.from(RequestText as r).where.eq(r.id, id)
    }.map(RequestText(r.resultName)).single.apply()
  }

  def findByIdWithQuery(id: Long)(implicit session: DBSession = autoSession): Option[RequestText] = {
    sql"select   id, input_text from   request_text where id = ${id}"
      .map{rs => RequestText(rs.long("id"), rs.string("input_text"))}.single.apply
  }
  def create(input_text: String)(implicit session: DBSession = autoSession): RequestText = {
    val generatedKey = withSQL {
      insert.into(RequestText).namedValues(
        column.input_text -> input_text
      )
    }.updateAndReturnGeneratedKey.apply()

    RequestText(
      id = generatedKey,
      input_text = input_text)
  }

}