/*
 * Copyright ${YEAR} Elizabeth Harper
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.vulpine.lib.kalo;

import java.lang.reflect.Method;

class MethodRoot implements Imu
{
  private final Poi annotation;

  private final Method method;

  public MethodRoot( final Poi annotation, final Method method )
  {
    this.annotation = annotation;
    this.method = method;
  }

  @Override
  public Poi getAnnotation()
  {
    return annotation;
  }

  @Override
  public Method getMember()
  {
    return method;
  }

  @Override
  public Object getValue( final Object inst )
  {
    return MethodUtils.processMethod(method, inst);
  }

  @Override
  public String getName()
  {
    return annotation.header().isEmpty()
      ? MethodUtils.formatName(method)
      : annotation.header();
  }

  @Override
  public Class < ? > getType()
  {
    return method.getReturnType();
  }
}
