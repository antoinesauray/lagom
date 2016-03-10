/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package com.lightbend.lagom.sbt.core;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;


public class Build {

  public static final List<String> sharedClasses;
  static {
    List<String> list = new ArrayList<String>();
    list.addAll(play.core.Build.sharedClasses);
    list.add(com.lightbend.lagom.sbt.server.ReloadableServer.class.getName());
    sharedClasses = Collections.unmodifiableList(list);
  }

}
