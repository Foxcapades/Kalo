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

import org.apache.poi.ss.usermodel.CellType;

class PoiUtil
{
  public static CellType translateType( final Imu imu )
  {
    final Poi.Type p = imu.getAnnotation().type();

    if ( p.translation != null )
      return p.translation;

    final Class type = ClassUtils.dePrimitive(imu.getType());

    if ( String.class.equals(type) )
      return CellType.STRING;
    if ( Number.class.isAssignableFrom(type) )
      return CellType.NUMERIC;
    if ( Boolean.class.equals(type) )
      return CellType.BOOLEAN;

    return CellType.STRING;
  }
}
