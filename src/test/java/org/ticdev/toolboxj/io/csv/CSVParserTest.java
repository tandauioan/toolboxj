package org.ticdev.toolboxj.io.csv;

import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * CSVParser functional tests.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class CSVParserTest {

    /**
     * Test builder setters and getters
     */
    @Test
    public void testCSVParserBuilderSettersGetters() {

        CSVParserBuilder builder = CSVParserBuilder.newInstance();

        Character[] separators = { ',', '\t', '-' };

        for (char separator : separators) {
            builder.addDelimiter(separator);
        }

        Assert.assertEquals(Arrays.asList(separators),
            builder.getDelimiters());

        Assert.assertNull(builder.getTextDelimiter());

        Character textDelimiter = '"';

        Assert.assertEquals(textDelimiter,
            builder.setTextDelimiter(textDelimiter).getTextDelimiter());

        Assert.assertEquals(CSVParserBuilder.MAX_FIELD_SIZE,
            builder.getMaxFieldSize());

        Assert.assertEquals(CSVParserBuilder.MAX_FIELDS_PER_RECORD,
            builder.getMaxFieldsPerRecord());

        Assert.assertEquals(CSVParserBuilder.MAX_RECORD_CHAR_SIZE,
            builder.getMaxRecordCharSize());

        int maxFieldSize = 10;

        int maxFieldsPerRecord = 11;

        int maxRecordCharSize = 12;

        Assert.assertEquals(maxFieldSize,
            builder.setMaxFieldSize(maxFieldSize).getMaxFieldSize());

        Assert.assertEquals(maxFieldsPerRecord,
            builder.setMaxFieldsPerRecord(maxFieldsPerRecord)
                .getMaxFieldsPerRecord());

        Assert.assertEquals(maxRecordCharSize,
            builder.setMaxRecordCharSize(maxRecordCharSize)
                .getMaxRecordCharSize());

    }

    /**
     * Takes an array of arrays of String and returns a list of lists of
     * strings.
     * 
     * @param arr
     *            the array
     * @return the list
     */
    private static List<List<String>>
        string_array_to_list_(String[][] arr) {
        List<List<String>> result = new LinkedList<>();
        for (String[] sarr : arr) {
            result.add(Arrays.asList(sarr));
        }
        return result;
    }

    /**
     * Test the text delimited parser
     */
    @Test
    public void testTextDelimited() {

        char delimiter = ',';

        Character textDelimiter = '"';

        String multiLineEOL = "\n";

        /* regular records */
        String str = "\"1,2\n3\",4,\"\n\"\n\"5\"";

        String[][] str_expected = {
            { "1,2\n3", "4", "\n" },
            { "5" }
        };

        List<List<String>> expected = string_array_to_list_(str_expected);

        List<List<String>> actual;

        try {
            actual = CSVSupport.parseReader(new StringReader(str),
                delimiter, textDelimiter, multiLineEOL);
            Assert.assertEquals(expected, actual);
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }

        /* last line empty "" record */
        /* regular records */
        str = "\"1,2\n3\",4,\"\n\"\n\"\"";

        str_expected = new String[][] {
            { "1,2\n3", "4", "\n" },
            { "" }
        };

        expected = string_array_to_list_(str_expected);

        try {
            actual = CSVSupport.parseReader(new StringReader(str),
                delimiter, textDelimiter, multiLineEOL);
            Assert.assertEquals(expected, actual);
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }

    }

    /**
     * Test the non-text delimited parser
     */
    @Test
    public void testNonTextDelimited() {

        char delimiter = ',';

        String multiLineEOL = "\n";

        /* regular records */
        String str = "1,2,3\n4\n5,6";

        String[][] str_expected = {
            { "1", "2", "3" },
            { "4" },
            { "5", "6" }
        };

        List<List<String>> expected = string_array_to_list_(str_expected);

        List<List<String>> actual;

        try {
            actual = CSVSupport.parseReader(new StringReader(str),
                delimiter, null, multiLineEOL);
            Assert.assertEquals(expected, actual);
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }

        /* ending with empty line */
        str = "1,2,3\n4\n5,6\n";

        str_expected = new String[][] {
            { "1", "2", "3" },
            { "4" },
            { "5", "6" }
        };

        expected = string_array_to_list_(str_expected);

        try {
            actual = CSVSupport.parseReader(new StringReader(str),
                delimiter, null, multiLineEOL);
            Assert.assertEquals(expected, actual);
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }

        /* contains empty fields */
        str = "1,,3\n4\n5,6,";

        str_expected = new String[][] {
            { "1", "", "3" },
            { "4" },
            { "5", "6", "" }
        };

        expected = string_array_to_list_(str_expected);

        try {
            actual = CSVSupport.parseReader(new StringReader(str),
                delimiter, null, multiLineEOL);
            Assert.assertEquals(expected, actual);
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }

    }

}
