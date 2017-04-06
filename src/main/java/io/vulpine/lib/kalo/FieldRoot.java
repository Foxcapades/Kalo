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

import java.lang.reflect.Field;

class FieldRoot implements Imu
{
  private final Poi annotation;

  private final Field field;

  private final PropertyAggregator map;

  FieldRoot( final Poi annotation, final Field field, final PropertyAggregator map )
  {
    this.annotation = annotation;
    this.field = field;
    this.map = map;
  }

  @Override
  public Poi getAnnotation()
  {
    return annotation;
  }

  @Override
  public Field getMember()
  {
    return field;
  }

  @Override
  public Object getValue( final Object inst )
  {
    return FieldUtil.processField(field, map, inst);
  }

  @Override
  public String getName()
  {
    return annotation.header().isEmpty()
      ? FieldUtil.formatName(field)
      : annotation.header();
  }

  @Override
  public Class < ? > getType()
  {
    return field.getType();
  }
}
