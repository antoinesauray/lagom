/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package sample.helloworld.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

 //#helloservice-interface
public interface HelloService extends Service {
  @Override
  default Descriptor descriptor() {
    return named("helloservice").with(
        restCall(Method.GET,  "/api/hello/:id", hello())
      ).withAutoAcl(true);
  }

  ServiceCall<String, NotUsed, String> hello();
}
//#helloservice-interface
