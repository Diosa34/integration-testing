package com.github.diosa;

import com.github.diosa.base.Decomposable;
import com.github.diosa.csv.Csv;
import com.github.diosa.logarithmic.Ln;
import com.github.diosa.logarithmic.Log;
import com.github.diosa.trigonometric.*;

import java.io.IOException;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws IOException {
        final Cos cos = new Cos();
        String pathToCsv = "C:\\Users\\Diosa\\IdeaProjects\\Integration testing\\src\\main\\resources\\csv";
        Csv.write(
                pathToCsv + "\\cos.csv",
                cos,
                new BigDecimal(-3),
                new BigDecimal(3),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001"));

        final Sin sin = new Sin();
        Csv.write(
                pathToCsv + "\\sin.csv",
                sin,
                new BigDecimal(-3),
                new BigDecimal(3),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001"));

        final Tg tg = new Tg();
        Csv.write(
                pathToCsv + "\\tg.csv",
                tg,
                new BigDecimal(-1),
                new BigDecimal(1),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001"));

        final Ctg ctg = new Ctg();
        Csv.write(
                pathToCsv + "\\ctg.csv",
                ctg,
                BigDecimal.valueOf(0.1),
                BigDecimal.valueOf(3.1),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001"));

        final Sec sec = new Sec();
        Csv.write(
                pathToCsv + "\\sec.csv",
                sec,
                new BigDecimal(-1),
                new BigDecimal(1),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001"));

        final Csc csc = new Csc();
        Csv.write(
                pathToCsv + "\\csc.csv",
                csc,
                new BigDecimal(-1),
                BigDecimal.valueOf(-0.1),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001"));

        final Ln ln = new Ln();
        Csv.write(
                pathToCsv + "\\ln.csv",
                ln,
                BigDecimal.valueOf(0.1),
                new BigDecimal(5),
                new BigDecimal("0.1"),
                new BigDecimal("0.0000000001"));

        final Log log2 = new Log(2);
        Csv.write(
                pathToCsv + "\\log2.csv",
                log2,
                new BigDecimal(1),
                new BigDecimal(20),
                new BigDecimal("0.1"),
                new BigDecimal("0.00000000001"));

        final Log log3 = new Log(3);
        Csv.write(
                pathToCsv + "\\log3.csv",
                log3,
                new BigDecimal(1),
                new BigDecimal(20),
                new BigDecimal("0.1"),
                new BigDecimal("0.00000000001"));

        final Log log5 = new Log(5);
        Csv.write(
                pathToCsv + "\\log5.csv",
                log5,
                new BigDecimal(1),
                new BigDecimal(20),
                new BigDecimal("0.1"),
                new BigDecimal("0.00000000001"));

        final Log log10 = new Log(10);
        Csv.write(
                pathToCsv + "\\log10.csv",
                log10,
                new BigDecimal(1),
                new BigDecimal(20),
                new BigDecimal("0.1"),
                new BigDecimal("0.00000000001"));

        final Decomposable func = new SystemOfFunctions();
        Csv.write(
                pathToCsv + "\\func.csv",
                func,
                BigDecimal.valueOf(-3),
                BigDecimal.valueOf(-0.1),
                new BigDecimal("0.1"),
                new BigDecimal("0.00000000001"));
    }
}