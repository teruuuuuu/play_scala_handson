package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.H2Driver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = PlayEvolutions.schema ++ RequestText.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table PlayEvolutions
   *  @param id Database column ID SqlType(INTEGER), PrimaryKey
   *  @param hash Database column HASH SqlType(VARCHAR), Length(255,true)
   *  @param appliedAt Database column APPLIED_AT SqlType(TIMESTAMP)
   *  @param applyScript Database column APPLY_SCRIPT SqlType(CLOB)
   *  @param revertScript Database column REVERT_SCRIPT SqlType(CLOB)
   *  @param state Database column STATE SqlType(VARCHAR), Length(255,true)
   *  @param lastProblem Database column LAST_PROBLEM SqlType(CLOB) */
  case class PlayEvolutionsRow(id: Int, hash: String, appliedAt: java.sql.Timestamp, applyScript: Option[java.sql.Clob], revertScript: Option[java.sql.Clob], state: Option[String], lastProblem: Option[java.sql.Clob])
  /** GetResult implicit for fetching PlayEvolutionsRow objects using plain SQL queries */
  implicit def GetResultPlayEvolutionsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[java.sql.Clob]], e4: GR[Option[String]]): GR[PlayEvolutionsRow] = GR{
    prs => import prs._
    PlayEvolutionsRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<?[java.sql.Clob], <<?[java.sql.Clob], <<?[String], <<?[java.sql.Clob]))
  }
  /** Table description of table PLAY_EVOLUTIONS. Objects of this class serve as prototypes for rows in queries. */
  class PlayEvolutions(_tableTag: Tag) extends Table[PlayEvolutionsRow](_tableTag, "PLAY_EVOLUTIONS") {
    def * = (id, hash, appliedAt, applyScript, revertScript, state, lastProblem) <> (PlayEvolutionsRow.tupled, PlayEvolutionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(hash), Rep.Some(appliedAt), applyScript, revertScript, state, lastProblem).shaped.<>({r=>import r._; _1.map(_=> PlayEvolutionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INTEGER), PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.PrimaryKey)
    /** Database column HASH SqlType(VARCHAR), Length(255,true) */
    val hash: Rep[String] = column[String]("HASH", O.Length(255,varying=true))
    /** Database column APPLIED_AT SqlType(TIMESTAMP) */
    val appliedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("APPLIED_AT")
    /** Database column APPLY_SCRIPT SqlType(CLOB) */
    val applyScript: Rep[Option[java.sql.Clob]] = column[Option[java.sql.Clob]]("APPLY_SCRIPT")
    /** Database column REVERT_SCRIPT SqlType(CLOB) */
    val revertScript: Rep[Option[java.sql.Clob]] = column[Option[java.sql.Clob]]("REVERT_SCRIPT")
    /** Database column STATE SqlType(VARCHAR), Length(255,true) */
    val state: Rep[Option[String]] = column[Option[String]]("STATE", O.Length(255,varying=true))
    /** Database column LAST_PROBLEM SqlType(CLOB) */
    val lastProblem: Rep[Option[java.sql.Clob]] = column[Option[java.sql.Clob]]("LAST_PROBLEM")
  }
  /** Collection-like TableQuery object for table PlayEvolutions */
  lazy val PlayEvolutions = new TableQuery(tag => new PlayEvolutions(tag))

  /** Entity class storing rows of table RequestText
   *  @param id Database column ID SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param inputText Database column INPUT_TEXT SqlType(CHAR), Length(256,false) */
  case class RequestTextRow(id: Long, inputText: Option[String])
  /** GetResult implicit for fetching RequestTextRow objects using plain SQL queries */
  implicit def GetResultRequestTextRow(implicit e0: GR[Long], e1: GR[Option[String]]): GR[RequestTextRow] = GR{
    prs => import prs._
    RequestTextRow.tupled((<<[Long], <<?[String]))
  }
  /** Table description of table REQUEST_TEXT. Objects of this class serve as prototypes for rows in queries. */
  class RequestText(_tableTag: Tag) extends Table[RequestTextRow](_tableTag, "REQUEST_TEXT") {
    def * = (id, inputText) <> (RequestTextRow.tupled, RequestTextRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), inputText).shaped.<>({r=>import r._; _1.map(_=> RequestTextRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column INPUT_TEXT SqlType(CHAR), Length(256,false) */
    val inputText: Rep[Option[String]] = column[Option[String]]("INPUT_TEXT", O.Length(256,varying=false))
  }
  /** Collection-like TableQuery object for table RequestText */
  lazy val RequestText = new TableQuery(tag => new RequestText(tag))
}
