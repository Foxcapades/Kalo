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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class WorkbookUtil
{
  public static void parseHeaders(
    final Sheet sheet,
    final PropertyAggregator map,
    final Collection < Imu > columns,
    final CellStyle headerStyle
  )
  {
    final Row row = sheet.createRow(0);
    int index = 0;

    for ( final Imu imu : columns ) {
      final Cell cell = row.createCell(index);

      cell.setCellType(PoiUtil.translateType(imu));
      cell.setCellStyle(headerStyle);
      cell.setCellValue(imu.getName());

      index++;
    }
  }

  public static Collection < Imu > sortColumns( final Collection < Imu > in )
  {
    final List < Imu > list = new ArrayList <>(in.size());

    for ( final Imu imu : in ) {

      final Poi p = imu.getAnnotation();

      if ( p.index() == -1 ) {
        list.add(imu);
      } else {
        list.add(p.index(), imu);
      }
    }

    return list;
  }
}
