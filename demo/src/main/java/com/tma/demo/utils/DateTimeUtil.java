package com.tma.demo.utils;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

/*For datetime value, using Joda time to get the value with UTC timezone,
 declare a DateTimeUtil to reusable and minimum changing when needed.
 * */
@Getter
@Setter
public class DateTimeUtil {
    private DateTime dateTime;

    public DateTimeUtil() {
        this.dateTime = new DateTime(DateTimeZone.UTC);
    }
}
