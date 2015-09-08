package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table;

import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.index;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.WebDriverByUtils.fillByTemplate;
import static java.lang.String.format;

/**
 * Created by 12345 on 26.10.2014.
 */
public class Rows extends TableLine {
    public Rows() {
        haveHeader = false;
        elementIndex = ElementIndexType.Nums;
    }

    protected By columnTemplate = By.xpath(".//tr/td[%s]");
    protected By rowsHeadersTemplate = By.xpath(".//th");
    protected List<WebElement> getHeadersAction() {
        return table.getWebElement().findElements(rowsHeadersTemplate);
    }
    protected List<WebElement> getColumnAction(int colNum) {
        return table.getWebElement().findElements(fillByTemplate(columnTemplate, colNum));
    }

    private RuntimeException throwRowsException(String rowName, Exception ex) {
        return asserter.exception(format("Can't Get Rows '%s'. Exception: %s", rowName, ex));
    }

    public final MapArray<String, ICell> getColumn(String colName) {
        try {
            String[] headers = headers();
            List<WebElement> webColumn = getColumnAction(index(headers, colName));
            return new MapArray<>(count(),
                    key -> headers[key],
                    value -> table.cell(webColumn.get(value), new Column(colName), new Row(headers[value])));
        }
        catch (Exception ex) { throw throwRowsException(colName, ex); }
    }
    public final MapArray<String, String> getColumnAsText(String colName) {
        return getColumn(colName).toMapArray(IText::getText);
    }

    public MapArray<String, ICell> cellsToColumn(Collection<ICell> cells) {
        return new MapArray<>(cells,
                cell -> headers()[cell.rowNum() - 1],
                cell -> cell);
    }

    public final MapArray<String, ICell> getColumn(int colNum) {
        int rowsCount = -1;
        if (count > 0)
            rowsCount = count;
        else if (headers != null && (headers.length > 0))
            rowsCount = headers.length;
        if (rowsCount == -1)
            rowsCount = headers().length;
        if (rowsCount > 0 && rowsCount < colNum)
            throw asserter.exception(format("Can't Get Row '%s'. [num] > RowsCount(%s).", colNum, rowsCount));
        try {
            return new MapArray<>(count(),
                    rowNum -> headers()[rowNum],
                    rowNum -> table.cell(new Column(colNum), new Row(rowNum + 1)));
        }
        catch (Exception ex) { throw throwRowsException(colNum + "", ex); }
    }
    public final MapArray<String, String> getColumnAsText(int colNum) {
        return getColumn(colNum).toMapArray(IText::getText);
    }

    public MapArray<String, MapArray<String, ICell>> get() {
        return new MapArray<>(headers(), key -> key, value -> table.rows().getColumn(value));
    }
}