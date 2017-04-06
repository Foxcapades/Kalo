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

import java.lang.reflect.AccessibleObject;

/**
 * = Imu
 *
 * Poi organizational container used internally.
 *
 * @author mailto:elliefops@gmail.com[Ellie Harper]
 * @version 1
 * @since v0.1.0 - 2017-03-26
 */
interface Imu
{
  Poi getAnnotation();

  AccessibleObject getMember();

  Object getValue( final Object inst );

  String getName();

  Class < ? > getType();
}
