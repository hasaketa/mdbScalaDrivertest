import org.mongodb.scala._

import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Aggregates._

import com.mongodb.WriteConcern

import Helpers._

object Main  {
   def main(args: Array[String]): Unit = {

      val mongoClient: MongoClient = MongoClient("mongodb://localhost")

      val database: MongoDatabase = mongoClient.getDatabase("scaladriverdb").withWriteConcern(WriteConcern.MAJORITY)

      val collection: MongoCollection[Document] = database.getCollection("findTest")
       
      collection.drop().results()

      val doc: Document = Document("_id" -> 0, "name" -> "MongoDB", "type" -> "database",
                             "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

      collection.insertOne(doc).results()


      val startTime = System.currentTimeMillis()
      collection.find(equal("name", "MongoDB")).first().printHeadResult()
      val endTime = System.currentTimeMillis()
      System.out.println("Total execution time for find: " + (endTime - startTime) + "ms")

     
      val startTime2 = System.currentTimeMillis()
      collection.aggregate(Seq(filter(equal("name", "MongoDB")))).printResults()
      val endTime2 = System.currentTimeMillis()
      System.out.println("Total execution time for aggregation: " + (endTime2 - startTime2 + "ms"))
    }
} 
