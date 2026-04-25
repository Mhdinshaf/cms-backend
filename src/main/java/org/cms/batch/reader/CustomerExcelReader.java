package org.cms.batch.reader;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import org.cms.dto.CustomerExcelDto;
import org.springframework.batch.item.ItemReader;

import java.util.LinkedList;
import java.util.Queue;

public class CustomerExcelReader implements ItemReader<CustomerExcelDto>, ReadListener<CustomerExcelDto> {


    private final Queue<CustomerExcelDto> buffer = new LinkedList<>();
    private boolean isFinished = false;


    public CustomerExcelReader(String filePath) {
        EasyExcel.read(filePath, CustomerExcelDto.class, this).sheet().doRead();
    }


    @Override
    public void invoke(CustomerExcelDto data, AnalysisContext context) {
        buffer.add(data);
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        isFinished = true;
    }


    @Override
    public CustomerExcelDto read() {
        if (!buffer.isEmpty()) {
            return buffer.poll();
        } else if (isFinished) {
            return null;
        }
        return null;
    }
}
