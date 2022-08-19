package indomarco.util;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Convert {

    public static String formatTanggal(LocalDate localDate){
        return localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }

    public static String rupiah(int uang){
        NumberFormat indo = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        return indo.format(uang);
    }
}
