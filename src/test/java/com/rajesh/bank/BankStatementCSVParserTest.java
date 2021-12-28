package com.rajesh.bank;

import java.time.LocalDate;
import java.time.Month;

import com.rajesh.bank.core.BankStatementCSVParser;
import com.rajesh.bank.core.BankStatementParser;
import com.rajesh.bank.core.BankTransaction;

import org.junit.Assert;
import org.junit.Test;

public class BankStatementCSVParserTest {
    private final BankStatementParser statementParser = new BankStatementCSVParser();

    @Test
    public void shouldParseOneCorrectLine() throws Exception {
        final String line = "01/11/21  ,UPI-JAGDAMBA MEDICALS-9900420080-1@OKBIZAXIS-UTIB0000000-130541242490-NA,01/11/21 ,10.00,0.00,0000130541242490,326447.24";
        final BankTransaction result = statementParser.parseFrom(line);

        final BankTransaction expected = new BankTransaction(LocalDate.of(2021, Month.NOVEMBER, 01), -10.00,
                "UPI-JAGDAMBA MEDICALS-9900420080-1@OKBIZAXIS-UTIB0000000-130541242490-NA");
        final double tolerance = 0.0d;

        Assert.assertEquals(expected.getDate(), result.getDate());
        Assert.assertEquals(expected.getAmount(), result.getAmount(), tolerance);
        Assert.assertEquals(expected.getDescription(), result.getDescription());

    }

}
