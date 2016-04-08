package org.ticdev.toolboxj.io.csv;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test methods for just inside/outside the boundary limits for the maximim line
 * size for a record, maximum number of characters in a field, and maximum
 * number of fields per record.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class CSVParserTestLimits {

    /**
     * maxRecordCharSize boundary test, inside boundary
     */
    @Test
    public void test_textDelimited_maxRecordCharSize_pass() {

        String[] fields = { "\"1\n2\"", "3" };
        char delimiter = ',';
        char textDelimiter = '"';
        String eol = "\n";
        String stringToParse = String.join(delimiter + "", fields);
        CSVParser parser = CSVParserBuilder.newInstance()
            .setMaxRecordCharSize(stringToParse.length())
            .addDelimiter(delimiter).setTextDelimiter(textDelimiter)
            .setMultiLineEOL(eol).build();
        try {
            List<String> record =
                parser.parseRecord(new StringReader(stringToParse), null);
            Assert.assertEquals(fields.length, record.size());
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * maxRecordCharSize boundary test, inside the boundary
     */
    @Test
    public void test_nonTextDelimited_maxRecordCharSize_pass() {
        String[] fields = { "1", "2", "3", "4", "5" };
        char delimiter = ',';
        String stringToParse = String.join(",", fields);
        CSVParser parser = CSVParserBuilder.newInstance()
            .setMaxRecordCharSize(fields.length).addDelimiter(delimiter)
            .build();
        try {
            List<String> record =
                parser.parseRecord(new StringReader(stringToParse), null);
            Assert.assertEquals(fields.length, record.size());
        } catch (CSVParserLineTooLongException ex) {
            Assert.fail(ex.toString());
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * maxRecordCharSize boundary test, outside the boundary
     */
    @Test
    public void test_textDelimited_maxRecordCharSize_fail() {

        String[] fields = { "\"1\n2\"", "3" };
        char delimiter = ',';
        char textDelimiter = '"';
        String eol = "\n";
        String stringToParse = String.join(delimiter + "", fields);
        /*
         * there are 6 characters, but once we remove double-quotes we're left
         * with 4
         */
        int limit = 3;
        CSVParser parser = CSVParserBuilder.newInstance()
            .setMaxRecordCharSize(limit)
            .addDelimiter(delimiter).setTextDelimiter(textDelimiter)
            .setMultiLineEOL(eol).build();
        try {
            List<String> record =
                parser.parseRecord(new StringReader(stringToParse), null);
            Assert.fail(String.format("Limit char: %d. Records: %d", limit,
                record.size()));
        } catch (CSVParserLineTooLongException ex) {
            Assert.assertEquals(limit, ex.getRestriction());
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * maxRecordCharSize boundary test, outside the boundary
     */
    @Test
    public void test_nonTextDelimited_maxRecordCharSize_fail() {
        String[] fields = { "1", "2", "3", "4", "5" };
        char delimiter = ',';
        String stringToParse = String.join(",", fields);
        int limit = fields.length - 1;
        CSVParser parser = CSVParserBuilder.newInstance()
            .setMaxRecordCharSize(limit).addDelimiter(delimiter).build();
        try {
            List<String> record =
                parser.parseRecord(new StringReader(stringToParse), null);
            Assert.fail(String.format("Limit char: %d. Rcords: %d", limit,
                record.size()));
        } catch (CSVParserLineTooLongException ex) {
            Assert.assertEquals(limit, ex.getRestriction());
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * maxFieldSize bounary test, inside the boundary
     */
    @Test
    public void test_textDelimited_maxFieldSize_pass() {
        String[] fields = { "\"1\n2\"", "3" };
        char delimiter = ',';
        char textDelimiter = '"';
        String eol = "\n";
        String stringToParse = String.join(delimiter + "", fields);
        int limit = 3;
        CSVParser parser = CSVParserBuilder.newInstance()
            .setMaxFieldSize(limit).addDelimiter(delimiter)
            .setTextDelimiter(textDelimiter).setMultiLineEOL(eol).build();
        try {
            List<String> record =
                parser.parseRecord(new StringReader(stringToParse), null);
            Assert.assertEquals(fields.length, record.size());
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * maxFieldSize boundary test, inside the boundary
     */
    @Test
    public void test_nonTextDelimited_maxFieldSize_pass() {
        String[] fields = { "123", "2345" };
        int limit = 0;
        for (String field : fields) {
            if (limit < field.length()) {
                limit = field.length();
            }
        }
        char delimiter = ',';
        String stringToParse = String.join(",", fields);
        CSVParser parser = CSVParserBuilder.newInstance()
            .setMaxFieldSize(limit).addDelimiter(delimiter)
            .build();
        try {
            List<String> record =
                parser.parseRecord(new StringReader(stringToParse), null);
            Assert.assertEquals(fields.length, record.size());
        } catch (CSVParserFieldTooLargeException ex) {
            Assert.fail(ex.toString());
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * maxFieldSize bounary test, outside the boundary
     */
    @Test
    public void test_textDelimited_maxFieldSize_fail() {
        String[] fields = { "\"1\n2\"", "3" };
        char delimiter = ',';
        char textDelimiter = '"';
        String eol = "\n";
        String stringToParse = String.join(delimiter + "", fields);
        int limit = 2;
        CSVParser parser = CSVParserBuilder.newInstance()
            .setMaxFieldSize(limit).addDelimiter(delimiter)
            .setTextDelimiter(textDelimiter).setMultiLineEOL(eol).build();
        try {
            List<String> record =
                parser.parseRecord(new StringReader(stringToParse), null);
            Assert.fail(String.format("Limit char: %d. Records: %d", limit,
                record.size()));
        } catch (CSVParserFieldTooLargeException ex) {
            Assert.assertEquals(limit, ex.getRestriction());
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * maxFieldSize boundary test, outside the boundary
     */
    @Test
    public void test_nonTextDelimited_maxFieldSize_fail() {
        String[] fields = { "123", "2345" };
        int limit = 0;
        for (String field : fields) {
            if (limit < field.length()) {
                limit = field.length();
            }
        }
        limit = limit - 1;
        char delimiter = ',';
        String stringToParse = String.join(",", fields);
        CSVParser parser = CSVParserBuilder.newInstance()
            .setMaxFieldSize(limit).addDelimiter(delimiter)
            .build();
        try {
            List<String> record =
                parser.parseRecord(new StringReader(stringToParse), null);
            Assert.fail(String.format(
                "Limit: %d. Should have failed for at least: %d", limit,
                record.stream().map(s -> s.length())
                    .max(Integer::compare).get()));
        } catch (CSVParserFieldTooLargeException ex) {
            Assert.assertEquals(limit, ex.getRestriction());
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * maxFieldsPerRecord boundary test, inside the boundary
     */
    @Test
    public void test_textDelimited_maxFieldsPerRecord_pass() {
        String[] fields = { "\"1\n2\"", "3" };
        int limit = fields.length;
        char delimiter = ',';
        char textDelimiter = '"';
        String eol = "\n";
        String stringToParse = String.join(delimiter + "", fields);
        CSVParser parser = CSVParserBuilder.newInstance()
            .setMaxFieldsPerRecord(limit).addDelimiter(delimiter)
            .setTextDelimiter(textDelimiter).setMultiLineEOL(eol).build();
        try {
            List<String> record =
                parser.parseRecord(new StringReader(stringToParse), null);
            Assert.assertEquals(fields.length, record.size());
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * maxFieldsPerRecord boundary test, inside the boundary
     */
    @Test
    public void test_nonTextDelimited_maxFieldsPerRecord_pass() {
        String[] fields = { "1", "2", "3" };
        int limit = fields.length;
        char delimiter = ',';
        String stringToParse = String.join(delimiter + "", fields);
        CSVParser parser = CSVParserBuilder.newInstance()
            .setMaxFieldsPerRecord(limit).addDelimiter(delimiter)
            .build();
        try {
            List<String> record =
                parser.parseRecord(new StringReader(stringToParse), null);
            Assert.assertEquals(fields.length, record.size());
        } catch(CSVParserException |
            CSVParserLineTooLongException |
            CSVParserTooManyFieldsException |
            IOException |
            InterruptedException ex) {
                Assert.fail(ex.toString());
        } catch(Exception ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * maxFieldsPerRecord boundary test, inside the boundary
     */
    @Test
    public void test_textDelimited_maxFieldsPerRecord_fail() {
        String[] fields = { "\"1\n2\"", "3" };
        int limit = fields.length - 1;
        char delimiter = ',';
        char textDelimiter = '"';
        String eol = "\n";
        String stringToParse = String.join(delimiter + "", fields);
        CSVParser parser = CSVParserBuilder.newInstance()
            .setMaxFieldsPerRecord(limit).addDelimiter(delimiter)
            .setTextDelimiter(textDelimiter).setMultiLineEOL(eol).build();
        try {
            List<String> record =
                parser.parseRecord(new StringReader(stringToParse), null);
            Assert.fail(String.format("Limit: %d. Records: %d", limit,
                record.size()));
        } catch (CSVParserTooManyFieldsException ex) {
            Assert.assertEquals(limit, ex.getRestriction());
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * maxFieldsPerRecord boundary test, outside the boundary
     */
    @Test
    public void test_nonTextDelimited_maxFieldsPerRecord_fail() {
        String[] fields = { "1", "2", "3" };
        int limit = fields.length - 1;
        char delimiter = ',';
        String stringToParse = String.join(delimiter + "", fields);
        CSVParser parser = CSVParserBuilder.newInstance()
            .setMaxFieldsPerRecord(limit).addDelimiter(delimiter)
            .build();
        try {
            List<String> record =
                parser.parseRecord(new StringReader(stringToParse), null);
            Assert.fail(String.format("Limit: %d. Actual: %d", limit,
                record.size()));
        } catch (CSVParserTooManyFieldsException ex) {
            Assert.assertEquals(limit, ex.getRestriction());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

}
