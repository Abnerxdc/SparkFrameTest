package manager

import java.util.Properties

import config.ConfigManager
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.log4j.Logger

/**
  * Created by Administrator on 2017/7/21.
  */
object KafkaManager {
  //获取kafkaProducer对象
  private val mKafkaProducer : KafkaProducer[String,String] = setKafkaManager
  //获取kafkaConsumer对象
  private val mKafkaConsumer : KafkaConsumer[String,String] = setKafkaConsumer

  private val logger = Logger.getLogger(KafkaManager.getClass)

  def getmKafkaProducer : KafkaProducer[String , String ] = mKafkaProducer

  def getmKafkaConsumer : KafkaConsumer[String , String] = mKafkaConsumer

  def setKafkaManager : KafkaProducer[String,String]= {
    logger.info(" Start init KafkaManager ")
    val props = ConfigManager.getKafkaProConf()
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,classOf[StringSerializer].getName)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,classOf[StringSerializer].getName)
    new KafkaProducer[String,String](props)
  }
  Runtime.getRuntime.addShutdownHook(new Thread(){
    override def run(): Unit ={
      mKafkaProducer.close()
      mKafkaConsumer.close()
    }
  })

  def setKafkaConsumer :KafkaConsumer[String , String] = {
  val props = ConfigManager.getKafkaConConf()

    new KafkaConsumer[String , String](props)
  }

}
