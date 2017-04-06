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
import java.lang.reflect.Method;
import java.util.*;

/**
 * = Property Aggregator
 *
 * Collects and sorts {@link Poi} annotated properties.
 *
 * @author mailto:elliefops@gmail.com[Ellie Harper]
 * @version 1
 * @since v0.1.0 - 2017-03-26
 */
class PropertyAggregator
{
  /**
   * Methods Found
   */
  protected final Map < Method, Poi[] > annotatedMethods;

  /**
   * Fields Found
   */
  protected final Map < Field, Poi[] > annotatedFields;

  /**
   * Prop
   */
  protected final Collection < Imu > columns;

  private final Map < String, Method > methods;

  public PropertyAggregator()
  {
    columns = new ArrayList <>();
    methods = new HashMap <>();
    annotatedMethods = new HashMap <>();
    annotatedFields = new HashMap <>();
  }

  public Map < String, Method > methodsByName()
  {
    return Collections.unmodifiableMap(methods);
  }

  public Collection < Imu > columns()
  {
    return Collections.unmodifiableCollection(columns);
  }

  public void add( final Class type )
  {
    final Method[] meth = type.getDeclaredMethods();
    final Field[] field = type.getDeclaredFields();
    final int max = Math.max(meth.length, field.length);

    for ( int i = 0; i < max; i ++ ) {
      if ( i < meth.length )
        add(meth[i]);
      if ( i < field.length )
        add(field[i]);
    }
  }

  private void add( final Method method )
  {
    final Poi[] poi;
    final String name;

    if ( method.getParameterCount() > 0 )
      return;

    poi  = method.getAnnotationsByType(Poi.class);
    name = method.getName();

    if ( poi.length > 0 ) {
      final Method usable = methods.getOrDefault(name, method);
      annotatedMethods.put(usable, poi);

      for ( final Poi p : poi ) {
        columns.add(new MethodRoot(p, usable));
      }
    }
    if ( !methods.containsKey(name) )
      methods.put(name, method);
  }

  private void add( final Field field )
  {
    final Poi[] ans = field.getAnnotationsByType(Poi.class);

    if ( ans.length > 0 ) {
      annotatedFields.put(field, ans);
      for ( final Poi p : ans ) {
        columns.add(new FieldRoot(p, field, this));
      }
    }
  }
}
