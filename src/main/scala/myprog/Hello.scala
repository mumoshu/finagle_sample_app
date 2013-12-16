package myprog

import java.net.SocketAddress
import java.net.InetSocketAddress

import org.jboss.netty.handler.codec.http.HttpRequest
import org.jboss.netty.handler.codec.http.HttpResponse
import org.jboss.netty.handler.codec.http.DefaultHttpResponse

import com.twitter.finagle.Service
import com.twitter.finagle.builder.Server
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.Http
import org.jboss.netty.buffer.ChannelBuffers.copiedBuffer
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.handler.codec.http.HttpResponseStatus._
import org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1
import org.jboss.netty.util.CharsetUtil.UTF_8
import com.twitter.util.Future

object Hello {

  def main(args: Array[String]) {
    val service: Service[HttpRequest, HttpResponse] = new Service[HttpRequest, HttpResponse] {
      def apply(request: HttpRequest) = Future {
        val response = new DefaultHttpResponse(HTTP_1_1, OK)
        val finagle = "Finagle"
        response.setContent(copiedBuffer(s"Hello ${finagle}!", UTF_8))
        response
      }
    }

    val address: SocketAddress = new InetSocketAddress(10000)

    val server: Server = ServerBuilder()
      .codec(Http())
      .bindTo(address)
      .name("HttpServer")
      .build(service)
  }

}
