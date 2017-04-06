package io.vulpine.lib.kalo;

import java.util.Collection;

/**
 * = Page
 *
 * Meta data for creating a sheet in a workbook.
 *
 * @author mailto:elliefops@gmail.com[Elizabeth Harper]
 * @version 1.0
 * @since v - 2017-04-06
 */
public class Page < T >
{
  private String sheetName;

  private Class < T > type;

  private Collection < T > rows;

  public Page(
    final String sheetName,
    final Class < T > type,
    final Collection < T > rows
  )
  {
    this.sheetName = sheetName;
    this.type = type;
    this.rows = rows;
  }

  public String getSheetName()
  {
    return sheetName;
  }

  public void setSheetName( final String sheetName )
  {
    this.sheetName = sheetName;
  }

  public Class < T > getType()
  {
    return type;
  }

  public void setType( final Class < T > type )
  {
    this.type = type;
  }

  public Collection < T > getRows()
  {
    return rows;
  }

  public void setRows( final Collection < T > rows )
  {
    this.rows = rows;
  }

  public static < R > Page< R > create(
    final String name,
    final Collection < R > rows,
    final Class < R > type
  )
  {
    return new Page<>(name, type, rows);
  }
}
