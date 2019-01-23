package com.xlizy.middleware.cc.client.spring.property;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;

/**
 * @author xlizy
 */
public class SpringValueRegistry {

  private final Multimap<String, SpringValue> registry = LinkedListMultimap.create();

  public void register(String key, SpringValue springValue) {
    registry.put(key, springValue);
  }

  public Collection<SpringValue> get(String key) {
    return registry.get(key);
  }
}
