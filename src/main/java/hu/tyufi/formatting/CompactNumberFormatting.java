package hu.tyufi.formatting;

import java.text.NumberFormat;
import java.util.Locale;

public class CompactNumberFormatting {

    public static void main(String[] args) {
        int number = 2592;

        Locale localeUs = new Locale("en", "US");
        NumberFormat likesShort =
                NumberFormat.getCompactNumberInstance(localeUs, NumberFormat.Style.SHORT);
        likesShort.setMaximumFractionDigits(2);
        assert "2.59K".equals(likesShort.format(number));

        NumberFormat likesLong =
                NumberFormat.getCompactNumberInstance(localeUs, NumberFormat.Style.LONG);
        likesLong.setMaximumFractionDigits(1);
        assert "2.6 thousand".equals(likesLong.format(number));

        Locale localeHu = new Locale("hu", "HU");
        NumberFormat likesShortHu =
                NumberFormat.getCompactNumberInstance(localeHu, NumberFormat.Style.SHORT);
        likesShortHu.setMaximumFractionDigits(2);
        assert "2,59 E".equals(likesShortHu.format(number));

        NumberFormat likesLongHu =
                NumberFormat.getCompactNumberInstance(localeHu, NumberFormat.Style.LONG);
        assert "3 ezer".equals(likesLongHu.format(number));

    }
}
