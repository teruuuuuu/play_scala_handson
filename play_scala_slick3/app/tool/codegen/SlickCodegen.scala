package tool.codegen

import slick.codegen.SourceCodeGenerator


/**
  * homeControllerのクラス内に以下を記述しておいてsbt run後にactionを叩いて実行する
  *  import tool.codegen.SlickCodgeGen
  *  SlickCodgeGen.codeGen
  */
object SlickCodgeGen {

  def main(args: Array[String]): Unit = {
    codeGen
  }

  def codeGen: Unit ={
    // コード出力対象DBの接続設定
    val slickDriver = "slick.driver.H2Driver"
    val jdbcDriver = "org.h2.Driver"
    val url ="jdbc:h2:mem:slickDb"
    val user = "sa"
    val password = ""
    val outputFolder = "app"
    val pkg = "models"

    SourceCodeGenerator.main(
      Array(
        slickDriver,
        jdbcDriver,
        url,
        outputFolder,
        pkg,
        user,
        password
      )
    )
  }
}