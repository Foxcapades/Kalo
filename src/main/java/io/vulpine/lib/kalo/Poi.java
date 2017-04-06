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

import java.lang.annotation.*;

/**
 * = Poi
 *
 * Used to annotate fields and methods on classes that are to be exported to
 * a spreadsheet when run through {@link Kalo}
 *
 * @author mailto:elliefops@gmail.com[Ellie Harper]
 * @version 1
 * @since v0.1.0 - 2017-03-26
 */
@Documented
@Inherited
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention( RetentionPolicy.RUNTIME )
public @interface Poi
{
  /**
   * Column Index
   *
   * Used to give a specific order to columns in the output spreadsheet.
   *
   * Without this specified columns will be added in the order they are returned
   * by {@link Class#getDeclaredMethods()} and {@link Class#getDeclaredFields()}
   */
  int index() default -1;

  /**
   * Column Header
   *
   * Used to provide a specific string to use as header text for a given
   * property.
   *
   * If left blank the field or method name will be parsed into a header.
   */
  String header() default "";

  Type type() default Type.AUTO;

  enum Type {
    AUTO(null),
    BLANK(CellType.BLANK),
    BOOLEAN(CellType.BOOLEAN),
    ERROR(CellType.ERROR),
    FORMULA(CellType.FORMULA),
    NUMERIC(CellType.NUMERIC),
    STRING(CellType.STRING);

    public final CellType translation;

    Type( final CellType translation )
    {
      this.translation = translation;
    }
  }
}
