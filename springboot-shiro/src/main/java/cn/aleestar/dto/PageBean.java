package cn.aleestar.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageBean {

    private Integer total;
    private List rows;

    public PageBean(Integer total, List rows) {
        this.total = total;
        this.rows = rows;
    }
}
