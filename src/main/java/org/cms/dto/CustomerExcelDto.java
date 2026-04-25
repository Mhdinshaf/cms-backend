package org.cms.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerExcelDto {

    @ExcelProperty("Name")
    private String name;

    @ExcelProperty("NIC")
    private String nic;

    @ExcelProperty("DOB")
    private String dob; 

    @ExcelProperty("Mobile 1")
    private String mobile1;

    @ExcelProperty("Mobile 2")
    private String mobile2;

    @ExcelProperty("Address 1 City")
    private String address1City;

    @ExcelProperty("Address 2 City")
    private String address2City;
}
