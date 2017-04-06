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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class FieldUtil
{
  static Object processField(
    final Field field,
    final PropertyAggregator map,
    final Object inst
  )
  {
    Object value = tryFieldDirectly(field, inst);

    if ( value == null)
      value = tryGetter(field.getName(), map, inst);
    if ( value == null )
      value = tryFieldByForce(field, inst);

    return value;
  }

  static Object tryFieldDirectly( final Field field, final Object inst )
  {
    if ( (field.getModifiers() & Modifier.PUBLIC) != Modifier.PUBLIC )
      return null;

    try {
      return field.get(inst);
    } catch ( final IllegalAccessException e ) {
      return null;
    }
  }

  static Object tryFieldByForce( final Field field, final Object inst )
  {
    final Boolean accessible = field.isAccessible();
    Object ret;

    field.setAccessible(true);
    try {
      ret = field.get(inst);
    } catch ( IllegalAccessException e ) {
      ret = null;
    }
    field.setAccessible(accessible);

    return ret;
  }

  /**
   * Attempt to retrieve a Field value via a defined getter method.
   *
   * @param field Field to retrieve value for
   * @param map   PropertyAggregator of class hierarchy
   * @param inst  Object instance to pull Field value from
   *
   * @return Object value or null
   */
  static Object tryGetter( String field, PropertyAggregator map, Object inst )
  {
    final Method method;
    final String name;

    name = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);

    if ( !map.methodsByName().containsKey(name) )
      return null;

    method = map.methodsByName().get(name);

    try {
      return method.invoke(inst);
    } catch ( IllegalAccessException | InvocationTargetException e ) {
      return null;
    }
  }

  public static String formatName( final Field f )
  {
    String name = f.getName().replaceAll("[A-Z]", " $0").trim();
    return name.substring(0, 1).toUpperCase() + name.substring(1);
  }
}
