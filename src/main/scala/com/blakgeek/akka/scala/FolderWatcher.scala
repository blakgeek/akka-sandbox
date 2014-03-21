package com.blakgeek.akka.scala

import java.nio.file.{Paths, FileSystems}
import java.nio.file.StandardWatchEventKinds._
import scala.collection.JavaConverters._

/**
 * User: Carlos Lawton
 * Date: 3/20/14
 * Time: 11:26 PM
 */
object FolderWatcher extends App {

  val watcher = FileSystems.getDefault.newWatchService()
  Paths.get("/tmp/foo").register(watcher, ENTRY_CREATE, ENTRY_DELETE)

  while(true) {
    println("taking")
    val key = watcher.take
    key.pollEvents().asScala foreach { event =>
      event.kind() match {
        case ENTRY_CREATE => println("created")
        case ENTRY_DELETE => println("deleted")
      }
    }
    key.reset
  }
}
