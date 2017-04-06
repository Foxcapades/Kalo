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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import static io.vulpine.lib.kalo.WorkbookUtil.sortColumns;

/**
 * = Kalo
 *
 *
 * Kalo, in combination with {@link Poi} provides methods for easily working
 * with Apache POI.
 *
 * https://poi.apache.org/[Apache POI]
 * https://en.wikipedia.org/wiki/Poi_(food)[Poi & Kalo]
 *
 * @author mailto:elliefops@gmail.com[Ellie Harper]
 * @version 1
 * @since v0.1.0 - 2017-03-26
 */
public final class Kalo
{
  /**
   * Create and populate an XSSF Workbook.
   *
   * @param items Items to serialize as rows.
   * @param type  Type definition for annotation lookups.
   * @param <T>   Type of items.
   *
   * @return Generated workbook.
   */
  public < T > Workbook xssf(
    final Collection < T > items,
    final Class < T > type
  )
  {
    return poi(items, type, new XSSFWorkbook());
  }

  /**
   * Create and populate an HSSF Workbook.
   *
   * @param items Items to serialize as rows.
   * @param type  Type definition for annotation lookups.
   * @param <T>   Type of items.
   *
   * @return Generated workbook.
   */
  public < T > Workbook hssf(
    final Collection < T > items,
    final Class < T > type
  )
  {
    return poi(items, type, new HSSFWorkbook());
  }

  /**
   * Create, populate, and write an XSSF Workbook to file.
   *
   * @param items Items to serialize as rows.
   * @param type  Type definition for annotation lookups.
   * @param <T>   Type of items.
   */
  public < T > void writeXssf(
    final File file,
    final Collection < T > items,
    final Class < T > type
  )
  throws IOException
  {
    final FileOutputStream stream;

    file.createNewFile();
    stream = new FileOutputStream(file);

    xssf(items, type).write(stream);
    stream.close();
  }

  /**
   * Create, populate, and write an HSSF Workbook to file.
   *
   * @param items Items to serialize as rows.
   * @param type  Type definition for annotation lookups.
   * @param <T>   Type of items.
   */
  public < T > void writeHssf(
    final File file,
    final Collection < T > items,
    final Class < T > type
  )
  throws IOException
  {
    final FileOutputStream stream;

    file.createNewFile();
    stream = new FileOutputStream(file);

    hssf(items, type).write(stream);
    stream.close();
  }

  public < T > Workbook poi(
    final Collection < T > items,
    final Class < T > type,
    final Workbook workbook
  )
  {
    final Sheet sheet = workbook.createSheet();
    final PropertyAggregator map = ClassUtils.parse(type);
    final Collection < Imu > sorted = sortColumns(map.columns());
    int row = 1;

    WorkbookUtil.parseHeaders(
      sheet,
      map,
      sorted,
      new PoiConfig().getHeaderStyle(workbook, null)
    );

    for ( final T item : items ) {
      final Row sheetRow = sheet.createRow(row++);
      int col = 0;
      for ( final Imu imu : sorted ) {
        final Cell cell = sheetRow.createCell(col++);
        final CellType cType = PoiUtil.translateType(imu);

        cell.setCellType(cType);
        switch ( cType ) {
          case BOOLEAN:
            cell.setCellValue((Boolean) imu.getValue(item));
            break;
          case NUMERIC:
            cell.setCellValue(((Number) imu.getValue(item)).doubleValue());
            break;
          default:
            cell.setCellValue(String.valueOf(imu.getValue(item)));
        }
      }
    }

    return workbook;
  }
}
