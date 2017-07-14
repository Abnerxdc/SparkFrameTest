package main

import config.ConfigManager
import manager.{AppInit, SparkManager}

/**
  * Created by Administrator on 2017/7/14.
  */
object Main extends Serializable{
  def main(args : Array[String]): Unit ={
    AppInit.initApp()
    SparkManager.sparkClusterContextInstance(ConfigManager.getSparkConf())
    val ssi = SparkManager.sparkSqlContextInstance
    val users = ssi match {
      case Some(sqlContext) => sqlContext.read.load(ConfigManager.getPargPath())
    }
    println("ssssssssssssssssssssssss = "+users.count())
    users.show(10)
  }

}
