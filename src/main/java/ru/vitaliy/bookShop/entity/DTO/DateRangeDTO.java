package ru.vitaliy.bookShop.entity.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class DateRangeDTO {
    private Date start;
    private Date end;
}
